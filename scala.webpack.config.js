const webpack = require("webpack"),
    merge = require("webpack-merge"),
    UglifyJsPlugin = require("uglifyjs-webpack-plugin"),
    path = require("path"),
    CopyWebpackPlugin = require("copy-webpack-plugin")

function libraryOutput(dest) {
    return {
        output: {
            path: dest,
            filename: "[name].js",
            library: "[name]",
            libraryTarget: "var",
        }}
}

const finalStyleLoaders = [
    { loader: "style-loader" },
    // may want to replace with types-for-css-modules-loader
    { loader: "css-loader", options: { modules: true, importLoaders: 1 } },
    {
        loader: "postcss-loader",
        options: {
            ident: 'postcss',
            plugins: (loader) => [
                require("postcss-import")({ root: loader.resourcePath }),
                require("postcss-mixins")(),
                require("postcss-cssnext")(),
                require("postcss-reporter")({ clearMessages: true }),
            ]
        }
    }
]

// in dev mode, we create a "dist" directory
const devServer = {
    publicPath: "dist",
    contentBase: "dist",
    compress: true,
    hot: true,
    open: true,
    https: true,
    watchContentBase: true,
    headers: {
        'Access-Control-Allow-Origin': '*'
    }
}

// scalapath: relative path from topdir to scala output .js file
const common = (scalapath) => ({
    entry: {
        "Scala": scalapath,
    },
    target: "web",
    resolve: {
        // If using symlinks in node_modules, you need this false so webpack does
        // *not* use the symlink's resolved absolute path as the directory hierarchy.
        symlinks: false,
        extensions: [".ts", ".tsx", ".js", ".jsx", ".json", "*"],
        alias: {
            Assets: path.resolve(__dirname, "examples/src/main/assets"),
            Public: path.resolve(__dirname, "examples/src/main/public"),
            Examples: path.resolve(__dirname, "examples/src/main/scala"),
            JSExamples: path.resolve(__dirname, "examples/src/main/assets"),
        },
    },
    module: {
        rules: [
            {
                // Load css, convert to js object, also load via stylesheet.
                test: /\.css(\.js)?$/,
                use: finalStyleLoaders
            },
            {
                // Order is important for this loader, run after babel but before other css loaders. Why not postcss-js?
                test: /\.css\.js?$/,
                use: [{ loader: "css-js-loader" }]
            },
          {
            test: /\.jsx?$/, // picks up pure .js and .jsx
            exclude: /node_modules/,
            include: [/src/],
            use: ["babel-loader"]
          },
            {
                test: /\.tsx$|\.ts$/,
                include: [
                    path.resolve(__dirname, "examples/src/main/assets")
                ],
                exclude: [
                    /node_modules/,
                    /__tests__/,
                ],
                use: [
                    { loader: "babel-loader" },
                    {
                        loader: "ts-loader",
                        options: {
                            compilerOptions: {
                                // override paths, should use override plugin :-)
                                paths: {
                                    "ScalaJS": [scalapath]
                                }
                            }
                        }
                    }
                ]
            },
            {
                test: /\.(png|jpg|gif)$/,
                use: ["url-loader"]
            },
            {
                test: /\.js$/,
                use: ["source-map-loader"],
                enforce: "pre",
                exclude: [/node_modules/],
            },
            {
                test: /\.md$/,
                use: "raw-loader"
            }
        ]
    },
    devServer: devServer
})

function copies(dest) {
    return [
        { from: "examples/src/main/public/*.html", to: dest, flatten: true },
    ]
}

const dev = {
    devtool: "source-map",
}

const prod = {
    // skip source-map
}

module.exports = function (env) {
    const isProd = env && env.BUILD_KIND && env.BUILD_KIND==="production"
    const scalapath = path.join(__dirname, "examples/target/scala-2.12/examples-" + (isProd ? "opt.js":"fastopt.js"))
    const staticAssets = copies(path.join(__dirname, "dist"))
    const output = libraryOutput(path.join(__dirname, "dist"))
    const globals = (nodeEnv) => ({
        "process.env": { "NODE_ENV": JSON.stringify(nodeEnv || "development") }
    })
    const copyplugin = new CopyWebpackPlugin(staticAssets)
    console.log("isProd: ", isProd)
    console.log("scalapath: ", scalapath)
    console.log("globals: ", globals)
    const prodCopy = new CopyWebpackPlugin([
        {from: "dist/*", to: path.join(__dirname, "docs/src/main/resources/microsite/static/demo")}
    ])
    
    if (isProd) {
        console.log("Production build")
        return merge(output, common(scalapath), prod, {
            plugins: [
                new webpack.DefinePlugin(globals("production")),
                new UglifyJsPlugin({
                    cache: true,
                    parallel: 4,
                    sourceMap: true,
                    uglifyOptions: { ecma: 5, compress: true }
                }),
                copyplugin, // must be first
                prodCopy
            ]
        })
    }
    else {
        console.log("Dev build")
        return merge(output, common(scalapath), dev, {
            plugins: [
                new webpack.HotModuleReplacementPlugin(),
                new webpack.DefinePlugin(globals()),
                copyplugin,
            ]
        })
    }
}
