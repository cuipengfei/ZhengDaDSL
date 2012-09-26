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
            result = handleSingleMethod(inputCode);
        } else if (isNested(inputCode)) {
            if (isMultiNested(inputCode)) {

            } else {
                result = handleSingleLevelNestedCode(inputCode);
            }
        }
        return result;
    }

    private static boolean isMultiNested(String inputCode) {
        Boolean isNested = inputCode.contains("{");
        Boolean hasMultiLevels = inputCode.indexOf("{") != inputCode.lastIndexOf("{");
        return isNested && hasMultiLevels;
    }

    private static String handleSingleLevelNestedCode(String inputCode) throws IOException {
        String rootMethodName = getRootMethodName(inputCode);

        DSLNode rootNode = new DSLNode();
        rootNode.setCalleeName(rootMethodName);

        HashMap<String, String> nestedMethodNames = getNestedMethodNames(inputCode);
        for (Map.Entry<String, String> callerCalleePair : nestedMethodNames.entrySet()) {
            DSLNode subNode = new DSLNode();
            subNode.setCallerName(callerCalleePair.getKey());
            subNode.setCalleeName(callerCalleePair.getValue());
            rootNode.addSubNode(subNode);
        }

        return rootNode.toString();
    }

    private static String handleSingleMethod(String inputCode) {
        DSLNode dslNote = new DSLNode();
        dslNote.setCalleeName(inputCode);

        return dslNote.toString();
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
