package sudoku;

import java.util.Random;

public class SudokuGenerator1 {

	private int[][] values;
	public static final int GRID_SIZE = 9;
	public static final int GRID_SUBSIZE = 3;
		
	public void generateNumbers() {
		Random randomGenerator = new Random();
		values = new int[GRID_SIZE][GRID_SIZE];
		int count = 0;
		
		/*
		while(count < 40) { //20 = number of filled cells at beginning
			int randomNumber = randomGenerator.nextInt(9) + 1;
			int randomCol = randomGenerator.nextInt(9);
			int randomRow = randomGenerator.nextInt(9);
			if(isValid(values,randomRow, randomCol, randomNumber)) {
				values[randomRow][randomCol] = randomNumber;
				count++;
			}
		}
		*/
	}
	
	private static boolean isValid(int[][] tempValues,int row, int col, int value) {
		return isEmpty(tempValues, row, col) && 
				!usedInRow(tempValues, row, value) &&
				!usedInCol(tempValues, col, value) &&
				!usedInBox(tempValues, row - row % 3, col - col % 3, value);
	}
	
	private static boolean isEmpty(int[][] tempValues,int row,int col) {
		return (tempValues[row][col] == 0) ? true : false;
	}
	
	private static boolean usedInRow(int[][] tempValues, int row, int value) {
		for(int col = 0; col < GRID_SIZE; col++) {
			if(tempValues[row][col] == value) return true;
		}
		return false;
	}
	
	private static boolean usedInCol(int[][] tempValues, int col, int value) {
		for(int row = 0; row < GRID_SIZE; row++) {
			if(tempValues[row][col] == value) return true;
		}
		return false;
	}
	
	private static boolean usedInBox(int[][] tempValues, int boxStartRow, int boxStartCol, int num) {
		for(int row = 0; row < GRID_SUBSIZE; row++) {
			for (int col = 0; col < GRID_SUBSIZE; col++) {
				if (tempValues[row + boxStartRow][col + boxStartCol] == num) return true;
			}
		}
		
		return false;
	}
	
	public int[][] getValues() {
		return values;
	}

	public void setValues(int[][] values) {
		this.values = values;
	}
}
