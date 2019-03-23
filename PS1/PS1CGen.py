import random
import string
from random import randint

numQueries = 200
numDistinct = 8
List = []

file = open("test cases.txt", "w")

class Patient:
    def __init__(self,name,eLevel):
        self.name = name
        self.eLevel = eLevel

def actionZero():
    name = "".join(random.choice(string.ascii_letters.upper())
                       for n in range(randint(3,9)))
    eLevel = randint(30,100)
    tempPatient = Patient(name,eLevel)
    if tempPatient not in List:
        List.append(tempPatient)
    file.write("0 " + name +  " " + str(eLevel) + "\n")
        
def actionOne():
        updatePatient = random.choice(List)
        updatePatientELevel = updatePatient.eLevel
        toIncrease = randint(0,100-updatePatientELevel)
        file.write ("1" + " " + updatePatient.name + " " + str(toIncrease)\
                    + "\n")
        newPatient = Patient(updatePatient.name, updatePatientELevel + \
                             toIncrease)
        List.remove(updatePatient)
        List.append(newPatient)

def actionTwo():
    treatedPatient = random.choice(List)
    file.write("2" + " " + treatedPatient.name + "\n")
    List.remove(treatedPatient)
        
file.write(str(numQueries) + "\n")

for i in  range(numQueries+1):
    ran = randint(0,3)
    if ran == 0: #Add if too  not too many patients. Else, treat
        actionTwo() if len(List) >= numDistinct else actionZero()
    elif ran == 1: #Update only if list isnt empty
        actionOne() if len(List) != 0 else actionZero()
    elif ran == 2: #Treat only if list isnt empty
        actionTwo() if len(List) != 0 else actionZero()
    else: #Query
        file.write(str(3) + "\n")

file.close()
        


