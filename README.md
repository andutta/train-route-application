# train-route-app
### Description
This simple gradle based java application uses Dijkstra's shortest path algoritum to calculate shortest route between two stations. 
Application can also calculate number of stops between two given stations. 

### Functional assumptions
- Works with directed graphs only.
- When calculating shortest path one source station is pre-decided. All the distances calculated are relative to the decided source station.
- Program cannot calculate round trips via different stations.
- RouteMap.calcuatePath() method must be executed after changing and distance data set in order to recalculate the path.
- Distances are programatically supplied to station list data structure, but new features/functionality such as loading from a file can be easily added.

### Technical Assumptions
- This application assumes you IDE is able to compile and run gradle projects.
- Class RouteMap is not tested in a multi-thread environment, since the class hold state in various 
data structure it may not be thread-safe

### How to run
- ./gradlew clean build test
- java -jar ./build/libs/train-route-app-1.0.jar



