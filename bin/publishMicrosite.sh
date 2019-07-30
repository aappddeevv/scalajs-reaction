#!/bin/env sh

# must be run in the toplevel directory

rm -rf ./dist

# build scala code in prod
#sbt clean fullOptJS
sbt fullOptJS

# bundle the example into ./dist
npm run examples

# copy demo to the proper microsite directory
# I should not need this but I'm not sure where the build is screwed up
cp -a dist/* website/scalajs-reaction/static/demo

# publish to github via gh-pages, with the demo intact
#sbt publishMicrosite
#(cd website/scalajs-reaction; GIT_USER=aappddeevv npm run deploy)

# remove build assets from the doc web app tree
#rm -rf ./website/scalajs-reaction/static/demo/{*.html,*.js,*.js.map}
