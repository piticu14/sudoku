package sudoku;

import javafx.scene.layout.AnchorPane;

/** 
 *The SudokuGame class creates an instance of the sudokuGridPane and
 *SudokuButtons class. It contains the AnchorPane root, which is
 *the main pane of the application
 *
 * @author Cudelcu Valentin Emil
 * @version 1.0
 */

public class SudokuGame {

	/**
	 * Main pane of the application
	 */
	private AnchorPane root;
	
	/**
	 * 9x9 sudokuGrid
	 */
	private SudokuGrid sudokuGrid;
	
	/**
	 * Application buttons
	 */
	private SudokuButtons sudokuButtons;

	/**
	 * Default constructor for SudokuGame
	 * Creates instances of AnchorPane (Main Pane), sudokuGridPane
	 * and SudokuButtons
	 * Adds the sudokuGridPane and the SudokuButtons to the root
	 */
	public SudokuGame() {
		this.root = new AnchorPane();
		this.sudokuGrid = new SudokuGrid(this.root); // grid_size = 9; grid_subsize = 3....9x9 sudoku
		this.sudokuButtons = new SudokuButtons(this.sudokuGrid);

		AnchorPane.setLeftAnchor(this.sudokuGrid.getGridPane(), 0.0);
		this.root.getChildren().addAll(this.sudokuGrid.getGridPane(), this.sudokuButtons.getGameButtons(),
				this.sudokuButtons.getDigitButtons());
	}

	/**
	 * Returns the instance of the root, Main Pane of the Application
	 * @return AnchorPane
	 */
	public AnchorPane getRoot() {
		return this.root;
	}
}
