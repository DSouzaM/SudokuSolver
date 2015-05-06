import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		byte[][] grid = new byte[9][9];
		Scanner input = new Scanner(System.in);
		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++) {
				grid[row][col] = input.nextByte();
			}
		}
		SudokuSolver solver = new SudokuSolver();
		ArrayList<byte[][]> solutions = solver.getSolution(grid);
		if (solutions.size() == 0) {
			System.out.println("There are no solutions.");
		} else {
			for (int i = 0; i < solutions.size(); i++) {
				System.out.println("Solution " + (i + 1) + "\n"
						+ SudokuSolver.gridToString(solutions.get(i)) + "\n");
			}
		}

	}

}
