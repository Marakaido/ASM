package asm;

import java.util.Map;
import static java.util.Map.*;

public abstract class Instruction {
    /*public static Instruction from(final String str){
        final String formatted = format(str);

    }*/

    public String getComment() { return comment; }
    public String getLabel() { return label; }

    public abstract byte[] toByteArray();

    static String format(final String str) {
        return str.replaceAll("\\s", "").toUpperCase();
    }

    static String formatCommand(final String command) {
        String intermidiate = command.replaceAll("\\s+", " ").trim().toUpperCase();
        String[] elements = intermidiate.split(" ");
        StringBuilder arguments = new StringBuilder();
        for (int i = 1; i < elements.length; i++) arguments.append(elements[i]);
        return elements[0] + " " + arguments.toString().replaceAll("\\s", "");
    }

    static String parseComment(final String str) {
        String comment = null;
        String[] elements = str.split(";");
        if(elements.length > 1) comment = elements[1];
        if(comment != null) return format(comment);
        else return null;
    }

    static String parseLabel(final String str) {
        String label = null;
        String[] elements = str.split(":");
        if(elements.length > 1) label = elements[0];
        if(label != null) return format(label);
        else return null;
    }

    static String parseCommand(final String str) {
        String command = null;
        String[] elements = str.split(":");
        String withoutLabel = elements[0];
        if(elements.length > 1) withoutLabel = elements[1];
        elements = withoutLabel.split(";");
        if(elements[0] != null) return formatCommand(elements[0]);
        else return null;
    }

    static int determineNumberOfArguments(final String str) {
        int result = 1;
        String withoutComment = str.split(";")[0];
        for (int i = 0; i < withoutComment.length(); i++)
            if(withoutComment.charAt(i) == ',')
                result++;
        return result;
    }

    private String comment = null;
    private String label = null;
    private Map<String, String> table = Map.ofEntries(
            entry("MOV R,R", "1000101 w mod reg r/m"),
            entry("MOV R,M", "1000101 w mod reg r/m"),
            entry("MOV M,R", "1000100 w mod reg r/m"),
            entry("MOV R,I", "1011 w reg im"),
            entry("MOV M,I", "1100011 w mod 000 r/m"),

            entry("INT I", "11001101 im")
    );
}