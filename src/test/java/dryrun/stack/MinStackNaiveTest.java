package dryrun.stack;

import org.junit.jupiter.api.Test;

public class MinStackNaiveTest {

    @Test
    void minStackNaive_Test(){
        MinStack stack = new MinStackNaive();
        for (int i = 0; i < 10; i++) {
            stack.push(i);
        }

        System.out.println("Pop="+stack.pop());
        System.out.println("Top="+stack.top());
        System.out.println("Min="+stack.getMin());
        for (int i = 0; i < 8; i++) {
            System.out.println(stack.pop());
        }
    }
}
