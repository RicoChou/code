from time import time
# 快速排序
def quick_sort(list, l, r):
    if l < r:
        mid = partition(list, l, r) 
        quick_sort(list, l, mid - 1)
        quick_sort(list, mid + 1, r)

# 从数组左边出发，(j)找到比主元(pivot element)小的数, 与一个比主元大的数对调，最后返回中点
def partition(list, l, r):
    i = l - 1
    for j in range(l, r):
        # < 与 <= 皆可
        if list[j] <= list[r]:
            i += 1
            list[i], list[j] = list[j], list[i]
    list[i+1], list[r] = list[r], list[i+1]
    return i + 1

start = time()
list = [3,9,27,7,9,0,1,5,16,12,4,8,17,9]
quick_sort(list, 0, len(list) - 1)
end = time()
print('time is %f ms' % (end - start))
print(list)
