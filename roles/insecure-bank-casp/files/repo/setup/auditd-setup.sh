sudo apt-get install auditd -y

#get & update rules
#wget https://raw.githubusercontent.com/Neo23x0/auditd/master/audit.rules
sudo cp /etc/audit/rules.d/audit.rules /etc/audit/rules.d/audit.rules.old
sudo cp audit.rules /etc/audit/rules.d/audit.rules

#start auditd
sudo auditctl -a exit,always -S execve
