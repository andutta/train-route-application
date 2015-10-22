# train-route-app
### Description
I named this application train-route-application. At the heart of this application is an algorithm that calculates shortest path in a directed graph data structure. The most important component of this application is RouteMap class that provides a mechanism to not only hold the graph data structure but it also exposes methods to calculate shortest path for each station (or Vertices in Graph data structure terms). There are other methods to calculate possible combinations for travelling between a pair of start and stop station. 

When you run this application (refer README.md on how to run), you will be asked whether you want to see the results for tests that was prescribed in the problem statement. Selecting to run will run all the test as per preset data (again prescribed in the problem statement). Next, it will provide you an option to load your data from a text file (see a sample test file in src/main/resource codebase), selecting that option will let you enter a text file with full path available on your computer. Once the file is loaded successfully, it will next ask for a destination station label, select a label from your data file, application will then respond back with shortest path to the destination. Note that source is always the first station from your data file.

All the JUnits also cover the prescribed test cases, I didn't write any other test case as I felt that these tests are providing good coverage for most of the functionality. 

### Functional assumptions
* This assumes the data supplied will create a directed graph.
* Source is always the first station you enter in your data.
* RouteMap class instance will maintain a certain state for objects internally for its execution, hence the class is not thread safe, more syncronization needs to added to make sure threads do step on each other.
* List, Queue, Stack data structures used in the application can hold large amount of data (limited to JVM available memory), I have not tested it with large datasets, so scaling this application will need some more considerations in terms of what data structures should used. E.g. I have used list to hold the stations, I can quickly iterate over it to get stattion reference from its label. In case when no of stations are more a HashMap may be a better option.
* More than one edge pair with bi-directional edge is not allowed.

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

**Get shortest path for station, enter To station label** - Enter your to station label from your data file

Next it shows the result

Next prompted for -

**Want to check another one ?** Y/N - Press Y to check another route, N to get out

Next Prompt -

**Want to continue Y/N?** - Enter Y if you want to go through all of the above, N to exit program
