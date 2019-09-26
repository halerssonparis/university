import matplotlib.pyplot as plt
import csv 
import random
from sklearn import datasets, svm, metrics
from sklearn.model_selection import train_test_split
from sklearn.metrics import confusion_matrix

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


#n_samples = len(values)
#data = values
# Create a classifier: a support vector classifier

classifier = svm.SVC(gamma=0.0001)

X_train, X_test, y_train, y_test = train_test_split(values, classValues, test_size=0.3)

classifier.fit(X_train,y_train)

y_pred=classifier.predict(X_test)

print("Accuracy:",metrics.accuracy_score(y_test, y_pred))

print(confusion_matrix(y_test, y_pred)) 



# We learn the digits on the first half of the digits
#classifier.fit(data[:n_samples // 2], classValues[:n_samples // 2])

# Now predict the value of the digit on the second half:
#expected = classValues[n_samples // 2:]
#predicted = classifier.predict(data[n_samples // 2:])

#print("Classification report for classifier %s:\n%s\n"
#      % (classifier, metrics.classification_report(expected, predicted)))
#print("Confusion matrix:\n%s" % metrics.confusion_matrix(expected, predicted))


