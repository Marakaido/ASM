package asm;

import java.util.Map;
import static java.util.Map.*;

public class ModRM {
    public static ModRM from(final Argument argument) {
        String rep = argument.toString();
        String key = null;
        switch (argument.getType()) {
            case "M":
                if(argument.isVariable()) key = "direct";
                else key = replaceDispWithType(rep);
                break;
            case "R": key = rep; break;
            case "I": return null;
        }

        return table.get(key);
    }

    private static String replaceDispWithType(String key) {
        String[] elements = key.substring(1, key.length()-1).split("\\+");
        String result = key;
        for(String element : elements)
            if(!table.containsKey(element)) {
                int value = parseImmediateValue(element);
                if(value > 255) result = key.replace(element, "disp16");
                else result = key.replace(element, "disp8");
            }
        return result;
    }

    private static int parseImmediateValue(String str) {
        if(!table.containsKey(str)) return Integer.valueOf(str.substring(0, str.length()-1), 16);
        else throw new NumberFormatException();
    }

    public String getMod() { return mod; }
    public String getRm() { return rm; }
    public String getW() { return w; }

    private ModRM(String mod, String rm, String w) {
        this.mod = mod;
        this.rm = rm;
        this.w = w;
    }

    private final String mod, rm, w;
    private static final Map<String, ModRM> table = Map.ofEntries(
            entry("[BX+SI]", new ModRM("00", "000", null)),
            entry("[BX+DI]", new ModRM("00", "001", null)),
            entry("[BP+SI]", new ModRM("00", "010", null)),
            entry("[BP+DI]", new ModRM("00", "011", null)),
            entry("[SI]", new ModRM("00", "100", null)),
            entry("[DI]", new ModRM("00", "101", null)),
            entry("direct", new ModRM("00", "110", null)),
            entry("[BX]", new ModRM("00", "111", null)),

            entry("[BX+SI+disp8]", new ModRM("01", "000", null)),
            entry("[BX+DI+disp8]", new ModRM("01", "001", null)),
            entry("[BP+SI+disp8]", new ModRM("01", "010", null)),
            entry("[BP+DI+disp8]", new ModRM("01", "011", null)),
            entry("[SI+disp8]", new ModRM("01", "100", null)),
            entry("[DI+disp8]", new ModRM("01", "101", null)),
            entry("[BP+disp8]", new ModRM("01", "110", null)),
            entry("[BX+disp8]", new ModRM("01", "111", null)),

            entry("[BX+SI+disp16]", new ModRM("10", "000", null)),
            entry("[BX+DI+disp16]", new ModRM("10", "001", null)),
            entry("[BP+SI+disp16]", new ModRM("10", "010", null)),
            entry("[BP+DI+disp16]", new ModRM("10", "011", null)),
            entry("[SI+disp16]", new ModRM("10", "100", null)),
            entry("[DI+disp16]", new ModRM("10", "101", null)),
            entry("[BP+disp16]", new ModRM("10", "110", null)),
            entry("[BX+disp16]", new ModRM("10", "111", null)),

            entry("AL", new ModRM("11", "000", "0")),
            entry("CL", new ModRM("11", "001", "0")),
            entry("DL", new ModRM("11", "010", "0")),
            entry("BL", new ModRM("11", "011", "0")),
            entry("AH", new ModRM("11", "100", "0")),
            entry("CH", new ModRM("11", "101", "0")),
            entry("DH", new ModRM("11", "110", "0")),
            entry("BH", new ModRM("11", "111", "0")),

            entry("AX", new ModRM("11", "000", "1")),
            entry("CX", new ModRM("11", "001", "1")),
            entry("DX", new ModRM("11", "010", "1")),
            entry("BX", new ModRM("11", "011", "1")),
            entry("SP", new ModRM("11", "100", "1")),
            entry("BP", new ModRM("11", "101", "1")),
            entry("SI", new ModRM("11", "110", "1")),
            entry("DI", new ModRM("11", "111", "1"))
    );
}
