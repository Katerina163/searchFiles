import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Search {
    private static Predicate<Path> newCondition(ArgsName argsName) {
        switch (argsName.get("t")) {
            case "mask" -> {
                return path -> {
                    String mask = argsName.get("n").replaceAll("[*]", "");
                    return mask.length() == 0 || mask.equals("*") || path.getFileName().toString().contains(mask);
                };
            }
            case "name" -> {
                return path -> path.getFileName().toString().contains(argsName.get("n"));
            }
            case "regex" -> {
                return path -> {
                    Pattern regex = Pattern.compile(argsName.get("n"));
                    Matcher matcher = regex.matcher(path.getFileName().toString());
                    return matcher.find();
                };
            }
            default -> throw new IllegalArgumentException("Invalid argument entry");
        }
    }

    public static List<Path> search(ArgsName argsName) throws IOException {
        Predicate<Path> predicate = newCondition(argsName);
        SearchFiles searcher = new SearchFiles(predicate);
        Path root = Paths.get(argsName.get("d"));
        Files.walkFileTree(root, searcher);
        return searcher.getPaths();
    }

    private static class SearchFiles extends SimpleFileVisitor<Path> {
        private List<Path> list = new ArrayList<>();
        private Predicate<Path> predicate;

        public SearchFiles(Predicate<Path> predicate) {
            this.predicate = predicate;
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            if (predicate.test(file)) {
                list.add(file);
            }
            return FileVisitResult.CONTINUE;
        }

        public List<Path> getPaths() {
            return list;
        }
    }
}