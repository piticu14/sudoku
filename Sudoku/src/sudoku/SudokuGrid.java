package sudoku;

import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class SudokuGrid {
	
	private TextField[][] sudokuCells;
	private GridPane sudokuGrid;
	private int selectedCellRow;
	private int selectedCellCol;
	private SudokuGenerator sudokuGenerator;
	private SudokuChecker sudokuChecker;
	private int[][] userSudokuValues;
	private AnchorPane gameRoot;
	
	public SudokuGrid (AnchorPane gameRoot) {	
		this.sudokuCells = new TextField[Utility.GRID_SIZE][Utility.GRID_SIZE];
		this.sudokuGrid = new GridPane();
		this.selectedCellRow = 0;
		this.selectedCellCol = 0;
		this.sudokuChecker = new SudokuChecker();
		this.userSudokuValues = new int[Utility.GRID_SIZE][Utility.GRID_SIZE];
		this.gameRoot = gameRoot;
		
		createSudokuCells();
		setGridValues(null);
		
		this.sudokuGrid.setPrefSize(432, 432); // size of cell = 48px(3em) * 9
		this.sudokuGrid.getStylesheets().add("style.css");
		
	}
	
	private void createSudokuCells() {
		for (int row = 0; row < Utility.GRID_SIZE; row++) {
			for(int col = 0; col < Utility.GRID_SIZE; col++) {
				StackPane cell = new StackPane();
                cell.getStyleClass().add("cell");
                cell.getChildren().add(createTextField(row,col));

                addBackground(cell,row,col);
                
				this.sudokuGrid.add(cell, col, row);
			}
		}	
	}
	
	private TextField createTextField(int row, int col) {
		this.sudokuCells[row][col] = new TextField() {
	          @Override
	            public void replaceText(int start, int end, String text) {
	                // If the replaced text would end up being invalid, then simply
	                // ignore this call!
	                if (text.matches("\\d?")) {
	                    super.setText(text);
	                    if(text != "") {
	                    	checkIfValid(row, col,Integer.parseInt(text));//check only if exists number
	                    }
                    	changeCellValue(row, col, text);
	                }
	            }
	        };
	     this.sudokuCells[row][col].setOnMouseClicked(new EventHandler<MouseEvent>() {
	    	    @Override
	    	    public void handle(MouseEvent mouseEvent) {
	    	        if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
	    	            cellSelected(row, col);
	    	        }
	    	    }
	    	});
	   return this.sudokuCells[row][col];
	}
	
	private void cellSelected(int row, int col) {
		this.selectedCellRow = row;
		this.selectedCellCol = col;
	}
	
	private void addBackground(StackPane cell, int row, int col) {
		String[] colors = {"#b0cbe1", "#cbe183", "#e18398","#b0e183", "#b8778a", "#e198b0", "#b08398", "#cb98b0", "#e1b0cb"};
		int colorIndex = 3 * (row / 3) + (col / 3);
		cell.setStyle("-fx-background-color: " + colors[colorIndex] + ";");
	}
	
	private void resetCells() {
		for(int row = 0; row < Utility.GRID_SIZE; row++) {
			for(int col = 0; col < Utility.GRID_SIZE; col++) {
				this.sudokuCells[row][col].setDisable(false);
				this.sudokuCells[row][col].setStyle("-fx-text-fill: black");
			}
		}
	}
	
	public void checkIfValid(int row, int col, int num) {
		if (this.sudokuChecker.isSafe(this.userSudokuValues, row, col, num)) {
			this.sudokuCells[row][col].setStyle("-fx-text-fill: black");
		}else{
			this.sudokuCells[row][col].setStyle("-fx-text-fill: red");
		}		
	}
	
	public void changeCellValue(int row, int col, String value) {
		if(value == ""){
			this.userSudokuValues[row][col] = 0;
		}else{
			this.userSudokuValues[row][col] = Integer.parseInt(value);
			this.sudokuChecker.setCheckerValues(this.userSudokuValues);
			if(this.sudokuChecker.isResolved()) finishGame();
		}
	}
	
	private void finishGame() {
		for(int row = 0; row < Utility.GRID_SIZE; row++) {
			for(int col = 0; col < Utility.GRID_SIZE; col++) {
				this.sudokuCells[row][col].setDisable(true);
			}
		}
		
		Text congratulationsText = new Text (10, 20, "Congratulations\nStart a new game");
		congratulationsText.setId("congratulations");
		AnchorPane.setTopAnchor(congratulationsText, 170.0);
		AnchorPane.setLeftAnchor(congratulationsText, 55.0);
		this.gameRoot.getChildren().add(congratulationsText);
	}
	public void setGridValues(int[][] values) {
		resetCells();
		if(values == null){
			this.sudokuGenerator = new SudokuGenerator();
			this.sudokuGenerator.generateSudoku();
			this.sudokuChecker.setSolverValues(this.sudokuGenerator.getSudokuSolver().getSolverValues());
			values = this.sudokuGenerator.getGeneratorValues();
			this.userSudokuValues = Utility.copyArray(this.sudokuGenerator.getGeneratorValues());
		}
		for (int row = 0; row < Utility.GRID_SIZE; row++) {
			for(int col = 0; col < Utility.GRID_SIZE; col++) {
				if(values[row][col] != 0){
					this.sudokuCells[row][col].setText(String.valueOf(values[row][col]));
					this.sudokuCells[row][col].setDisable(true);
				}else{
					this.sudokuCells[row][col].setText("");
				}
			}
		}	
	}
	
	public GridPane getGrid() {
		return this.sudokuGrid;
	}
		
	public int getSelectedCellRow() {
		return this.selectedCellRow;
	}
	
	public int getSelectedCellCol() {
		return this.selectedCellCol;
	}
	
	public TextField[][] getSudokuCells() {
		return this.sudokuCells;
	}
	
	public void setSudokuCells(TextField[][] sudokuCells) {
		this.sudokuCells = sudokuCells;
	}
	
	public SudokuGenerator getSudokuGenerator() {
		return this.sudokuGenerator;
	}
	
}
