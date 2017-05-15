package sudoku;

import java.util.Arrays;
import java.util.Random;

/**
 * 
 * @author Cudelcu Valentin Emil
 *
 * The Utility class is a final class which contains
 * the grid and subgrid size, which are final ints.
 * Contains general methods which aren't specific for
 * the Sudoku classes
 */
public final class Utility {
	public static final int GRID_SIZE = 9;
	public static final int GRID_SUBSIZE = 3;

	private Utility() {
	} // Prevent the class from being constructed

	/**
	 * Makes a copy of a two dimensional array
	 * @param array	source array using in the copy process
	 * @return copy of the array
	 */
	public static int[][] copyArray(int[][] array) {
		int[][] newArray = new int[9][];
		for (int row = 0; row < 9; row++) {
			newArray[row] = Arrays.copyOf(array[row], array[row].length);
		}
		return newArray;
	}

	/**
	 * Prints a two-dimensional array which contains int values
	 * @param values	values for printing
	 */
	public static void printNums(int[][] values) {
		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++) {
				System.out.print(values[row][col]);
			}
			System.out.println();
		}
		System.out.println("----------------------------------------");
	}

	/**
	 * Shuffles the numbers in the array
	 * @param nums	one-dimensional array
	 * @return New one-dimensional array with shuffled numbers
	 */
	public static int[] shuffleNums(int[] nums) {
		Random random = new Random();
		for (int i = nums.length - 1; i > 0; i--) {
			int index = random.nextInt(i + 1);

			int a = nums[index];
			nums[index] = nums[i];
			nums[i] = a;
		}
		return nums;
	}
}
