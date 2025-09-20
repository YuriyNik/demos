package dryrun.stack;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.EmptyStackException;

public class MinStackNaive implements MinStack{

    private Deque<Integer> stack = new ArrayDeque<>();

    private Deque<Integer> minStack = new ArrayDeque<>();

    @Override
    public void push(int x) {
        stack.push(x);
        if (minStack.isEmpty() || x<= minStack.peek()) {
            minStack.push(x);
        }
    }

    @Override
    public int pop() {
        if (stack.isEmpty()){
            throw new EmptyStackException();
        }
        int value = stack.pop();
        if (value == minStack.peek()) {
            minStack.pop();
        }
        return value;
    }

    @Override
    public int top() {
        if (stack.isEmpty()){
            throw new EmptyStackException();
        }
        return stack.peek();
    }

    @Override
    public int getMin() {
        if (minStack.isEmpty()) {
            throw new EmptyStackException();
        }
        return minStack.peek();
    }
}
