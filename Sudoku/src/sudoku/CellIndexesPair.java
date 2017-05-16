package sudoku;

/**
 *The CellIndexesPair class is used to create
 *a (row, col) pair where row and col are ints
 *Contains final variables because the variables
 *are initiated only once
 *
 * @author Cudelcu Valentin Emil
 * @version 1.0
 */
public class CellIndexesPair {

	private final int row;
	private final int col;
	
	/**
	 * Default constructor for CellIndexePair
	 * Sets the row and col
	 * @param aRow	cell row
	 * @param aCol	cell col
	 */

	public CellIndexesPair(int aRow, int aCol) {
		row = aRow;
		col = aCol;
	}

	/**
	 * Returns the row from the pair
	 * @return int
	 */
	public int row() {
		return row;
	}

	/**
	 * Returns the col from the pair
	 * @return int
	 */
	public int col() {
		return col;
	}
}
