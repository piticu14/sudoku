package sudoku;

import java.util.Random;

/**
 * 
 * @author Cudelcu Valentin Emil
 *
 *The SudokuGenerator class use the SudokuSolver class
 *to create a full valid Sudoku game
 *Randomly choose a cell and remove the number,
 *then checks using the SudokuSolver class if the
 *sudoku game is still unique
 *@see #SudokuSolver
 */

public class SudokuGenerator {

	/**
	 * Two-Dimensional array which contains
	 * the valus of the generator
	 */
	private int[][] generatorValues;
	
	/**
	 * Contains the instance of the SudokuSolver class
	 */
	private SudokuSolver sudokuSolver;

	/**
	 * Default constructor for SudokuGenerator
	 * Initializes the generatorValues array and
	 * create a new instace of SudokuSolver Class
	 */
	public SudokuGenerator() {
		this.generatorValues = new int[Utility.GRID_SIZE][Utility.GRID_SIZE];
		this.sudokuSolver = new SudokuSolver();
	}

	/**
	 * Removes 45 numbers from the full valid sudoku table
	 * which is created by the createValidSudoku method
	 * @see SudokuSolver#createValidSudoku(int, int)
	 */
	public void generateSudoku() {
		// generate a random valid sudoku for an empty table
		this.sudokuSolver.createValidSudoku(0, 0);
		this.generatorValues = Utility.copyArray(this.sudokuSolver.getSolverValues());
		Utility.printNums(this.generatorValues);

		int count = 0;
		int row = 0;
		int col = 0;
		//Two-Dimensional boolean array to mark which cells were chose
		boolean selected[][] = new boolean[9][9]; 
		int square = 0;
		int subSize = Utility.GRID_SUBSIZE;
		Random random = new Random();

		//Selected a number from every 3x3 square until the count = 45
		while (count < 45) {
			if (count % 9 == 0)
				square = 0;
			row = random.nextInt(subSize) + (square / subSize) * subSize;
			col = random.nextInt(subSize) + (square % subSize) * subSize;
			if (selected[row][col])
				continue;
			int num = this.generatorValues[row][col];

			// Set the cell to 0 and make a copy of generatorValues array
			selected[row][col] = true;
			this.generatorValues[row][col] = 0;
			int[][] values = Utility.copyArray(this.generatorValues);

			// If found a solution, set cell back to original num
			if (sudokuSolver.solveSudoku(values, num, row, col, -1)) {
				this.generatorValues[row][col] = num;
				continue;
			}
			sudokuSolver.addCellIndexesPair(row, col);
			square++;
			count++;
		}
	}

	/**
	 * Returns the generator values
	 * @return int[][]
	 */
	public int[][] getGeneratorValues() {
		return this.generatorValues;
	}

	/**
	 * Retrns the instance of the SudokuSolver Class
	 * @return SudokuSolver
	 */
	public SudokuSolver getSudokuSolver() {
		return this.sudokuSolver;
	}
}
