package asm;

public abstract class Argument {
    public static Argument from(final String str) {
        String formatted = format(str);
        Argument result = null;
        switch(determineType(formatted)) {
            case "R": result = new RegisterArgument(formatted); break;
            case "I": result = new ImmediateArgument(formatted); break;
            case "M": result = new MemoryArgument(formatted); break;
        }
        if(result != null) result.formatted = formatted;
        return result;
    }

    public static int parseValue(final String raw) {
        int base = 10;
        String value = raw;
        char lastChar = raw.charAt(raw.length()-1);

        if(!Character.isDigit(lastChar)) {
            value = raw.substring(0, raw.length()-1);
            switch (Character.toUpperCase(lastChar)) {
                case 'H': base = 16; break;
                case 'O': base = 8; break;
                case 'B': base = 2; break;
                default: throw new IllegalArgumentException();
            }
        }

        return Integer.valueOf(value, base);
    }

    public String getType() { return type; }
    public boolean isCompleted() { return isCompleted; }
    public boolean isVariable() { return false; }
    public void completeWith(final int value) {
        if(isCompleted()) throw new IllegalStateException("Argument already completed");
    }

    @Override public String toString() { return formatted; }

    static String format(String str) {
        return str.replaceAll("\\s", "").toUpperCase();
    }

    static String determineType(String str) {
        if(isRegister(str)) return "R";
        else if(isImmediate(str)) return "I";
        else if(isMemory(str)) return "M";
        else throw new IllegalArgumentException("Illegal argument");
    }

    static boolean isRegister(String str) { return Register.isRegister(str); }
    static boolean isImmediate(String str) { return Character.isDigit(str.charAt(0)); }
    static boolean isMemory(String str) { return !isRegister(str) && !isImmediate(str); }

    void setType(String type) { this.type = type; }
    void setCompleted(boolean completed) { isCompleted = completed; }

    private String type;
    private boolean isCompleted;
    private String formatted;
}

class RegisterArgument extends Argument {
    RegisterArgument(String str) {
        this.register = Register.from(Argument.format(str));
        this.setType("R");
        this.setCompleted(true);
    }

    Register getRegister() { return register; }

    private Register register;
}

class ImmediateArgument extends Argument {
    ImmediateArgument(String str) {
        this.setType("I");
        this.setCompleted(true);
        this.value = Argument.parseValue(str);
    }

    int getValue() { return value; }

    private int value;
}

class MemoryArgument extends Argument {
    MemoryArgument(String str) {
        this.setType("M");
        this.setCompleted(true);
        if(str.charAt(0) == '[') {
            String[] elements = str.substring(1, str.length()-1).split("\\+");
            if(elements.length == 1 && Argument.isImmediate(elements[0]))
                address = new ImmediateArgument(elements[0]).getValue();
            else if(elements.length > 3)
                throw new IllegalArgumentException();
        }
        else {
            variableName = str;
            setCompleted(false);
        }
    }

    @Override public boolean isVariable() { return variableName != null; }
    int getAddress() { return address; }
    String getVariableName() { return variableName; }

    @Override
    public void completeWith(int value) {
        super.completeWith(value);
        this.address = value;
        setCompleted(true);
    }

    private int address;
    private String variableName = null;
}
