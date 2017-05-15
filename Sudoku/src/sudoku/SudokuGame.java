package sudoku;


import javafx.scene.layout.AnchorPane;

public class SudokuGame {
	
	private AnchorPane root;
	private SudokuGrid sudokuGrid;
	private SudokuButtons sudokuButtons;

	public SudokuGame () {
		this.root = new AnchorPane();
		this.sudokuGrid = new SudokuGrid(this.root); //grid_size = 9; grid_subsize = 3....9x9 sudoku
		this.sudokuButtons = new SudokuButtons(this.sudokuGrid);
		

		AnchorPane.setLeftAnchor(this.sudokuGrid.getGrid(), 0.0);
		this.root.getChildren().addAll(
				this.sudokuGrid.getGrid(),
				this.sudokuButtons.getGameButtons(),
				this.sudokuButtons.getDigitButtons()
		);
	}
	
	public AnchorPane getRoot() {
		return this.root;
	}
}
