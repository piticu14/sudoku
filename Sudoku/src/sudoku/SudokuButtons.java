package sudoku;

import java.util.Random;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * 
 * @author Cudelcu Valentin Emil
 *
 *The SudokuButtons class contains every button
 *which appears in the Application
 *The types of button includes digit buttons and 
 *game buttons
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
	 * Contains the nstace of the SudokuGrid class
	 */
	private SudokuGrid sudokuGrid;

	/**
	 * Default constructor for SudokuButtons
	 * Initializes the private variables
	 * Calls the createGameButtons and createDigitButtons methods
	 * @see #createGameButtons()
	 * @see #createDigitButtons()
	 * @param sudokuGrid - contains the instace of SudokuGrid class
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
		newGame.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				if (mouseEvent.getButton().equals(MouseButton.PRIMARY))
					createNewGame();
			}
		});
		return newGame;
	}

	/*
	 * Creates a new Game using the generator
	 */
	private void createNewGame() {
		this.sudokuGrid.setGridValues(null);
	}

	private Button hintButton() {
		Button hint = new Button();
		hint.setText("Hint");
		hint.setPrefSize(75, 20); // Size of newGame Button
		hint.setId("record-sales");
		hint.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				if (mouseEvent.getButton().equals(MouseButton.PRIMARY))
					addHint();
			}
		});
		return hint;
	}

	/*
	 * Randomly select a row and insert the right number
	 */
	private void addHint() {
		Random random = new Random();
		int row = random.nextInt(Utility.GRID_SUBSIZE);
		int col = random.nextInt(Utility.GRID_SUBSIZE);
		int[][] solverValues = this.sudokuGrid.getSudokuGenerator().getSudokuSolver().getSolverValues();
		this.sudokuGrid.getSudokuCells()[row][col].setText(String.valueOf(solverValues[row][col]));
		this.sudokuGrid.getSudokuCells()[row][col].setDisable(true);
	}

	private Button resetButton() {
		Button reset = new Button("Reset");
		reset.setPrefSize(75, 20); // Size of newGame Button
		reset.setId("record-sales");
		reset.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				if (mouseEvent.getButton().equals(MouseButton.PRIMARY))
					resetGame();
			}
		});
		return reset;
	}

	/*
	 * Sets the gridValues to the values of the Generator
	 * User values disappears
	 */
	private void resetGame() {
		this.sudokuGrid.setGridValues(this.sudokuGrid.getSudokuGenerator().getGeneratorValues());
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
			digit.setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent mouseEvent) {
					if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
						InsertDigit(digit.getText());
					}
				}
			});
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
		this.sudokuGrid.setSudokuCells(sudokuCells);
		this.sudokuGrid.changeUserCellValue(selectedRow, selectedCol, digit);
		this.sudokuGrid.checkIfCorectly(selectedRow, selectedCol, Integer.parseInt(digit));
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
