package sudoku;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
	public static void main (String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Sudoku");
		
		SudokuGame sudokuGame = new SudokuGame();
		Scene scene = new Scene(sudokuGame.getRoot(),600,500);
		scene.getStylesheets().add("style.css");	
		primaryStage.setScene(scene);
		primaryStage.show();	
	}
}
