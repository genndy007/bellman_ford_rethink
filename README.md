# Parallel Computing Coursework

## Topic
Sequential and Parallel Bellman-Ford Algorithm implementation in Java

## Environment
- OpenJDK 17 LTS
- java.util.concurrent package
  - Executors.newFixedThreadPool
  - atomic access variables
  - tasks as lambdas
- Maven as dependency manager
- JetBrains IntelliJ IDEA as IDE

## Details
Uses graphs represented in txt files like:
```
vertices_num edges_num
start1 end1 weight1
start2 end2 weight2
...
startN endN weightN
```
Generate graphs with Python script in graph_gen/ folder.


## License
MIT

## Author
Hennadii Kochev, IP-91 group, FICT, Igor Sikorsky KPI