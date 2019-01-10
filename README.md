# Implementation

This repository contains the implementation of a microgrid simulation. We usually add the exercise number to the methods.

# Setup
It is probably possible to use a lot of different IDEs and setting but we used the following:
- Eclipse 2018-09 with Spring Tools 4

To use the project simply import it as a maven project. The Spring Tools should provide you with a run configuration. 
To use all features of this implementation the external weather component is also necessary.
Clone both repositories in the same folder and follow the readme of [WeatherCollector
](hhttps://github.com/smart-energy-system/WeatherCollector).

You will also need to use the price collector component which is located in the directory `priceCollector`. 
Import this project in the IDE of your choice too. To be able to use the component a security token from ENTSO-E is necessary to use their API. 
Please follow the documentation of the [ENRSO-E REST API](https://transparency.entsoe.eu/content/static_content/Static%20content/web%20api/Guide.html) to obtain a token.
With a valid token make a copy of the file credentials.template.properties (`priceCollector\src\main\resources`) and replace `<token>` with your token.
Then remove the word `template` form the name of your copy. You may also want to make adjustments to the bidding zone. The setting is located in application.properties.


# Usage
There is no frontend at the moment but there is a REST API. After launching the application the Swagger specification
for the REST API is available at http://localhost:8090/swagger-ui.html .
Keep in mind that the Weather Collector uses the port 8080 and this application uses 8090.
