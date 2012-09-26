package test;

import main.DSLNode;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class DSLNodeTest {
    @Test
    public void shouldReturnSingleOutputWhenNodeHasNoCalleeAndHasNoSubNodes() throws Exception {
        DSLNode node = new DSLNode();
        node.setCalleeName("tmpMethod");
        String output = node.toString();

        assertThat(output, is("tmpMethod: ROOT\n" +
                "Method(s) number: 1"));
    }

    @Test
    public void shouldReturnNestedOutputWhenNodeHasOneSubNode() throws Exception {
        DSLNode node = new DSLNode();
        node.setCalleeName("tmpMethod");

        DSLNode subNode1 = new DSLNode();
        subNode1.setCallerName("caller1");
        subNode1.setCalleeName("callee1");

        node.addSubNode(subNode1);

        String output = node.toString();

        assertThat(output, is("tmpMethod: ROOT\n" +
                ">callee1: ROOT -> caller1\n" +
                "Method(s) number: 2"));
    }

    @Test
    public void shouldReturnNestedOutputWhenNodeHasMultipleSubNodes() throws Exception {
        DSLNode node = new DSLNode();
        node.setCalleeName("tmpMethod");

        DSLNode subNode1 = new DSLNode();
        subNode1.setCallerName("caller1");
        subNode1.setCalleeName("callee1");

        DSLNode subNode2 = new DSLNode();
        subNode2.setCallerName("caller2");
        subNode2.setCalleeName("callee2");

        node.addSubNode(subNode1);
        node.addSubNode(subNode2);

        String output = node.toString();

        assertThat(output, is("tmpMethod: ROOT\n" +
                ">callee1: ROOT -> caller1\n" +
                ">callee2: ROOT -> caller2\n" +
                "Method(s) number: 3"));
    }

    @Test
    public void shouldReturnMultiNestedOutputWhenNodeHasMultiLevelsOfSubNodes() throws Exception {
        DSLNode node = new DSLNode();
        node.setCalleeName("tmpMethod");

        DSLNode subNode1 = new DSLNode();
        subNode1.setCallerName("caller1");
        subNode1.setCalleeName("callee1");

        DSLNode subNode2 = new DSLNode();
        subNode2.setCallerName("caller3");
        subNode2.setCalleeName("callee3");

        DSLNode subSubNode1 = new DSLNode();
        subSubNode1.setCallerName("caller2");
        subSubNode1.setCalleeName("callee2");

        node.addSubNode(subNode1);
        node.addSubNode(subNode2);
        subNode1.addSubNode(subSubNode1);

        assertThat(node.toString(), is("tmpMethod: ROOT\n" +
                ">callee1: ROOT -> caller1\n" +
                ">>callee2: caller1 -> caller2\n" +
                ">callee3: ROOT -> caller3\n" +
                "Method(s) number: 4"));
    }
}
