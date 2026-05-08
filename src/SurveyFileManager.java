import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

// Handles all serialization/deserialization of Survey and Response
// objects to and from disk. All paths are relative so the project
// works regardless of where it is unpacked.
public class SurveyFileManager {
    public static final String SURVEY_DIR = "surveys";
    public static final String RESPONSE_DIR = "responses";

    public SurveyFileManager() {
        ensureDir(SURVEY_DIR);
        ensureDir(RESPONSE_DIR);
    }

    private void ensureDir(String dir) {
        File f = new File(dir);
        if (!f.exists()) {
            f.mkdirs();
        }
    }

    // Save a Survey to surveys/<fileName>.ser. The .ser extension is
    // appended automatically if not already present.
    public void saveSurvey(Survey s, String fileName) throws IOException {
        if (s == null) throw new IOException("No survey to save.");
        String safeName = sanitize(fileName);
        if (!safeName.toLowerCase().endsWith(".ser")) safeName += ".ser";
        File file = new File(SURVEY_DIR, safeName);
        try (FileOutputStream fos = new FileOutputStream(file);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(s);
        }
    }

    // Load a Survey from surveys/<fileName>. Returns null if the file
    // cannot be read or the data is not a Survey.
    public Survey loadSurvey(String fileName) throws IOException, ClassNotFoundException {
        File file = new File(SURVEY_DIR, fileName);
        try (FileInputStream fis = new FileInputStream(file);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            Object obj = ois.readObject();
            if (obj instanceof Survey) {
                return (Survey) obj;
            }
            throw new IOException("File does not contain a Survey object.");
        }
    }

    // Save a Response to responses/<fileName>.ser.
    public void saveResponse(Response r, String fileName) throws IOException {
        if (r == null) throw new IOException("No response to save.");
        String safeName = sanitize(fileName);
        if (!safeName.toLowerCase().endsWith(".ser")) safeName += ".ser";
        File file = new File(RESPONSE_DIR, safeName);
        try (FileOutputStream fos = new FileOutputStream(file);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(r);
        }
    }

    // Load a Response from responses/<fileName>.
    public Response loadResponse(String fileName) throws IOException, ClassNotFoundException {
        File file = new File(RESPONSE_DIR, fileName);
        try (FileInputStream fis = new FileInputStream(file);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            Object obj = ois.readObject();
            if (obj instanceof Response) {
                return (Response) obj;
            }
            throw new IOException("File does not contain a Response object.");
        }
    }

    // List all .ser files in the surveys directory, sorted.
    public List<String> listSurveys() {
        return listFiles(SURVEY_DIR);
    }

    // List all .ser files in the responses directory, sorted.
    public List<String> listResponses() {
        return listFiles(RESPONSE_DIR);
    }

    private List<String> listFiles(String dir) {
        File folder = new File(dir);
        if (!folder.exists() || !folder.isDirectory()) return new ArrayList<>();
        String[] names = folder.list((d, name) -> name.toLowerCase().endsWith(".ser"));
        if (names == null) return new ArrayList<>();
        List<String> list = new ArrayList<>(Arrays.asList(names));
        Collections.sort(list);
        return list;
    }

    // Strip any path separators or other unsafe characters from a file name.
    private String sanitize(String name) {
        if (name == null) return "untitled";
        // strip any directory separators
        String cleaned = name.replace('/', '_').replace('\\', '_');
        cleaned = cleaned.replaceAll("[\\s]+", "_");
        if (cleaned.isEmpty()) cleaned = "untitled";
        return cleaned;
    }
}
