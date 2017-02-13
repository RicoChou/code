public class MinStack {

    Stack<Integer> stack;
    List<Integer> list;

    /** initialize your data structure here. */
    public MinStack() {
        stack = new Stack<>();
        list = new LinkedList<>();
    }

    public void push(int x) {
        stack.push(x);
        int i;
        for (i = 0; i < list.size(); i++) {
            if (list.get(i) > x) {
                break;
            }
        }
        list.add(i, x);
    }

    public void pop() {
        list.remove(new Integer(stack.pop()));
    }

    public int top() {
        return stack.peek();
    }

    public int getMin() {
        return list.get(0);
    }
}

/**
 * Your MinStack object will be instantiated and called as such:
 * MinStack obj = new MinStack();
 * obj.push(x);
 * obj.pop();
 * int param_3 = obj.top();
 * int param_4 = obj.getMin();
 */
