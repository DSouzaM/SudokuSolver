import java.util.ArrayList;
import java.util.HashSet;

public class SudokuSolver {


	public ArrayList<byte[][]> solve(byte[][] g) {
		ArrayList<byte[][]> solutions = new ArrayList<byte[][]>();
		byte[][] grid = g.clone();
		byte[][] numPossible = getNumPossible(g);
		// loop once and fill single possible spots
		boolean valueAdded = false;
		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++) {
				if (numPossible[row][col] == 1) {
					enterValue(getPossibleValues(row, col, grid).get(0), row,
							col, grid);
					numPossible = getNumPossible(g);
					valueAdded = true;
				}
			}
			if (row == 8 && valueAdded) {
				row = -1;
				System.out.println("Added value!");
				printGrid(grid);
				valueAdded = false;
			}
		}
		System.out.println("After filling in lone spots, the grid remaining is:");
		printGrid(grid);
		if (isFull(grid)) {
			System.out.println("This is a completed grid!");
			solutions.add(grid);
			return solutions;
		} 
		else if (hasNoPossible(getNumPossible(grid))){
			System.out.println("This grid has no solutions: ");
			printGrid(getNumPossible(grid));
			solutions.add(new byte[9][9]);
			return solutions;
		}
		//TODO impossible base case here
		else {
			int lowestPossible = -1;
			int lowestRow = -1;
			int lowestCol = -1;
			for (int row = 0; row < 9; row++) {
				for (int col = 0; col < 9; col++) {
					if ((lowestPossible == -1 && cellEmpty(row, col, grid))
							|| (numPossible[row][col] < lowestPossible)) {
						lowestRow = row;
						lowestCol = col;
						lowestPossible = numPossible[row][col];
					}
				}
			}
			// find a cell on the grid with the least # of possible values (n)
			ArrayList<Byte> pValues = getPossibleValues(lowestRow,
					lowestCol, grid);
			ArrayList<byte[][]> pGrids = new ArrayList<byte[][]>();
			
			System.out.println("This grid can go no further using lone spots: ");
			printGrid(getNumPossible(grid));
			System.out.println("Trying " + pValues.size() + " cases for row " + lowestRow + ", column " + lowestCol + ": " + pValues.toString());
			//create n new arrays and substitute each possible value into an array 
			for (byte pValue : pValues) {
				byte[][] newGrid = grid.clone();
				enterValue(pValue,lowestRow,lowestCol,newGrid);
				pGrids.add(newGrid);
			}
			for (byte[][] pGrid : pGrids){
				solutions.addAll(solve(pGrid));
			}
			return solutions;
		}
	}

	public byte[][] getNumPossible(byte[][] grid) {
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

	// TODO merge getPossibleValues and anyHas to avoid re-accessing everything
	public ArrayList<Byte> getPossibleValues(int row, int col, byte[][] grid) {
		ArrayList<Byte> possibleValues = new ArrayList<Byte>();
		for (byte value = 1; value <= 9; value++) {
			if (!anyHas(value, row, col, grid)) {
				possibleValues.add(value);
			}
		}
		return possibleValues;
	}

	public boolean hasNoPossible(byte[][] numPossible) {
		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++) {
				if (numPossible[row][col] > 0) {
					return false;
				}
			}
		}
		return true;
	}

	public void printGrid(byte[][] grid) {
		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++) {
				System.out.print(grid[row][col] == 0 ? "-" : grid[row][col]);

				if ((col + 1) % 3 == 0) {
					System.out.print(" ");
				}
			}
			System.out.println();
			if ((row + 1) % 3 == 0) {
				System.out.println();
			}
		}
	}

	public int getSection(int row, int col) {
		return (row / 3) * 3 + (col / 3);
	}

	public boolean isFull(byte[][] grid) {
		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++) {
				if (grid[row][col] == 0)
					return false;
			}
		}
		return true;
	}

	public void enterValue(byte value, int row, int col, byte[][] grid) {
		grid[row][col] = value;
	}

	public boolean anyHas(int value, int row, int col, byte[][] grid) {
		return (rowHas(value, row, grid) || colHas(value, col, grid) || sectionHas(
				value, row, col, grid));
	}

	public boolean rowHas(int value, int row, byte[][] grid) {
		for (int col = 0; col < 9; col++) {
			if (grid[row][col] == value) {
				return true;
			}
		}
		return false;
	}

	public boolean colHas(int value, int col, byte[][] grid) {
		for (int row = 0; row < 9; row++) {
			if (grid[row][col] == value) {
				return true;
			}
		}
		return false;
	}

	public boolean sectionHas(int value, int row, int col, byte[][] grid) {
		for (int r = 3 * (row / 3); r < 3 * (row / 3) + 3; r++) {
			for (int c = 3 * (col / 3); c < 3 * (col / 3) + 3; c++) {
				if (grid[r][c] == value) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean cellEmpty(int row, int col, byte[][] grid) {
		return (grid[row][col] == 0);
	}
}
