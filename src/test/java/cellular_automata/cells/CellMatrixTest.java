package cellular_automata.cells;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class CellMatrixTest {
	@Test
	void cellsCanBeSet() {
		final CellMatrix matrix = new CellMatrix(1, 1);

		assertNotNull(matrix.getCell(0, 0));
	}

	@Test
	void anExceptionIsThrownWhenSettingOutsideTheBoundaries() {
		final CellMatrix matrix = new CellMatrix(1, 1);

		assertThrows(IndexOutOfBoundsException.class, () -> matrix.set(1, 2, new Cell()));
	}

	@Test
	void anExceptionIsThrownWhenGettingOutsideTheBoundaries() {
		final CellMatrix matrix = new CellMatrix(1, 1);

		assertThrows(IndexOutOfBoundsException.class, () -> matrix.getCell(1, 2));
	}

	@Test
	void matrixCanBeCloned() throws CloneNotSupportedException {
		final CellMatrix matrix = new CellMatrix(1, 1);

		final CellMatrix clone = (CellMatrix) matrix.clone();

		assertTrue(matrix != clone);
	}

	@Test
	void clonedMatrixContentsAreNotTheOriginal() throws CloneNotSupportedException {
		final Cell originalCell = new Cell();
		final CellMatrix matrix = new CellMatrix(1, 1);

		final CellMatrix clone = (CellMatrix) matrix.clone();

		assertTrue(originalCell != clone.getCell(0, 0));
	}

	@Test
	void clonedMatrixCellsAreEqualToOriginal() throws CloneNotSupportedException {
		final Cell originalCell = new Cell();
		final CellMatrix matrix = new CellMatrix(1, 1);

		final CellMatrix clone = (CellMatrix) matrix.clone();

		assertEquals(originalCell, clone.getCell(0, 0));
	}

	@Test
	void checkMatrixCanBeReset() {
		final CellMatrix matrix = new CellMatrix(1, 2);
		matrix.getCell(0, 1).toggleState();

		assertTrue(CellState.LIVE.equals(matrix.getCell(0, 1).getState()));

		matrix.lockCurrentStateAsSeed();
		matrix.getCell(0, 1).toggleState();

		assertTrue(CellState.DEAD.equals(matrix.getCell(0, 1).getState()));

		matrix.reset();

		assertTrue(CellState.LIVE.equals(matrix.getCell(0, 1).getState()));
	}

	@Test
	void cellsDeadAfterClear() {
		final CellMatrix matrix = new CellMatrix(1, 2);
		matrix.getCell(0, 0).toggleState();

		final boolean aliveBeforeClear = CellState.LIVE.equals(matrix.getCell(0, 0).getState());

		matrix.clear();

		final boolean deadAfterClear = CellState.DEAD.equals(matrix.getCell(0, 0).getState());

		assertTrue(aliveBeforeClear && deadAfterClear);
	}

	@Test
	void getWidth() {
		final CellMatrix matrix = new CellMatrix(2, 1);
		
		assertEquals(2, matrix.getWidth());
	}
	
	@Test
	void getHeight() {
		final CellMatrix matrix = new CellMatrix(1, 5);
		
		assertEquals(5, matrix.getHeight());
	}
}