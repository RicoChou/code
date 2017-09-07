import math

# O(nlgn) 时间查找最大子序列
# 分治法，最大子序列存在于左序列，跨越中点序列，右序列其中之一
def find_max_subarray(list, low, high):
    if low == high:
        return (low, high, list[low])
    else:
        mid = int((low + high) / 2)
        (left_low, left_high, left_sum) = find_max_subarray(list, low, mid)
        (right_low, right_high, right_sum) = find_max_subarray(list, mid + 1, high)
        (cross_low, cross_high, cross_sum) = find_max_crossing_subarray(list, low, mid, high)
    if left_sum >= right_sum and left_sum >= cross_sum:
        return (left_low, left_high, left_sum)
    elif right_sum >= left_sum and right_sum >= cross_sum:
        return (right_low, right_high, right_sum)
    else:
        return (cross_low, cross_high, cross_sum)

# 查找跨越中点的最大子序列
def find_max_crossing_subarray(list, low, mid, high):
    left_sum = float("-inf")
    sum = 0
    left_pos = mid
    for i in range(mid, low - 1, -1):
        sum += list[i]
        if sum > left_sum:
            left_sum = sum
            left_pos = i

    right_sum = float("-inf")
    sum = 0
    right_pos = mid + 1
    for j in range(mid + 1, high + 1, 1):
        sum += list[j]
        if sum > right_sum:
            right_sum = sum
            right_pos = j
    return (left_pos, right_pos, left_sum + right_sum)

# 线性时间查找最大子序列，动态规划
def find_max_subarray_liner(list):
    max_sum = float("-inf")
    temp_sum = 0
    left_pos = 0
    right_pos = 0
    j = 0
    for i in list:
        temp_sum += i
        if temp_sum > max_sum:
            max_sum = temp_sum
            right_pos = j
        if temp_sum < 0:
            temp_sum = 0
            left_pos = j + 1
        j += 1
    return (left_pos, right_pos, max_sum)

list = [3,-1,2,5,-3,4,-6,-7,1,8,-3,5,9]
print (find_max_subarray(list, 0, len(list) - 1))
print (find_max_subarray_liner(list))

