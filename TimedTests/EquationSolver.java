import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import static java.lang.Math.round;

public class EquationSolver extends JFrame implements ActionListener {
    JButton button;

    EquationSolver() {
        button = new JButton();
        button.setBounds(150, 200, 200, 100);
        button.addActionListener(this);
        button.setText("Start");
        button.setFocusable(false);
        button.setHorizontalTextPosition(JButton.CENTER);
        button.setVerticalTextPosition(JButton.CENTER);
        button.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        button.setForeground(new Color(29, 85, 198));
        button.setBackground(new Color(140, 0, 16));
        button.setOpaque(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setSize(500, 500);
        this.add(button);
        this.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button) {
            System.out.println("What order would you like?");
            Scanner scanner = new Scanner(System.in);
            int order = scanner.nextInt();
            long startTime = System.nanoTime();
            int numberOfSolutions = 0;
            List<int[][]> matrices = generateMatrices(order);
            List<Integer> nums = List.of(-9, -8, -7, -6, -5, -4, -3, -2, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
            List<List<Integer>> badEqualities = getPermutations(nums, order);
            int[][] equalities = convertListToArray(badEqualities);
            int runs = 0;
            for (int[] array : equalities) {
                outer:
                for (int[][] matrix : matrices) {
                    runs++;
                    double[] solution = solveSystem(matrix, array, order);
                    if (solution != null) {
                        for (int i = 0; i < order; i++) {
                            if (!(solution[i] == round(solution[i]))) {
                                continue outer;
                            }
                        }
                    } else {
                        continue;
                    }
                    for (int i = 0; i < order; i++) {
                        numberOfSolutions++;
                    }
                }
            }
            double percentage = (double) (100 * numberOfSolutions) / runs;
            System.out.printf("%,d solutions out of %,d matrices, or %.2f%%.\n", numberOfSolutions, runs, percentage);
            double elapsedTime = (double) (System.nanoTime() - startTime) / 1000000000;
            System.out.printf("Completed in %.2f seconds.", elapsedTime);
            //2: 9.63 seconds
        }
    }

    public static List<List<Integer>> getPermutations(List<Integer> nums, int n) {
        List<List<Integer>> permutations = new ArrayList<>();
        backtrack(permutations, new ArrayList<>(), nums, n);
        return permutations;
    }

    private static void backtrack(List<List<Integer>> permutations, List<Integer> tempList,
                                  List<Integer> nums, int n) {
        // Base case: If the tempList has reached the desired length, add it to the permutations list
        if (tempList.size() == n) {
            permutations.add(new ArrayList<>(tempList));
            return;
        }

        // Recursive case: Generate permutations by adding each number from the remaining list
        for (int i = 0; i < nums.size(); i++) {
            int num = nums.get(i);

            // Skip if the number is already in the tempList
            if (tempList.contains(num)) {
                continue;
            }

            // Choose
            tempList.add(num);

            // Explore
            backtrack(permutations, tempList, nums, n);

            // Unchoose
            tempList.remove(tempList.size() - 1);
        }
    }

    public static List<int[][]> generateMatrices(int matrixSize) {
        List<int[][]> matrices = new ArrayList<>();
        int[][] matrix = new int[matrixSize][matrixSize];
        generateMatrix(matrices, matrix, 0, 0, matrixSize);
        return matrices;
    }

    private static void generateMatrix(List<int[][]> matrices, int[][] matrix, int row, int col, int matrixSize) {
        if (row == matrixSize) {
            int[][] newMatrix = copyMatrix(matrix);
            matrices.add(newMatrix);
            return;
        }

        for (int value = -9; value <= 9; value++) {
            matrix[row][col] = value;

            int nextRow = row;
            int nextCol = col + 1;
            if (nextCol == matrixSize) {
                nextRow++;
                nextCol = 0;
            }

            generateMatrix(matrices, matrix, nextRow, nextCol, matrixSize);
        }
    }

    public static int[][] copyMatrix(int[][] matrix) {
        int[][] copy = new int[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            System.arraycopy(matrix[i], 0, copy[i], 0, matrix[i].length);
        }
        return copy;
    }

    public static int[][] convertListToArray(List<List<Integer>> list) {
        int numRows = list.size();
        int numCols = list.get(0).size();

        int[][] array = new int[numRows][numCols];

        for (int i = 0; i < numRows; i++) {
            List<Integer> rowList = list.get(i);
            for (int j = 0; j < numCols; j++) {
                array[i][j] = rowList.get(j);
            }
        }

        return array;
    }
    public static double[] solveSystem(int[][] coefficients, int[] constants, int n) {
        double[][] augmentedMatrix = new double[n][n + 1];

        // Construct the augmented matrix
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                augmentedMatrix[i][j] = coefficients[i][j];
            }
            augmentedMatrix[i][n] = constants[i];
        }

        // Perform Gaussian elimination
        for (int i = 0; i < n; i++) {
            // Find the pivot row
            int pivotRow = i;
            for (int j = i + 1; j < n; j++) {
                if (Math.abs(augmentedMatrix[j][i]) > Math.abs(augmentedMatrix[pivotRow][i])) {
                    pivotRow = j;
                }
            }

            // Swap rows
            double[] temp = augmentedMatrix[i];
            augmentedMatrix[i] = augmentedMatrix[pivotRow];
            augmentedMatrix[pivotRow] = temp;

            // Make the diagonal element 1
            double pivot = augmentedMatrix[i][i];
            if (Math.abs(pivot) < 1e-10) {
                return null; // No unique solution
            }
            for (int j = i; j <= n; j++) {
                augmentedMatrix[i][j] /= pivot;
            }

            // Eliminate other rows
            for (int j = 0; j < n; j++) {
                if (j != i) {
                    double factor = augmentedMatrix[j][i];
                    for (int k = i; k <= n; k++) {
                        augmentedMatrix[j][k] -= factor * augmentedMatrix[i][k];
                    }
                }
            }
        }
        // Extract the solution
        double[] solution = new double[n];
        for (int i = 0; i < n; i++) {
            solution[i] = augmentedMatrix[i][n];
        }
        return solution;
    }
    public static void main(String[] args) {
        new EquationSolver();
    }
}
