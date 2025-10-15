package github.dakkabi.engine.model;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Class representing the structure of an Order Book.
 */
public class OrderBook {
  private final AtomicInteger nextOrderId = new AtomicInteger(0);

  // Java uses heaps data structures for priority queues.
  private final PriorityQueue<Order> bidOrders;
  private final PriorityQueue<Order> askOrders;

  /**
   * Public OrderBook constructor, initialises the bidOrders and askOrders
   * with a Comparator type.
   */
  public OrderBook() {
    Comparator<Order> getSmallerPrice = Comparator.comparingDouble(Order::getPrice);

    bidOrders = new PriorityQueue<>(getSmallerPrice.reversed());
    askOrders = new PriorityQueue<>(getSmallerPrice);
  }

  /**
   * Add an order into the Order Book.
   *
   * @param order The order instance with side, price and quantity details.
   * @return The updated order, with the id set.
   */
  public Order addOrder(Order order) {
    order.setId(nextOrderId.getAndIncrement());

    if (order.getSide().equals(Side.BID)) {
      bidOrders.add(order);
    } else {
      askOrders.add(order);
    }

    return order;
  }

  /**
   * Remove an order instance from the heaps.
   *
   * @param order The order to be removed.
   * @return A bool on whether the operation was successful.
   */
  public boolean removeOrder(Order order) {
    PriorityQueue<Order> sideQueue = order.getSide().equals(Side.ASK) ? askOrders : bidOrders;
    return removeFromHeap(order, sideQueue);
  }

  private boolean removeFromHeap(Order order, PriorityQueue<Order> sideQueue) {
    if (sideQueue.isEmpty()) {
      return false;
    }

    if (sideQueue.peek().equals(order)) {
      sideQueue.poll(); // O(1)
      return true;
    }
    return sideQueue.remove(order); // O(n)
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
    return bidOrders.peek();
  }

  public Order getBestAsk() {
    return askOrders.peek();
  }
}
