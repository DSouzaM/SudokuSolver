import java.util.ArrayList;
import java.util.Arrays;

public class SudokuSolver {
	private static final int BOARD_SIZE = 9;
	private static final int INITIAL_VALUE = -1;

	// Recursively solves the grid input, returning the solved byte[][].
	public Grid solve(byte[][] g) {
		return solve(new Grid(g));
	}
	public Grid solve(String str) {
		return solve(new Grid(str));
	}
	public Grid solve(Grid grid) {
		byte[][] numPossible = grid.getNumPossible();
		// Fills cells where only one value is possible.
		// If a new value is added at any point in the loop, repeats the loop
		// until there are no cells with only one possible value.
		boolean valueAdded = false;
		for (int row = 0; row < BOARD_SIZE || valueAdded; row++) {
			// If at the end of the loop and a value was added, repeat.
			if (row >= BOARD_SIZE) {
				row = 0;
				valueAdded = false;
			}
			for (int col = 0; col < BOARD_SIZE; col++) {
				numPossible = grid.getNumPossible();
				if (numPossible[row][col] == 1) {
					grid.enterValue(grid.getPossibleValues(row, col).get(0),
							row, col);
					valueAdded = true;
				}
			}
		}

		// If the grid is full, then it must be a solution since all values
		// added are first checked to be valid.
		if (grid.isFull()) {
			return grid;
		}
		for (int row = 0; row < BOARD_SIZE; row++) {
			for (int col = 0; col < BOARD_SIZE; col++) {
				// If there exists an empty cell on the grid with no
				// possible values, return an empty byte[][].
				if (grid.hasCellEmpty(row, col)
						&& grid.getPossibleValues(row, col).size() == 0) {
					return null;
				}
			}
		}
		// If neither of the above cases is true, find a cell with the least
		// number of possible values.
		int lowestPossible = INITIAL_VALUE;
		int lowestRow = INITIAL_VALUE;
		int lowestCol = INITIAL_VALUE;
		for (int row = 0; row < BOARD_SIZE; row++) {
			for (int col = 0; col < BOARD_SIZE; col++) {
				if ((lowestPossible == INITIAL_VALUE && grid.hasCellEmpty(row, col))
						|| (numPossible[row][col] != 0 && numPossible[row][col] < lowestPossible)) {
					lowestRow = row;
					lowestCol = col;
					lowestPossible = numPossible[row][col];
				}
			}
		}

		// For each of the possible values for the cell, enter the value in
		// the grid and call the function again. Add all the results to the
		// ArrayList, then return the ArrayList.
		for (byte pValue : grid.getPossibleValues(lowestRow,lowestCol)) {
			Grid newGrid = grid.getCopy();
			newGrid.enterValue(pValue, lowestRow, lowestCol);
			newGrid = solve(newGrid);
			if (newGrid != null) {
				return newGrid;
			}
		}

		return null;
	}
}
