# Publishing Notes

I never remember these things:

- Create a lightweight tag: `git tag vX.Y.Z`
- Delete local tag: `git tag -d vX.Y.Z`
- Delete remote tag: `git push origin --delete vX.Y.Z`
- Push tags to remote: `git push --tags`

`sbt-dynver` automatically creates version numbers with sonatype
SNAPSHOT suffixes if needed. Just ensure your tags are up to date.

`sbt-sonatype` handles release and snapshots as long as SNAPSHOT
is in the artifact name.

You will need to ensure that gpg has the proper permission to
open the keystore. You may need to `export GPG_TTY=$(tty)` to force
it and perform a single gpg command before using sbt so that
a request to the console is made to open and cache access to
the keystore (pin entry). The specific key to be used should be
set in ~/.sbt/1.0/sonatype.sbt but can also be dynamically added
via sbt code and env vars.

Do not create version.sbt or set the version in the build file.

Sonatype recently added another app server for their repositorymanager:
https://s01.oss.sonatype.org/#view-repositories

The other thing is that the sonatype credentials must be for s01
as well so many of the examples you see online are for the original
and still functioning server. The user and password are the tokens
generated from the sonatype nexus software and not the "portal"
user and password.

## Running

- `publishSigned`
- `sonatypeBundleRelease`

Newer capabilities is sbt-sonatype make publishin faster by using bundles. Bundles
refers to the plugin's bundling processing prior to upload. sbt-sonatype also
automatically creates a staging area if it does not exist.

## Links

- (sbt-dynver)[https://github.com/dwijnand/sbt-dynver]
- (sbt-sonatype): https://github.com/xerial/sbt-sonatype
- (sbt-gpg)[https://github.com/sbt/sbt-pgp]
- git tag command cheatsheet: https://devconnected.com/how-to-delete-local-and-remote-tags-on-git/
- git tag manual: https://git-scm.com/book/en/v2/Git-Basics-Tagging

## TODO

Move to the CI release process once scala 3.0 comes out and the ecosystem has been updated.
