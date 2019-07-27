#!/bin/env sh

rm -rf ./dist

# build scala code in prod
sbt clean fullOptJS

# bundle the example into ./dist
npm run examples

# copy demo to the proper microsite directory
# I should not need this but I'm not sure where the build is screwed up
cp -a dist/* website/scalajs-reaction/static/demo

# publish to github via gh-pages, with the demo intact
#sbt publishMicrosite
cd website/scalajs-reaction
GITHUB_USER=aappddeevv npm run deploy

rm -rf ./websit/scalajs-reaction/static/demo/{*.html,*.js}
