package dryrun;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StringTasksTest {

    @Test
    void firstEvenLengthWord_tests(){
        StringTasks stringTasks = new StringTasks();
        assertEquals(stringTasks.firstEvenLengthWord("Java is cool"),"Java");
        assertEquals(stringTasks.firstEvenLengthWord("I love coding"),"love");
        assertEquals(stringTasks.firstEvenLengthWord("Odd word here"),"word");
        assertEquals(stringTasks.firstEvenLengthWord("one three seven"),"");
    }

    @Test
    void longestWord_tests(){
        StringTasks stringTasks = new StringTasks();
        assertEquals("coding", stringTasks.longestWord("I love coding"));
        assertEquals("hello",  stringTasks.longestWord("hi hello hey"));
        assertEquals("Java",   stringTasks.longestWord("Java is cool"));
        assertEquals("example",stringTasks.longestWord("this is an example"));
    }
}
