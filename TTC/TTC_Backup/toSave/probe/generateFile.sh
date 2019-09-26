#ips  nmap  normal  port  probe  reduced  satan

#!/bin/bash

cat probe | grep normal > normal 
cat probe | grep ipswepp > ipsweep 
cat probe | grep portsweep > portsweep
cat probe | grep nmap > nmap 
cat probe | grep satan > satan

tail -n $1 portsweep > port_$1
tail -n $1 normal > normal_$1
tail -n $1 ipsweep > ipsweep_$1
tail -n $1 satan > satan_$1
tail -n $1 nmap > nmap_$1

(cat port_$1 && cat normal_$1 && cat ipsweep_$1 && cat satan_$1 && cat nmap_$1) > reduced
