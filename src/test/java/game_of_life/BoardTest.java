package game_of_life;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import cellular_automata.Board;

public class BoardTest {
    @Test void boardCellsExist() {
        final Board board = new Board();
        board.setUp(1, 1, 1, 1);

        assertNotNull(board.getCells());
    }
}
