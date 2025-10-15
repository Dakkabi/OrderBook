package github.dakkabi.engine.service;

import github.dakkabi.engine.model.MatchingAlgorithm;
import github.dakkabi.engine.model.OrderBook;

/**
 * Service class to handle the matching logic for incoming orders and trades
 * for a given Order Book.
 */
public class MatchingEngine {
  private final OrderBook orderBook;

  // Real-world order books rarely change algorithm during run-time.
  // So keeping this final is fine.
  private final MatchingAlgorithm algorithm;

  /**
   * Public algorithm-only argument, will initialise its own new Order Book.
   */
  public MatchingEngine(MatchingAlgorithm algorithm) {
    this(new OrderBook(),  algorithm);
  }

  /**
   * Public Order Book constructor, will handle the orderbook specified.
   *
   * @param orderBook The order book to manage.
   */
  public MatchingEngine(OrderBook orderBook,  MatchingAlgorithm algorithm) {
    this.orderBook = orderBook;

    if (algorithm.equals(MatchingAlgorithm.PRORATA)) {
      throw new UnsupportedOperationException("PRO-RATA is not currently supported");
    }

    this.algorithm = algorithm;
  }

  public OrderBook getOrderBook() {
    return orderBook;
  }

  public MatchingAlgorithm getAlgorithm() {
    return algorithm;
  }
}
