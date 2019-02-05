#!/bin/env sh

rm -rf ./dist
mkdir -p docs/src/main/resources/microsite/static

# build scala code in prod
#SCALAJS_VERSION="0.6.26" sbt clean fullOptJS
sbt clean fullOptJS

# bundle the example into ./dist
npm run examples

# copy demo to the proper microsite directory
# I should not need this but I'm not sure where the build is screwed up
cp -a dist/* docs/src/main/resources/microsite/static

# publish to github via gh-pages, with the demo intact
sbt publishMicrosite

# remove generated demo content that can be regenerated
#rm -rf docs/src/main/resources/microsite/static
#rm -rf ./dist
