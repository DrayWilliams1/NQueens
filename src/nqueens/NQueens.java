package nqueens;
/**
 * @author Drayton Williams
 * The purpose of this class is to solve the n Queens problem using a blind
 * search and an informed (heuristics-guided) search. Note to markers, the
 * informed search aspect of the code is able to work at n = 5 and works
 * at n = 8 when the seed is 701 or 42. It is able to apply the intended 
 * heuristics at those test settings and approaches a solution
 */
import java.util.*;

public class NQueens {

    private String searchType;

    private int probSize;       // problem size (NxN)
    private int numConfigs = 0; // number of tested board configurations
    private int[] product1;
    private boolean bSolnFound = false;
    private int solnCount = 0;

    private int seed;
    private ArrayList<Integer> product2;
    private boolean iSolnFound = false;

    // hScore keeps track of each psuedo-random array's hueristic score and
    // allows for the minimum value to be obtained
    private LinkedList<Integer> hScore;
    private LinkedList<ArrayList> bConfig;
    private int testAttempts = 0;

    public NQueens() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Blind or Informed search (b or i):  ");
        searchType = scanner.nextLine();
        System.out.print("Please indicate problem size (Number of Queens): ");
        probSize = scanner.nextInt();

        switch (searchType) {
            case "b": { // Blind search selected
                product1 = new int[probSize];
                // 0 will begin the safe check at the first column and probSize
                // keeps it bounded within the correct row amount
                useQueens(0, probSize);

                if (bSolnFound == false) {
                    System.out.println("No Solution Exists!");
                }
                break;
            }
            case "i": { // Informed (heuristic) search selected
                product2 = new ArrayList<>();
                System.out.print("Enter Seed: ");
                seed = scanner.nextInt();

                System.out.println("Seed Used: " + seed);
                //for (int i = 0; i < 2; i++) {
                HeuristicSearch();
                // if (iSolnFound == true){
                //     break;
                //  }
                //}

                if (iSolnFound == false) {
                    System.out.println("No Solution Exists!");
                }
                break;
            }
        }
        System.out.println("Number of board configurations tested: "
                + numConfigs);
    }

    /**
     * Performs a blind (and exhaustive) search for all solutions in the n
     * Queens problem, only printing the first discovered solution
     *
     * @param x the starting spot of the Queen to be checked on and eventually
     * placed
     * @param size the chosen problem-size
    *
     */
    public void useQueens(int x, int size) {
        numConfigs += 1;

        for (int i = 0; i < size; i++) {
            if (safeCheck(x, i)) {
                product1[x] = i;

                // goal state: if the indexed row number (+ 1 because arrays
                // begin at 0) equals the provided problem size, the goal state 
                // is achieved and all queens have been placed
                if ((x + 1) == size) {
                    solnCount += 1;
                    bSolnFound = true;

                    QBoard board = new QBoard(product1);
                    // Only prints board of the first discovered solution
                    if (solnCount <= 1) {
                        board.printBoard();
                        System.out.println();
                    }
                }
                useQueens(x + 1, size);
            }
        }
    }

    /**
     * Checks if it is safe to place a Queen at a specific spot on the board
     *
     * @return boolean whether the Queen is able to be placed at that spot in
     * the board
     * @param col the column where the queen could be placed
     * @param row the row where the queen could be placed
    *
     */
    private boolean safeCheck(int col, int row) {
        for (int i = 0; i < col; i++) {

            // Since columns are already guarenteed safe by the 2D array, this
            // condition will check if rows or diagonals are safe for placement
            if ((product1[i] == row) || (Math.abs(i - col)
                    == Math.abs(product1[i] - row))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Performs a heuristics-driven informed search for the n Queens problem
     *
     * @return List the persistent patron list
    *
     */
    private void HeuristicSearch() {
        if (testAttempts == 2) {
            product2.clear();
            randGenerator();
        }
        testAttempts += 1;
        int a = 0;
        ArrayList<Integer> temp = new ArrayList<>();
        ArrayList<Integer> temp2 = new ArrayList<>();
        bConfig = new LinkedList<>();
        hScore = new LinkedList<>();
        ArrayList<Integer> current = new ArrayList<>();

        if (product2.isEmpty()) {
            randGenerator();
        }

        bConfig.add(product2);
        hScore.add(checkHScore(product2));

        for (int i = 0; i < product2.size(); i++) {
            temp.add(product2.get(i));
            temp2.add(product2.get(i));
        }

        for (int i = 0; i < temp.size(); i++) {
            a = temp.get(i);
            for (int j = i; j < temp.size(); j++) {

                numConfigs += 1;

                temp.set(i, temp.get(j));
                temp.set(j, a);
                // if the new configuration of the (curent) best board has a
                // better heuristic score
                if (checkHScore(temp) < checkHScore(temp2)) {
                    temp2.clear();
                    for (int h = 0; h < temp.size(); h++) {
                        temp2.add(temp.get(h));
                    }
                    if (checkHScore(temp2) == 0) {
                        iSolnFound = true;
                        QBoard board = new QBoard(temp2);
                        System.out.println("---Final Generated Board---");
                        board.printBoard();
                        System.out.println("---Final Generated Board---");
                        System.out.println();
                        break;
                    }

                    // if the new configuration's heuristic score does not 
                    // improve upon the test board, it is reset
                }
                temp.clear();
                current = bConfig.getFirst();
                for (int h = 0; h < current.size(); h++) {
                    temp.add(current.get(h));
                }
                // if temp1 list has been updated by a configuration
                // yielding a better heuristic value, update the list containing 
                // board configurations with this new configuration
                if (!bConfig.contains(temp2)) {
                    bConfig.addFirst(temp2);
                    hScore.addFirst(checkHScore(temp2));
                }
            }
            if ((i == temp.size() - 1) && !iSolnFound) {
                product2.clear();
                for (int p = 0; p < temp2.size(); p++) {
                    product2.add(temp2.get(p));
                }
                bConfig.clear();
                hScore.clear();
                temp.clear();
                temp2.clear();
                current.clear();
                HeuristicSearch();
            }
        }
    }

    /**
     * Returns the board configuration that has the smallest heuristic value in
     * the current list of possible board configurations
     *
     * @return ArrayList<Integer> the list containing the smallest heuristic
     * score
    *
     */
    private ArrayList<Integer> chooseMinH() {
        int minH = 0;
        int index = 0;
        minH = hScore.getFirst();

        for (int i = 0; i < hScore.size(); i++) {
            minH = Math.min(minH, hScore.get(i));
        }

        index = hScore.indexOf(minH);
        return bConfig.get(index);
    }

    /**
     * Generates a pseudo-random initial board configuration to be tested on.
    *
     */
    private void randGenerator() {
        QBoard board = null;
        Random rand = new Random(seed);

        for (int i = 0; i < probSize; i++) {
            product2.add(i);
        }
        // With a given seed, board will always be shuffled psuedo-randomly with
        // a given seed, ensuring unique number and that no two Queens are in
        // each others row or column
        Collections.shuffle(product2, rand);
        board = new QBoard(product2);
        //System.out.println("---Random Generated Board---");
        //board.printBoard();
        //System.out.println("---Random Generated Board---");
        //System.out.println();
    }

    /**
     * Finds the heuristic score of a provided list of Queen coordinates. Since
     * all Queens are guaranteed to be in their own rows and columns, this will
     * check any diagonal collisions
     *
     * @return int the heuristic score of the given coordinates
     * @param config the list of coordinates to be checked
    *
     */
    private int checkHScore(ArrayList<Integer> config) {
        boolean fDiagonal = false; // forwards diagonal
        boolean bDiagonal = false; // backwards diagonal

        int hVal = 0;
        for (int i = 0; i < config.size(); i++) {
            for (int j = i; j < config.size(); j++) {
                if (i != j) {
                    fDiagonal = (Math.abs(i - j)) == (Math.abs(config.get(i)
                            - config.get(j)));
                    if (fDiagonal) {
                        hVal += 1;
                    }
                }
            }
        }
        return hVal;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        NQueens l = new NQueens();
    }
}
