from sklearn.model_selection import train_test_split
from sklearn.ensemble import RandomForestClassifier
from sklearn import metrics
import random
import csv

value = []
values = []
classValues = []

#normalProbe/probe

with open ('dos/dos') as csvfile:
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

clf=RandomForestClassifier(n_estimators=100,)
clf.fit(X_train,y_train)

y_pred=clf.predict(X_test)

print("Accuracy:",metrics.accuracy_score(y_test, y_pred))
