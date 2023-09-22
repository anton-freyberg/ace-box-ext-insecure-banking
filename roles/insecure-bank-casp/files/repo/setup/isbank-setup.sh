sudo apt update

# install Maven
echo "installing Maven"
sudo apt install maven

# Download, install and configure `OpenJDK 1.8.0_181`:
echo "Download and install Java"
wget https://github.com/AdoptOpenJDK/openjdk8-binaries/releases/download/jdk8u181-b13/OpenJDK8U-jdk_x64_linux_hotspot_8u181b13.tar.gz
#sudo mkdir /usr/lib/jvm
sudo tar xvf OpenJDK8U-jdk_x64_linux_hotspot_8u181b13.tar.gz -C /usr/lib/jvm
sudo update-alternatives --install /usr/bin/java java /usr/lib/jvm/jdk8u181-b13/bin/java 1000
sudo update-alternatives --install /usr/bin/javac javac /usr/lib/jvm/jdk8u181-b13/bin/javac 1000
sudo update-alternatives --set java /usr/lib/jvm/jdk8u181-b13/bin/java
sudo update-alternatives --set javac /usr/lib/jvm/jdk8u181-b13/bin/javac
java -version && javac -version

#update java keystore
sudo mv /usr/lib/jvm/jdk8u181-b13/jre/lib/security/cacerts /usr/lib/jvm/jdk8u181-b13/jre/lib/security/cacerts.old
cd /usr/lib/jvm/jdk8u181-b13/jre/lib/security/
sudo ln -s /etc/ssl/certs/java/cacerts cacerts
cd

#install insecure-bank
tar -zxvf /home/ubuntu/setup/insecure-bank.tar.gz
