import sys
def string_to_array(str):
    list = []
    for i in str:
        list.append(int(i))
    return list

if __name__ == '__main__':
    print(string_to_array(sys.argv[1]))
