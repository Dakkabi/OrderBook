package github.dakkabi.engine.model;

import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Deque;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Class representing the structure of an Order Book.
 */
public class OrderBook {
  private final AtomicInteger nextOrderId = new AtomicInteger(0);

  // Red-Black tree implementation, all operations are O(log(n)) but allows to hold all orders of
  // a price level.
  // Hashmap is a good alternative, but they do not keep sorted price levels, making traversing the
  // K-depths of an order-book O(n) rather than O(log(n))
  private final TreeMap<Double, ArrayDeque<Order>> bidOrders = new TreeMap<>(Collections.reverseOrder());
  private final TreeMap<Double, ArrayDeque<Order>> askOrders = new TreeMap<>();



  /**
   * Add an order into the Order Book.
   *
   * @param order The order instance with side, price and quantity details.
   * @return The updated order, with the id set.
   */
  public Order addOrder(Order order) {
    order.setId(nextOrderId.getAndIncrement());

    if (order.getSide() == Side.ASK) {
       addToMap(order, askOrders);
    } else {
      addToMap(order, bidOrders);
    }
    return order;
  }

  /**
   * Helper function for adding an order to a price level in the TreeMap.
   *
   * @param order The order to be added.
   * @param sideMap The side map to be added to.
   */
  private void addToMap(Order order, TreeMap<Double, ArrayDeque<Order>> sideMap) {
    if (!sideMap.containsKey(order.getPrice())) {
      sideMap.put(order.getPrice(), new ArrayDeque<>());
    }
    sideMap.get(order.getPrice()).add(order);
  }

  /**
   * Remove an order instance from the heaps.
   *
   * @param order The order to be removed.
   * @return A bool on whether the operation was successful.
   */
  public boolean removeOrder(Order order) {
    if (order.getSide() ==  Side.BID) {
      return removeFromMap(order, bidOrders);
    }
    return removeFromMap(order, askOrders);
  }

  /**
   * Helper function to remove an order from a Queue in the Side Tree Map.
   *
   * @param order The order to be removed.
   * @param sideMap The side map to search through.
   * @return A boolean on whether the operation was successful.
   */
  private boolean removeFromMap(Order order, TreeMap<Double, ArrayDeque<Order>> sideMap) {
    // We can always assume getting the best ask / bid will be O(1)
    // TreeMap will keep the best price at the root;
    // Queue will keep the earliest order in the front.

    Deque<Order> orders = sideMap.get(order.getPrice());
    boolean result = orders.remove(order);

    if (orders.isEmpty()) {
      sideMap.remove(order.getPrice());
    }

    return result;
  }

  /**
   * Check if the corresponding side queue is empty.
   *
   * @param side The side to check.
   * @return A bool on whether the side is empty or not.
   */
  public boolean isEmpty(Side side) {
      return side.equals(Side.ASK) ?  askOrders.isEmpty() : bidOrders.isEmpty();
  }

  // Level 1 Order Book data
  public Order getBestBid() {
    var bestPriceLevel = bidOrders.firstEntry();
    if (bestPriceLevel == null) {
      return null;
    }
    return bestPriceLevel.getValue().peek();
  }

  public Order getBestAsk() {
    var bestPriceLevel = askOrders.firstEntry();
    if (bestPriceLevel == null) {
      return null;
    }
    return bestPriceLevel.getValue().peek();
  }

  public Order getBestOrderBySide(Side side) {
    if (side ==  Side.ASK) {
      return getBestAsk();
    }
    return getBestBid();
  }
}
