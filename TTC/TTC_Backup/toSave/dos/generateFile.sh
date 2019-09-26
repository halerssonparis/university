#ips  nmap  normal  port  probe  reduced  satan

#!/bin/bash

cat $2 | grep back > back
cat $2 | grep land > land 
cat $2 | grep neptune > neptune
cat $2 | grep pod > pod 
cat $2 | grep smurf > smurf
cat $2 | grep teardrop > teardrop
cat $2 | grep normal > normal

tail -n $1 back > back_$1
tail -n $1 land > land_$1
tail -n $1 neptune > neptune_$1
tail -n $1 pod > pod_$1
tail -n $1 smurf > smurf_$1
tail -n $1 teardrop > teardrop_$1
tail -n $1 normal > normal_$1

(cat back_$1 && cat land_$1 && cat neptune_$1 && cat pod_$1 && cat smurf_$1 && cat teardrop_$1 && cat normal_$1) > reduced
