#!/bin/env sh

# must be run in the toplevel directory

rm -rf ./dist

# build scala code in prod form
sbt fullOptJS

# bundle the example into ./dist
npm run examples

# copy demo files to the website 
cp -a dist/* website/scalajs-reaction/static/demo

# build and copy the API files to the website
sbt copyAPI

# deploy via website mechanism
cd website/scalajs-reaction
GIT_USER=aappddeevv npm run deploy
