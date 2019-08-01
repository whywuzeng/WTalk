package com.utsoft.jan.common;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        //assertEquals(4, 2 + 2);
        //[ft108][ft107][ft114]
        //[ft\d]
        //[^ft\d{3}$]
        //\\[.\d{3}\\]
        String datastr = "fsdf[ft108][ft107][ft114]1111[123]";
        Pattern compile = Pattern.compile("(\\[\\w{2}\\d{3}\\])");
        Matcher matcher = compile.matcher(datastr);
        while (matcher.find()){
            String group = matcher.group();
            System.out.println(group);
        }
    }
}