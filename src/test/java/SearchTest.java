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
        Path root = Paths.get("./for_test");
        String searchType = "name";
        String string = "one";
        List<Path> result = Search.search(root, searchType, string);
        assertThat(result.get(0), is(Paths.get("./for_test/1/one.txt")));
        assertThat(result.get(1), is(Paths.get("./for_test/one.txt")));
    }

    @Test
    public void whenMaskAllElement() throws IOException {
        Path root = Paths.get("./for_test");
        String searchType = "mask";
        String string = "*";
        List<Path> result = Search.search(root, searchType, string);
        assertThat(result.size(), is(7));
    }

    @Test
    public void whenMask() throws IOException {
        Path root = Paths.get("./for_test");
        String searchType = "mask";
        String string = "exa*";
        List<Path> result = Search.search(root, searchType, string);
        assertThat(result.get(0), is(Paths.get("./for_test/example.txt")));
    }

    @Test
    public void whenRegex() throws IOException {
        Path root = Paths.get("./for_test");
        String searchType = "regex";
        String string = "[a]";
        List<Path> result = Search.search(root, searchType, string);
        assertThat(result.get(0), is(Paths.get("./for_test/1/star.png")));
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenWrong() throws IOException {
        Path root = Paths.get("./for_test");
        String searchType = "hello";
        String string = "exa";
        List<Path> result = Search.search(root, searchType, string);
    }
}