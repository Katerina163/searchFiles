import java.util.HashMap;
import java.util.Map;

public class ArgsName {
    private final Map<String, String> values = new HashMap<>();

    public String get(String key) {
        return values.get(key);
    }

    private void parse(String[] args) {
        if (args.length == 0) {
            throw new IllegalArgumentException("Required arguments are missing");
        }
        for (String s : args) {
            String[] array = s.split("=");
            if (array.length != 2 || array[0].length() == 0
                    || array[1].length() == 0) {
                throw new IllegalArgumentException("Invalid argument entry");
            }
            values.put(array[0].substring(1), array[1]);
        }
        if (values.size() != 4 ||
                !values.containsKey("d") || !values.containsKey("n")
                || !values.containsKey("t") || !values.containsKey("o")) {
            throw new IllegalArgumentException("Required arguments are missing");
        }
    }

    public static ArgsName of(String[] args) {
        ArgsName names = new ArgsName();
        names.parse(args);
        return names;
    }
}