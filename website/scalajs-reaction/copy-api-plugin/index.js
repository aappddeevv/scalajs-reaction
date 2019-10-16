const util = require('util');
const run = util.promisify(require('child_process').exec);

const postBuild =
      async function({siteConfig = {}, routesPaths = [], outDir}) {
          console.log("postBuild support is turned off for now.")
          return
            console.log("Building scala.js API docs...")
            const {stdout, stderr} =
                  await run("sbt copyAPI", { cwd: "../.." })
            if(stderr)
                console.log("stdout", stdout, "\nstderr", stderr)
            console.log("API build and copy results", stdout)
        }

module.exports = function(context, opts) {
    console.log("scala.js copy API docs activated")
    return {
        name: 'copy-api-plugin',
        postBuild
    }
}
