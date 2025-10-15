package github.dakkabi.engine.service;

import github.dakkabi.engine.model.Order;
import github.dakkabi.engine.model.OrderBook;

/**
 * Service class to handle the matching logic for incoming orders and trades
 * for a given Order Book.
 */
public class MatchingEngine {
  private final OrderBook orderBook;
  private final MatchingEngineLogger logger;

  /**
   * Public algorithm-only argument, will initialise its own new Order Book.
   */
  public MatchingEngine() {
    this(new OrderBook());
  }

  /**
   * Public Order Book constructor, will handle the orderbook specified.
   *
   * @param orderBook The order book to manage.
   */
  public MatchingEngine(OrderBook orderBook) {
    this.orderBook = orderBook;
    this.logger = new MatchingEngineLogger();
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
      case MARKET:
        // Market Orders get cancelled if not filled.
        logger.onOrderRemoved(order);
        break;

      case LIMIT:
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
}
