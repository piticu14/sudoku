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

public class SudokuButtons {
	private VBox gameButtons;
	private HBox digitButtons;
	private SudokuGrid sudokuGrid;
	
	public SudokuButtons(SudokuGrid sudokuGrid) {
		this.sudokuGrid = sudokuGrid;
		this.gameButtons = new VBox();
		this.digitButtons = new HBox();
		
		createGameButtons();
		createDigitButtons();
	}
	
	private void createGameButtons() {
	    this.gameButtons.setSpacing(10);   
		this.gameButtons.getChildren().addAll(newGameButton(),hintButton(),resetButton());
		
		AnchorPane.setRightAnchor(this.gameButtons, 40.0);
		AnchorPane.setTopAnchor(this.gameButtons, 171.0);
	}
	
	private Button newGameButton() {
		Button newGame = new Button("New Game");
		newGame.setId("record-sales");
		newGame.setOnMouseClicked(new EventHandler<MouseEvent>() {
    	    @Override
    	    public void handle(MouseEvent mouseEvent) {
    	        if(mouseEvent.getButton().equals(MouseButton.PRIMARY))createNewGame();
    	    }
    	});
		return newGame;
	}
	
	private void createNewGame() {
		this.sudokuGrid.setGridValues(null);
	}
	
	private Button hintButton() {
		Button hint = new Button();
		hint.setText("Hint");
		hint.setPrefSize(75,20); //Size of newGame Button
		hint.setId("record-sales");
		hint.setOnMouseClicked(new EventHandler<MouseEvent>() {
    	    @Override
    	    public void handle(MouseEvent mouseEvent) {
    	        if(mouseEvent.getButton().equals(MouseButton.PRIMARY)) addHint();
    	    }
    	});
		return hint;
	}
	
	private void addHint() {
		Random random = new Random();	
		int row = random.nextInt(Utility.GRID_SUBSIZE);
		int col = random.nextInt(Utility.GRID_SUBSIZE);
		int [][] solverValues = this.sudokuGrid.getSudokuGenerator().getSudokuSolver().getSolverValues();
		this.sudokuGrid.getSudokuCells()[row][col].setText(String.valueOf(solverValues[row][col]));
		this.sudokuGrid.getSudokuCells()[row][col].setDisable(true);
	}
		
	private Button resetButton() {
		Button reset = new Button("Reset");
		reset.setPrefSize(75,20);  //Size of newGame Button
		reset.setId("record-sales");
		reset.setOnMouseClicked(new EventHandler<MouseEvent>() {
    	    @Override
    	    public void handle(MouseEvent mouseEvent) {
    	        if(mouseEvent.getButton().equals(MouseButton.PRIMARY)) resetGame();
    	    }
    	});
		return reset;
	}
	
	private void resetGame() {
		this.sudokuGrid.setGridValues(this.sudokuGrid.getSudokuGenerator().getGeneratorValues());
	}
	
	private HBox createDigitButtons() {
		this.digitButtons.setSpacing(20);
		for(int i = 1; i < 10;i++) {
			Button digit = new Button(Integer.toString(i));
			this.digitButtons.getChildren().add(digit);
			digit.setId("dark-blue");
			digit.setOnMouseClicked(new EventHandler<MouseEvent>() {
	    	    @Override
	    	    public void handle(MouseEvent mouseEvent) {
	    	        if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
	    	            InsertDigit(digit.getText());
	    	        }
	    	    }
	    	});
		}
		AnchorPane.setBottomAnchor(digitButtons, 20.0);
		AnchorPane.setLeftAnchor(digitButtons, 5.0);
		return digitButtons;
	}
	
	private void InsertDigit(String digit) {
		TextField[][] sudokuCells = this.sudokuGrid.getSudokuCells();
		int selectedRow = this.sudokuGrid.getSelectedCellRow();
		int selectedCol = this.sudokuGrid.getSelectedCellCol();
		sudokuCells[selectedRow][selectedCol].setText(digit);
		this.sudokuGrid.setSudokuCells(sudokuCells);
		this.sudokuGrid.changeCellValue(selectedRow, selectedCol, digit);
		this.sudokuGrid.checkIfValid(selectedRow, selectedCol, Integer.parseInt(digit));
	}
	
	public VBox getGameButtons() {
		return this.gameButtons;
	}
	
	public HBox getDigitButtons() {
		return this.digitButtons;
	}
}
