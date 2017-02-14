public class MinStack {

    Stack<Element> stack;
    class Element {
        final int value;
        final int min;

        public Element(int value, int min) {
            this.value = value;
            this.min = min;
        }
    }

    /** initialize your data structure here. */
    public MinStack() {
        stack = new Stack<>();
    }

    public void push(int x) {
        if (stack.isEmpty()) {
            stack.push(new Element(x, x));
        } else {
            int min = stack.peek().min;
            if (x < min) {
                stack.push(new Element(x, x));
            } else {
                stack.push(new Element(x, min));
            }
        }
    }

    public void pop() {
        stack.pop();
    }

    public int top() {
        return stack.peek().value;
    }

    public int getMin() {
        return stack.peek().min;
    }
}

public class MinStack {

    private Element element;

    private class Element {
        final int value;
        final int min;
        final Element next;

        public Element(int value, int min) {
            this(value, min, null);
        }

        public Element(int value, int min, Element next) {
            this.value = value;
            this.min = min;
            this.next = next;
        }
    }

    /** initialize your data structure here. */
    public MinStack() {

    }

    public void push(int x) {
        if (element == null) {
            element = new Element(x, x);
        } else {
            element = new Element(x, Math.min(x, element.min), element);
        }
    }

    public void pop() {
        element = element.next;
    }

    public int top() {
        return element.value;
    }

    public int getMin() {
        return element.min;
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
