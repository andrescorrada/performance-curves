# Cheaper, Faster A/B Tests: Ghost A/B Testing

Good tools have advantages and limitations. A/B testing is an example
of such a tool.  It meets a high bar of scientific validity
principally by its use of the randomized division of the intended
audience. But it has its limitations in ad-tech. It costs money and
time to execute them. But perhaps more importantly, you can miss
successful tests due to the decision pipeline of ad-serving engines.

Performance Curves are also a tool that has advantages and
limitations. A good trick inventors and innovators use is to take two
or more things that have complimentary weaknesses and put them
together. The whole is greater than the parts.  A/B testing and
Perfomance Curves is such an example. By combining the cheap, nearly
instant synthetic A/B testing of Performance Curves with traditional
A/B testing, you get the best of both.

Most proposed improvements to an ad-serving engine are useless. They
either do nothing, or degrade the system. But some are not. By quickly
evaluating all possibilities open to you in your particular setting,
you can select whatever top-n is deemed sufficient for actual A/B
tests.

If the use of the words "synthetic A/B test" bother you, think of it
this way. A/B tests are meant to test two scientific hypothesis
(measurable, etc.). So why can you not use synthetic Performance Curve
A/B tests to create a cohort of such hypothesis that you want to test
rigorously?

They have many advantages: 
1. As fast and cheap as you can compute
them. 
2. Allow you to winnow out worst contenders.
3. Measured in units of the campaign KPI.

But they are not perfect. By their very construction they are
incapable of testing A/B hypothesis between different populations.
Since you are using the same campaign data from the past to test your
many contenders, you can only compare changes that are unrelated to
population changes. Since any measurement can be thought of as a
statistic, this means that this technique is not fool-proof. There may
be hypothesis that you are testing that have a hidden relation to the
audience of your campaign.

## New methodologies

It is beyond my statistical expertise to understand how to quantify
the advantage of new tests that become possible with Performance
Curves. Since they are cheap and instantaneous you can start doing all
sorts of ensembling of tests.

For example, suppose that you sub-divide your data into day intervals,
carry out the A/B test there and obtain that for all samples, the B
hypothesis was better. Is there no information in this test? That
seems intuitively wrong to me, but I cannot prove it mathematically.
If the noise of counterfactuals was so strong, how come B came up
every day for the last two weeks? Doesn't that seem like a good
hypothesis to test with an actual A/B test?

## Limitations

One limitation of Ghost A/B Testing is that you cannot test internal
states that you cannot calculate offline. Examples of this in ad-tech
would be bidder or pacer settings. Ad-servining engines typically do
a bad job of saving such internal state. Funnily, the business rules
dominate the buys, but the ML components get the most documentation
of internal state with things like feature values, model score, etc.

The community solution is that ad-tech engines need to expend money
to develop their internal state documentation of all business rules
that affect buy decisions. Doesn't that sound obvious when you say it
out loud? Why are ad-engines not saving operator changes that control
the way buys are done? If they did, they could use Ghost A/B Testing
to improve it.

They could show that the operator was changing the bidder so as to
work against campaign KPIs. The leanest, most efficient ad-serving
engines invest in internal state documentation so they can offload
A/B testing from their client's. The more internal state documentation
you have of everything that affects your buys - the better you will
be able to use Ghost A/B Testing to optimize your ad-serving engine.