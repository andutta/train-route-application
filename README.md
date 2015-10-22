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
- TrainRouteException is thrown for all the custom error with unique error number for differentiation 

### How to run
- ./gradlew clean build test
- java -jar ./build/libs/train-route-app-1.0.jar 

Will prompted for -

**[R]un assignment ? (Press R to run any other key to skip)** - Enter R, if you want to see the assignment result.

Next prompted for -

**[L]oad data from file ? (Press L to load any other key to skip)** - Enter L, if you want to load data from a file, sample file is available in the source code in src/main/resources/routemap.dat

Next prompted for - 

**Filename with path ?** Enter a file name e.g. src/main/resources/routemap.dat

Next prompted for -

**Get shortest path for station, enter From station label** - Enter your source station, it must be first one as per your data.

Next prompted for -

**Get shortest path for station, enter To station label** - Enter your to station label from your data file

Next it shows the result

Next prompted for -

**Want to check another one ?** Y/N - Press Y to check another route, N to get out

Next Prompt 

**Want to continue Y/N?** - Enter Y if you want to go through all of the above, N to exit program
