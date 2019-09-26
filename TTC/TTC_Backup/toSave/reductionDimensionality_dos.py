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

with open ('dos/reduced') as csvfile:
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
back_x = []
back_y = []
land_x = []
land_y = []
neptune_x = []
neptune_y = []
pod_x = []
pod_y = []
smurf_x = []
smurf_y = []
teardrop_x = []
teardrop_y = []

for i in range(0, len(classValues)):
    if (classValues[i] == 'normal.'):
        normal_x.append(X_pca[i, 0])
        normal_y.append(X_pca[i, 1])

    elif (classValues[i] == 'back.'):
        back_x.append(X_pca[i, 0])
        back_y.append(X_pca[i, 1])
    
    elif (classValues[i] == 'land.'):
        land_x.append(X_pca[i, 0])
        land_y.append(X_pca[i, 1])

    elif (classValues[i] == 'neptune.'):
        neptune_x.append(X_pca[i, 0])
        neptune_y.append(X_pca[i, 1])

    elif (classValues[i] == 'pod.'):
        pod_x.append(X_pca[i, 0])
        pod_y.append(X_pca[i, 1])

    elif (classValues[i] == 'smurf.'):
        smurf_x.append(X_pca[i, 0])
        smurf_y.append(X_pca[i, 1])

    elif (classValues[i] == 'teardrop.'):
        teardrop_x.append(X_pca[i, 0])
        teardrop_y.append(X_pca[i, 1])

plt.scatter(normal_x, normal_y, color='red', alpha=0.5,label='Normal')
plt.scatter(back_x, back_y, color='blue', alpha=0.5,label='back')
plt.scatter(land_x, land_y, color='yellow', alpha=0.5,label='land')
plt.scatter(neptune_x, neptune_y, color='pink', alpha=0.5,label='neptune')
plt.scatter(pod_x, pod_y, color='brown', alpha=0.5,label='pod')
plt.scatter(smurf_x, smurf_y, color='green', alpha=0.5,label='smurf')
plt.scatter(teardrop_x, teardrop_y, color='orange', alpha=0.5,label='teardrop')

plt.title("PCA")
plt.ylabel('Les coordonnees de Y')
plt.xlabel('Les coordonnees de X')  
plt.legend()
plt.show()