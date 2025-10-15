package github.dakkabi.engine.service;

import github.dakkabi.engine.model.OrderBook;

/**
 * Service class to handle the matching logic for incoming orders and trades
 * for a given Order Book.
 */
public class MatchingEngine {
  private final OrderBook orderBook;

  /**
   * Public no-args constructor, will initialise its own OrderBook attribute.
   */
  public MatchingEngine() {
    orderBook = new OrderBook();
  }

  /**
   * Public Order Book constructor, will handle the orderbook specified.
   *
   * @param orderBook The order book to manage.
   */
  public MatchingEngine(OrderBook orderBook) {
    this.orderBook = orderBook;
  }

  public OrderBook getOrderBook() {
    return orderBook;
  }
}
