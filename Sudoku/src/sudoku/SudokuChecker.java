package sudoku;

/**
 * 
 * @author Cudelcu Valentin Emil
 *
 *The SudokuChecker class is used to check the correctness of the selected cell
 *and if the user resolved the sudoku by comparing user values and the solver values
 */
public class SudokuChecker {

	/**
	 * SudokuChecker Class values (user input values)
	 */
	private int[][] checkerValues;
	
	/**
	 * SudokuSolver Class values
	 */
	private int[][] solverValues;

	/**
	 * Default constructor for SudokuGame
	 * Initializes the private variables
	 */
	public SudokuChecker() {
		this.checkerValues = new int[Utility.GRID_SIZE][Utility.GRID_SIZE];
		this.solverValues = new int[Utility.GRID_SIZE][Utility.GRID_SIZE];
	}

	/**
	 *Checks if user resolved the sudoku 
	 * @return TRUE if the sudoku is resolved, otherwise FALSE
	 */
	public boolean isResolved() {
		if (emptyCell())
			return false;
		if (!isValid())
			return false;

		return true;
	}

	/*
	 * Checks if the grid contains empty cells
	 */
	private boolean emptyCell() {
		for (int row = 0; row < Utility.GRID_SIZE; row++) {
			for (int col = 0; col < Utility.GRID_SIZE; col++) {
				if (checkerValues[row][col] == 0)
					return true;
			}
		}
		return false;
	}

	/*
	 * Checks if the checkerValues and solverValues contains same values
	 */
	private boolean isValid() {
		for (int row = 0; row < Utility.GRID_SIZE; row++) {
			for (int col = 0; col < Utility.GRID_SIZE; col++) {
				if (checkerValues[row][col] != solverValues[row][col])
					return false;
			}
		}
		return true;
	}

	/**
	 * Used in the createValidSudoku method of SudokuSolver Class
	 * to check if all the cells are filled
	 * 
	 * @param row - row index of the cell
	 * @param col - col index of the cell
	 * @return TRUE if table is filled
	 * @see SudokuSolver#createValidSudoku(int, int)
	 */
	public boolean isFilled(int row, int col) {
		return (row * col == 64); // 8x8 = 64...last cell
	}

	/**
	 * Checks if the value already exists in the row, col or 3x3 box
	 * @param values - array with current values
	 * @param row - row index of the cell
	 * @param col - col index of the cell
	 * @param num - number to check
	 * @return TRUE if every condition passed, otherwise FALSE
	 */
	public boolean isSafe(int[][] values, int row, int col, int num) {
		return !usedInRow(values, row, num) && !usedInCol(values, col, num)
				&& !usedInBox(values, row - row % 3, col - col % 3, num);
	}

	/*
	 * Check if value exists in the row
	 */
	private boolean usedInRow(int[][] values, int row, int num) {
		for (int col = 0; col < Utility.GRID_SIZE; col++) {
			if (values[row][col] == num)
				return true;
		}
		return false;
	}

	/*
	 * Check if value exists in the col
	 */
	private boolean usedInCol(int[][] values, int col, int num) {
		for (int row = 0; row < Utility.GRID_SIZE; row++) {
			if (values[row][col] == num)
				return true;
		}
		return false;
	}

	/*
	 * Check if value exists in the 3x3 box
	 */
	private boolean usedInBox(int[][] values, int boxStartRow, int boxStartCol, int num) {
		for (int row = 0; row < Utility.GRID_SUBSIZE; row++) {
			for (int col = 0; col < Utility.GRID_SUBSIZE; col++) {
				if (values[row + boxStartRow][col + boxStartCol] == num)
					return true;
			}
		}
		return false;
	}

	/**
	 * Sets the checker values
	 * user input values
	 * @param checkerValues - values including user input values
	 */
	public void setCheckerValues(int[][] checkerValues) {
		this.checkerValues = checkerValues;
	}

	/**
	 * Sets the solver values
	 * @param solverValues - values of the SudokuSolver Class
	 */
	public void setSolverValues(int[][] solverValues) {
		this.solverValues = solverValues;
	}

}
