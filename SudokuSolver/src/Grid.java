import java.util.Arrays;
import java.util.ArrayList;

public class Grid {
	private static final int BOARD_SIZE = 9;
	private byte[][] grid;

	public Grid(byte[][] gridValues) {
		grid = gridValues;
	}
	public Grid(String gridValues){
		grid = toByteArray(gridValues);
	}

	// Returns a Grid with a copy of the grid values.
	public Grid getCopy() {
		byte[][] copy = new byte[BOARD_SIZE][BOARD_SIZE];
		for (int row = 0; row < BOARD_SIZE; row++) {
			copy[row] = Arrays.copyOf(grid[row], BOARD_SIZE);
		}
		return new Grid(copy);
	}

	// Returns a byte[][] with cells representing the number of possible values
	// for each cell.
	public byte[][] getNumPossible() {
		byte[][] numPossible = new byte[BOARD_SIZE][BOARD_SIZE];
		for (int row = 0; row < BOARD_SIZE; row++) {
			for (int col = 0; col < BOARD_SIZE; col++) {
				numPossible[row][col] = 0;
				if (hasCellEmpty(row,col)) {
					for (int value = 1; value <= BOARD_SIZE; value++) {
						if (!anyHas(value, row, col)) {
							numPossible[row][col]++;
						}
					}
				}
			}
		}
		return numPossible;
	}

	// Returns an ArrayList with all the possible values for a given cell.
	public ArrayList<Byte> getPossibleValues(int row, int col) {
		ArrayList<Byte> possibleValues = new ArrayList<Byte>();
		for (byte value = 1; value <= BOARD_SIZE; value++) {
			if (!anyHas(value, row, col)) {
				possibleValues.add(value);
			}
		}
		return possibleValues;
	}

	// Enters the value in the indicated location.
	public void enterValue(byte value, int row, int col) {
		grid[row][col] = value;
	}

	// Returns true if a cell in the same row, column, or section has the same
	// value as the given one.
	private boolean anyHas(int value, int row, int col) {
		return (rowHas(value, row) || colHas(value, col) || sectionHas(value,
				row, col));
	}

	// Returns true if a cell in the same row has the same value as the given
	// one.
	private boolean rowHas(int value, int row) {
		for (int col = 0; col < BOARD_SIZE; col++) {
			if (grid[row][col] == value) {
				return true;
			}
		}
		return false;
	}

	// Returns true if a cell in the same column has the same value as the given
	// one.
	private boolean colHas(int value, int col) {
		for (int row = 0; row < BOARD_SIZE; row++) {
			if (grid[row][col] == value) {
				return true;
			}
		}
		return false;
	}

	// Returns true if a cell in the same section has the same value as the
	// given one.
	private boolean sectionHas(int value, int row, int col) {
		for (int r = 3 * (row / 3); r < 3 * (row / 3) + 3; r++) {
			for (int c = 3 * (col / 3); c < 3 * (col / 3) + 3; c++) {
				if (grid[r][c] == value) {
					return true;
				}
			}
		}
		return false;
	}

	// Returns true if there are no empty (zero) cells in a grid.
	public boolean isFull() {
		for (int row = 0; row < BOARD_SIZE; row++) {
			for (int col = 0; col < BOARD_SIZE; col++) {
				if (grid[row][col] == 0)
					return false;
			}
		}
		return true;
	}
	
	public boolean hasCellEmpty(int row,int col){
		return (grid[row][col] == 0);
	}
	public static byte[][] toByteArray(String nums){ 
		byte[][] grid = new byte[9][9];
		for (int num = 0; num < BOARD_SIZE*BOARD_SIZE; num++){
			grid[num/9][num%9] = Byte.parseByte(nums.charAt(num)+"");
		}
		return grid;
	}
	public static String toSingleString(byte[][] nums){
		String singleString = "";
		for (byte[] row : nums){
			for (byte cell : row) {
				singleString+=cell;
			}
		}
		return singleString;
	}

	@Override
	// Returns a formatted String to print the grid.
	public String toString() {

		String str = "";
		for (int row = 0; row < BOARD_SIZE; row++) {
			for (int col = 0; col < BOARD_SIZE; col++) {
				str += (grid[row][col] == 0 ? "-" : grid[row][col]);
				if ((col + 1) % 3 == 0) {
					str += " ";
				}
			}
			str += "\n";
			if ((row + 1) % 3 == 0) {
				str += "\n";
			}
		}
		return str;
	}
}
