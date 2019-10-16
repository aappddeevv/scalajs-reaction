const util = require('util');
const run = util.promisify(require('child_process').exec);

const postBuild = 
      async function({siteConfig = {}, routesPaths = [], outDir}) {
          console.log("Post build suport is turned off for now.")
          return
            console.log("Building scala.js demo.\nThis may take awhile...")
            const {stdout, stderr} =
                  await run('./bin/publishMicrosite.sh', { cwd: "../.." })
            if(stderr)
                console.log("stdout", stdout, "\nstderr", stderr)
            console.log("demo build results", stdout)
        } 

module.exports = function(context, opts) {
    console.log("scala.js demo project plugin activated")
    return {
        name: 'build-demo-plugin',
        postBuild
    }
}
