import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class ResultToFileTest {
    @Test
    public void test() {
        List<Path> listPath = List.of(Paths.get("/first.txt"), Paths.get("/second"));
        Path result = Paths.get("./for_test/ResultToFileTest.txt");
        ResultToFile.writingTo(result, listPath);
        List<String> list = new ArrayList<>();
        try (BufferedReader in = new BufferedReader(
                new FileReader(result.toString()))) {
            list = in.lines().collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertThat(list.get(0), is(listPath.get(0).toString()));
        assertThat(list.get(1), is(listPath.get(1).toString()));
    }
}