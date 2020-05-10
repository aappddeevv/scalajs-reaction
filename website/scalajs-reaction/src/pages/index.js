/**
 * Copyright (c) 2017-present, Facebook, Inc.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

import React from 'react';
import classnames from 'classnames';
import Layout from '@theme/Layout';
import CodeBlock from '@theme/CodeBlock';
import Link from '@docusaurus/Link';
import useDocusaurusContext from '@docusaurus/useDocusaurusContext';
import withBaseUrl from '@docusaurus/withBaseUrl';
import styles from './styles.module.css';

const features = [
  {
    title: <>Easy to Use</>,
    imageUrl: 'img/undraw_docusaurus_mountain.svg',
    description: (
      <>
        scalajs-reaction uses reactjs hooks to make component creation and use easy. You use common, everyday, highly inferable scala idoms--no macros or tricks.
      </>
    ),
  },
  {
    title: <>Focus on What Matters</>,
    imageUrl: 'img/undraw_docusaurus_tree.svg',
    description: (
      <>
        Your real focus should be on creating great web applications not on the framework or making the interop work.
      </>
    ),
  },
  {
    title: <>Powered by React</>,
    imageUrl: 'img/undraw_docusaurus_react.svg',
    description: (
      <>
        It is all reactjs underneath the hood and interop is a breeze so you can drop down to js or typescript any time you want.
      </>
    ),
  },
];

function Home() {
  const context = useDocusaurusContext();
  const { siteConfig = {} } = context;
  return (
    <Layout
      title={`Hello from ${siteConfig.title}`}
      description="scalajs-reaction documentation">
      <header className={classnames('hero hero--primary', styles.heroBanner)}>
        <div className="container">
          <h1 className="hero__title">{siteConfig.title}</h1>
          <p className="hero__subtitle">{siteConfig.tagline}</p>
          <div className={styles.buttons}>
            <Link
              className={classnames(
                'button button--outline button--secondary button--lg',
                styles.getStarted,
              )}
              to={withBaseUrl('docs/intro/index')}>
              Get Started
            </Link>
          </div>
        </div>
      </header>
      <main>
        {features && features.length && (
          <section className={styles.features}>
            <div className="container">
              <div className="row">
                {features.map(({ imageUrl, title, description }, idx) => (
                  <div
                    key={idx}
                    className={classnames('col col--4', styles.feature)}>
                    {imageUrl && (
                      <div className="text--center">
                        <img
                          className={styles.featureImage}
                          src={withBaseUrl(imageUrl)}
                          alt={title}
                        />
                      </div>
                    )}
                    <h3>{title}</h3>
                    <p>{description}</p>
                  </div>
                ))}
              </div>
            </div>
          </section>
        )}

        <section className={styles.features}>
          <div className="container">
            <p>It is easy to create a component. The code below creates a functional component that takes zero args.</p>
            <p>
              <CodeBlock className="scala">{
`val HelloWorld: ReactFC0 = () => div("hello world")`
}
              </CodeBlock>
            </p>
            <p>A functional component that takes props (one argument) with a mandatory [name] property is</p>
            <p><CodeBlock className="scala">{
`object MyComponent {
  trait Props extends js.Object {
     val name: String
  }

  // you could also use the type ReactFC[Props]
  val render: Props => ReactNode = props => div("hello " + props.name)
  
  def apply(props: Props) = render.elementWith(props)
}
`}
            </CodeBlock>
            </p>
            <p>By defining an apply method (or not) you decide how component users
create elements.</p>
            <p>To use MyComponent, we just need to create it. Creating an element is as natural and easy
as calling it's apply function.</p>
            <p>
              <CodeBlock className="scala">{
`// scala code
div(
  MyComponent(new Props {
    val name = "world"
  })
)`
}
              </CodeBlock>
            </p>
            <p>You can create custom Props creation methods if you choose. Everything above
            is just pure scala.js--not tricks or macros. Type inference is very good
and it is a natural scala programming style.</p>

          </div>
        </section>

      </main>
    </Layout>
  );
}

export default Home;
