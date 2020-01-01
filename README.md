# NQueens
Completed in 2017, this is a project where the purpose was to solve the classic N-Queens problem using a blind search and an informed search. Provided a few user inputs, the program will output a correct board configuration and the number of board configurations before arriving at the result.

A few details on the program:
- allows for the choice of blind or informed search
- when selecting informed search, user will be asked to provide a seed (seed allows for repeated results when testing)
- the first successful board configuration will be displayed along with the number of tested configurations

# Detailed Report
For this project, a blind and informed search were used to solve the classic N Queens problem. The blind search was generally able to compute times on par with the informed search when using relatively small values of n. Given that the blind search is exhaustive, when the size of n increases to be much larger, the informed search was, understandably, much faster.

The blind search made use of backtracking via recursion. The coordinates of each queen was kept track of using a 1-dimensional array. This array was also filled with unique values that fell between 0 and the problem size then pseudo-randomly shuffled with a seed. This means the Queens would already exclusively fall in their own rows and columns when represented on the 2-dimensional chess board. The goal state of this checks that if the amount of Queens on the board is equivalent to the problem size (of n), then a solution has been found.

When implementing the informed search, the heuristics used were to check what coordinates on the 1-dimensional array would make it so there are diagonal collisions by computing the absolute value difference between the different indices and their respective values. Queens would be swapped with the location of all Queens in other rows and if the new board configuration had a heuristic score that was below the current lowest, it would be saved and the resulting configuration would be updated. Due to all Queens already having unique locations on the board, all possible other locations for a Queen have been tested during the swaps. If at the end of the swaps, the heuristic score was no better or a solution was not found, the entire board configuration would be reset using the same seed to be tested on again until success.
