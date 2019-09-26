from sklearn import preprocessing

allFeatures1 = []
allFeatures = []
allFeatures2 = []
allFeaturesLabeled1 = []
allFeaturesLabeled = []
allFeaturesLabeled2 = []

le = preprocessing.LabelEncoder()

def isAdded( feature, allFeatures2 ):
    if (len(allFeatures2) == 0):
        return True
    else:
        notHasBeenAdded = True

        for current_feature in allFeatures2:
            if current_feature == feature:
                return False

        if notHasBeenAdded:
            return True



f_read = open("dos/dos", "r")
f_write = open("dos/new_dos", "w")

f1 = f_read.readlines()

for line in f1:
    
    line_t = line.split(",")

    if ( isAdded(line_t[2], allFeatures) ):
        allFeatures.append(line_t[2])
        allFeaturesLabeled = le.fit_transform(allFeatures)

    if ( isAdded(line_t[1], allFeatures1) ):
        allFeatures1.append(line_t[1])
        allFeaturesLabeled1 = le.fit_transform(allFeatures1)

    if ( isAdded(line_t[3], allFeatures2) ):
        allFeatures2.append(line_t[3])
        allFeaturesLabeled2 = le.fit_transform(allFeatures2)


    for index in range(0, len(allFeatures)):
        if allFeatures[index] == line_t[2]:
            line_t[2] = allFeaturesLabeled[index]
            break
            
    for index in range(0, len(allFeatures2)):
        if allFeatures2[index] == line_t[3]:
            line_t[3] = allFeaturesLabeled2[index]
            break

    for index in range(0, len(allFeatures1)):
        if allFeatures1[index] == line_t[1]:
            line_t[1] = allFeaturesLabeled1[index]
            break

    str_tmp = ""
    for i in range(len(line_t)):
        str_tmp += "{}".format(line_t[i]) if (i == (len(line_t) - 1)) else "{},".format(line_t[i]) 

    f_write.write(str_tmp)

print(allFeatures)
