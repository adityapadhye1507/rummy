###Rummy Returns PROJECT  

###Introduction
A multiplayer card based game for two players.   

#### Features/Functionalities
1. **Login/Register**
    a. User can register to the application.
    b. User can login if already registered.

2. **Waiting Room** 
	a. User will see the list of players available to play.
	b. User can request a user to play the game.
	c. User will be notified if another player is requesting the user to play.
	d. User can accept or reject the request.
	e. Accept the request to start a new game.
		
3. **Gameplay** 
    a. A user is allowed to draw card from the pile or the deck.
    b. User need to throw a card after drawing the card.
    c. Players will be notified when someone wins the game.
    d. A player can opt to forfeit the game anytime they want, making the opponent win the game.
    
4. **Game Rules**
	To win the game, following conditions should be met:

		a. Form a pure sequence of 7 cards
		b. Form two pure sequence of 4 cards and 3 cards
		c. Form one pure sequence of 4 cards and one sequence of 3 of a kind
	Pure Sequence: Cards of same suit, in a sequence

	Three of a kind: Cards of different suits with same card value
	
5. **Chat**

	a. Users can send and recieve messages while playing the game.
	
### Technologies

    Java 1.7
    Glassfish 4
    Maven
    Eclipse
    Jquery
    Bootstrap

 		
### Required Modules
	Requires JRE to be installed previously on the machine. Should have JRE 1.7 or greater to get best results.
	Requires Maven to generate a WAR file for Deployment.
	Requires a Java application server such as Glassfish or Apache Tomcat. Glassfish 4 is recommended.
	
### Installation	
####To Run the server
	1. Edit configurations of database under server source code "/src/main/webapp/WEB-INF/spring-config.xml", edit the database server url, username and password.
    2. Install Maven on machine.(https://maven.apache.org/install.html)
    3. Open terminal or command prompt and go to the project source directory(where pom.xml file is present).
    4. Run the command : "mvn clean install", this will generate a WAR file of the project under "target" folder and also download the dependencies of the project.
    5. Install an application server(Glassfish is used for testing this project)(https://glassfish.java.net/docs/4.0/installation-guide.pdf)
    6. On Glassfish server, create a domain if not already created (if installed with default configurations, skip this step as a default domain "domain1" is created while installing).
    7. Deploy the application WAR on server under Applications sub menu. (Glassfish Admin Console can be found at "http://localhost:4848" to do complete this step)
    8. The application should be running on localhost "http://127.0.0.1:8080/Rummy/".
    
####To Run the UI
	1. Use Apache PHP/Xampp/Lamp/Wamp server to serve the UI code.
	2. Copy the UI code in www or sites directory.
	3. browse to the index.html page
	
#### Developed by
* Aditya Padhye
