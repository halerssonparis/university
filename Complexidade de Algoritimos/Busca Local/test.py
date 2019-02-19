import numpy as np
import matplotlib.pyplot as plt

ax = plt.subplot(111)

t = np.arange(0.0, 5.0, 0.01)
s = np.cos(2*np.pi*t)
line, = plt.plot(t, s, lw=1)

plt.ylim(-2,2)
plt.show()


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
