package asm;

public enum Register {
    AX("AX", "000", "1"),
    CX("CX", "001", "1"),
    DX("DX", "010", "1"),
    BX("BX", "011", "1"),
    SP("SP", "100", "1"),
    BP("BP", "101", "1"),
    SI("SI", "110", "1"),
    DI("DI", "111", "1"),

    AL("AL", "000", "0"),
    CL("CL", "001", "0"),
    DL("DL", "010", "0"),
    BL("BL", "011", "0"),
    AH("AH", "100", "0"),
    BH("BH", "101", "0"),
    SH("SH", "110", "0"),
    DH("DH", "111", "0"),

    ES("ES", "00", "0"),
    CS("CS", "01", "0"),
    SS("SS", "10", "0"),
    DS("DS", "11", "0");

    public String getName() { return name; }
    public String getBits() { return bits; }
    public String getW() { return w; }

    Register(String name, String bits, String w) {
        this.name = name;
        this.bits = bits;
        this.w = w;
    }

    private final String name;
    private final String bits;
    private final String w;
}
