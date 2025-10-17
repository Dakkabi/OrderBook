package github.dakkabi.engine.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class OrderBookTest {
  private OrderBook orderBook;

  @BeforeEach
  public void beforeEach() {
    orderBook = new OrderBook();
  }

  @Test
  public void getBestOrder_CheckNullSafety() {
    assertNull(orderBook.getBestBid());
    assertNull(orderBook.getBestAsk());
  }

  @Test
  public void addOrder_AddTwoOrders() {
    orderBook.addOrder(new Order(Side.ASK, Type.LIMIT, 100, 50.54));
    orderBook.addOrder(new Order(Side.ASK, Type.LIMIT, 100, 50.54));
    // TODO: Assert that the size of the best price level is 200 (100 + 100)

    orderBook.removeOrder(orderBook.getBestAsk());
    orderBook.removeOrder(orderBook.getBestAsk());
    // TODO: Assert there exists no Price Levels in the Map
  }
}
