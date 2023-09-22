#!/bin/bash
# install OpenJDK 1.8.0_181 required for the log4shell example
wget https://github.com/AdoptOpenJDK/openjdk8-binaries/releases/download/jdk8u181-b13/OpenJDK8U-jdk_x64_linux_hotspot_8u181b13.tar.gz
sudo mkdir /usr/lib/jvm
sudo tar xvf OpenJDK8U-jdk_x64_linux_hotspot_8u181b13.tar.gz -C /usr/lib/jvm
sudo update-alternatives --install /usr/bin/java java /usr/lib/jvm/jdk8u181-b13/bin/java 1000
sudo update-alternatives --install /usr/bin/javac javac /usr/lib/jvm/jdk8u181-b13/bin/javac 1000
sudo update-alternatives --set java /usr/lib/jvm/jdk8u181-b13/bin/java
sudo update-alternatives --set javac /usr/lib/jvm/jdk8u181-b13/bin/javac

# download a newer version of JDK to copy the cacerts as otherwise SSL connection fail
wget https://github.com/adoptium/temurin8-binaries/releases/download/jdk8u352-b08/OpenJDK8U-jdk_x64_linux_hotspot_8u352b08.tar.gz
tar xfvz OpenJDK8U-jdk_x64_linux_hotspot_8u352b08.tar.gz
sudo cp jdk8u352-b08/jre/lib/security/cacerts /usr/lib/jvm/jdk8u181-b13/jre/lib/security/cacerts
rm OpenJDK8U-jdk_x64_linux_hotspot_8u352b08.tar.gz
rm -rf jdk8u352-b08/