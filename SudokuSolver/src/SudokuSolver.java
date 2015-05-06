import java.util.ArrayList;
import java.util.Arrays;


public class SudokuSolver {
	
	// Calls the private solve() method and removes all failed cases before returning an ArrayList of solutions (some puzzles have multiple solutions).
	public ArrayList<byte[][]> getSolution(byte[][] grid) {
		ArrayList<byte[][]> solutions = solve(grid);
		for (int i = 0; i < solutions.size(); i++) {
			if (solutions.get(i)[0][0] == 0) {
				solutions.remove(i);
				i--;
			}
		}
		return solutions;
	}
	// Recursively solves the grid input, returning an ArrayList of byte[][]s (has both the valid solutions as well as empty byte[][]s from failed routes).
	private ArrayList<byte[][]> solve(byte[][] g) {
		ArrayList<byte[][]> solutions = new ArrayList<byte[][]>();
		byte[][] grid = twoDimensionalCopy(g);
		byte[][] numPossible = getNumPossible(grid);
		//  Fills cells where only one value is possible. 
		// If a new value is added at any point in the loop, repeats the loop until there are no cells with only one possible value.
		boolean valueAdded = false;
		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++) {
				numPossible = getNumPossible(grid);
				if (numPossible[row][col] == 1) {
					enterValue(getPossibleValues(row, col, grid).get(0), row,
							col, grid);
					valueAdded = true;
				}
			}
			// If at the end of the loop and a value was added, repeat.
			if (row == 8 && valueAdded) {				
				row = -1;
				valueAdded = false;
			}
		}
		// If the grid is full, then it must be a solution since all values added are first checked to be valid.
		if (isFull(grid)) {
			solutions.add(grid);
			return solutions;
		}
		else {
			for (int row = 0; row < 9; row++) {
				for (int col = 0; col < 9; col++) {
					// If there exists an empty cell on the grid with no possible values, return an empty byte[][].
					if (cellEmpty(row, col, grid)
							&& getPossibleValues(row, col, grid).size() == 0) {
						solutions.add(new byte[9][9]);
						return solutions;
					}
				}
			}
			// If neither of the above cases works, find a cell with the least number of possible values.
			int lowestPossible = -1;
			int lowestRow = -1;
			int lowestCol = -1;
			for (int row = 0; row < 9; row++) {
				for (int col = 0; col < 9; col++) {
					if ((lowestPossible == -1 && cellEmpty(row, col, grid))
							|| (numPossible[row][col] != 0 && numPossible[row][col] < lowestPossible)) {
						lowestRow = row;
						lowestCol = col;
						lowestPossible = numPossible[row][col];
					}
				}
			}
			
			ArrayList<Byte> pValues = getPossibleValues(lowestRow, lowestCol,
					grid);
			ArrayList<byte[][]> pGrids = new ArrayList<byte[][]>();


			// For each of the possible values for the cell, enter the value in the grid and call the function again. Add all the results to the ArrayList, then return the ArrayList.
			for (byte pValue : pValues) {
				byte[][] newGrid = twoDimensionalCopy(grid);
				enterValue(pValue, lowestRow, lowestCol, newGrid);
				pGrids.add(newGrid);				
				solutions.addAll(solve(newGrid));
			}			
			return solutions;
		}
	}
	// Returns a byte[][] with cells representing the number of possible values for each cell.
	private byte[][] getNumPossible(byte[][] grid) {
		byte[][] numPossible = new byte[9][9];
		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++) {
				numPossible[row][col] = 0;
				if (cellEmpty(row, col, grid)) {
					for (int value = 1; value <= 9; value++) {
						if (!anyHas(value, row, col, grid)) {
							numPossible[row][col]++;
						}
					}
				}
			}
		}
		return numPossible;
	}
	// Returns an ArrayList with all the possible values for a given cell.
	private ArrayList<Byte> getPossibleValues(int row, int col, byte[][] grid) {
		ArrayList<Byte> possibleValues = new ArrayList<Byte>();
		for (byte value = 1; value <= 9; value++) {
			if (!anyHas(value, row, col, grid)) {
				possibleValues.add(value);
			}
		}
		return possibleValues;
	}
	// Returns true if there are no empty (zero) cells in a grid.
	private boolean isFull(byte[][] grid) {
		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++) {
				if (grid[row][col] == 0)
					return false;
			}
		}
		return true;
	}
	// Enters a value in the indicated location on a grid.
	private void enterValue(byte value, int row, int col, byte[][] grid) {
		grid[row][col] = value;
	}
	// Returns true if a cell in the same row, column, or section has the same value as the given one. 
	private boolean anyHas(int value, int row, int col, byte[][] grid) {
		return (rowHas(value, row, grid) || colHas(value, col, grid) || sectionHas(
				value, row, col, grid));
	}
	// Returns true if a cell in the same row has the same value as the given one.
	private boolean rowHas(int value, int row, byte[][] grid) {
		for (int col = 0; col < 9; col++) {
			if (grid[row][col] == value) {
				return true;
			}
		}
		return false;
	}
	// Returns true if a cell in the same column has the same value as the given one.
	private boolean colHas(int value, int col, byte[][] grid) {
		for (int row = 0; row < 9; row++) {
			if (grid[row][col] == value) {
				return true;
			}
		}
		return false;
	}
	// Returns true if a cell in the same section has the same value as the given one.
	private boolean sectionHas(int value, int row, int col, byte[][] grid) {
		for (int r = 3 * (row / 3); r < 3 * (row / 3) + 3; r++) {
			for (int c = 3 * (col / 3); c < 3 * (col / 3) + 3; c++) {
				if (grid[r][c] == value) {
					return true;
				}
			}
		}
		return false;
	}
	// Returns true if a cell is empty.
	private boolean cellEmpty(int row, int col, byte[][] grid) {
		return (grid[row][col] == 0);
	}
	// Returns a byte[][] with identical values as the given one. This is used to clone values without copying by reference.
	private byte[][] twoDimensionalCopy(byte[][] grid) {
		byte[][] copy = new byte[9][9];
		for (int row = 0; row < 9; row++) {
			copy[row] = Arrays.copyOf(grid[row], 9);
		}
		return copy;
	}
	// Returns a formatted String to print the grid.
	public static String gridToString(byte[][] grid) {
		String str = "";
		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++) {
				str+= (grid[row][col] == 0 ? "-" : grid[row][col]);

				if ((col + 1) % 3 == 0) {
					str+= " ";
				}
			}
			str+= "\n";
			if ((row + 1) % 3 == 0) {
				str+="\n";
			}
		}
		return str;
	}
}
