package asm;

import org.junit.Test;
import static org.junit.Assert.*;

public class ModRMTests {
    @Test public void fromExactKeyValueTest() {
        ModRM value = ModRM.from(Argument.from("[BX+SI]"));
        assertEquals("00", value.getMod());
        assertEquals("000", value.getRm());
    }

    @Test public void fromKeyWithDisp8ValueTest() {
        ModRM value = ModRM.from(Argument.from("[BP+DI+21H]"));
        assertEquals("01", value.getMod());
        assertEquals("011", value.getRm());
    }

    @Test public void fromKeyWithDisp16ValueTest() {
        ModRM value = ModRM.from(Argument.from("[BP+100H]"));
        assertEquals("10", value.getMod());
        assertEquals("110", value.getRm());
    }

    @Test public void from8BitRegisterTest() {
        ModRM value = ModRM.from(Argument.from("CL"));
        assertEquals("11", value.getMod());
        assertEquals("001", value.getRm());
        assertEquals("0", value.getW());
    }

    @Test public void from16BitRegisterTest() {
        ModRM value = ModRM.from(Argument.from("BP"));
        assertEquals("11", value.getMod());
        assertEquals("101", value.getRm());
        assertEquals("1", value.getW());
    }

    @Test public void fromDirectTest() {
        ModRM value = ModRM.from(Argument.from("variableName"));
        assertEquals("00", value.getMod());
        assertEquals("110", value.getRm());
    }

    @Test public void fromImmediateTest() {
        ModRM value = ModRM.from(Argument.from("21H"));
        assertNull(value);
    }
}
