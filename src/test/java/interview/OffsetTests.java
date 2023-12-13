package interview;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OffsetTests {
    @Test
    void testToString() {
        Offset offset = new Offset(3, 15);
        assertEquals("[lineOffset=3, charOffset=15]", offset.toString());
    }

    @Test
    void testEquals() {
        Offset offset1 = new Offset(2, 10);
        Offset offset2 = new Offset(2, 10);
        Offset offset3 = new Offset(3, 15);

        assertEquals(offset1, offset2);
      //  assertNotEquals(offset1, offset3);
    }





}
