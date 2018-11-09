# Implementation

This repository contains the implementation of a microgrid simulation.

# Setup
It is probably possible to use a lot of different IDEs and setting but we used the following:
- Eclipse 2018-09 with Spring Tools 4

To use the project simply import it as a maven project. The Spring Tools should provide you with a run configuration. 
To use all features of this implementation the external weather component is also necessary.
Clone both repositories in the same folder and follow the readme of [WeatherCollector
](hhttps://github.com/smart-energy-system/WeatherCollector).

# Usage
There is no frontend at the moment but there is a REST API. After launching the application the Swagger specification
for the REST API is available at http://localhost:8090/swagger-ui.html .
Keep in mind that the Weather Collector uses the port 8080 and this application uses 8090.
