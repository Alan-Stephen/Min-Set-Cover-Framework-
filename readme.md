# Plans
## Objective
 - Create a learning hyper-hueristic optimistion approach to solving the min-sat problem
 - using hueristic selection and elaborate move acceptence.
## Current Approach
 - hueristics:
   - increment / decrement IOM/DOS
   - local searches?
   - Mutations?
   - we let reinforcement thing decide whether to local search or mutate solution.
   - choice function with simulated annealing, make choice function very leniant
   - RUIN AND RECREATE:
     - how will it work..
     - remove subsets at random,then add subsets at random until we've got valid solution
