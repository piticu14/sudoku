package sudoku;

import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

/**
 * 
 * @author Cudelcu Valentin Emil
 *
 *The SudokuGrid class creates a 9x9 grid which contains
 *numbers from 1 to 9. This is the grid where the player
 *will solve the sudoku
 */

public class SudokuGrid {

	/**
	 * Two-dimensional array which contains TextFields
	 * TextField contains one number from 1 to 9
	 */
	private TextField[][] sudokuCells;
	
	/**
	 * Grid for the TextFields
	 */
	private GridPane sudokuGrid;
	
	/**
	 * Index of the selected row
	 * Used for the digit buttons
	 * @see SudokuButtons
	 */
	private int selectedCellRow;
	
	/**
	 * Index of the selected col
	 * Used for the digit buttons
	 * @see SudokuButtons
	 */
	private int selectedCellCol;
	
	/**
	 * Generates an unique sudoku
	 * @see SudokuGenerator
	 */
	private SudokuGenerator sudokuGenerator;
	
	/**
	 * Checks the correctness of the grid
	 */
	private SudokuChecker sudokuChecker;
	
	/**
	 * Two-dimensional int array which
	 * contains the user's input
	 */
	private int[][] userSudokuValues;
	
	/**
	 * Instance of the gameRoot (Main Root)
	 */
	private AnchorPane gameRoot;
	
	/**
	 * Default constructor for SudokuGame
	 * Initializes the private variables
	 * Call the createSudokuCells method and the setGridValues method
	 * Sets the the size of the grid
	 * 
	 * @param gameRoot - instance of the MainRoot
	 * @see SudokuGame
	 */

	public SudokuGrid(AnchorPane gameRoot) {
		this.sudokuCells = new TextField[Utility.GRID_SIZE][Utility.GRID_SIZE];
		this.sudokuGrid = new GridPane();
		this.selectedCellRow = 0;
		this.selectedCellCol = 0;
		this.sudokuChecker = new SudokuChecker();
		this.userSudokuValues = new int[Utility.GRID_SIZE][Utility.GRID_SIZE];
		this.gameRoot = gameRoot;

		createSudokuCells();
		setGridValues(null);

		this.sudokuGrid.setPrefSize(432, 432); // size of cell set in style.css  = 48px(3em) * 9
		this.sudokuGrid.getStylesheets().add("style.css");

	}

	/*
	 * Creates 9x9 = 81 cells using StackPane and adds them to the sudokuGrid
	 */
	private void createSudokuCells() {
		for (int row = 0; row < Utility.GRID_SIZE; row++) {
			for (int col = 0; col < Utility.GRID_SIZE; col++) {
				StackPane cell = new StackPane();
				cell.getStyleClass().add("cell");
				cell.getChildren().add(createTextField(row, col));

				addBackground(cell, row, col);

				this.sudokuGrid.add(cell, col, row);
			}
		}
	}

