import matplotlib.pyplot as plt
import time
import numpy as np

plt.axis([0, 100, -10, 10])
plt.ion()
plt.show()
ydata = [0]
line, = plt.plot(ydata)

ts_start = time.time()

## perpetual loop code
for i in range(1000):
    p_x = int(int(time.time())-int(ts_start))
    p_y = 100 # keeps getting generated in the loop code
    ydata.append(p_y)
    line.set_xdata(np.arange(len(ydata)))
    line.set_ydata(ydata)
    plt.draw()
    time.sleep(0.05)

'''if (self.weights[i] < self.maxCapacity):      

                self.newSoluction.append([self.weights[i], self.values[i]])         
                newWeigth = self.weights[i]
            
                if (i == self.inputsLength - 1):
                    self.soluctions.add(self.newSoluction, newWeigth)
                    return # 

                for j in range(i+1, self.inputsLength):
                    if (newWeigth + self.weights[j] < self.maxCapacity):
                        self.newSoluction.append([self.weights[j], self.values[j]])
                        newWeigth = newWeigth + self.weights[i]

                        if (j == self.inputsLength - 1):
                            self.soluctions.add(self.newSoluction, newWeigth)
                            break #

                        for k in range(j + 1, self.inputsLength):
                            if (newWeigth + self.weights[k] < self.maxCapacity):
                                self.newSoluction.append([self.weights[j], self.values[j]])
                                newWeigth = newWeigth + self.weights[j]

                            elif (newWeigth + self.weights[k] == self.maxCapacity):
                                self.newSoluction.append([self.weights[k], self.values[k]])
                                self.soluctions.add(self.newSoluction, newWeigth + self.weights[k])
                                self.newSoluction.pop()

                    elif (newWeigth + self.weights[j] == self.maxCapacity):
                        self.newSoluction.append([self.weights[j], self.values[i]])
                        self.soluctions.add(self.newSoluction, newWeigth + self.weights[i])
                        self.newSoluction.pop()
                    
            elif (self.weights[i] == self.maxCapacity):
                self.soluctions.add(self.weights[i], self.values[i])'''
