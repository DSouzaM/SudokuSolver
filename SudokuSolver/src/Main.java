import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		String nums = new Scanner(System.in).next();
		SudokuSolver solver = new SudokuSolver();

		System.out.println(solver.solve(nums));


	}

}
