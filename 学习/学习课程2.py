users = {
    "user1" : [{"username": "user1"},{"password": "123"}],
    "user2" : [{"username":"user2"},{"password":"123"}]
}
language = []
language.append("c++")
language.append("python")
language.append("c#")
print(language)
language.insert(1,"java")
print(language)
del language[2]
print(language)
print(language[2])
language.append("html")
language.append("css")
print(language)
out_language = language.pop(3)
print(out_language)
print(language)
language.remove("c++")
print(language)
# for user,count in users.items() :
#     print(user)
#     for count1 in count :
#         for u,v in count1.items() :
#             print(u+" "+v)
for user in users.keys() :
    print(user)
    for count in users[user] :
        for i in count.keys() :
            print(i+" "+count[i])
# print(users["user1"])