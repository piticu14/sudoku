package sudoku;

import javafx.scene.layout.AnchorPane;

/** 
 * @author Cudelcu Valentin Emil
 *
 *The SudokuGame class creates an instance of the SudokuGrid and
 *SudokuButtons class. It contains the AnchorPane root, which is
 *the main pane of the application
 */

public class SudokuGame {

	/**
	 * Main pane of the application
	 */
	private AnchorPane root;
	
	/**
	 * 9x9 SudokuGrid
	 */
	private SudokuGrid sudokuGrid;
	
	/**
	 * Application buttons
	 */
	private SudokuButtons sudokuButtons;

	/**
	 * Default constructor for SudokuGame
	 * Creates instances of AnchorPane (Main Pane), SudokuGrid
	 * and SudokuButtons
	 * Adds the SudokuGrid and the SudokuButtons to the root
	 */
	public SudokuGame() {
		this.root = new AnchorPane();
		this.sudokuGrid = new SudokuGrid(this.root); // grid_size = 9; grid_subsize = 3....9x9 sudoku
		this.sudokuButtons = new SudokuButtons(this.sudokuGrid);

		AnchorPane.setLeftAnchor(this.sudokuGrid.getGrid(), 0.0);
		this.root.getChildren().addAll(this.sudokuGrid.getGrid(), this.sudokuButtons.getGameButtons(),
				this.sudokuButtons.getDigitButtons());
	}

	/**
	 * Returns the instace of the root, Main Pane of the Application
	 * @return AnchorPane
	 */
	public AnchorPane getRoot() {
		return this.root;
	}
}
