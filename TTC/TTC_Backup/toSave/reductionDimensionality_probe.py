from sklearn.decomposition import PCA
import matplotlib.pyplot as plt
import csv 
import random
from sklearn import datasets, svm, metrics
from sklearn.model_selection import train_test_split
from sklearn.metrics import confusion_matrix

value = []
values = []
classValues = []

with open ('normalProbe/reduced') as csvfile:
    readed = csv.reader(csvfile, delimiter=',')
    print(readed)
    for row in readed: 
        value.append(row)

random.shuffle(value)

for ovalue in value:
    last = ovalue.pop()
    values.append(ovalue)
    classValues.append(last)

LabelsD = list(set(classValues))


pca = PCA(n_components=2, whiten = False, random_state = 2019)
X_pca = pca.fit_transform(values)

print(pca.explained_variance_ratio_)

X_pca_reconst = pca.inverse_transform(X_pca)

plt.figure(figsize=(12,12))

normal_x = []
normal_y = []
nmap_x = []
nmap_y = []
port_x = []
port_y = []
ips_x = []
ips_y = []
satan_x = []
satan_y = []

for i in range(0, len(classValues)):
    if (classValues[i] == 'normal.'):
        normal_x.append(X_pca[i, 0])
        normal_y.append(X_pca[i, 1])

    elif (classValues[i] == 'nmap.'):
        nmap_x.append(X_pca[i, 0])
        nmap_y.append(X_pca[i, 1])
    
    elif (classValues[i] == 'portsweep.'):
        port_x.append(X_pca[i, 0])
        port_y.append(X_pca[i, 1])

    elif (classValues[i] == 'ipsweep.'):
        ips_x.append(X_pca[i, 0])
        ips_y.append(X_pca[i, 1])

    elif (classValues[i] == 'satan.'):
        satan_x.append(X_pca[i, 0])
        satan_y.append(X_pca[i, 1])

plt.scatter(normal_x, normal_y, color='red', alpha=0.5,label='Normal')
plt.scatter(nmap_x, nmap_y, color='blue', alpha=0.5,label='nmap')
plt.scatter(port_x, port_y, color='yellow', alpha=0.5,label='port')
plt.scatter(ips_x, ips_y, color='pink', alpha=0.5,label='ips')
plt.scatter(satan_x, satan_y, color='brown', alpha=0.5,label='satan')

plt.title("PCA")
plt.ylabel('Les coordonnees de Y')
plt.xlabel('Les coordonnees de X')  
plt.legend()
plt.show()