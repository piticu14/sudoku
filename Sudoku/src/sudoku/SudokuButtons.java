package sudoku;

import java.util.List;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 *The SudokuButtons class contains every button
 *which appears in the Application
 *The types of button includes digit buttons and 
 *game buttons
 *
 *@author Cudelcu Valentin Emil
 *@version 1.0
 */
public class SudokuButtons {
	
	/**
	 * Box which contains game buttons
	 * New Game, Reset Game, Hint
	 */
	private VBox gameButtons;
	
	/**
	 * Box which contains digits from 1 to 9
	 */
	private HBox digitButtons;
	
	/**
	 * Contains the instance of the SudokuGrid class
	 */	
	private SudokuGrid sudokuGrid;
	
	/**
	 * Default constructor for SudokuButtons
	 * Initializes the private variables
	 * Calls the createGameButtons and createDigitButtons methods
	 * @see #createGameButtons()
	 * @see #createDigitButtons()
	 * @param sudokuGridPane - contains the instance of sudokuGridPane class
	 */
	
	public SudokuButtons(SudokuGrid sudokuGrid) {
		this.sudokuGrid = sudokuGrid;
		this.gameButtons = new VBox();
		this.digitButtons = new HBox();

		createGameButtons();
		createDigitButtons();
	}

	/*
	 * Creates the Game Buttons - New Game,
	 * Reset Game, Hint using the following methods:
	 * newGameButton(), hintButton(), resetButton() 
	 */
	private void createGameButtons() {
		this.gameButtons.setSpacing(10);
		this.gameButtons.getChildren().addAll(newGameButton(), hintButton(), resetButton());

		AnchorPane.setRightAnchor(this.gameButtons, 40.0);
		AnchorPane.setTopAnchor(this.gameButtons, 171.0);
	}

	private Button newGameButton() {
		Button newGame = new Button("New Game");
		newGame.setId("record-sales");
		newGame.setOnAction(actionEvent -> createNewGame());
		return newGame;
	}

	/*
	 * Creates a new Game using the generator
	 */
	private void createNewGame() {
		this.sudokuGrid.setGridValues(null);
		this.gameButtons.getChildren().get(1).setDisable(false);

		int sizeOfRootPane = this.sudokuGrid.getRootPane().getChildren().size();
		
		//if the game is finished, delete the congratulations text
		if(this.sudokuGrid.getRootPane().getChildren().get(sizeOfRootPane - 1) instanceof Text){
			this.sudokuGrid.getRootPane().getChildren().remove(sizeOfRootPane - 1);
		}
	}

	private Button hintButton() {
		Button hint = new Button();
		hint.setText("Hint");
		hint.setPrefSize(75, 20); // Size of newGame Button
		hint.setId("record-sales");
		hint.setOnAction(actionEvent -> addHint());
		return hint;
	}

	/*
	 * Randomly select a cell and insert the right number
	 */
	private void addHint() {
		List<CellIndexesPair> removedCells = this.sudokuGrid.getSudokuGenerator().getSudokuSolver().getRemovedCells();
		int[][] solverValues = this.sudokuGrid.getSudokuGenerator().getSudokuSolver().getSolverValues();
		boolean hintFound = false;
		int removedCellIndex = 0;
		int row = 0;
		int col = 0;
		
		while(!hintFound) {
			row = removedCells.get(removedCellIndex).row();
			col = removedCells.get(removedCellIndex).col();
			TextField cell = this.sudokuGrid.getSudokuCells()[row][col];
			
			if(cell.getText().isEmpty()) {
				hintFound = true;
			} else{
				if(Integer.valueOf(cell.getText()) != solverValues[row][col]) {
					hintFound = true;
				}
			}
			removedCellIndex++;
			
			if(removedCellIndex == removedCells.size()){
				hintFound = true; //Table is full filled
				this.gameButtons.getChildren().get(1).setDisable(true);
			}
		}

		this.sudokuGrid.getSudokuCells()[row][col].setStyle("-fx-text-fill: #ff00b4");
		this.sudokuGrid.getSudokuCells()[row][col].setText(String.valueOf(solverValues[row][col]));
		this.sudokuGrid.getSudokuCells()[row][col].setDisable(true);
		this.sudokuGrid.changeUserCellValue(row, col, String.valueOf(solverValues[row][col]));
	}

	private Button resetButton() {
		Button reset = new Button("Reset");
		reset.setPrefSize(75, 20); // Size of newGame Button
		reset.setId("record-sales");
		reset.setOnAction(actionEvent -> resetGame());
		return reset;
	}

	/*
	 * Sets the gridValues to the values of the Generator
	 * User values disappears
	 */
	private void resetGame() {
		this.sudokuGrid.setGridValues(this.sudokuGrid.getSudokuGenerator().getGeneratorValues());
		this.gameButtons.getChildren().get(1).setDisable(false);
		int sizeOfRootPane = this.sudokuGrid.getRootPane().getChildren().size();
		
		//if the game is finished, delete the congratulations text
		if(this.sudokuGrid.getRootPane().getChildren().get(sizeOfRootPane - 1) instanceof Text){
			this.sudokuGrid.getRootPane().getChildren().remove(sizeOfRootPane - 1);
		}
	}

	/*
	 * Creates the Digit Buttons
	 * 10 buttons: 1 - 9 
	 */
	private HBox createDigitButtons() {
		this.digitButtons.setSpacing(20);
		for (int i = 1; i < 10; i++) {
			Button digit = new Button(Integer.toString(i));
			this.digitButtons.getChildren().add(digit);
			digit.setId("dark-blue");
			digit.setOnAction(actionEvent -> InsertDigit(digit.getText()));
		}
		AnchorPane.setBottomAnchor(digitButtons, 20.0);
		AnchorPane.setLeftAnchor(digitButtons, 5.0);
		return digitButtons;
	}

	/*
	 * Sets the cell to the digit of the clicked button
	 */
	private void InsertDigit(String digit) {
		TextField[][] sudokuCells = this.sudokuGrid.getSudokuCells();
		int selectedRow = this.sudokuGrid.getSelectedCellRow();
		int selectedCol = this.sudokuGrid.getSelectedCellCol();
		sudokuCells[selectedRow][selectedCol].setText(digit);
		this.sudokuGrid.checkIfCorectly(selectedRow, selectedCol, Integer.parseInt(digit));
		this.sudokuGrid.changeUserCellValue(selectedRow, selectedCol, digit);
	}

	/**
	 * Returns the box with the game buttons
	 * @return VBox
	 */
	public VBox getGameButtons() {
		return this.gameButtons;
	}

	/**
	 * Returns the box with the digit buttons
	 * @return HBox
	 */
	public HBox getDigitButtons() {
		return this.digitButtons;
	}
}
