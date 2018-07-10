import collections

a = [1, 3, 5, 6, 2]

b = [1,3,5,6,0]

result =  all(elem in a  for elem in b)


if (result):
    print "sambou!"

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
