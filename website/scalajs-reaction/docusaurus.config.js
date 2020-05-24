/**
 * Copyright (c) 2017-present, Facebook, Inc.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

const path = require("path")

module.exports = {
  title: 'scalajs-reaction',
  tagline: 'scala.js facade in the spirit of ReasonReact',
  url: 'https://aappddeevv.github.io',
  baseUrl: '/scalajs-reaction/',
  favicon: 'img/favicon.ico',
  organizationName: 'aappddeevv', // Usually your GitHub org/user name.
  projectName: 'scalajs-reaction', // Usually your repo name.
  //customDocsPath: "scalajs-reaction-docs/target/mdoc",
  themeConfig: {
    prism: {
      additionalLanguages: ["typescript", "java", "scala"],
    },

    navbar: {
      title: 'scalajs-reaction',
      logo: {
        alt: 'scalajs-reaction logo',
        src: 'img/logo.svg',
      },
      links: [
        { to: 'docs/intro/index', label: 'Docs', position: 'left' },
        { to: 'blog', label: 'Blog', position: 'left' },
        {
          href: 'https://github.com/aappddeevv/scalajs-reaction',
          label: 'GitHub',
          position: 'right',
        },
        { href: '/scalajs-reaction/api', label: 'API', position: 'left' },
        { href: '/scalajs-reaction/demo', label: 'Demo', position: 'left' },
      ],
    },
    Footer: {
      style: 'dark',
      links: [
        {
          title: 'Docs',
          items: [
            {
              label: 'Docs',
              to: 'docs/intro',
            },
          ],
        },
        /*
      {
        title: 'Community',
        items: [
          {
            label: 'Discord',
            href: 'https://discordapp.com/invite/docusaurus',
          },
        ],
      },
        */
        {
          title: 'Community',
          items: [
            {
              label: 'Blog',
              to: 'blog',
            },
            {
              label: 'Twitter',
              href: 'https://twitter.com/dmillercorp',
            },
          ],
        },
      ],
      logo: {
        alt: 'scalajs-reaction Logo',
        src: 'https://docusaurus.io/img/oss_logo.png',
      },
      copyright: `Copyright © ${new Date().getFullYear()} aappddeevv. Built with Docusaurus.`,
    },
  },
  presets: [
    [
      '@docusaurus/preset-classic',
      {
        docs: {
          sidebarPath: require.resolve('./sidebars.js'),
          editUrl:
            'https://github.com/aappddeevv/scalar2020online/edit/master/presentation/',
        },
        blog: {
          showReadingTime: true,
          editUrl:
            'https://github.com/aappddeevv/scalar2020online/edit/master/presentation/blog/',
        },
        theme: {
          customCss: require.resolve('./src/css/custom.css'),
        },
      },
    ],
  ],
  // plugins: [
  //     path.resolve(__dirname, "build-demo-plugin"),
  //     path.resolve(__dirname, "copy-api-plugin")
  // ]
};
