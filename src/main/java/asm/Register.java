package asm;

import java.util.Map;
import static java.util.Map.*;

public class Register {
    public static Register from(String str) {
        return table.get(str);
    }

    public static boolean isRegister(String str) {
        return table.containsKey(str);
    }

    public String getName() { return name; }
    public String getReg() { return reg; }
    public String getW() { return w; }

    private Register(String name, String reg, String w) {
        this.name = name;
        this.reg = w;
        this.w = w;
    }
    
    private String name, reg, w;
    private static final Map<String, Register> table = Map.ofEntries(
            entry("AX", new Register("AX", "000", "1")),
            entry("CX", new Register("CX", "001", "1")),
            entry("DX", new Register("DX", "010", "1")),
            entry("BX", new Register("BX", "011", "1")),
            entry("SP", new Register("SP", "100", "1")),
            entry("BP", new Register("BP", "101", "1")),
            entry("SI", new Register("SI", "110", "1")),
            entry("DI", new Register("DI", "111", "1")),

            entry("AL", new Register("AL", "000", "0")),
            entry("CL", new Register("CL", "001", "0")),
            entry("DL", new Register("DL", "010", "0")),
            entry("BL", new Register("BL", "011", "0")),
            entry("AH", new Register("AH", "100", "0")),
            entry("BH", new Register("BH", "101", "0")),
            entry("SH", new Register("SH", "110", "0")),
            entry("DH", new Register("DH", "111", "0")),

            entry("ES", new Register("ES", "00", "0")),
            entry("CS", new Register("CS", "01", "0")),
            entry("SS", new Register("SS", "10", "0")),
            entry("DS", new Register("DS", "11", "0"))
    );
}
