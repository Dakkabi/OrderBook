package github.dakkabi;

import github.dakkabi.engine.model.*;
import github.dakkabi.engine.service.MatchingEngine;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Simple CLI that interacts directly with the server.
 */
public class Server {
  private final MatchingEngine matchingEngine = new MatchingEngine(MatchingAlgorithm.FIFO);

  /**
   * Main entry-point for the basic CLI to the server.
   *
   * @param args Additional arguments, does nothing.
   */
  public static void main(String[] args) {
    Server server = new Server();
    server.run();
  }

  /**
   * Main runnable.
   */
  public void run() {
    System.out.println("Server starting");
    System.out.println("Example: BID LIMIT 500@56.54");

    Thread cliThread = new Thread(this::handleUserInput);
    cliThread.setDaemon(true);
    cliThread.start();

    while (true) {

    }
  }

  private void handleUserInput() {
    try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
      String line;
      while ((line = reader.readLine()) != null) {
        processInput(line);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void processInput(String cmd) {
    cmd = cmd.trim().toUpperCase();
    String[] parts =  cmd.split(" ");

    switch (parts[0]) {
      case "BID", "ASK":
        createNewOrder(parts);
        break;

      case "PRINT":
        getLevelOneData();
        break;
    }
  }
  private void getLevelOneData() {
    OrderBook orderBook = matchingEngine.getOrderBook();
    String msg = String.format(
        "Best Bid: %s | Best Ask: %s",
        orderBook.isEmpty(Side.BID) ? "None" : orderBook.getBestBid().getPrice(),
        orderBook.isEmpty(Side.ASK) ? "None" : orderBook.getBestAsk().getPrice()
    );
    System.out.println(msg);
  }

  private void createNewOrder(String[] parts) {

    Side side = Side.valueOf(parts[0]);
    Type type = Type.valueOf(parts[1]);

    String[] quantityPrice = parts[2].split("@");
    int quantity = Integer.parseInt(quantityPrice[0]);
    double price = Double.parseDouble(quantityPrice[1]);

    matchingEngine.submitOrder(new Order(side, type, quantity, price));
  }
}
