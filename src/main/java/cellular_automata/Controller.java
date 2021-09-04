package cellular_automata;

public interface Controller {
  public ControllerData getShareableData();
  public void setShareableData(final ControllerData data);
}
