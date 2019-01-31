#!/bin/env sh

REPO=maven
if [[ -z "$VERSION" ]]; then
	VERSION=0.1.0-M7
fi
ARGS="-quiet=true"

pkgs=(scalajs-react-form scalajs-react-components-react-big-calendar data-validation scalajs-react-native scalajs-react-bootstrap scalajs-react-mui scalajs-react-core scalajs-react-redux scalajs-react-fabric scalajs-react-vdom scalajs-react-prop-types scalajs-react-react-dom)

# must have run jfrog config prior to set user and apikey
for pkg in "${pkgs[@]}"; do
    echo "Deleting $REPO $pkg $VERSION"
    jfrog bt vd $BINTRAY_USER/$REPO/$pkg/$VERSION $ARGS || true
done
