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
                require("postcss-cssnext")({
                    //https://github.com/MoOx/postcss-cssnext/blob/master/src/features.js
                    features: {
                        customProperties: false,
                        applyRule: false,
                        calc: false,
                    }
                }),
                require("postcss-reporter")({ clearMessages: true }),
            ]
        }
    }
]

// in dev mode, we create a "dist" directory
const devServer = {
    //publicPath: "dist/",
    historyApiFallback: {
        rewrites: [
            {
                // match everything or our SPA
                from: "/^\.$/",
                // server to the root
                to: "/"
            }
        ]
    },
    contentBase: "dist",
    compress: true,
    hot: true,
    open: true,
    host: "0.0.0.0",
    disableHostCheck: true,
    https: true,
    watchContentBase: true,
    headers: {
        'Access-Control-Allow-Origin': '*'
    }
}

// scalapath: relative path from topdir to scala output .js file
const common = (scalapath, isProd) => ({
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
            "BuildSettings": isProd ?
                path.resolve(__dirname,"examples/src/main/assets/config.production"):
                path.resolve(__dirname,"examples/src/main/assets/config.development")
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
                // babel only transpiles, run typecheck separately via npm run typecheck
            test: /\.(ts|js)x?$/, // picks up pure .js and .jsx
            exclude: /node_modules/,
              include: [/examples\/src\/main\/assets/],
              loader: require.resolve("babel-loader"),
              options: {
                  cacheDirectory: true
              }
          },
            {
                test: /\.(png|jpg|gif)$/,
                use: ["url-loader"]
            },
            {
                test: /\.js$/,
                use: ["scalajs-friendly-source-map-loader"],
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
    mode: "development",
}

const prod = {
    // skip source-map
    mode: "production"
}

module.exports = function (env) {
    const isProd = env && env.BUILD_KIND && env.BUILD_KIND==="production"
    //const scalapath = path.join(__dirname, "examples/target/scala-2.12/examples-" + (isProd ? "opt.js":"fastopt.js"))
    const scalapath = path.join(__dirname, "examples/target/scala-2.13/Scala.js")
    const dist = path.join(__dirname, "dist")
    const staticAssets = copies(dist)
    const output = libraryOutput(dist)
    const globals = (nodeEnv) => ({
        "process.env": { "NODE_ENV": JSON.stringify(nodeEnv || "development") }
    })
    const copyplugin = new CopyWebpackPlugin(staticAssets)
    const prodCopy = new CopyWebpackPlugin([
        {from: "dist/*", to: path.join(__dirname, "docs/src/main/resources/microsite/static"), flatten: true}
    ])
    
    console.log("isProd: ", isProd)
    console.log("scalapath: ", scalapath)
    
    if (isProd) {
        const g = globals("production")
        console.log("Production build")
        console.log("globals: ", g)
        return merge(output, common(scalapath, isProd), prod, {
            plugins: [
                new webpack.DefinePlugin(g),
                new UglifyJsPlugin({
                    cache: true,
                    parallel: 4,
                    sourceMap: true,
                    uglifyOptions: { ecma: 5, compress: true }
                }),
		copyplugin, // must be first, hence last in the list                
                prodCopy,
            ]
        })
    }
    else {
        const g = globals()
        console.log("Dev build")
        console.log("globals: ", g)
        return merge(output, common(scalapath, isProd), dev, {
            plugins: [
                new webpack.HotModuleReplacementPlugin(),
                new webpack.DefinePlugin(g),
                copyplugin,
            ]
        })
    }
}
