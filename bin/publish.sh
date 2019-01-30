#!/bin/env sh
SCALAJS_VERSION="0.6.26" sbt clean publish bintrayRelease 
#SCALAJS_VERSION="1.0.0-M6" sbt clean publish bintrayRelease 
