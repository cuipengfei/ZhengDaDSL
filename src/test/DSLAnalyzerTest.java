package test;

import main.DSLAnalyzer;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class DSLAnalyzerTest {
    @Test
    public void should_return_single_result_when_input_is_one_method() {
        String result = DSLAnalyzer.analyze("method0");
        assertThat(result, is("method0: ROOT\n" +
                "Method(s) number: 1"));
    }

}
