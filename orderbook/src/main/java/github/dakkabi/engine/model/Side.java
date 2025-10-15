package github.dakkabi.engine.model;

/**
 * Enum representing the intention of the agent.
 */
public enum Side {
  BID,
  ASK;

  /**
   * Get the opposing enum order side.
   *
   * @return The opposite Side, e.g. BID -> ASK.
   */
  public Side other() {
    return this.equals(BID) ? ASK : BID;
  }
}
