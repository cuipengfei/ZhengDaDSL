package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Map;

public class DSLAnalyzer {
    public static String analyze(String inputCode) throws IOException {
        DSLNode currentNode = createRootNode(inputCode);
        StringReader stringReader = new StringReader(inputCode);
        BufferedReader bufferedReader = new BufferedReader(stringReader);
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            if (isMethodCall(line)) {
                DSLNode node = createNewNode(line);
                currentNode.addSubNode(node);
                if (line.contains("{")) {
                    currentNode = node;
                }
            }
            if (line.contains("}") && currentNode.getParentNode() != null) {
                currentNode = currentNode.getParentNode();
            }
        }

        String result = currentNode.toString();
        return result;
    }

    private static DSLNode createRootNode(String inputCode) throws IOException {
        String rootMethodName = getRootMethodName(inputCode);
        DSLNode currentNode = new DSLNode();
        currentNode.setCalleeName(rootMethodName);
        return currentNode;
    }

    private static DSLNode createNewNode(String line) {
        DSLNode node = new DSLNode();
        Map.Entry<String, String> callerCalleePair = getMethodName(line);
        node.setCallerName(callerCalleePair.getKey());
        node.setCalleeName(callerCalleePair.getValue());
        return node;
    }

    private static Map.Entry<String, String> getMethodName(String line) {
        String[] parts = line.split("\\.");
        final String caller = parts[0];

        String secondPart = parts[1];
        final String callee = secondPart.replace("(", "").replace(")", "").replace(";", "").replace("{", "");

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
        return line.contains(".") && line.contains("(") && line.contains(")");
    }

    private static String getRootMethodName(String inputCode) throws IOException {
        StringReader stringReader = new StringReader(inputCode);
        BufferedReader bufferedReader = new BufferedReader(stringReader);
        String firstLine = bufferedReader.readLine();
        bufferedReader.close();
        return firstLine.replace("{", "");
    }
}
