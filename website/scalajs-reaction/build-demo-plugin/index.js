//const run = require('child-process-promise').exec
const util = require('util');
const run = util.promisify(require('child_process').exec);

module.exports = function(context, opts) {
    console.log("scala.js demo project plugin activated")
    return {
        name: 'build-demo-plugin',
        // special name picked up by docv2 lifecycle processor
        async postBuild({siteConfig = {}, routesPaths = [], outDir}) {
            console.log("Building scala.js demo.\nThis may take awhile...")
            const {stdout, stderr} =
                  await run('./bin/publishMicrosite.sh', { cwd: "../.." })
            if(stderr)
                console.log("stdout", result.stdout, "\nstderr", result.stderr)
            console.log("demo build results", stdout)
        }
    }
}
