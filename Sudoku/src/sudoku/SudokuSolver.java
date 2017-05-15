package sudoku;

import java.util.ArrayList;
import java.util.List;

public class SudokuSolver {
	
	private int[] allowedNums;
	private List<CellIndexesPair> removedCells;
	private int[][] solverValues;
	private SudokuChecker sudokuChecker;
	
	public SudokuSolver () {
		this.removedCells = new ArrayList<>();
		this.allowedNums = new int[9]; //numbers from  1 to 9;
		this.solverValues = new int[Utility.GRID_SIZE][Utility.GRID_SIZE];
		this.sudokuChecker = new SudokuChecker();
		addNums();
	}
	
	private void addNums() {
		for (int i = 0; i < 9; i++) {
			this.allowedNums[i] = i + 1;
		}
	}

	public boolean solveSudoku (int[][] values, int forbiddenNum, int row, int col,int index) {
		for (int i = 0; i < Utility.GRID_SIZE; i++) {
			int allowedNum = this.allowedNums[i];	
			if(allowedNum == forbiddenNum) continue;

			if(sudokuChecker.isSafe(values,row, col, allowedNum)) {
				values[row][col] = allowedNum;	
				index++;
				if(index == this.removedCells.size()) return true;
				row = this.removedCells.get(index).row();
				col = this.removedCells.get(index).col();			
				if(solveSudoku(values, 0, row, col, index)) return true;
			}			
		}
		values[row][col] = 0;
		return false;
	}

	
	public void addCellIndexesPair(int row, int col) {
		this.removedCells.add(new CellIndexesPair(row,col));
	}
	
	public boolean createValidSudoku(int row, int col) {
		this.allowedNums = Utility.shuffleNums(this.allowedNums);
		for (int i = 0; i < Utility.GRID_SIZE; i++) {
			int allowedNum = this.allowedNums[i];	
			if(sudokuChecker.isSafe(this.solverValues,row, col, allowedNum)) {
				this.solverValues[row][col] = allowedNum;	
				if(sudokuChecker.isFilled(row, col)) return true;
				
				if (col == 8) {
					row++;
					col = 0;
				} else{
					col++;
				}
				if(createValidSudoku(row,col)) return true;
			}		
		}
		this.solverValues[row][col] = 0;
		return false;
	}	
	
	public int[][] getSolverValues() {
		return this.solverValues;
	}
}
