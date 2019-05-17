# The Performance Curves Research Project: Ad-Tech Measurement for Everyone.

If ad-tech measurement technology continues to be developed by
the Big Four only, 3rd-party companies have already lost the ad-share war.
This Open Source research project aims to develop further an
additional measurement methodology - Performance Curves. Depending
on who you are, you may like to think of them as either, "Ghost Ads
on Steroids" or "Naive Ghost Ads".

Performance Curves are an alternative way to run synthetic ad-tech
campaigns, visualize and measure their KPI performance. All the while
without spending any additional money. Like Ghost Ads, no actual ads
are served. You just use your existing campaign data to evaluate
whatever lift measurement you want to try.

Sound too good to be true? It is. There is an important caveat to them.
And depending on whether you are an academic scientist or an 
industrial scientist, this could be a half-empty or half-full situation
for you. So let's get to the flaw of Performance Curves right away.

## The problem of counterfactuals in ad-tech

Performance Curves allow you to run synthetic campaigns and create all
sorts of neat lift measurements but they are **incapable** of taking
into account the effect of counterfactuals.

If you are an academic scientist, this is a terrible flaw. "You are claiming
to do a lift measurement but you do not handle counterfactuals? Huh?"

To an industrial scientist this is not such a bad flaw. What other
choice do you have? To think that counterfactuals could **possibly**
affect your lift measurement does not mean that they **actually** are.
Sometimes the effect you are looking for is so strong that it rises
above the uncertainty of your lack of knowledge of counterfactuals.

Would you use a measurement methodology that lacked theoretical
justification? If you answer no, let me ask you - do you use
Naive Bayes? Ever used it? Performance Curves is the Naive Bayes of
techniques like Ghost Ads - it assumes the noise of counterfactuals
is not relevant and treats it like an **empirical** hypothesis
to be tested. Why suffer analysis paralysis by requiring theoretical
justifications for the effect of counterfactuals when you can just 
deploy an experiment today to test the hypothesis empirically?

## History

This approach was developed in 2012 at DataXu. In the ad-tech
industry, experiments are an imperfect way to model production
systems due to the absence of counterfactuals. Instead of the ROC/AUC
methodology which does not generalize gracefully to tasks other than
binary classification, performance curves exploit the common case of
optimizing for a single KPI (Key Performance Index). This includes
costs and benefits for a given task and generalizes to any algorithm
that provides a score for sorting test data. For example, you could
use it to discover buggy stages, like bidders and pacers, in your data
pipeline

In addition, it uses measurements on unserved ads to make lift measurements that can
help optimize a campaign. Think of it as Ghost Ads on steroids without the heavy
statistical baggage associated with it. It exemplifies the quote by John Tukey - 
> Far better an approximate answer to the right question, which is often vague, than 
> the exact answer to the wrong question, which can always be made precise."

With it, I have discovered that most deployed models using the AUC/ROC are no better
than the variance of random models. I have also used it to blame shift from AI/ML models
to other stages of the ad-serving pipeline. Here is a tongue-in-cheek explanation:
[The Science of Blame Shifting in Ad-Tech](https://www.linkedin.com/pulse/science-blame-shifting-ad-tech-performance-curves-corrada-emmanuel/)

## The idea quickly explained

The goal of deploying production systems in ad-tech is often to
optimize some KPI such as CTR or CPA. Machine learning algorithms (and
other components of a production system!) usually do this by assigning
a score to different choices. We can combine these two features with
the following procedure: 
* Rank the test data by desired score. 
* Starting from the top, measure KPI for all ranks above the current
one.
* Plot cumulative KPI versus some other cumulative property of the data.
* Read the "tea leaves" or compare average KPI height for different models/scorers.

That's it.

## Usage

Assuming that KPI is expressed as a ratio of two cumulative sums, you
need: 
* Sorting variable: A score that can be sorted descending (most
common case). 
* Numerator variable: For example, if you are interested
in CPA (cost per action), you can invert it as (actions per cost) and
use as the numerator the actions. This could be an indicator function
or some positive integer. 
* Denominator variable: In the case of CPA
it would be cost per attempt. 
* xAxis variable: You could choose the
x-axis to acumulate by total attempts (views).

### Scala/Spark

Two functions have been implemented.

* `FullPerformanceCurve(df: DataFrame, numColumn: String, denColumn: String, sortColumn: String, xAxisColumn: String)`
  * This computes the performance curve for all Rows in your DataFrame. You probably do not want to use this directly as it may involve millions of points in the curve.
* `UniformlySampledPerformanceCurve(df: DataFrame, numColumn: String, denColumn: String, sortColumn: String, xAxisColumn: String, nth: Int)`
  * Samples every `nth` point in the full performance curve. Always includes first and last points.

## Applications

1. Use it to select better best-of-breed models offline by taking into
account the KPIs that matter to your clients, not ML/AI metrics that
do not relate to the costs of your errors.
2, Compare campaign performance to random and oracle baselines. Is the
campaign struggling because the task is hard or the strategy is wrong?
3. Improve the odds that deployed campaigns beat any random baseline, not just
the average random model. Beating the random model means your model may
lose to your random baseline. That is not something you want to spend
time explaining to your clients. With Performance Curves you can check
that you are beating 95% of any possible outcome for a random baseline.
Client is happy. You are happy.

## How do I contribute?

I'm looking for data scientists that are willing to test Performance Curves
in its different applications and report back successes or failures of their
attempted experiments. This is an empirical technique and we may discover
that it helps in some areas but not in others. Please drop me a line in
LinkedIn if you are interested in participating in the project.


