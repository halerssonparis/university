import numpy as np
import csv

'''
for i in range(40):
    if (i != 0):
        print
    for j in range(40):
        print np.random.randint(low=0, high=1000, size=1)[0], 
        #if (j != 19):
            #print ",",
'''

a = [1, 3, 5, 1]

a = list(set(a))
print a[0]

if (len(a) != len(set(a))):
    print "pora"
    print a
