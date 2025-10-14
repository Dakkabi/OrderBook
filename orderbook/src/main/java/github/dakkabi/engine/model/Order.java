package github.dakkabi.engine.model;

/**
 * Class representing an Order to be entered into the OrderBook.
 */
public class Order {
  private int id;
  final Side side;
  final int quantity;
  final int price;

  /**
   * Public Order constructor, id should be manually set by the server.
   *
   * @param side The order side, whether the agent intends to sell / buy.
   * @param quantity The quantity the agent wants to sell / buy.
   * @param price The amount the agent would sell / buy for.
   */
  public Order(Side side, int quantity, int price) {
    if (quantity <= 0) {
      throw new IllegalArgumentException("quantity must be greater than 0");
    }

    this.side = side;
    this.quantity = quantity;
    this.price = price;
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

  public int getQuantity() {
    return quantity;
  }

  public int getPrice() {
    return price;
  }
}
