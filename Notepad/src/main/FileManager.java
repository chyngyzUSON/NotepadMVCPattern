import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;

public class FileManager {
    private String pathToFile;

    public void setPathToFile(String pathToFile) {
        this.pathToFile = pathToFile;
    }

    public boolean isFilePathNull() {
        return pathToFile == null;
    }

    public void saveToFile(String text) {
        try (FileWriter fw = new FileWriter(pathToFile)) {
            fw.write(text);
        } catch (IOException exc) {
            System.out.println(exc);
        }
    }

    public String readFromFile() {
        File file = new File(pathToFile);
        int[] codepoints = new int[(int) file.length()];
        try (FileInputStream fis = new FileInputStream(file)) {
            int c;
            for (int i = 0; (c = fis.read()) > 0; i++) {
                codepoints[i] = c;
            }
        } catch (IOException exc) {
            System.out.println(exc);
        }
        String text = new String(codepoints, 0, codepoints.length);
        codepoints = null;
        file = null;
        return text;
    }

    public String getFileName() {
        return pathToFile;
    }
}
