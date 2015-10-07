# WattsStrogatzSmallWorld
This project is to simulate a small-world graph according to Watts-Strogatz Model. The smallworld graph consists of a ring lattice with 5000 nodes (N = 5000) and each node has 20 short range neighbors (k = 20). The goal is to understand the properties of small-world graphs through simulation.

In this project, average path lengths, clustering coefficient of small-world
graphs are computed and analyzed. Through this project, we gain thorough
understanding of the Watts-Strogatz model, which explores the formation of networks that result in the "small world" phenomenon.

This project is developed in Windows 7 using java in Eclipse.

Files included are 6 source code files: Graph.java GraphDrawer.java GraphProcessor.java PathExplorer.java Point.java WattsStrogatzSmallWorld.java

 the file to run the project:
        run.bat

a user guide:
    UserGuide.pdf
To run the project:

In Windows 7, just double click the run.bat file.
    Input example 1:
        num of nodes = 100
        num of neighbors = 4
        probability = 0.005

    Input example 2:
        num of nodes = 100
        num of neighbors = 4
        probability = 0.1

In Linux, use command line: java -jar SmallWorld.jar
