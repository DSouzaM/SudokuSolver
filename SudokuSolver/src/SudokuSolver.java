import java.util.HashSet;

public class SudokuSolver {
	byte[][] grid;
	byte[][][] possibleValues;

	public SudokuSolver(byte[][] values) {
		grid = values;
		possibleValues = new byte[9][9][9];
		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++) {
				byte value = grid[row][col];
				if (value != 0) {
					useValue(value,row,col);
				}
			}
		}
	}

	public void printGrid() {
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
	public void printUsedGrid(int value) {
		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++){ 
				System.out.print(possibleValues[value-1][row][col]);
				if ((col+1) % 3 == 0){
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

	public boolean isFull() {
		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++) {
				if (grid[row][col] == 0)
					return false;
			}
		}
		return true;
	}

	public void useValue(byte value, int row, int col) {
		for (int x = 0; x < 9; x++) {
			possibleValues[value-1][row][x] = 1; //"uses" each cell in the row 
			possibleValues[value-1][x][col] = 1;//"uses" each cell in the col
			possibleValues[x][row][col] = 1; //"uses" the cell in each values' array
		}
		for (int r = 3 * (row / 3); r < 3 * (row / 3) + 3; r++) {
			for (int c = 3 * (col / 3); c < 3 * (col / 3) + 3; c++) {
				possibleValues[value-1][r][c] = 1;
			}
		}
	}
	public void enterValue(byte value, int row, int col) {
		grid[row][col]=value;
		useValue(value,row,col);
	}

	public boolean rowHas(int value, int row) {
		for (int col = 0; col < 9; col++) {
			if (grid[row][col] == value) {
				return true;
			}
		}
		return false;
	}

	public boolean colHas(int value, int col) {
		for (int row = 0; row < 9; row++) {
			if (grid[row][col] == value) {
				return true;
			}
		}
		return false;
	}

	public boolean sectionHas(int value, int row, int col) {
		for (int r = 3 * (row / 3); r < 3 * (row / 3) + 3; r++) {
			for (int c = 3 * (col / 3); c < 3 * (col / 3) + 3; c++) {
				if (grid[r][c] == value) {
					return true;
				}
			}
		}
		return false;
	}
}
