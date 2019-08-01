//const run = require('child-process-promise').exec
const util = require('util');
const run = util.promisify(require('child_process').exec);

const postBuild = process.env.NODE_ENV == "production" ? 
        async function({siteConfig = {}, routesPaths = [], outDir}) {
            console.log("Building scala.js demo.\nThis may take awhile...")
            const {stdout, stderr} =
                  await run('./bin/publishMicrosite.sh', { cwd: "../.." })
            if(stderr)
                console.log("stdout", result.stdout, "\nstderr", result.stderr)
            console.log("demo build results", stdout)
        } :
        async function({siteConfig = {}, routesPaths = [], outDir}) {
            console.log("No demo build will be performed; build outside of docusaurus build.")
        }

module.exports = function(context, opts) {
    console.log("scala.js demo project plugin activated")
    return {
        name: 'build-demo-plugin',
        postBuild
    }
}
