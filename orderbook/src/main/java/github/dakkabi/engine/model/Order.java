package github.dakkabi.engine.model;

import java.time.Instant;

/**
 * Class representing an Order to be entered into the OrderBook.
 */
public class Order {
  private int id;
  private final Side side;
  private final Type type;
  private final int initialQuantity;
  private int remainingQuantity;
  private final double price;
  private final long timestamp;

  /**
   * Public Order constructor, id should be manually set by the server.
   *
   * @param side The order side, whether the agent intends to sell / buy.
   * @param initialQuantity The quantity the agent wants to sell / buy.
   * @param price The amount the agent would sell / buy for.
   */
  public Order(Side side, Type type, int initialQuantity, double price) {
    if (initialQuantity <= 0) {
      throw new IllegalArgumentException("quantity must be greater than 0");
    }

    this.id = -1;
    this.side = side;
    this.type = type;
    this.initialQuantity = initialQuantity;
    this.remainingQuantity = initialQuantity;
    this.price = price;
    this.timestamp = Instant.now().getNano();
  }

  /**
   * Return whether the order quantity has been filled, and completed.
   *
   * @return A boolean on whether the order is completed.
   */
  public boolean completed() {
    return remainingQuantity == 0;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public Side getSide() {
    return side;
  }

  public Type getType() {
    return type;
  }

  public int getInitialQuantity() {
    return initialQuantity;
  }

  public int getRemainingQuantity() {
    return remainingQuantity;
  }

  /**
   * Set the remaining quantity left in an order.
   *
   * @param remainingQuantity The remaining quantity to be bought / sold.
   */
  public void setRemainingQuantity(int remainingQuantity) {
    if (remainingQuantity < 0) {
      throw new IllegalArgumentException("remainingQuantity must be greater than or equal to 0");
    } else if (remainingQuantity > initialQuantity) {
      throw new IllegalArgumentException(
          "remainingQuantity must be less than or equal to initial quantity."
      );
    }

    this.remainingQuantity = remainingQuantity;
  }

  public double getPrice() {
    return price;
  }

  public long getTimestamp() {
    return timestamp;
  }
}
