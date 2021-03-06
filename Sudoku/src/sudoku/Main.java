package sudoku;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**  
 * This class is the Main Class of the sudoku game.
 * Here the Sudoku Game starts. The game uses a 9x9 grid 
 * and using a backtracking algorithm to generate an unique sudoku
 * 
 * @author Cudelcu Valentin Emil
 * @version 1.0
 */
public class Main extends Application {
	/**
	 * The main method which launch the application
	 * @param args	main method args
	 */
	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * Creates a new Scene, set the Scene to the Stage
	 * and show the stage
	 * @param primaryStage	Application stage
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Sudoku");

		SudokuGame sudokuGame = new SudokuGame();
		Scene scene = new Scene(sudokuGame.getRoot(), 600, 500);
		scene.getStylesheets().add("style.css");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}
