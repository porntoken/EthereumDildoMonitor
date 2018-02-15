# EthereumDildoMonitor

## Monitors Etherscan and sets off the Lovesense Nora Dildo when $PTWO is purchased with ETH

* Make configuration changes to application.properties and launch locally with mvn clean jetty:run to test.

* Run under Tomcat in production

No unit tests were written as this is a one timer for the $PTWO ICO

This one-off was written in Spring Boot V1 to save time, but note that all future projects are written in Spring Boot V2

## Database

* Create a MySQL database called dildo_monitor with username/password as per application.properties

* The dildo_monitor schema will automatically be created, but is also located in resources/database