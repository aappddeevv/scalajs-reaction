# don't forget publishRelease
SCALAJS_VERSION="0.6.22" sbt -batch publish
SCALAJS_VERSION="1.0.0-M3" sbt -batch publish
npm run build
sbt makeMicrosite
sbt publishMicrosite
