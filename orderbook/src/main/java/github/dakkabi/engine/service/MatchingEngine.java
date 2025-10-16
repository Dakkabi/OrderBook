package github.dakkabi.engine.service;

import github.dakkabi.engine.model.Order;
import github.dakkabi.engine.model.OrderBook;
import github.dakkabi.engine.model.Side;
import github.dakkabi.engine.model.Type;

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
      logger.onOrderRemoved(order);
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
    Side opposingSide = order.getSide().other();

    if (orderBook.isEmpty(opposingSide)) {
      return false;
    }

    Order bestOrder;
    while ((bestOrder = orderBook.getBestOrderBySide(opposingSide)) != null) {
      if (canMatchOrder(order, bestOrder)) {
        commitTransaction(order, bestOrder);

        if (bestOrder.completed()) {
          orderBook.removeOrder(bestOrder);
        }

        if (order.completed()) {
          return true;
        }

      } else {
        // Can't match, abort.
        break;
      }
    }

    return false;
  }

  private boolean canMatchOrder(Order incomingOrder, Order existingOrder) {
    if (incomingOrder.getSide() == existingOrder.getSide()) {
      throw new IllegalArgumentException("Cannot match two orders of the same Side");
    }

    if (incomingOrder.getType() == Type.MARKET) {
      return true; // Market orders can always be matched.
    }

    // Limit order checks.
    if (incomingOrder.getSide() == Side.BID) {
      return incomingOrder.getPrice() >= existingOrder.getPrice();
    }
    return incomingOrder.getPrice() <= existingOrder.getPrice();
  }

  private void commitTransaction(Order order, Order otherOrder) {
    int safeQuantityTransferAmount = Math.min(order.getRemainingQuantity(), otherOrder.getRemainingQuantity());
    order.setRemainingQuantity(order.getRemainingQuantity() - safeQuantityTransferAmount);
    otherOrder.setRemainingQuantity(otherOrder.getRemainingQuantity() - safeQuantityTransferAmount);
  }

  public OrderBook getOrderBook() {
    return orderBook;
  }
}
