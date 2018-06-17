## TO DO: to implement roullet,   change the bubbleSort

import numpy as np

class Caixeiro:
    
    totalCities = 0
    cities = []
    population = []
    redundancyPopulation = [] # this is the fitness result population
    
    selectedUsers = [] # this is the selected user's (by roullet)
    newPopulation = [] # this is the son's 

    def __init__(self, citiesLen):
        self.totalCities = citiesLen
        self.cities = np.zeros([citiesLen, citiesLen])
        self.inicializeCities(citiesLen)
        self.initRandomPopulation()

        #put the FOR here!
        self.sortData()
        self.userSelector()
        self.crossing()
        self.mutation()
        self.addNewPopulation()

    def inicializeCities(self, citiesLen):
        for i in range(citiesLen):
            for j in range(citiesLen):
                self.cities[i][j] = input()

    def addNewPopulation(self):
        count = 0
        for i in range(len(self.population)/2, len(self.population)):
            self.population[i] = self.newPopulation[count]
            count = count + 1 
            if (count == len(self.newPopulation)):
                break

    def mutation(self):
        for individual in range(len(self.newPopulation)):
            self.newPopulation[individual][np.random.randint(low=0, high=self.totalCities, size=1)[0]] = np.random.randint(low=0, high=self.totalCities, size=1)[0]


    def crossing(self):
        del self.newPopulation[:]

        for parentsIndex in range(0, len(self.selectedUsers), 2):
            first = []
            second = []

            for geneIndex in range(self.totalCities/2):
                first.append(self.selectedUsers[parentsIndex][geneIndex])
                second.append(self.selectedUsers[parentsIndex+1][geneIndex])
            
            for geneIndex in range(len(self.selectedUsers[parentsIndex])):
                if ((self.selectedUsers[parentsIndex][geneIndex] not in first) and len(first) != self.totalCities):
                    first.append(self.selectedUsers[parentsIndex][geneIndex])
                if ((self.selectedUsers[parentsIndex+1][geneIndex] not in second) and len(second) != self.totalCities):
                    second.append(self.selectedUsers[parentsIndex+1][geneIndex])

            self.newPopulation.append(first)
            self.newPopulation.append(second)

    def userSelector(self):
        #to implement roullet
        for lucky in range(len(self.population)/2):
            self.selectedUsers.append(self.population[np.random.randint(low=0, high=len(self.population)/2, size=1)[0]])


    def sortData(self):
        for way in self.population:
            self.redundancyPopulation.append(self.fitness(way))

        #This is bubbleSort, *temporary" CHANGE THIS!!
        for i in range(len(self.redundancyPopulation) - 1):
            for j in range(i+1, len(self.redundancyPopulation)):
                if (self.redundancyPopulation[i] > self.redundancyPopulation[j]):
                    self.redundancyPopulation[i], self.redundancyPopulation[j] = self.redundancyPopulation[j], self.redundancyPopulation[i]
                    self.population[i], self.population[j] = self.population[j], self.population[i]
        
        del self.redundancyPopulation[:]
   
    def initRandomPopulation(self):
        for p in range(0, 5):
            self.population.append(np.random.choice(range(self.totalCities), self.totalCities, replace=False))

    def fitness(self, way):
        return np.sum(way)

    def printa(self):
        #print self.cities
        print self.population
        #print self.newPopulation        
        #print self.selectedUsers
        #print self.fitness([1,2,5,2,5])

           





print "Numero de cidades: "
lCities = input()
problem = Caixeiro(lCities)

#problem.initRandomPopulation()
problem.printa()
