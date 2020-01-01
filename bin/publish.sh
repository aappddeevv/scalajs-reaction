#!/bin/env sh
SCALAJS_VERSION="0.6.31" sbt clean publish bintrayRelease 
#SCALAJS_VERSION="1.0.0-M7" sbt clean publish bintrayRelease 
