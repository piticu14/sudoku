package sudoku;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Cudelcu Valentin Emil
 *
 *The SudokuSolver create a full valid sudoku game
 *for an empty table
 *Resolves Sudoku for a table which is not empty
 */
public class SudokuSolver {

	/**
	 * Array of integer with number from 1 to 9
	 */
	private int[] allowedNums;
	
	/**
	 * A list of CellIndexesPair instances which contains
	 * the removed cells
	 * @see CellIndexesPair 
	 */
	private List<CellIndexesPair> removedCells;
	
	/**
	 * Two-Dimensional array with the values of the solver
	 */
	private int[][] solverValues;
	
	/**
	 * Instance of the SudokuChecker class
	 */
	private SudokuChecker sudokuChecker;

	/**
	 * Default constructor for SudokuGame
	 * Initializes the private variables
	 * Call the addNums method
	 * @see SudokuSolver#addNums()
	 */
	public SudokuSolver() {
		this.removedCells = new ArrayList<>();
		this.allowedNums = new int[9]; // numbers from 1 to 9;
		this.solverValues = new int[Utility.GRID_SIZE][Utility.GRID_SIZE];
		this.sudokuChecker = new SudokuChecker();
		addNums();
	}

	/*
	 * Adds to allowedNums array numbers from 1 to 9
	 */
	private void addNums() {
		for (int i = 0; i < 9; i++) {
			this.allowedNums[i] = i + 1;
		}
	}

	/**
	 * Resolve sudoku using backtracking algorithm
	 * Tries to make a new sudoku for the values array
	 * Fills the last removed cell and then fills the cells from the
	 * removedCells array
	 * @param values - Two-Dimensional array (not empty) which is used to create another valid sudoku
	 * @param forbiddenNum - number which cannot be used in the last removed cell
	 * @param row	row of the last removed cell value
	 * @param col	col of the last removed col value
	 * @param index	index of the CellIndexesPair array (starts at -1)
	 * @return TRUE if a new valid Sudoku found, otherwise FALSE
	 * @see SudokuGenerator#generateSudoku()
	 */
	public boolean solveSudoku(int[][] values, int forbiddenNum, int row, int col, int index) {
		for (int i = 0; i < Utility.GRID_SIZE; i++) {
			int allowedNum = this.allowedNums[i];
			if (allowedNum == forbiddenNum)
				continue;

			if (sudokuChecker.isSafe(values, row, col, allowedNum)) {
				values[row][col] = allowedNum;
				index++; //if last removed cell was filled then try to fill the removed cells from the List
				if (index == this.removedCells.size())
					return true;
				row = this.removedCells.get(index).row();
				col = this.removedCells.get(index).col();
				if (solveSudoku(values, 0, row, col, index))
					return true;
			}
		}
		values[row][col] = 0;
		return false;
	}

	/**
	 * Adds the row and col indexes of the last removed cell
	 * if the cell was removed and sudoku is still unique
	 * @param row	row of the last removed cell
	 * @param col	col of the last removed cell
	 */
	public void addCellIndexesPair(int row, int col) {
		this.removedCells.add(new CellIndexesPair(row, col));
	}

	/**
	 * Creates a full valid sudoku using an empty table
	 * allowedNums array is every time shuffled
	 * It helps to avoid creating the same sudoku again
	 * @param row	row used in backtracking algorithm to fill the table
	 * @param col	col used in backtracking algorithm to fill the table
	 * @return	TRUE if the sudoku was created, otherwise FALSE
	 */
	public boolean createValidSudoku(int row, int col) {
		this.allowedNums = Utility.shuffleNums(this.allowedNums);
		for (int i = 0; i < Utility.GRID_SIZE; i++) {
			int allowedNum = this.allowedNums[i];
			if (sudokuChecker.isSafe(this.solverValues, row, col, allowedNum)) {
				this.solverValues[row][col] = allowedNum;
				if (sudokuChecker.isFilled(row, col))
					return true;

				/*
				 * If gets to the last value of the row, then go to next row
				 * and start from the first value
				 */
				if (col == 8) {
					row++;
					col = 0;
				} else {
					col++;
				}
				if (createValidSudoku(row, col))
					return true;
			}
		}
		this.solverValues[row][col] = 0;
		return false;
	}

	/**
	 * Returns the solver values (a full valid sudoku table)
	 * @return int[][]
	 */
	public int[][] getSolverValues() {
		return this.solverValues;
	}
}
