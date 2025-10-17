package github.dakkabi.engine.service;

import github.dakkabi.engine.model.Order;
import github.dakkabi.engine.model.Side;
import github.dakkabi.engine.model.Type;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MatchingEngineTest {
  private MatchingEngine matchingEngine;

  @BeforeEach
  public void beforeEach() {
    matchingEngine = new MatchingEngine();
  }

  @Test
  public void submitOrder_LimitOrderWithNoLiquidity_PutInOrderBook() {
    Order returnedOrder = matchingEngine.submitOrder(
        new Order(Side.ASK, Type.LIMIT, 100, 100)
    );
    // id of '-1' means the order book hasn't assigned an id to the order
    assertNotEquals(-1, returnedOrder.getId());
  }

  @Test
  public void submitOrder_MarketOrderWithNoLiquidity_RejectOrder() {
    Order returnedOrder = matchingEngine.submitOrder(
        new Order(Side.ASK, Type.MARKET, 100, 100)
    );
    assertEquals(-1, returnedOrder.getId());
  }

  @Test
  public void submitOrder_BidLimitInstantlyFilled_NoRemainingQuantity() {
    int quantity = 100;
    Order existingOrder = matchingEngine.submitOrder(
        new Order(Side.ASK, Type.LIMIT, quantity, 100)
    );
    Order incomingOrder =  matchingEngine.submitOrder(
        new Order(Side.BID, Type.LIMIT, quantity, 150)
    );
    assertEquals(-1, incomingOrder.getId()); // Should not be placed on the order book
    assertTrue(incomingOrder.completed());
    assertTrue(existingOrder.completed());
  }

  @Test
  public void submitOrder_MarketAsk_BidHasRemainingQuantity() {
    Order existingOrder = matchingEngine.submitOrder(
        new Order(Side.BID, Type.LIMIT, 150, 150)
    );
    Order incomingOrder = matchingEngine.submitOrder(
        new Order(Side.ASK, Type.MARKET, 100, 100)
    );
    assertTrue(incomingOrder.completed());
    assertFalse(existingOrder.completed());
  }

  @Test
  public void submitOrder_LimitAskInstantlyFilled_NoRemainingQuantity() {
    int quantity = 100;
    Order existingOrder = matchingEngine.submitOrder(
        new Order(Side.BID, Type.LIMIT, quantity, 150)
    );
    Order incomingOrder =  matchingEngine.submitOrder(
        new Order(Side.ASK, Type.LIMIT, quantity, 100)
    );
    assertEquals(-1, incomingOrder.getId()); // Should not be placed on the order book
    assertTrue(incomingOrder.completed());
    assertTrue(existingOrder.completed());
  }



  @Test
  public void getOrderBook() {
    assertNotNull(matchingEngine.getOrderBook());
  }
}
