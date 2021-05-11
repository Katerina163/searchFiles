import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Search {
    public static List<Path> search(Path root, String searchType, String string) throws IOException {
        switch (searchType) {
            case "mask" -> {
                SearchFilesByMask searcher = new SearchFilesByMask(string);
                Files.walkFileTree(root, searcher);
                return searcher.getPaths();
            }
            case "name" -> {
                SearchFilesByName search = new SearchFilesByName(string);
                Files.walkFileTree(root, search);
                return search.getPaths();
            }
            case "regex" -> {
                SearchFilesByRegex s = new SearchFilesByRegex(string);
                Files.walkFileTree(root, s);
                return s.getPaths();
            }
            default -> {
                throw new IllegalArgumentException("Invalid argument entry");
            }
        }
    }

    private static class SearchFilesByName extends SimpleFileVisitor<Path> {
        private List<Path> list = new ArrayList<>();
        private String name;

        public SearchFilesByName(String name) {
            this.name = name;
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            if (file.getFileName().toString().contains(name)) {
                list.add(file);
            }
            return FileVisitResult.CONTINUE;
        }

        public List<Path> getPaths() {
            return list;
        }
    }

    private static class SearchFilesByMask extends SimpleFileVisitor<Path> {
        private List<Path> list = new ArrayList<>();
        private String mask;

        public SearchFilesByMask(String mask) {
            this.mask = mask.replaceAll("[*]", "");
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            if (mask.length() == 0 || mask.equals("*") || file.getFileName().toString().contains(mask)) {
                list.add(file);
            }
            return FileVisitResult.CONTINUE;
        }

        public List<Path> getPaths() {
            return list;
        }
    }

    private static class SearchFilesByRegex extends SimpleFileVisitor<Path> {
        private List<Path> list = new ArrayList<>();
        private Pattern regex;

        public SearchFilesByRegex(String regex) {
            this.regex = Pattern.compile(regex);
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            Matcher matcher = regex.matcher(file.getFileName().toString());
            if (matcher.find()) {
                list.add(file);
            }
            return FileVisitResult.CONTINUE;
        }

        public List<Path> getPaths() {
            return list;
        }
    }
}
