package Ex2;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Decrypt {
    public static void main(String[] args) throws Exception {
        File file = new File("exercise2.txt");
        createRewriteFile(file);
        String data = getTextFromFile(file);
        String preposition = getTextFromFile(new File("preposition.txt"));
        String result = Pattern.compile(preposition, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE).matcher(data).replaceAll(Matcher.quoteReplacement("Java"));
        saveToFile(new File("decrypted.txt"), result);
    }

    private static String getTextFromFile(File file) throws Exception {
        StringBuilder string = new StringBuilder();
        try {
            if (file.getName().equals("preposition.txt")) {
                Files.lines(file.toPath()).forEach(s -> string.append("\\b(").append(s).append(")\\b|"));
                string.deleteCharAt(string.length() - 1); // Delete last '|' from the string
            } else {
                Files.lines(file.toPath()).forEach(s -> string.append(s).append("\n"));
            }
        } catch (Exception e) {
            throw new Exception(Arrays.toString(e.getStackTrace()));
        }
        return string.toString();
    }

    private static void createRewriteFile(File file) throws Exception {
        URL url = new URL("https://www.gutenberg.org/ebooks/1513.txt.utf-8");
        InputStream is = null;
        try {
            URLConnection con = url.openConnection();
            is = con.getInputStream();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        saveToFile(file, is);
    }

    private static void saveToFile(File file, InputStream is) throws Exception {
        try (PrintWriter pw = new PrintWriter(new FileWriter(file));
             BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            String line = null;
            while ((line = br.readLine()) != null) {
                pw.write(line + "\n");
            }
            pw.flush();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    private static void saveToFile(File file, String data) throws Exception {
        Files.write(Paths.get(file.getPath()), data.getBytes());
    }
}
