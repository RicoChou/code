# 调整堆中单个节点位置
def heapify(list, i, heap_size):
    # 注意下标从0开始，使用移位操作符注意优先级
    l = (i << 1) + 1
    r = (i << 1) + 2
    
    # 父节点和两个子节点取最大节点为父节点
    largest = i
    if l < heap_size and list[i] < list[l]:
        largest = l

    if r < heap_size and list[largest] < list[r]:
        largest = r

    if i != largest:
        list[i], list[largest] = list[largest], list[i]
        heapify(list, largest, heap_size)

# 数组下标从int(len(list) / 2) + 1开始可视为朴素堆（即单个元素的堆）
def build_heap(list):
    heap_size = len(list)
    for i in range(int((len(list) - 1) / 2), -1, -1): 
        heapify(list, i, heap_size)
    return list

def heap_sort(list):
    build_heap(list)
    heap_size = len(list)
    for i in range(len(list) - 1, 0, -1):
        list[0], list[i] = list[i], list[0]
        heap_size -= 1
        heapify(list, 0, heap_size)
    return list

# 堆排序，将根节点与数组最后一个元素互换，并使得 heap_size - 1，同时调整堆，实现 in-place 排序
list = [3,9,27,7,9,9,1,5,16,12,4,8,17,0]
# print(build_heap(list))
print(heap_sort(list))
