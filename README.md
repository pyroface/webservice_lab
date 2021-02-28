This is made with Maven
The primary code that works with JPA and the database is in core/src/main/ServerExample

Build with mvn package or press the maven tab on the right and then
parent-project > Lifecycle > package

If you want to change the code and errors occur remember to use the "clean"

After you've built
Open Terminal and do:
cd core\target
Then run this line in the terminal:
java --module-path core-1.0-SNAPSHOT.jar;modules -m core/x.snowroller.ServerExample

Remember to change the persistance.xml file to it matches the database
