PreRequistes:

1.Run mysql on 3306 port
2.Run redis on 6379 port
3.Java 8
4.Maven

//DB set up
1.create database auzmor;
2.change username and password in application.properties file under resources folder

//Code Set up
 1.clone the project in git and go to working directory
 2.mvn clean install(which will generate jar and run the tests)

//run the generated jar
java -jar <generated jar>

//open the swagger for api details
http://localhost:8080/swagger-ui.html





