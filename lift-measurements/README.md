# Synthetic Lift Measurements with Performance Curves

![Curves for random lift measurement](../random-lift-measurement.png "Random Lift Measurement")

A simple example that shows a straightforward lift measurement can be
done by comparing the performance of a campaign to random targeters. The
measurement is already expressed graphically in th curves above. There is
a geometrical viewpoint to using Performance Curves, but you can also
derive a single measure of goodness - KPI at average rank - that can
be used for numerical comparisons. This README is going to talk only
about the curves geometrically. A future section will discuss how to
compute KPI at average rank.

There are a couple of things to note about how the measurement is
defined:

1. We will be looking at the performance of the campaign in-toto, not just
its AI/ML sub-components. Pacers and bidders also affect how campaign's
perform. But to be clear, if there is something that changes or modifies 
the score of your ad-buying decisions, Performance Curves can help you 
detect when it is sick, under-performing, or your best star. We will
not be talking about this data pipeline stage debugging aspect here.

2. We will be comparing the campaign's performance not just to average
random performance, but to the ensemble of 97.5% of random targeters.
This subtle change is enormously important from the point of view of
satisfying your client expectations. Let me explain why. Comparing
yourself to average random performance means that if you run your
campaign against a random baseline many times, on average, you are x
better. Do you see what is wrong with this operationally? I have seen
it many times. The correct comparison is to the 97.5% percentile or
whatever percentile you want to make sure that your campaign beats
the random baseline the first time, the 2nd time. You pay for this
convenience by demanding more from your models. But isn't that the
point of innovation?

## The Curves

Each curve represents a synthetic campaign. This conceptual sleight
of hand is achieved by asserting that sorting according to an
evaluators score is equivalent to running the campaign the way they
would like to run it. It puts what they think is cream first.

You may not like this vague metaphorical assertion at this point,
but in the end it will not matter because you will see that ranking
the data by each evaluator is the sensible way to understand not
just the final outcome but how it was reached.

And here we point out one outstanding feature of Performance Curves -
all comparisons are done on the same data. It is the purest
apples to apples comparison one can come up with. All four curves
are constructed from exactly the same CSV table. The only difference
between the curves is how that table is sorted and scrambled.

### The oracle (orange, top) curve

The oracle curve is the curve that you construct by doing the
cheating experiment. You sort the data by the KPI you are trying
to maximize. You put the known good stuff at the top, the bad 
stuff at the bottom. It represents, empirically, the maximum wisdom
of a god-like figure restricted through your KPI. If an omnipotent 
being was only allowed to use its knowledge of true KPI and nothing
else to decide how to sort, this curve represents the best they
could do.

The value of the Oracle Curve is that it measures how much gold
is in the stream. Sometimes campaigns are asked to do impossible
things. THere could be very few conversions because the task is
hard. But be cautious, this is one place where counterfactuals could
bite you. The reason the task may seem hard is that your campaign
sucked to find the gold. So the caveat here is that two explanations
are possible -
1. This campaign KPI is very hard to optimize.
2. Your ad-engine is so biased that it is not detecting opportunities
correctly.

Implicitly, this curve is also defining the direction of improvement
in the plot. In this case, being higher is better (closer to the
oracle curve).

### The curves for the two production stages

The green and blue curves are two stages in the ad-serving pipeline
that actually served ads during the campaign. But not that they
are only part of the full pipeline. These ML stages, like most industrial
data pipelines do not have "bite". They advise but do not decide.

Here we see that the green stage is healthy and the blue stage is sick.
It is sick because its highest scoring decisions are no better than
random targeters (the red curves). We explain those curves next.

### The curves for the ensemble of random targeters

The two red curves in the plot are the envelopes that encompass the
possible performance of 95% of the random targeters.

A single random targeter synthetic campaign is randomly shuffling the
campaign data table. The envelope (the two red curves) is constructed
by identifying the 2.5 and 97.5 percentiles at each point for an
ensemble of many such random shuffles.

As we mentioned before, this is the synthetic baseline for this campaign.
This plot answers the question - "Is this stage in the ad-serving
pipeline better than most random targeters?" As you can see, the blue
stage stumbles out of the gate. It ranks highest views that actually
were some of the worst performers.

Here is where the academic criticism of Performance Curves has been
most silly. I've utilized this synthetic random lift measurement many times.
I've done it in companies where the empirical random targeter baseline
kept beating the campaign itself to the consternation of everyone. Using
this measurement I was able to diagnose the problem and repeatedly fix
it. In the process I discovered that most deployed models where no
better than random guessers at the beginning.

## The counterfactual problem with the random synthetic lift measurement

The response to the continued empirical success with this lift measurement
has ranged from:
1. You don't have counterfactuals, this plot is irrelevant.
2. Don't you think you are being harsh requiring that the model be above
the 95 percentile?

The possibility existed in all of those measurements that you could be
deceived by lack of knowledge of counterfactuals. If you are an
industrial scientist you see how silly this objection is. The
**possibility** that we may be missing important information from
counterfactuals is not an **actuality** that we are in a specific
case.

The counterfactual argument here could be something like-
> You don't know if a synthetic campaign you trace in your plot
> would actually obtain the data you are using in this comparison.
> It could be that the blue sick stage, is actually okay. The other
> stages are the ones that are sick, for example. They biased
> the data collection and selected data that makes the blue one
> look sick at the beginning.

True. But I ask from industrial scientists, how often do well-engineered
systems break down in 5 stages at the same time versus 1? The correct
way to treat Performance Curves is to view them as generators of
experimental tests. They provide to you, the industrial scientist, a
tool that can find where your system is under-performing. In that
world, the lack of counterfactual knowledge should not lead to
analysis paralysis. You **can** perform the experiment to test
in production, serving actual ads, if the conclusion you reach with
this measurement is correct or not.

### Empirical verification

One of the goals of this project is to start a repository of successful
and failed experiments using the Performance Curves methodology. In
future update I'll present a "before" and "after" pair of plots that
may serve well as an initial format for "documenting" the experiments.

One advantage of the plots as documentation is that they lend themselves
to numerical obfuscation so that proprietary information is not made
public. An example on one such plot is the one above. It deliberately
avoids detailing the KPI (vATR1K = view action rate per 1000)




