from sklearn.model_selection import train_test_split
from sklearn.naive_bayes import GaussianNB
from sklearn import preprocessing
from sklearn import metrics
import numpy as np
import random
import csv

value = []
values = []
classValues = []

with open ('normalProbe/probe') as csvfile:
    readed = csv.reader(csvfile, delimiter=',')
    print(readed)
    for row in readed: 
        value.append(row)

random.shuffle(value)

for ovalue in value:
    last = ovalue.pop()
    values.append(ovalue)
    classValues.append(last)

X_train, X_test, y_train, y_test = train_test_split(values, classValues, test_size=0.3)

le = preprocessing.LabelEncoder()

X_train = np.asarray(X_train, dtype=np.float64)
X_test = np.asarray(X_test, dtype=np.float64)  #tem q converter essa bosta se n da pau de compatibilidade de array
y_train = le.fit_transform(y_train)
y_test = le.fit_transform(y_test)

gnb = GaussianNB()

gnb.fit(X_train, y_train)

y_pred = gnb.predict(X_test)

print("Accuracy:",metrics.accuracy_score(y_test, y_pred))