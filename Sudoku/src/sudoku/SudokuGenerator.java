package sudoku;

import java.util.Random;

public class SudokuGenerator {
	
	private int[][] generatorValues;
	private SudokuSolver sudokuSolver;
	
	public SudokuGenerator() {
		this.generatorValues = new int [Utility.GRID_SIZE] [Utility.GRID_SIZE];
		this.sudokuSolver = new SudokuSolver();
	}

	public void generateSudoku() {
		//generate a random valid sudoku for an empty table
		this.sudokuSolver.createValidSudoku(0, 0);
		this.generatorValues = Utility.copyArray(this.sudokuSolver.getSolverValues());
		Utility.printNums(this.generatorValues);
		
		int count = 0;
		int row = 0;
		int col = 0;
		boolean selected[][] = new boolean[9][9];
		int square = 0;
		int subSize = Utility.GRID_SUBSIZE;
		Random random = new Random();	
		
		while(count < 45){
			if(count % 9 == 0) square = 0;	
			row = random.nextInt(subSize) + (square / subSize) * subSize;
			col = random.nextInt(subSize) + (square % subSize) * subSize;
			if(selected[row][col]) continue;
			int num = this.generatorValues[row][col];
			
			//Set the cell to 0 and make a copy of generatorValues array
				selected[row][col] = true;
				this.generatorValues[row][col] = 0;
				int [][] values = Utility.copyArray(this.generatorValues);
				
			//If found a solution, set cell back to original num
			if(sudokuSolver.solveSudoku(values, num, row, col, -1)) {
				this.generatorValues[row][col] = num;
				continue;
			}
			sudokuSolver.addCellIndexesPair(row, col);
			square++;
			count++;
		}
	}
	
	public int[][] getGeneratorValues () {
		return this.generatorValues;
	}
	
	public SudokuSolver getSudokuSolver () {
		return this.sudokuSolver;
	}
}
