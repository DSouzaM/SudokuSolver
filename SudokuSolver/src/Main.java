import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		byte[][] grid = new byte[9][9];
		Scanner input = new Scanner(System.in);
		for (int row = 0; row<9;row++){
			for (int col = 0; col<9; col++){
				grid[row][col] = input.nextByte();
			}
		}
		SudokuSolver solver = new SudokuSolver(grid);
		solver.printGrid();
		for (int i = 1; i<=9; i++) {
			System.out.println(i + ".");
			solver.printUsedGrid(i);
		}
		
		
	}
}
