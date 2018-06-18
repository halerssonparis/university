## TO DO: to implement roullet,   change the bubbleSort

import numpy as np
import csv

class Caixeiro:
    
    totalCities = 0
    cities = []
    population = []
    redundancyPopulation = [] # this is the fitness result population
    
    selectedUsers = [] # this is the selected user's (by roullet)
    newPopulation = [] # this is the son's 

    def __init__(self):
        self.totalCities = 20
        self.inicializeCities()
        self.initRandomPopulation()

        #put the FOR here!
        self.sortData()
        self.printa()
        for i in range(50):
            self.sortData()
            self.userSelector()
            self.crossing()
            self.mutation()
            self.addNewPopulation()
    
        self.sortData()

    def inicializeCities(self):
        with open('20cities.csv') as dataset:
            data = csv.reader(dataset, delimiter=' ')
            for distanceTable in data:
                self.cities.append(map(int, distanceTable))


    def addNewPopulation(self):
        count = 0
        for i in range(len(self.population)/2, len(self.population)):
            self.population[i] = self.newPopulation[count]
            count = count + 1 
            if (count == len(self.newPopulation)):
                break

    def mutation(self):
        for individual in range(len(self.newPopulation)):
            self.newPopulation[individual][np.random.randint(low=0, high=self.totalCities-1, size=1)[0]] = np.random.randint(low=0, high=self.totalCities, size=1)[0]


    def crossing(self):
        del self.newPopulation[:]

        for parentsIndex in range(0, len(self.selectedUsers), 2):
            first = []
            second = []

            for geneIndex in range(self.totalCities/2):
                first.append(self.selectedUsers[parentsIndex][np.random.randint(low=0, high=self.totalCities-1, size=1)[0]])
                second.append(self.selectedUsers[parentsIndex+1][np.random.randint(low=0, high=self.totalCities-1, size=1)[0]])

            #print "EU SOU O FIRST {}".format(second)

            for geneIndex in range(self.totalCities):
                #print self.selectedUsers[parentsIndex][geneIndex]

                if ((self.selectedUsers[parentsIndex+1][geneIndex] not in first) and len(first) != self.totalCities):
                    first.append(self.selectedUsers[parentsIndex+1][geneIndex])
                    if (len(first) != len(set(first))):
                        first = list(set(first))

                if ((self.selectedUsers[parentsIndex][geneIndex] not in second) and len(second) != self.totalCities):
                    second.append(self.selectedUsers[parentsIndex][geneIndex])
                    if (len(second) != len(set(second))):
                        second = list(set(second))

            #print "eu sou o segundo f {}".format(second)
            '''if (len(first) != len(set(first))):
                print "FIRST {}   {}".format(first, len(set(first)))

            if (len(second) != len(set(second))):
                print "SECOND {}     {}".format(second, len(set(second)))'''

            #print first
            #print second
            #print
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
        for p in range(0, 32):
            self.population.append(np.random.choice(range(self.totalCities), self.totalCities, replace=False))

    def fitness(self, way):
        #return np.sum(way)
        i = 0
        for city in range(1, len(way)):
            i = i + self.cities[way[city-1]][way[city]]
        return i    
    
    def bestRoute(self):
        route = 9999999
        w = []
        for way in self.population:
            if (self.fitness(way) < route):
                route = self.fitness(way)
                w = way

        print "\n{}   :   {}".format(w, route) 

    def printa(self):
        #print self.cities
        for s in self.population:
            print "{}   :  {}".format(s, self.fitness(s))
            
        print
        #self.bestRoute()
        #print self.newPopulation        
        #print self.selectedUsers
        #print self.fitness([1,2,5,2,5])

           





problem = Caixeiro()

#problem.initRandomPopulation()
problem.printa()
