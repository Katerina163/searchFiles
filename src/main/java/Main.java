import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        if (args.length != 4) {
            throw new IllegalArgumentException();
        }
        Main main = new Main();
        ArgsName ar = ArgsName.of(args);
        main.search(ar);
    }

    public void search(ArgsName argsName) throws IOException {
        List<Path> list = Search.search(argsName);
        Path file = Paths.get(argsName.get("o"));
        ResultToFile.writingTo(file, list);
    }
}
