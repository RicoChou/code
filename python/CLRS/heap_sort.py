# 调整堆中单个节点位置
def heapify(list, i):
    # 注意下标从0开始，使用移位操作符注意优先级
    l = (i << 1) + 1
    r = (i << 1) + 2
    
    # 父节点和两个子节点取最大节点为父节点
    largest = i
    if l < len(list) and list[i] < list[l]:
        largest = l

    if r < len(list) and list[largest] < list[r]:
        largest = r

    if i != largest:
        list[i], list[largest] = list[largest], list[i]
        print(list)
        heapify(list, largest)

# 数组下标从int(len(list) / 2) + 1开始可视为朴素堆（即单个元素的堆）
def build_heap(list):
    for i in range(int((len(list) - 1) / 2), -1, -1): 
        heapify(list, i)
    return list

list = [3,9,27,7,13,10,1,5,16,12,4,8,17,0]
print(build_heap(list))
