import org.junit.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class SearchTest {
    @Test
    public void whenName() throws IOException {
        ArgsName argsName = ArgsName.of(new String[] {"-d=./for_test", "-n=one", "-t=name", "-o=log.txt"});
        List<Path> result = Search.search(argsName);
        assertThat(result.get(0), is(Paths.get("./for_test/1/one.txt")));
        assertThat(result.get(1), is(Paths.get("./for_test/one.txt")));
    }

    @Test
    public void whenMaskAllElement() throws IOException {
        ArgsName argsName = ArgsName.of(new String[] {"-d=./for_test", "-n=*", "-t=mask", "-o=log.txt"});
        List<Path> result = Search.search(argsName);
        assertThat(result.size(), is(7));
    }

    @Test
    public void whenMask() throws IOException {
        ArgsName argsName = ArgsName.of(new String[] {"-d=./for_test", "-n=exa*", "-t=mask", "-o=log.txt"});
        List<Path> result = Search.search(argsName);
        assertThat(result.get(0), is(Paths.get("./for_test/example.txt")));
    }

    @Test
    public void whenRegex() throws IOException {
        ArgsName argsName = ArgsName.of(new String[] {"-d=./for_test", "-n=[a]", "-t=regex", "-o=log.txt"});
        List<Path> result = Search.search(argsName);
        assertThat(result.get(0), is(Paths.get("./for_test/1/star.png")));
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenWrong() throws IOException {
        ArgsName argsName = ArgsName.of(new String[] {"-d=./for_test", "-n=exa", "-t=hello", "-o=log.txt"});
        List<Path> result = Search.search(argsName);
    }
}