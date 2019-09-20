class Users() :
    def __init__(self,first_name,last_name,age):
        self.first_name = first_name
        self.last_name = last_name
        self.age = age
    def describe_user(self):
        print((self.first_name+" "+self.last_name).title()+" is "+self.age+" years old now!")
    def greet_user(self):
        print((self.first_name+" "+self.last_name).title()+",welcome to python world!")
first_name = input("Please tell me your first name")
last_name = input("Then tell me your last name")
age = input("So how old are you?")
user = Users(first_name,last_name,age)
user.describe_user()