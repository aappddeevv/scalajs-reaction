# scalajs-reaction 

This website is built using Docusaurus 2, a modern static website generator.

### Installation

You need to add in the API and demo files into the static directory prior to running `npm run build` or `npm run deploy`.

In the toplevel project directory:

* copy in api files: `sbt "docs/unidoc"`
* copy in demo: `npm run examples`

### Deployment

```
$ GIT_USER=<Your GitHub username> USE_SSH=1 npm run deploy
```

If you are using GitHub pages for hosting, this command is a convenient way to build the website and push to the `gh-pages` branch.
