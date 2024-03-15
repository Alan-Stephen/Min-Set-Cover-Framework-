# Min Set Cover Framework

## Description

Optimisation framework to solve the [Set Cover Problem](https://en.wikipedia.org/wiki/Set_cover_problem) using a Hyper-Hueristic Approach to the problem in order to produce "good-enough" answers with an acceptable runtime.

## Installation

Clone the repository and either run with an IDE or using (Linux) command line using
```
javac Main.java && java Main
```

## Usage

Modify the ```Parameters``` object passed into ```Runner``` to change the parameters of the test includings:
 - Depth of Search (DOS)
 - Intensity of Mutation (IOM)
 - Alpha
 - Reheating Factor
See [Simulated Annealing](https://en.wikipedia.org/wiki/Simulated_annealing) for context behind Alpha and Reheating factor

Adding extra hueristics is also intended functionality, simply create a new implementation of the ```Hueristic``` interface, which will be subsequently called by ```ReinforcementHyperHueristic```,
Note that you do need to append your hueristic the ```ArrayList<Hueristic> hueristics``` which is intended to be done in the constructor of ```ReinforcementHyperHueristic``` e.g
```
hueristics.add(new SteepestDescent(depthOfSearch));
hueristics.add(new RandomMutation(problemInstance.random, intensityOfMutation));
hueristics.add(new BestSolutionBiasedCrossover(problemInstance.random, 0.3));
hueristics.add(new RemoveRandomSet(intensityOfMutation,problemInstance.random));
hueristics.add(new RuinAndRecreateHueristic(intensityOfMutation,random));
hueristics.add(new MyNewHueristic(paramater1,parameter2, parameter3));
```

