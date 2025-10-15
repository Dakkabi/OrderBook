package github.dakkabi.engine.service;

import github.dakkabi.engine.model.Order;

public class MatchingEngineLogger {
  public void onOrderAdded(Order order) {
    System.out.println(
        "[ADD]" + order.getSide() +
            "Order " + order.getId() +
            " added: " + order.getRemainingQuantity() +
            "@" + order.getPrice()
    );
  }
}
