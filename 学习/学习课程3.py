# from 练习课程2 import judge as jd
# pets = []
# num = input("你有多少只宠物:")
# for i in range(int(num)) :
#     pet = input("它是什么:")
#     pets.append(pet)
# print("现在我们要删除所有的猫")
# while "猫" in pets :
#     pets.remove("猫")
#     print(pets)
# age = input()
# jd(age)
class Dog() :
    def __init__(self,name,age):
        self.name = name
        self.age = age
    def sit(self):
        print(self.name+" is now sitting")
    def roll_over(self):
        print(self.name+" is now rolling over")
my_dog = Dog("lucky",18)
print("My dog's name is "+my_dog.name+",it is "+str(my_dog.age)+" years old")
