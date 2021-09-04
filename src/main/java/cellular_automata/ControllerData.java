package cellular_automata;

import cellular_automata.cells.rules.CellRules;

public class ControllerData {
  private CellRules cellRules;

  public CellRules getCellRules() {
    return cellRules;
  }

  public void setCellRules(CellRules cellRules) {
    this.cellRules = cellRules;
  }
}
