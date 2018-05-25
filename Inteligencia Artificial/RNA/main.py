import numpy as np
import csv 


class NeuralNetwork:

    weights = []
    z = []
    zs = []
    a = []
    yHat = []

    delta = []
    total = []

    def __init__(self, inputs, hidden_layers, out):
        self.inputs = inputs
        self.hidden_layers = hidden_layers
        self.out = out

        self.weights.append(np.random.rand(self.inputs, self.hidden_layers))
       
        for w in range(1, hidden_layers):
            self.weights.append(np.random.rand(self.hidden_layers, self.hidden_layers))
    
        self.weights.append(np.random.rand(self.hidden_layers, self.out))
        #print(self.weights)

    def feedFoward(self, x):
        #z = final value (somatorio) |  a = simgoid dos valores

        self.z = np.dot(x, self.weights[0])
        for i in range(1, len(self.weights) - 1):
            self.a.append(self.sigmoid(self.z))
            self.z.append(np.dot(self.a[i-1]), self.self.weights[i])

                
        yHat = self.sigmoid(self.z[-1])
        return yHat        

    def sigmoid(self, z):
        return 1 / (1 + np.exp(-z))

    def costFunctionPrime(self, x, y):
        self.yHat = self.feedFoward(x)
        
        #delta  = np.multiply(-(y - self.yHat), self.sigmoidPrime(self.self.z[-1]))
        
    
class Database:
        
    data = []

    URL = []    
    URL_LENGHT = []
    NUMBER_SPECIAL_CHARACTERS = []
    CHARSET = []
    SERVER = []
    CONTENT_LENGTH = []
    WHOIS_COUNTRY = []
    WHOIS_STATEPRO = []
    TCP_CONVERSATION_EXCHANGE = []
    DIST_REMOTE_TCP_PORT = []
    REMOTE_IPS = []
    APP_BYTES = []
    SOURCE_APP_PACKETS = []
    REMOTE_APP_PACKETS = []
    SOURCE_APP_BYTES = []
    REMOTE_APP_BYTES = []
    APP_PACKETS = []
    DNS_QUERY_TIMES = []
    
    modified_row = []

    def getData(self):
        with open('dataset.csv') as dataset:
            self.data = csv.reader(dataset, delimiter=',')

            err = 0
            for row in self.data:
                for item in self.URL:
                    if (item.lower() == row[0].lower()):
                        err = 1
                if (err == 0):
                    self.URL.append(row[0].lower())
                err = 0   


                for item in self.CHARSET:
                    if (item.lower() == row[3].lower()):
                        err = 1
                if (err == 0):
                    self.CHARSET.append(row[3].lower())
                err = 0    
 

                for item in self.SERVER:
                    if (item.lower() == row[4].lower()):
                        err = 1
                if (err == 0):
                    self.SERVER.append(row[4].lower())
                err = 0 


                for item in self.WHOIS_COUNTRY:
                    if (item.lower() == row[6].lower()):
                        err = 1
                if (err == 0):
                    self.WHOIS_COUNTRY.append(row[6].lower())
                err = 0 
    
                for item in self.WHOIS_STATEPRO:
                    if (item.lower() == row[7].lower()):
                        err = 1
                if (err == 0):
                    self.WHOIS_STATEPRO.append(row[7].lower())
                err = 0 


            #for item in self.URL:
                #print(item)
            #print()


    def printer(self):
        
        with open('dataset.csv') as dataset:
            self.data = csv.reader(dataset, delimiter=',')

            for row in self.data:
                for index in range(len(row)):
                    #print(index)
                    if (index == 0):
                        for id in range(len(self.URL)):
                            if (self.URL[id] == row[index].lower()):
                                self.modified_row.append(id)

                    elif (index == 3):
                        for id in range(len(self.CHARSET)):
                            if (self.CHARSET[id] == row[index].lower()):
                                self.modified_row.append(id)

                    elif (index == 4):
                        for id in range(len(self.SERVER)):
                            if (self.SERVER[id] == row[index].lower()):
                                self.modified_row.append(id)

                    elif (index == 5):
                        if (row[index] == "NA"):
                            self.modified_row.append(0)

                    elif (index == 6):
                        for id in range(len(self.WHOIS_COUNTRY)):
                            if (self.WHOIS_COUNTRY[id] == row[index].lower()):
                                self.modified_row.append(id)

                    elif (index == 7):
                        for id in range(len(self.WHOIS_STATEPRO)):
                            if (self.WHOIS_STATEPRO[id] == row[index].lower()):
                                self.modified_row.append(id)

                    else:
                        self.modified_row.append(row[index].lower())


                if (self.modified_row[17] == "0" or self.modified_row[17] == "1"):
                    print "myNetwork.activate([",
                    for value in range(len(self.modified_row)):
                        if (value != 17):
                            print "{},".format(self.modified_row[value]),
        
                    print "]);"
                    print "myNetwork.propagate(learningRate, [{}]);".format(self.modified_row[17])
                del self.modified_row[:]
    

    def types(self):
        print(self.URL)
        print(self.CHARSET)
        print(self.SERVER)
        print(self.WHOIS_COUNTRY)
        print(self.WHOIS_STATEPRO)


    def randomData(self):
        print()        
        #self.data = numpy.random.shuffle(self.data)
        



db = Database()
db.getData()
db.printer()
#db.types()
#db.randomData()

#v = [0,5,2,5,5]
#print(range(len(v)))

#rn = NeuralNetwork(10, 3, 1)
#rn.feedFoward(np.random.rand(10, 3))
'''
matriz = []
matriz.append(np.zeros((4,4), dtype=np.int))
matriz.append(np.zeros((4,4), dtype=np.int))

print(matriz[1][0][0])'''

#w1 = np.random.rand(2, 3)
#print(len(w1))
#print(w1.shape[0])
#print(w1)



'''
import os

var = os.system("ls")
print(var)'''

'''
M4_48	194	16	UTF-8	Apache	NA	ES	Barcelona	0	0	0	0	0	3	186	0	0	0	1
M4_41	198	17	UTF-8	Apache	NA	ES	Barcelona	0	0	0	0	0	2	124	0	0	0	1
B0_162	201	34	utf-8	Apache/2.2.16 (Debian)	8904	US	FL	83	2	6	6631	87	89	132181	6945	87	4	0
B0_1152	234	34	ISO-8859-1	cloudflare-nginx	NA	US	CA	0	0	0	0	0	0	0	0	0	0	0
B0_676	249	40	utf-8	Microsoft-IIS/8.5	24435	US	Wisconsin	19	6	11	2314	25	28	3039	2776	25	6	0
'''

