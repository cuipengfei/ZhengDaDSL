package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DSLNode {
    private String callerName;
    private String calleeName;
    private List<DSLNode> subNodes = new ArrayList<DSLNode>();

    @Override
    public String toString() {
        String result = null;
        if (subNodes.size() == 0) {
            result = callerName + ": ROOT\n"
                    + "Method(s) number: 1";
        } else {
            result = callerName + ": ROOT\n";
            for (DSLNode subNode : subNodes) {
                result += (">" + subNode.calleeName + ": " + "ROOT -> " + subNode.callerName + "\n");
            }

            Integer methodsNumber = subNodes.size() + 1;
            result += "Method(s) number: " + methodsNumber.toString();
        }
        return result;
    }

    public void setCallerName(String callerName) {
        this.callerName = callerName;
    }

    public void setCalleeName(String calleeName) {
        this.calleeName = calleeName;
    }

    public void addSubNode(DSLNode subNode) {
        subNodes.add(subNode);
    }
}
