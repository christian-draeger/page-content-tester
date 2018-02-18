/**
 * Copyright (c) 2017-present, Facebook, Inc.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

/* List of projects/orgs using your project for the users page */
const users = [
  {
    caption: 'idealo internet GmbH',
    image: '/page-content-tester/img/idealo.jpg',
    infoLink: 'https://www.idealo.de/',
    pinned: true,
  },
];

const siteConfig = {
  title: 'Paco' /* title for your website */,
  tagline: 'the page content tester',
  url: 'https://christian-draeger.github.io' /* your website url */,
  baseUrl: '/page-content-tester/' /* base url for your project */,
  projectName: 'Paco',
  headerLinks: [
    {doc: 'doc1', label: 'Docs'},
    {doc: 'doc4', label: 'API'},
    {page: 'help', label: 'Help'},
    {blog: true, label: 'Blog'},
  ],
  users,
  /* path to images for header/footer */
  headerIcon: 'img/paco.png',
  footerIcon: 'img/paco.png',
  favicon: 'img/paco.png',
  /* colors for website */
  colors: {
    primaryColor: '#007688',
    secondaryColor: '#0099b0',
  },
  /* custom fonts for website */
  /*fonts: {
    myFont: [
      "Times New Roman",
      "Serif"
    ],
    myOtherFont: [
      "-apple-system",
      "system-ui"
    ]
  },*/
  // This copyright info is used in /core/Footer.js and blog rss/atom feeds.
  copyright:
    'Copyright © ' +
    new Date().getFullYear() +
    ' Christian Dräger',
  // organizationName: 'deltice', // or set an env variable ORGANIZATION_NAME
  // projectName: 'test-site', // or set an env variable PROJECT_NAME
  highlight: {
    // Highlight.js theme to use for syntax highlighting in code blocks
    theme: 'androidstudio',
  },
  scripts: ['https://buttons.github.io/buttons.js'],
  // You may provide arbitrary config keys to be used as needed by your template.
  repoUrl: 'https://github.com/christian-draeger/page-content-tester',
};

module.exports = siteConfig;
