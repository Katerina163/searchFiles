import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class ArgsNameTest {
    @Test
    public void whenGetNormal() {
        ArgsName jvm = ArgsName.of(new String[] {"-d=c:/", "-n=*", "-t=mask", "-o=log.txt"});
        assertThat(jvm.get("d"), is("c:/"));
        assertThat(jvm.get("n"), is("*"));
        assertThat(jvm.get("t"), is("mask"));
        assertThat(jvm.get("o"), is("log.txt"));
    }

    @Test
    public void whenReorder() {
        ArgsName jvm = ArgsName.of(new String[] {"-o=log.txt", "-t=mask", "-n=*", "-d=c:/"});
        assertThat(jvm.get("d"), is("c:/"));
        assertThat(jvm.get("n"), is("*"));
        assertThat(jvm.get("t"), is("mask"));
        assertThat(jvm.get("o"), is("log.txt"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenGetNotExist() {
        ArgsName jvm = ArgsName.of(new String[] {});
        jvm.get("d");
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenWrongArgument() {
        ArgsName jvm = ArgsName.of(new String[] {"-d=c:/", "-n=*", "-t=", "-o=log.txt"});
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenFewArguments() {
        ArgsName jvm = ArgsName.of(new String[] {"-d=c:/", "-n=*"});
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenManyArguments() {
        ArgsName jvm = ArgsName.of(new String[] {"-d=c:/", "-n=*", "-t=mask", "-o=log.txt", "-encoding=UTF-8"});
    }
}