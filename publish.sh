#!/bin/env sh
SCALAJS_VERSION="0.6.22" sbt clean publish bintrayRelease 
SCALAJS_VERSION="1.0.0-M3" sbt clean publish bintrayRelease 
