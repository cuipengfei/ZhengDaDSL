package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DSLNode {
    private String callerName;
    private String calleeName;

    private DSLNode parentNode;

    private List<DSLNode> subNodes = new ArrayList<DSLNode>();

    @Override
    public String toString() {
        String result = null;
        if (isRootNode()) {
            if (subNodes.size() == 0) {
                result = calleeName + ": ROOT\n"
                        + "Method(s) number: 1";
            } else {
                result = calleeName + ": ROOT\n";
                result = appendSubOutputs(result);

                Integer methodsNumber = getNodesCount();
                result += "Method(s) number: " + methodsNumber.toString();
            }
        } else {
            result = calleeName + ": " + (parentNode.callerName == null ? "ROOT" : parentNode.callerName) + " -> " + callerName + "\n";
            result = appendSubOutputs(result);
        }
        return result;
    }

    private int getNodesCount() {
        int subNodesCount = 0;
        for (DSLNode subNode : subNodes) {
            subNodesCount += subNode.getNodesCount();
        }
        return subNodesCount + 1;
    }

    private String appendSubOutputs(String result) {
        String prefix = getPrefix();
        for (DSLNode subNode : subNodes) {
            result += (prefix + subNode.toString());
        }
        return result;
    }

    private String getPrefix() {
        String prefix;
        if (isRootNode()) {
            prefix = ">";
        } else {
            prefix = new String(new char[getLevelCount()]).replace('\0', '>');
        }
        return prefix;
    }

    private int getLevelCount() {
        int levelCount = 1;
        DSLNode tmpParentNode = parentNode;
        while (tmpParentNode != null) {
            levelCount++;
            tmpParentNode = tmpParentNode.parentNode;
        }
        return levelCount;
    }

    private boolean isRootNode() {
        return parentNode == null;
    }

    public void setCallerName(String callerName) {
        this.callerName = callerName;
    }

    public void setCalleeName(String calleeName) {
        this.calleeName = calleeName;
    }

    public void addSubNode(DSLNode subNode) {
        subNodes.add(subNode);
        subNode.setParentNode(this);
    }

    public void setParentNode(DSLNode parentNode) {
        this.parentNode = parentNode;
    }
}
