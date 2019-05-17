# Synthetic Lift Measurements with Performance Curves

A simple example that shows a straightforward lift measurement can be
done by comparing the performance of a campaign to random targeters.

There are a couple of things to note about how this measurement is being
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