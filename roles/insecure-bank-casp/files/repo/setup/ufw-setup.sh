#add ufw rules
sudo ufw allow 22
#sudo ufw allow 80
sudo ufw allow 443
sudo ufw allow 8080
sudo ufw allow 1337
#sudo ufw allow 8388
#sudo ufw allow 8389

#enable logging
sudo ufw logging medium
sudo service rsyslog restart

#start ufw
sudo ufw --force enable
sudo ufw status verbose
ls -la /var/log/ufw*
