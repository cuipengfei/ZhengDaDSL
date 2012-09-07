package main;

/**
 * Created with IntelliJ IDEA.
 * User: twer
 * Date: 9/8/12
 * Time: 2:45 AM
 * To change this template use File | Settings | File Templates.
 */
public class DSLAnalyzer {
    public static String analyze(String inputCode) {
        String result = null;
        if (isSingleMethod(inputCode)) {
            result = inputCode + ": ROOT\n"
                    + "Method(s) number: 1"
            ;
        }
        return result;
    }

    private static boolean isSingleMethod(String inputCode) {
        return !inputCode.contains("{");
    }
}
