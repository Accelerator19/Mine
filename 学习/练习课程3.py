class Restaurant() :
    def __init__(self,name,type):
        self.name = name
        self.type = type
        self.number_served = 0
    def describe_restaurant(self):
        print("这是个"+self.type+"餐馆，它的名称是"+self.name)
    def open_restaurant(self):
        print(self.name+"现在已经开张了")
    def set_number_served(self,number):
        print("这一次"+self.name+"招待了"+str(number)+"个人")
        self.number_served+=number
    def report_score(self):
        print(self.name+"已经招待了"+str(self.number_served)+"个人!")
loop = 5
restaurants = []
while loop:
    loop = 5
    while loop==5:
        try:
            loop = int(input("请输入操作:\n1.添加餐馆\n2.描述餐馆\n3.选择餐馆吃饭\n4.查看业绩\n0.退出程序\n"))
        except ValueError:
            print("请输入操作前的数字！")
    if loop==1 :
        name = input("请输入餐馆名称")
        type = input("请输入餐馆类型")
        restaurants.append(Restaurant(name,type))
    elif loop==2 :
        if restaurants!=[] :
            for i in restaurants :
                i.describe_restaurant()
        else :
            print("还没有添加任何餐馆呢")
    elif loop==3 :
        for i in range(len(restaurants)) :
            print(str(i+1)+"."+restaurants[i].name)
        num = int(input("请选择在哪里吃饭,输入餐馆前数字"))
        if num<0 or num>len(restaurants) :
            print("输入错误，请重新输入")
        else :
            number_people = int(input("请输入用餐人数"))
            restaurants[num-1].set_number_served(number_people)
    elif loop==4 :
        for i in restaurants :
            i.report_score()
    elif loop==0 :
        break
    else :
        print("请输入正确的数字!")