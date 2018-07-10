import numpy as np
import matplotlib.pyplot as plt


for i in range(50000000):
    d = np.random.randint(low=0, high=20, size=1)[0]
    x = np.arange(0, d, 0.1);
    y = np.sin(x)
    plt.plot(x, y)
    plt.show()
    plt.pause(0.001)
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
