package asm;

import org.junit.Test;
import static org.junit.Assert.*;

public class ArgumentTests {
    @Test public void typeRegisterTest() {
        Argument value = Argument.from("SI");
        assertEquals("R", value.getType());
    }

    @Test public void typeImmediateTest() {
        Argument value = Argument.from("21H");
        assertEquals("I", value.getType());
    }

    @Test public void typeMemoryBracketsTest() {
        Argument value = Argument.from("[BP+SI+23H]");
        assertEquals("M", value.getType());
    }

    @Test public void typeMemoryVariableTest() {
        Argument value = Argument.from("variableName");
        assertEquals("M", value.getType());
    }

    @Test public void hexValueParseTest() {
        int value = Argument.parseValue("21H");
        assertEquals(33, value);
    }

    @Test public void octalValueParseTest() {
        int value = Argument.parseValue("67o");
        assertEquals(55, value);
    }

    @Test public void decimalValueParseTest() {
        int value = Argument.parseValue("79");
        assertEquals(79, 79);
    }

    @Test(expected = IllegalArgumentException.class)
    public void wrongFormatSpecifierParseTest() {
        Argument.parseValue("74i");
    }

    @Test(expected = NumberFormatException.class)
    public void wrongNumberFormatParseTest() {
        Argument.parseValue("78o");
    }

    @Test public void isCompletedTest() {
        assertTrue(Argument.from("[BP+SI+31H]").isCompleted());
        assertTrue(Argument.from("45").isCompleted());
        assertFalse(Argument.from("variableName").isCompleted());
        assertTrue(Argument.from("[31H]").isCompleted());
    }

    @Test(expected = IllegalStateException.class)
    public void completeWithCompletedTest() {
        Argument.from("[BP+SI+31H]").completeWith(32);
    }

    @Test public void completeWithVariableTest() {
        MemoryArgument argument = (MemoryArgument) Argument.from("variable");
        argument.completeWith(32);
        assertTrue(argument.isCompleted());
        assertEquals(32, argument.getAddress());
    }

    @Test public void registerArgumentGetRegisterTest() {
        RegisterArgument argument = (RegisterArgument) Argument.from("BX");
        assertEquals(Register.from("BX"), argument.getRegister());
    }
}
