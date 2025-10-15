package github.dakkabi.engine.model;

/**
 * Enum representing the trading algorithm to utilise for a Matching Engine.
 *
 * <p>FIFO: First-in, First-out, uses orders' timestamp to decide priority. <br/>
 *  PRO-RATA: Uses orders' quantity to decide priority.</p>
 */
public enum MatchingAlgorithm {
  FIFO,
  PRORATA
}
