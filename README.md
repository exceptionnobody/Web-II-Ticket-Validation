# wa2-g12-ticket-validation

Repository for the Lab 2 assignment of the Web Applications II course at Polytechnic University of Turin (academic year 2021-2022).

## Group 12 members:
| Student ID | Surname | Name |
| --- | --- | --- |
| s286154 | Ballario | Marco |
| s277873 | Galazzo | Francesco |
| s276086 | Tangredi | Giovanni |
| s292522 | Ubertini | Pietro |

## Usage

1. Move to the benchmark folder: ```cd benchmark```
2. Install loadtest: ```npm install -g loadtest```
3. Install jsonwebtoken: ```npm install jsonwebtoken```
4. Start the server
5. Run the benchmark clients benchmark1 or benchmark2:
   1. ```loadtest -p benchmark1.js -n [number-of-requests] -c [number-of-concurrent-clients] -T application/json http://localhost:8080/validate```
   2. ```loadtest -p benchmark2.js -n [number-of-requests] -c [number-of-concurrent-clients] -T application/json http://localhost:8080/validate```

benckmark1.js is used to verify for point 5 while benchmark2.js was used in point 6 and 7.

Windows users note: the loadtest tool may be automatically installed in: ```C:\Users\[Username]\AppData\Roaming\npm\loadtest.cmd```

[Concurrency.kt](./server/src/main/kotlin/com/group12/server/Concurrency.kt)
can be used for generate the csv files in your local machine
## Graphs
All the csv and graphs can be found in the docs folder.
All the following measures were produced by clients and a server running on the same machine. Results would have been more accurate by running clients and the server on different machines. In our case in fact the CPU must switch between clients' threads and the server thread and will use more resources. Please bear in mind that values depend on the machine running the server.
### Exercise 5 Stateless Validation
![Graph 1](./docs/Lab2Ex5.PNG)

The graph above shows the points from the csv file generated using the usl4j library.

### Exercise 6 Stateful Validation
![Graph 2](./docs/Lab2Ex6.PNG)

The graph above shows all the measures for exercise 6 , with the server now using a ConcurentHashMap for rejecting ticket already validated.
In general the Requests per seconds are slightly lower respect to the previous graph.

###  Exercise 7 Keep-Alive
![Graph 3](./docs/Lab2Ex7%20Keep-Alive.PNG)

The graph above represents the throughput with the keep-alive flag set.
Since the connections are not closed at the end of the HTTP requests, the requests per second are much higher than in the previous graphs because the clients do not need to open new TCP connections but can continue to use the same ones.

###  Exercise 7 Time limit
![Graph 4](./docs/Lab2Ex7%20Timeout%202s.PNG)

For this graph, the time limit value was set to 2 seconds.
Since the time is too low, not all requests will be completed before the connections are shut down.
For this reason, the requests per second are much lower.

![Graph 5](./docs/Lab2Ex7%20Timeout%2030s.PNG)

For this graph, the time limit value was set to 30 seconds.
Now the available time is enough to complete all the requests, so we can see that the throughput is similar to the one for Exercise 6.
