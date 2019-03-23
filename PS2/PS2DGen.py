import random
import string
from random import randint

numQueries = 500
numDistinct = 30
List = []

file = open("PS2 test cases.txt", "w")

class Patient:
    def __init__(self,name,gender):
        self.name = name
        self.gender = gender

def actionOne():
    name = "".join(random.choice(string.ascii_letters.upper())
                       for n in range(randint(1,30)))
    gender = randint(1,2)
    if name not in List:
        List.append(name)
    file.write("1 " + name + " " + str(gender) + "\n")
        
def actionTwo():
    toRemove = random.choice(List)
    file.write("2 " + toRemove + "\n")
    List.remove(toRemove)

def actionThree():
    correct = False
    while (not correct):
        start = "".join(random.choice(string.ascii_letters.upper())
                       for n in range(randint(1,30)))
        end = "".join(random.choice(string.ascii_letters.upper())
                       for n in range(randint(1,30)))
        if (end > start):
            correct = True
    num = randint(0,2)
    file.write("3 " + start + " " + end + " " + str(num) + "\n")
        

for i in  range(numQueries+1):
    ran = randint(1,3)
    if ran == 1: #Add 
        actionOne()
    elif ran == 2: #Remove only if list isnt empty
        actionTwo() if len(List) != 0 else actionOne()
    elif ran == 3: #Query only if list isnt empty
        actionThree() if len(List) != 0 else actionOne()
    else:
        continue
    
file.write("0")
file.close()
        


