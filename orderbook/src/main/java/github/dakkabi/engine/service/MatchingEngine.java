package github.dakkabi.engine.service;

import github.dakkabi.engine.model.MatchingAlgorithm;
import github.dakkabi.engine.model.Order;
import github.dakkabi.engine.model.OrderBook;
import github.dakkabi.engine.model.Type;

/**
 * Service class to handle the matching logic for incoming orders and trades
 * for a given Order Book.
 */
public class MatchingEngine {
  private final OrderBook orderBook;
  private final MatchingEngineLogger logger;

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
    this.logger = new MatchingEngineLogger();

    if (algorithm.equals(MatchingAlgorithm.PRORATA)) {
      throw new UnsupportedOperationException("PRO-RATA is not currently supported");
    }

    this.algorithm = algorithm;
  }

  /**
   * Attempt to immediately fill the order, before adding to the order book if
   * available.
   *
   * @param order The new order instance.
   * @return The updated order instance.
   */
  public Order submitOrder(Order order) {
    if (matchOrder(order)) {
      // Order was able to be immediately completed.
      return order;
    }

    // Unfilled
    switch (order.getType()) {
      case Type.MARKET:
        // Market Orders get cancelled if not filled.
        break;

      case Type.LIMIT:
        // Limit orders are placed onto the Order Book
        orderBook.addOrder(order);
        logger.onOrderAdded(order);
        break;

      default:
        throw new UnsupportedOperationException("Unknown order type: " + order.getType());
    }
    return order;
  }

  private boolean matchOrder(Order order) {
    // TODO: Implement Matching FIFO
    return false;
  }

  public OrderBook getOrderBook() {
    return orderBook;
  }

  public MatchingAlgorithm getAlgorithm() {
    return algorithm;
  }
}
