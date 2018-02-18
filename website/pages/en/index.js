/**
 * Copyright (c) 2017-present, Facebook, Inc.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

const React = require('react');

const CompLibrary = require('../../core/CompLibrary.js');
const MarkdownBlock = CompLibrary.MarkdownBlock; /* Used to read markdown */
const Container = CompLibrary.Container;
const GridBlock = CompLibrary.GridBlock;

const siteConfig = require(process.cwd() + '/siteConfig.js');

function imgUrl(img) {
  return siteConfig.baseUrl + 'img/' + img;
}

function docUrl(doc, language) {
  return siteConfig.baseUrl + 'docs/' + (language ? language + '/' : '') + doc;
}

function pageUrl(page, language) {
  return siteConfig.baseUrl + (language ? language + '/' : '') + page;
}

class Button extends React.Component {
  render() {
    return (
      <div className="pluginWrapper buttonWrapper">
        <a className="button" href={this.props.href} target={this.props.target}>
          {this.props.children}
        </a>
      </div>
    );
  }
}

Button.defaultProps = {
  target: '_self',
};

const SplashContainer = props => (
  <div className="homeContainer">
    <div className="homeSplashFade">
      <div className="wrapper homeWrapper">{props.children}</div>
    </div>
  </div>
);

const Logo = props => (
  <div className="projectLogo">
    <img src={props.img_src} />
  </div>
);

const ProjectTitle = props => (
  <h2 className="projectTitle">
    {siteConfig.title}
    <small>{siteConfig.tagline}</small>
  </h2>
);

const PromoSection = props => (
  <div className="section promoSection">
    <div className="promoRow">
      <div className="pluginRowBlock">{props.children}</div>
    </div>
  </div>
);

class HomeSplash extends React.Component {
  render() {
    let language = this.props.language || '';
    return (
        <SplashContainer>
        <Logo img_src={imgUrl('paco.png')} />
        <div className="inner">
          <ProjectTitle />
          <a target="_blank" href="https://search.maven.org/#search%7Cgav%7C1%7Cg%3A%22io.github.christian-draeger%22%20AND%20a%3A%22page-content-tester%22">
            <img className="mvn-badge" src="https://img.shields.io/maven-central/v/io.github.christian-draeger/page-content-tester.svg?style=for-the-badge"></img>
          </a>
          <PromoSection>
            <Button href="#why">Why?</Button>
            <Button href="#how">Setup</Button>
            <Button href={docUrl('doc1.html', language)}>Example Link</Button>
          </PromoSection>
        </div>
      </SplashContainer>
    );
  }
}

const Block = props => (
  <Container
    padding={['bottom', 'top']}
    id={props.id}
    background={props.background}>
    <GridBlock align="center" contents={props.children} layout={props.layout} />
  </Container>
);

const Features = props => (
  <Block layout="fourColumn">
    {[
        {
            title: 'robust and fast',
            content: 'The content of my second feature',
            image: imgUrl('docusaurus.svg'),
            imageAlign: 'top',
        },
      {
        title: 'non-blocking and highly parallelized',
        content: 'This is the content of my feature',
        image: imgUrl('rocket.png'),
        imageAlign: 'top',
      },
      {
        title: 'e@sy to use',
        content: 'Paco allows you to configure all your test specific data individually and directly in place (on your test method and/or test class) via annotations. You only need to describe how you want to fetch an http response (e.g. requesting a web page by using a proxy, mobile userAgent, setting cookies, add a specific referrer, doing a POST that sends some request body, etc).',
        image: imgUrl('annotation.png'),
        imageAlign: 'top',
      },
    ]}
  </Block>
);

const FeatureCallout = props => (
  <div
    className="productShowcaseSection paddingBottom"
    style={{textAlign: 'center'}}>
    <h2>Feature Callout</h2>
    <MarkdownBlock>These are features of this project</MarkdownBlock>
  </div>
);

const LearnHow = props => (
  <Block id="why" background="light">
    {[
      {
        title: 'Why would you use Paco?',
        content: ' The motivation of bringing this little buffed out guy to live have been the need of having a robust and fast solution to relieve a long running and unstable Selenium suite. After a code dive through these Selenium tests it turned out that lots of them were just checking things (like Dom elements, displayed data, cookies, etc) without the need of interacting with a web browser. So Paco was born as an alternative and he is doing his job rapidly fast and reliable. In a bigger test project where this framework is in use it runs ~400 tests in less than 10 seconds. When using Paco you can focus on your tests itself instead of messing around with setting up a complex test project yourself.',
        image: imgUrl('docusaurus.svg'),
        imageAlign: 'right',
      },
    ]}
  </Block>
);

const TryOut = props => (
  <Block id="how">
    {[
      {
        content: 'Talk about trying this out',
        image: imgUrl('docusaurus.svg'),
        imageAlign: 'left',
        title: 'Try it Out',
      },
    ]}
  </Block>
);

const Description = props => (
  <Block background="dark">
    {[
      {
        content: 'This is another description of how this project is useful',
        image: imgUrl('docusaurus.svg'),
        imageAlign: 'right',
        title: 'Description',
      },
    ]}
  </Block>
);

const Showcase = props => {
  if ((siteConfig.users || []).length === 0) {
    return null;
  }
  const showcase = siteConfig.users
    .filter(user => {
      return user.pinned;
    })
    .map((user, i) => {
      return (
        <a href={user.infoLink} key={i}>
          <img src={user.image} title={user.caption} />
        </a>
      );
    });

  return (
    <div className="productShowcaseSection paddingBottom">
      <h2>{"Who's Using This?"}</h2>
      <p>This project is used by all these people</p>
      <div className="logos">{showcase}</div>
      <div className="more-users">
        <a className="button" href={pageUrl('users.html', props.language)}>
          More {siteConfig.title} Users
        </a>
      </div>
    </div>
  );
};

class Index extends React.Component {
  render() {
    let language = this.props.language || '';

    return (
      <div>
        <HomeSplash language={language} />
        <div className="mainContainer">
          <Features />
          <FeatureCallout />
          <LearnHow />
          <TryOut />
          <Description />
          <Showcase language={language} />
        </div>
      </div>
    );
  }
}

module.exports = Index;