	/*
	 * Creates a TextField for every cell and add some properties
	 * TextField can have only numbers from 1 to 9
	 */
	private TextField createTextField(int row, int col) {
		this.sudokuCells[row][col] = new TextField() {
			@Override
			public void replaceText(int start, int end, String text) {
				// If the replaced text would end up being invalid, then simply
				// ignore this call!
				if (text.matches("\\d?")) {
					super.setText(text);
					if (text != "") { // check only if exists number
						checkIfCorectly(row, col, Integer.parseInt(text));
					}
					changeUserCellValue(row, col, text);
				}
			}
		};
		this.sudokuCells[row][col].setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
					cellSelected(row, col);
				}
			}
		});
		return this.sudokuCells[row][col];
	}

	/*
	 * When user selects a cell sets the selectedCellRow
	 * and selectedCellCol 
	 */
	private void cellSelected(int row, int col) {
		this.selectedCellRow = row;
		this.selectedCellCol = col;
	}

	private void addBackground(StackPane cell, int row, int col) {
		String[] colors = { "#b0cbe1", "#cbe183", "#e18398", "#b0e183", "#b8778a", "#e198b0", "#b08398", "#cb98b0",
				"#e1b0cb" };
		int colorIndex = 3 * (row / 3) + (col / 3);
		cell.setStyle("-fx-background-color: " + colors[colorIndex] + ";");
	}

	/*
	 * Sets cells to default (enabled, color = black)
	 */
	private void resetCells() {
		for (int row = 0; row < Utility.GRID_SIZE; row++) {
			for (int col = 0; col < Utility.GRID_SIZE; col++) {
				this.sudokuCells[row][col].setDisable(false);
				this.sudokuCells[row][col].setStyle("-fx-text-fill: black");
			}
		}
	}

	/*
	 * Checks the added number
	 * If its already exists in the row, col or 3x3 box
	 * set the color to red, otherwise set to black
	 */
	public void checkIfCorectly(int row, int col, int num) {
		if (this.sudokuChecker.isSafe(this.userSudokuValues, row, col, num)) {
			this.sudokuCells[row][col].setStyle("-fx-text-fill: black");
		} else {
			this.sudokuCells[row][col].setStyle("-fx-text-fill: red");
		}
	}

	/**
	 * Changes the value of the userSudokuValues int array
	 * at the position row and col 
	 * @param row - row index of the new value
	 * @param col - col index of the new value
	 * @param value - the new user input value
	 */
	public void changeUserCellValue(int row, int col, String value) {
		if (value == "") {
			this.userSudokuValues[row][col] = 0;
		} else {
			this.userSudokuValues[row][col] = Integer.parseInt(value);
			this.sudokuChecker.setCheckerValues(this.userSudokuValues);
			if (this.sudokuChecker.isResolved())
				finishGame();
		}
	}

	/*
	 * Finish the game ==> disable every Cell
	 */
	private void finishGame() {
		for (int row = 0; row < Utility.GRID_SIZE; row++) {
			for (int col = 0; col < Utility.GRID_SIZE; col++) {
				this.sudokuCells[row][col].setDisable(true);
			}
		}

		Text congratulationsText = new Text(10, 20, "Congratulations\nStart a new game");
		congratulationsText.setId("congratulations");
		AnchorPane.setTopAnchor(congratulationsText, 170.0);
		AnchorPane.setLeftAnchor(congratulationsText, 55.0);
		this.gameRoot.getChildren().add(congratulationsText);
	}

	/**
	 * Sets the GridValues by adding the values of the Generator
	 * Generator generates an unique sudoku
	 * @param values - null when starts a new game
	 * 				 - generator values when resets the game
	 * @see SudokuButtons#resetGame()
	 * @see SudokuGenerator#generateSudoku()
	 */
	public void setGridValues(int[][] values) {
		resetCells();
		if (values == null) {
			this.sudokuGenerator = new SudokuGenerator();
			this.sudokuGenerator.generateSudoku();
			this.sudokuChecker.setSolverValues(this.sudokuGenerator.getSudokuSolver().getSolverValues());
			values = this.sudokuGenerator.getGeneratorValues();
			this.userSudokuValues = Utility.copyArray(this.sudokuGenerator.getGeneratorValues());
		}
		for (int row = 0; row < Utility.GRID_SIZE; row++) {
			for (int col = 0; col < Utility.GRID_SIZE; col++) {
				if (values[row][col] != 0) {
					this.sudokuCells[row][col].setText(String.valueOf(values[row][col]));
					this.sudokuCells[row][col].setDisable(true);
				} else {
					this.sudokuCells[row][col].setText("");
				}
			}
		}
	}

	/**
	 * Returns the Sudoku Grid
	 * @return GridPane
	 */
	public GridPane getGrid() {
		return this.sudokuGrid;
	}

	/**
	 * Returns the SelectedCellRow
	 * The Cell selected by user
	 * @return int
	 */
	public int getSelectedCellRow() {
		return this.selectedCellRow;
	}

	/**
	 * Returns the SelectedCellCol
	 * The Cell selected by user
	 * @return int
	 */
	public int getSelectedCellCol() {
		return this.selectedCellCol;
	}
	
	/**
	 * Returns the array of Sudoku Cells
	 * @return Two-Dimensional array with TextFields
	 */
	public TextField[][] getSudokuCells() {
		return this.sudokuCells;
	}

	/**
	 * Sets the sudokuCells array
	 * Used in the SudokuButtons class
	 * @see SudokuButtons#InsertDigit()
	 * @param sudokuCells
	 */
	public void setSudokuCells(TextField[][] sudokuCells) {
		this.sudokuCells = sudokuCells;
	}

	/**
	 * Returns the instace of the SudokuGenerator
	 * @return SudokuGenerator instance
	 */
	public SudokuGenerator getSudokuGenerator() {
		return this.sudokuGenerator;
	}

}
