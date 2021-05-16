package cellular_automata.persistence;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import cellular_automata.cells.Cell;

public class SaveDataTest {
	@Test
	void showGridLinesIsFalseByDefault() {
		final SaveData data = new SaveData();

		assertFalse(data.getShowGridLines());
	}

	@Test
	void cellsAreNullByDefault() {
		final SaveData data = new SaveData();

		assertNull(data.getCells());
	}

	@Test
	void showGridLinesCanBeSet() {
		final SaveData data = new SaveData();

		data.setShowGridLines(true);

		assertTrue(data.getShowGridLines());
	}

	@Test
	void cellsCanBeSet() {
		final SaveData data = new SaveData();

		data.setCells(new Cell[1][1]);

		assertNotNull(data.getCells());
	}

	@Test
	void saveDataCanBeInitializedWithObjects() {
		final SaveData data = new SaveData(new Cell[1][1], true);

		assertTrue(data.getCells() != null && data.getShowGridLines());
	}
}
