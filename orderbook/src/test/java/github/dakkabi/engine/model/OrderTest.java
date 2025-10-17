package github.dakkabi.engine.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class OrderTest {
  private Order order;

  @BeforeEach
  public void beforeEach() {
    order = new Order(Side.ASK, Type.LIMIT, 100, 50.54);
  }

  @Test
  public void setRemainingQuantity_IllegalArgumentException() {
    Assertions.assertThrows(IllegalArgumentException.class, () -> {
      order.setRemainingQuantity(-1);
    });
    Assertions.assertThrows(IllegalArgumentException.class, () -> {
      order.setRemainingQuantity(500);
    });
  }

  @Test
  public void constructor_NegativeInitialQuantity_ThrowException() {
    Assertions.assertThrows(IllegalArgumentException.class, () -> {
      new Order(Side.ASK, Type.LIMIT, -100, 50.54);
    });
  }

  @Test
  public void getTimestamp() {
    Order newOrder = new Order(Side.ASK, Type.LIMIT, 500, 126.4);
    assertNotEquals(order.getTimestamp(), newOrder.getTimestamp());
  }
}
