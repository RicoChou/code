def count_sort(list, k):
    counter = [0] * k
    res = [None] * len(list)

    for e in list:
        counter[e] += 1
    # 记录小于等于自身的元素个数，即知道自己的位置
    for i in range(1, len(counter)):
        counter[i] += counter[i-1]
    
    for ele in list:
        res[counter[ele] - 1] = ele
        # 减一兼容所有元素并不都互异情况
        counter[ele] -= 1
    return res

list = [3,9,27,7,9,0,1,5,16,12,4,8,17,9]
print(count_sort(list, 28))
