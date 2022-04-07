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
5. Run the benchmark clients test1 or test2:
   1. ```loadtest -p test1.js -n [number-of-requests] -c [number-of-concurrent-clients] -T application/json http:://localhost:8080/validate```
   2. ```loadtest -p test2.js -n [number-of-requests] -c [number-of-concurrent-clients] -T application/json http:://localhost:8080/validate```
   
TO DO: Put here commands and instructions to produce measures and graphs.

PATH WINDOWS: ```C:\Users\s286154\AppData\Roaming\npm\loadtest.cmd```

```loadtest.cmd -n numbreq -c numclients -p .\test1.js -T application/json http://localhost:8080/validate```

## Graphs

TO DO: Graph 1

TO DO: Graph 2

TO DO: Graph 3