package test;

import main.DSLAnalyzer;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class DSLAnalyzerTest {
    @Test
    public void should_return_single_result_when_input_is_one_method() throws IOException {
        String result = DSLAnalyzer.analyze("method0");
        assertThat(result, is("method0: ROOT\n" +
                "Method(s) number: 1"));
    }

    @Test
    public void should_return_nested_result_when_input_is_nested_with_one_method() throws IOException {
        String result = DSLAnalyzer.analyze("method0{\n" +
                "A.method1();\n" +
                "}");
        assertThat(result, is("method0: ROOT\n" +
                ">method1: ROOT -> A\n" +
                "Method(s) number: 2"));
    }

    @Test
    public void should_return_nested_result_when_input_is_nested_with_two_method() throws IOException {
        String result = DSLAnalyzer.analyze("method0{\n" +
                "A.method1();\n" +
                "B.method2();\n" +
                "}");
        assertThat(result, is("method0: ROOT\n" +
                ">method1: ROOT -> A\n" +
                ">method2: ROOT -> B\n" +
                "Method(s) number: 3"));
    }
}
