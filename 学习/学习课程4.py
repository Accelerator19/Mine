#class Car():
#     def __init__(self,name,type):
#         self.name = name;
#         self.type = type;
#         self.oil = 0;
#     def add_oil(self,num):
#         self.oil+=num;
#
# class Electric_car(Car):
#     def __init__(self,name,type):
#         super().__init__(name,type)
#         self.battery = 0
#     def add_oil(self,num):
#         print("Electric cars don't need oil")
#     def charge_in(self,num):
#         self.battery+=num
# my_car = Car("保时捷","911")
# my_electric_car = Electric_car("奔驰","smart")
# my_car.add_oil(10)
# my_electric_car.add_oil(10)
# my_electric_car.charge_in(10)
# print("My car is "+my_car.name+my_car.type+",and it has "+str(my_car.oil)+" oil")
# print("My car is "+my_electric_car.name+my_electric_car.type+",and it has "+str(my_electric_car.battery)+" battery")
class Battery():
    def __init__(self,num,size):
        self.battery = num;
        self.size = size;
    def charge_in(self,num):
        self.battery+=num
    def check_battery(self):
        print("The car has "+str(self.battery)+" battery now")
class Electric_car():
    def __init__(self,name,type):
        self.name = name;
        self.type = type;
        self.battery = Battery(0,100)
my_car = Electric_car("奔驰","smart")
my_car.battery.charge_in(20)
my_car.battery.check_battery()