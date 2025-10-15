package github.dakkabi.engine.model;

import java.time.Instant;

/**
 * Class representing an Order to be entered into the OrderBook.
 */
public class Order {
  private int id;
  private final Side side;
  private final Type type;
  private final int quantity;
  private int remainingQuantity;
  private final double price;
  private final long timestamp;

  /**
   * Public Order constructor, id should be manually set by the server.
   *
   * @param side The order side, whether the agent intends to sell / buy.
   * @param quantity The quantity the agent wants to sell / buy.
   * @param price The amount the agent would sell / buy for.
   */
  public Order(Side side, Type type, int quantity, double price) {
    if (quantity <= 0) {
      throw new IllegalArgumentException("quantity must be greater than 0");
    }

    this.side = side;
    this.type = type;
    this.quantity = quantity;
    this.remainingQuantity = quantity;
    this.price = price;
    this.timestamp = Instant.now().getNano();
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

  public int getQuantity() {
    return quantity;
  }

  public int getRemainingQuantity() {
    return remainingQuantity;
  }

  public void setRemainingQuantity(int remainingQuantity) {
    this.remainingQuantity = remainingQuantity;
  }

  public double getPrice() {
    return price;
  }

  public long getTimestamp() {
    return timestamp;
  }
}
