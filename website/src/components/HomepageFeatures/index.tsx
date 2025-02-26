import clsx from 'clsx';
import Heading from '@theme/Heading';
import styles from './styles.module.css';

type FeatureItem = {
    title: string;
    Svg?: React.ComponentType<React.ComponentProps<'svg'>>;
    description: JSX.Element;
};

const FeatureList: FeatureItem[] = [
    {
        title: 'Model',
        description: (
            <>
                Decision logic is expressed as rules, where each rule have inputs and outputs. Such constrained model
                allows defining the decisions in an organised way with no arbitrary if expressions.
            </>
        ),
    },
    {
        title: 'Visualize',
        description: (
            <>
                Rules can be rendered into a visual representation (e.g. Markdown or DMN) that can be understood by non-technical people.
            </>
        ),
    },
    {
        title: 'Understand',
        description: (
            <>
                Each evaluation of the logic provides comprehensive diagnostic data that makes it easy to understand why
                and how the given decision was taken.
            </>
        ),
    },
];

function Feature({title, Svg, description}: FeatureItem) {
    return (
        <div className={clsx('col col--4')}>
            {/*<div className="text--center">*/}
            {/*  <Svg className={styles.featureSvg} role="img" />*/}
            {/*</div>*/}
            <div className="text--center padding-horiz--md">
                <Heading as="h3">{title}</Heading>
                <p>{description}</p>
            </div>
        </div>
    );
}

export default function HomepageFeatures(): JSX.Element {
    return (
        <section className={styles.features}>
            <div className="container">
                <div className="row">
                    {FeatureList.map((props, idx) => (
                        <Feature key={idx} {...props} />
                    ))}
                </div>
            </div>
        </section>
    );
}
