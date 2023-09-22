FROM debian:stable-slim

# upgrade and install necessary dependencies: python, wget & maven
RUN apt update && apt upgrade -y && apt install -y --no-install-recommends maven && apt-get clean && rm -rf /var/lib/apt/lists/*

# copy the application sources to /root, build them and start app once for all dependencies to be downloaded
WORKDIR /root
COPY src /root/src
COPY pom.xml /root
RUN mvn clean package cargo:start -DskipTests

# run application
CMD mvn cargo:run
