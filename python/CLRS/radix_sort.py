import random

def radix_sort(list, digit):
    for i in range(digit):
        radix = 10 ** i
        bucket = [[] for i in range(10)]
        for e in list:
            bucket[e // radix % 10].append(e)
        list = [ele for ele_list in bucket for ele in ele_list]
    return list

list = [random.randint(0,9999) for i in range(20)]
print(radix_sort(list, 4))
