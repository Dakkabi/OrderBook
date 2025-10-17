package github.dakkabi.engine.service;

import github.dakkabi.engine.model.Order;
import github.dakkabi.engine.model.Type;

/**
 * Observer class to log a Matching Engine's events.
 */
public class MatchingEngineLogger {
  /**
   * Log Order initialisation.
   *
   * @param order The order affected.
   */
  public void onOrderAdded(Order order) {
    String msg = String.format(
        "[ADD] %s Order %s %d@%.2f",
        order.getType(),
        order.getId(),
        order.getInitialQuantity(),
        order.getPrice()
    );

    System.out.println(msg);
  }

  /**
   * Log when an Order is completed, failed to fill as a market order, or was cancelled by user.
   *
   * @param order The order affected.
   */
  public void onOrderRemoved(Order order) {
    String msg;
    if (order.completed()) {
      msg = String.format(
          "[REMOVE] Completed %s Order %s %d@%.2f",
          order.getType(),
          order.getId(),
          order.getInitialQuantity(),
          order.getPrice()
      );

    } else if (order.getType() == Type.MARKET) {
      // Market orders dont get assigned IDs
      msg = String.format(
          "[CANCEL] Failed to fill %s Order, %d@%.2f with %s remaining quantity",
          order.getType(),
          order.getInitialQuantity(),
          order.getPrice(),
          order.getRemainingQuantity()
      );

    } else { // Most likely user cancelled
      msg = String.format(
          "[CANCEL] Cancelled %s Order %s by User, %d@%.2f with %s remaining quantity",
          order.getType(),
          order.getId(),
          order.getInitialQuantity(),
          order.getPrice(),
          order.getRemainingQuantity()
      );
    }
    System.out.println(msg);
  }
}
