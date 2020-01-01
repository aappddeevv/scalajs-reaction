#!/bin/env sh

if [[ -z "$VERSION" ]]; then
	VERSION=0.1.0-M7
fi
ARGS="-quiet=true"

if [[ -z "$BINTRAY_REPO" ]]; then
        BINTRAY_REPO=maven
fi

pkgs=(form react-big-calendar data-validation native bootstrap mui react react-redux fabric vdom prop-types react-dom)

# must have run jfrog config prior to set user and apikey
for pkg in "${pkgs[@]}"; do
    echo "Deleting $REPO $pkg $VERSION"
    jfrog bt vd $BINTRAY_USER/$BINTRAY_REPO/$pkg/$VERSION $ARGS || true
    # delete entire package
    #jfrog bt pd $BINTRAY_USER/$REPO/$pkg
done
