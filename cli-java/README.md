# **TICKET Corner - Simulation**

**A Java CLI application simulating a concurrent movie ticket booking system.**

## **Getting Started**

### **Prerequisites:**
* Java Development Kit (JDK) 17 or later
* Maven

### **Installation:**
1. **Clone the Repository:**
   ```bash
   git clone https://github.com/dilruk25/movie-ticket-booking-system.git
   ```

2. **Build the Project:**
   Navigate to the project directory 'cli-java' and run:
   ```bash
   mvn clean package
   ```

3. **Run the Simulation:**
   ```bash
   java -jar target/movieticketbooking-1.0-SNAPSHOT-jar-with-dependencies.jar
   ```

### **Usage**

1. **Configuration:**
   The simulation starts by prompting for configuration details:
    * Total number of tickets
    * Ticket release rate (tickets/second) by vendors
    * Customer retrieval rate (tickets/second) by customers
    * Maximum ticket pool capacity
    * Number of vendors
    * Number of customers

2. **Simulation:**
   The simulation runs, displaying real-time updates on ticket addition, purchase, and pool status.

3. **Termination:**
   The simulation continues until all tickets are sold or manually stopped by pressing `Ctrl+C`.

### **Troubleshooting**

* **Incorrect Configuration:**
    - Ensure the maximum ticket pool capacity is less than the total number of tickets to avoid deadlocks.
    - Adjust vendor/customer rates for a balanced supply/demand.

* **Unexpected Behavior:**
    - Check the generated log file for error details.
    - Review the code for synchronization issues or race conditions.

* **Slow Performance:**
    - Experiment with different configuration settings.
    - Consider a more efficient data structure for the ticket pool.

### **Additional Notes**

* The simulation utilizes a shared `TicketPool` for synchronized ticket access.
* Vendors and customers interact concurrently with the pool, demonstrating producer-consumer patterns.
* The `Logging` utility provides detailed logging for debugging and analysis.

**Customize the configuration and experiment with various scenarios to explore concurrent programming concepts!**
