wsiadamy
========

wsiadamy.pl

How to compile
========
Standard compile
```
mvn clean install -e
```
Compile offline without tests
```
mvn clean install -DskipTests -o
```

How to deploy
========
```
cp wsiadamy-webapp\wsiadamy-webapp-war\target\wsiadamy-webapp-war.war YOUR_SERVER_DIRECTORY
```
