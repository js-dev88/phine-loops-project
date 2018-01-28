# README 

Projet : Phine Loops

Abderraouf Haddad
Julien Saussier

We used maven and the plugin and the Maven Shade Plugin to obtain one fat jar with all dependencies

 * mvn clean install

The Shade Plugin has a single goal:
shade:shade is bound to the package phase and is used to create a shaded jar.

To build the project 

 * mvn package shade:shade

We did it with eclipse interface :
right click on project
Run as
Build...

 * Goals : package shade:shade

The jar is projetHaddadSaussier.jar

help :

 * -a,--algorithm <arg>   Algorithm of choice of piece
 
 * -c,--check <arg>       Check whether the grid in <arg> is solved.
 
 * -g,--generate  <arg>   Generate a grid of size height x width.
 
 * -h,--help              Display this help
 
 * -i,--interface <arg>   Launching the interface of the <arg> grid
 
 * -o,--output <arg>      Store the generated or solved grid in <arg>. (Use
                        only with --generate and --solve.)
 
 * -s,--solve <arg>       Solve the grid stored in <arg>.
 
 * -t,--threads <arg>     Maximum number of solver threads. (Use only with
                        --solve.)
 
 * -x,--nbcc <arg>        Maximum number of connected components. (Use only
                        with --generate.)

