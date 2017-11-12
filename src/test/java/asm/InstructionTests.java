package asm;

import org.junit.Test;
import static org.junit.Assert.*;

public class InstructionTests {
    @Test public void parseLabelTest() {
        assertEquals("LABEL", Instruction.parseLabel(" label    : mov ax, bx ; comment  "));
    }

    @Test public void parseLabelWithoutLabelTest() {
        assertNull(Instruction.parseLabel("mov ax, bx ; comment  "));
    }

    @Test public void parseCommentTest() {
        assertEquals("COMMENT", Instruction.parseComment(" label    : mov ax, bx ; comment  "));
    }

    @Test public void parseCommentWithoutCommentTest() {
        assertNull(Instruction.parseComment(" label    : mov ax, bx"));
    }

    @Test public void parseCommandTest() {
        assertEquals("MOV AX,BX", Instruction.parseCommand(" label    : mov ax, bx ; comment  "));
    }

    @Test public void determineNumberOfArguments1Test() {
        assertEquals(1, Instruction.determineNumberOfArguments("push ax"));
    }

    @Test public void determineNumberOfArguments2Test() {
        assertEquals(2, Instruction.determineNumberOfArguments("mov ax, bx"));
    }
}
