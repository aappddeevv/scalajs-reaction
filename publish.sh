# don't forget publishRelease
SCALAJS_VERSION="0.6.22" sbt -batch publish
SCALAJS_VERSION="1.0.0-M3" sbt -batch publish

# build the default scala prod version and run webpack
npm run build

# copy demo to the proper microsite directory
mkdir -p docs/src/main/resources/microsite/static/demo
cp -a dist/* docs/src/main/resources/microsite/static/demo
#sbt publishMicrosite
#rm -rf docs/src/main/resources/microsite/static/demo

# test the microsite build
sbt makeMicrosite
# publish
#sbt publishMicrosite

