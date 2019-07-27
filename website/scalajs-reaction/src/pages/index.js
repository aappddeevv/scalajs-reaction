/**
 * Copyright (c) 2017-present, Facebook, Inc.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

import React from 'react';
import classnames from 'classnames';
import Layout from '@theme/Layout';
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
        scalajs-reaction uses reactjs hooks to make component creation and use easy.
      </>
    ),
  },
  {
    title: <>Focus on What Matters</>,
    imageUrl: 'img/undraw_docusaurus_tree.svg',
    description: (
      <>
         Your real focus should be on creating great web applications not on the framework or interop.
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
  const {siteConfig = {}} = context;
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
              to={withBaseUrl('docs/intro')}>
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
                {features.map(({imageUrl, title, description}, idx) => (
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
          Hello world!
          </div>
          </section>

     </main>
    </Layout>
  );
}

export default Home;
