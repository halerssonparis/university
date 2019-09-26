from sklearn.tree import DecisionTreeClassifier
from sklearn.model_selection import train_test_split
from sklearn import metrics
from sklearn.metrics import confusion_matrix
import csv
import random

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

clf = DecisionTreeClassifier()

clf.fit(X_train,y_train)

y_pred=clf.predict(X_test)

print("Accuracy:",metrics.accuracy_score(y_test, y_pred))

print(confusion_matrix(y_test, y_pred)) 