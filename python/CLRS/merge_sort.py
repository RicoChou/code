import math
# 归并排序，分治法
def merge_sort(list, begin, end):
    if begin < end:
        middle = int((begin + end) / 2)
        merge_sort(list, begin, middle)
        merge_sort(list, middle + 1, end)
        merge(list, begin, middle, end)

# 合并左右两边已排序子序列
def merge(list, begin, middle, end):
    l = list[begin:middle + 1] + [float("inf")]
    r = list[middle + 1:end + 1] + [float("inf")]
    m = 0
    n = 0
    for i in range(begin, end + 1):
        if l[m] <= r[n]:
            list[i] = l[m]
            m += 1
        else:
            list[i] = r[n]
            n += 1

list = [8, 3, 4, 2, 1, 6, 0, 4, 8, 3, 7, 3, 5, 2, 0, 3, 5, 2, 5, 8] 
merge_sort(list, 0, len(list) - 1)
print(list)
