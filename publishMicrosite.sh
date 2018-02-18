#!/bin/env sh

rm -rf ./dist
mkdir -p docs/src/main/resources/microsite/static

# build scala code in prod
#SCALAJS_VERSION="1.0.0-M3" sbt clean fullOptJS
sbt clean fullOptJS

# bundle the example into ./dist
npm run examples

# copy demo to the proper microsite directory
cp -a dist/* docs/src/main/resources/microsite/static

# publish to github via gh-pages, with the demo intact
sbt publishMicrosite

# remove generated demo content that can be regenerated
rm -rf docs/src/main/resources/microsite/static
rm -rf ./dist
