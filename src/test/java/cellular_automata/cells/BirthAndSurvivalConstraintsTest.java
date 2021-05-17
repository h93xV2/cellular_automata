package cellular_automata.cells;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class BirthAndSurvivalConstraintsTest {
	@Test void birthNeighborsEmpty() {
		final BirthAndSurvivalConstraints constraints = new BirthAndSurvivalConstraints();
		
		assertTrue(constraints.getLiveNeighborsRequiredForBirth().isEmpty());
	}
	
	@Test void survivalNeighborsEmpty() {
		final BirthAndSurvivalConstraints constraints = new BirthAndSurvivalConstraints();
		
		assertTrue(constraints.getLiveNeighborsRequiredForSurvival().isEmpty());
	}
	
	@Test void addBirthNeighborCount() {
		final BirthAndSurvivalConstraints constraints = new BirthAndSurvivalConstraints();
		constraints.addBirthNeighborCount(10);
		
		assertEquals(10, constraints.getLiveNeighborsRequiredForBirth().get(0));
	}
	
	@Test void addSurvivalNeighborCount() {
		final BirthAndSurvivalConstraints constraints = new BirthAndSurvivalConstraints();
		constraints.addSurvivalNeighborCount(13);
		
		assertEquals(13, constraints.getLiveNeighborsRequiredForSurvival().get(0));
	}
}
