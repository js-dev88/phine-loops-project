# README 

Projet : Phine Loops

Abderraouf Haddad
Julien Saussier

We used maven and the plugin and the Maven Shade Plugin to obtain one fat jar with all dependencies
mvn clean install

The Shade Plugin has a single goal:
shade:shade is bound to the package phase and is used to create a shaded jar.

To build the project 
mvn package shade:shade

We did it with eclipse interface :
right click on project
Run as
Build...
Goals : package shade:shade

The jar is projetHaddadSaussier.jar

