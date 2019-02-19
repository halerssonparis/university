import numpy as np

def linearRegression(x0, x1, y0, y1, newValue):
    v1 = (y0 * x1) + (y1 * -x0)
    v2 = (y0 * -newValue) +  (y1 * newValue)
    divisor = x1 - x0;
    print (v1 + v2) / divisor

linearRegression(785, 654, 28, 12, 690) 



#654 12
#785 28
#690 16
