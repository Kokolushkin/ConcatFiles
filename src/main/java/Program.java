import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Program {

    private static final String START_DIRECTORY_PATH = "D:\\test";
    private static final String RESULT_PATH = "D:\\test\\RESULT.txt";
    private static final String EXTENSION_FILE = ".txt";
    private static final List<File> txtFiles = new ArrayList<File>();

    public static void main(String[] args) {
        File startDirectory = new File(START_DIRECTORY_PATH);

        getAllTxtFile(startDirectory);
        txtFiles.sort(Comparator.comparing(File::getName));

        String allText = getTextFromFiles(txtFiles);
        writeToFile(new File(RESULT_PATH), allText);
    }

    private static void getAllTxtFile(File startDirectory){
        FilenameFilter txtFilter = (file , name) -> (new File(file.getAbsolutePath()+File.separator+name).isDirectory())
                || name.toLowerCase().endsWith(EXTENSION_FILE);

        for (File file : startDirectory.listFiles(txtFilter)) {
            if (file.isDirectory()) {
                getAllTxtFile(file);
            } else {
                txtFiles.add(file);
            }
        }
    }

    public static String getTextFromFiles(List<File> files){
        StringBuilder allText = new StringBuilder();
        files.forEach(file -> {
            try {
                Files.lines(Paths.get(file.getAbsolutePath())).forEach(allText::append);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        });

        return allText.toString();
    }

    public static void writeToFile(File resultFile, String text){
        try(BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(resultFile))){
            outputStream.write(text.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
