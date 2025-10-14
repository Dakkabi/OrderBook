package github.dakkabi.engine.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderTest {

  @Test
  public void negativeQuantityConstructor() {
    Assertions.assertThrows(IllegalArgumentException.class, () ->
        new Order(Side.ASK, -100, 50.53)
    );
  }

  @Test
  public void getQuantity() {
    int quantity = 50;
    Order order = new Order(Side.ASK, quantity, 50.53);

    assertEquals(quantity, order.getQuantity());
  }
}
