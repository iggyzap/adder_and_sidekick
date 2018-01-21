# adder_and_sidekick
Exploration of minimalistic adder service and a front-end to it.

How It Works?
-------------

This is multi-module project, written in about 4 hours and contains minimalistic Dropwizard microservice (adder),
wrapper around netty's hexdump proxy example which serves as user-facing client service (sidekick) and parent pom.

There are few Spock's specifications which run these services, they are plugged into Maven build pipeline.
To run all tests (for adder, sidekick) simply run 
````
mvn clean verify
````

This will run these tests and create packages.

Also, applications can be run as standalone, adder via: 
```
java -jar adder/target/adder-<project.version>.jar server
```

Sidekick via:
```
java -jar sidekick/target/sidekick-<project.version>.jar
```

When started, one can send requests to adder directly via curl:
```bash
curl -v http://localhost:8080/+/-1/200 ; echo
*   Trying ::1...
* TCP_NODELAY set
* Connected to localhost (::1) port 8080 (#0)
> GET /+/-1/200 HTTP/1.1
> Host: localhost:8080
> User-Agent: curl/7.54.0
> Accept: */*
> 
< HTTP/1.1 200 OK
< Date: Sun, 21 Jan 2018 11:09:39 GMT
< Content-Type: text/plain
< Vary: Accept-Encoding
< Content-Length: 3
< 
* Connection #0 to host localhost left intact
199
```
Or via client-facing server:
```bash
curl -v http://localhost:18080/+/-1/200 ; echo
*   Trying ::1...
* TCP_NODELAY set
* Connected to localhost (::1) port 18080 (#0)
> GET /+/-1/200 HTTP/1.1
> Host: localhost:18080
> User-Agent: curl/7.54.0
> Accept: */*
> 
< HTTP/1.1 200 OK
< Date: Sun, 21 Jan 2018 20:44:55 GMT
< Content-Type: text/plain
< Vary: Accept-Encoding
< Content-Length: 3
< 
* Connection #0 to host localhost left intact
199
```

Added also features health-check which always work.

Any Specification is _standalone_ so it can be run in IDE and application will be _debuggable_