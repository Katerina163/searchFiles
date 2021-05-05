import java.io.BufferedWriter;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.List;

public class ResultToFile {
    public static void writingTo(Path path, List<Path> list) {
        try (BufferedWriter out = new BufferedWriter(
                new java.io.FileWriter(path.toString(), Charset.forName("UTF-8"))
        )) {
            for (Path s : list) {
                out.write(s.toString() + System.lineSeparator());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
