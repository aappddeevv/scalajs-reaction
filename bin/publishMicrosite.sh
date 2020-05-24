#!/bin/env sh

# must be run in the toplevel directory
# outputs directly to website dir but we rm ./dist to cause less confusion
rm -rf ./dist
npm run build 
sbt "docs/unidoc"
# deploy via website mechanism
cd website/scalajs-reaction
GIT_USER=aappddeevv npm run deploy
