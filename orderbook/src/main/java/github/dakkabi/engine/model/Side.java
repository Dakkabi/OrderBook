package github.dakkabi.engine.model;

/**
 * Enum representing the intention of the agent.
 */
public enum Side {
  BID,
  ASK;

  public Side other() {
    return this.equals(BID) ? ASK : BID;
  }
}
