package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

public class DSLAnalyzer {
    public static String analyze(String inputCode) throws IOException {
        String result = null;
        if (isSingleMethod(inputCode)) {
            result = inputCode + ": ROOT\n"
                    + "Method(s) number: 1";
        } else if (isNested(inputCode)) {
            String rootMethodName = getRootMethodName(inputCode);
            HashMap<String, String> nestedMethodNames = getNestedMethodNames(inputCode);

            result = rootMethodName + ": ROOT\n";
            for (Map.Entry<String, String> callerCalleePair : nestedMethodNames.entrySet()) {
                result += (">" + callerCalleePair.getValue() + ": " + "ROOT -> " + callerCalleePair.getKey() + "\n");
            }

            Integer methodsNumber = nestedMethodNames.size() + 1;
            result += "Method(s) number: " + methodsNumber.toString();
        }
        return result;
    }

    private static HashMap<String, String> getNestedMethodNames(String inputCode) throws IOException {
        StringReader stringReader = new StringReader(inputCode);
        BufferedReader bufferedReader = new BufferedReader(stringReader);

        HashMap<String, String> results = new HashMap<String, String>();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            if (isMethodCall(line)) {
                results.put(getMethodName(line).getKey(), getMethodName(line).getValue());
            }
        }
        return results;
    }

    private static Map.Entry<String, String> getMethodName(String line) {
        String[] parts = line.split("\\.");
        final String caller = parts[0];

        String secondPart = parts[1];
        final String callee = secondPart.replace("(", "").replace(")", "").replace(";", "");

        return new Map.Entry<String, String>() {
            @Override
            public String getKey() {
                return caller;
            }

            @Override
            public String getValue() {
                return callee;
            }

            @Override
            public String setValue(String s) {
                return null;
            }
        };
    }

    private static boolean isMethodCall(String line) {
        return line.contains(".") && line.contains(";");
    }

    private static String getRootMethodName(String inputCode) throws IOException {
        StringReader stringReader = new StringReader(inputCode);
        BufferedReader bufferedReader = new BufferedReader(stringReader);
        String firstLine = bufferedReader.readLine();
        bufferedReader.close();
        return firstLine.replace("{", "");
    }

    private static boolean isNested(String inputCode) {
        return !isSingleMethod(inputCode);
    }

    private static boolean isSingleMethod(String inputCode) {
        return !inputCode.contains("{");
    }
}
