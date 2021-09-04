package cellular_automata.controllers;

public interface Controller {
  public ControllerData getShareableData();
  public void setShareableData(final ControllerData data);
}
