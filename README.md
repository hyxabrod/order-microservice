# Order Microservice

This service processes complex business logic for orders asynchronously.

## Key Responsibilities:
1.  **Pure Kafka Consumer**: Listens to the `orders.request` topic for new order requests.
2.  **Order Processing**: Simulates heavy business logic (e.g., payment, inventory check).
3.  **Reply Sender**: After processing, sends an `OrderReply` (Success/Failure) to the `orders.reply` topic.
4.  **Decoupled Architecture**: Totally decoupled from the User Service; handles load peaks via Kafka buffering.
