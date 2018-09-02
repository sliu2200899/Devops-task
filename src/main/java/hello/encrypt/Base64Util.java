package hello.encrypt;

import org.apache.commons.codec.binary.Base64;

public class Base64Util {
    private static final String padding = "newtonx-padding";

    public static String encode(String input) {
        if (input == null || input.length() == 0) return "";
        String output = input + padding;
        return new String(Base64.encodeBase64(output.getBytes()));
    }

    public static String decode(String input) {

        if (input == null || input.length() == 0) return "";
        String output = new String(Base64.decodeBase64(input.getBytes()));
        int indexOfLast = output.lastIndexOf(padding);
        String newString = output;
        if(indexOfLast >= 0) newString = output.substring(0, indexOfLast);
        System.out.println("the processed string is " + newString);

        return newString;
    }
}
