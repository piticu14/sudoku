package sudoku;

public class CellIndexesPair {

	private final int row;
	private final int col;
	
	public CellIndexesPair (int aRow, int aCol) {
		row = aRow;
		col = aCol;
	}
	
	public int row() { return row;}
	public int col() { return col;}
}
