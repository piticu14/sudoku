package sudoku;

public class SudokuChecker {
	
	private int[][] checkerValues;
	private int[][] solverValues;
	
	public SudokuChecker() {
		this.checkerValues = new int[Utility.GRID_SIZE][Utility.GRID_SIZE];
		this.solverValues = new int[Utility.GRID_SIZE][Utility.GRID_SIZE];
	}
	
	public boolean isResolved() {
		if(emptyCell()) return false;
		if(!isValid()) return false;
		
		return true;
	}
	
	private boolean emptyCell() {
		for(int row = 0; row < Utility.GRID_SIZE; row++) { 
			for(int col = 0; col < Utility.GRID_SIZE; col++){
				if(checkerValues[row][col] == 0) return true;
			}
		}
		return false;
	}
	
	private boolean isValid() {
		for(int row = 0; row < Utility.GRID_SIZE; row++) {
			for (int col = 0; col < Utility.GRID_SIZE; col++) {
				if(checkerValues[row][col] != solverValues[row][col]) return false;
			}
		}
		return true;
	}
	
	public boolean isFilled(int row, int col) {
		return (row * col == 64); //8x8 = 64...last cell
	}
	
	public boolean isSafe(int[][] values,int row, int col, int num) {
		return !usedInRow(values, row, num) &&
			   !usedInCol(values, col, num) &&
			   !usedInBox(values, row - row % 3, col - col % 3, num);
	}
	
	private boolean usedInRow(int[][] values, int row, int num) {
		for (int col = 0; col < Utility.GRID_SIZE; col++) {
			if(values[row][col] == num) return true;
		}
		return false;
	}
	
	private boolean usedInCol(int[][] values, int col, int num) {
		for (int row = 0; row < Utility.GRID_SIZE; row++) {
			if(values[row][col] == num) return true;
		}
		return false;
	}
	
	private boolean usedInBox(int[][] values, int boxStartRow, int boxStartCol, int num) {
		for(int row = 0; row < Utility.GRID_SUBSIZE; row++) {
			for (int col = 0; col < Utility.GRID_SUBSIZE; col++) {
				if (values[row + boxStartRow][col + boxStartCol] == num) return true;
			}
		}
		return false;
	}

	public void setCheckerValues(int[][] checkerValues) {
		this.checkerValues = checkerValues;
	}
	
	public void setSolverValues(int[][] solverValues) {
		this.solverValues = solverValues;
	}
	
}
