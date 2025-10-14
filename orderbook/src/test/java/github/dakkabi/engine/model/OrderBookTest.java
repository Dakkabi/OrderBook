package github.dakkabi.engine.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderBookTest {
  OrderBook orderBook;

  @BeforeEach
  public void beforeEach() {
    orderBook = new OrderBook();
  }

  @Test
  public void getBestBid() {
    Order bestBid = new Order(Side.BID, 100, 100);
    Order worstBid = new Order(Side.BID, 50, 50);

    Order betterOrder = orderBook.addOrder(bestBid);
    orderBook.addOrder(worstBid);

    assertEquals(betterOrder.getId(), orderBook.getBestBid().getId());
  }

  @Test
  public void getBestAsk() {
    Order bestAsk = new Order(Side.ASK, 100, 100);
    Order worstAsk = new Order(Side.ASK, 50, 500);

    Order betterOrder = orderBook.addOrder(bestAsk);
    orderBook.addOrder(worstAsk);

    assertEquals(betterOrder.getId(), orderBook.getBestAsk().getId());
  }
}
