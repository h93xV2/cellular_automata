package cellular_automata.cells;

@FunctionalInterface
public interface CellTriConsumer {
  public void accept(final Integer a, final Integer b, final Cell c);
}
