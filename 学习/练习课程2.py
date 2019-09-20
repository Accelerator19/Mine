#unchecked = []
# checked = []
# print("Please enter your username,enter 'quit' to quit")
# while 1:
#     name = input()
#     if name=="quit" :
#         break
#     if name not in unchecked :
#         unchecked.append(name)
# unchecked.sort()
# while len(unchecked) :
#     name = unchecked.pop()
#     print(name)
#     checked.append(name)
# print(checked)

def judge(age) :
    if age<0 :
        print("Are you Fucking kiding me?")
    elif age<3 :
        print("You are too young to buy a ticket,just seat on your mother's legs")
    elif age<12 :
        print("You have to pay 10$ for this this ticket")
    else :
        print("You have to pay 15$ for this ticket")
while 1:
    age = int(input("请输入你的年龄，输入0结束"))
    if age == 0 :
        break
    judge(age)