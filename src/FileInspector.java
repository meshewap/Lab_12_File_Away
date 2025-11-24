import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.nio.file.StandardOpenOption.CREATE;

public class FileInspector {

    public static void main(String[] args)
    {
        JFileChooser chooser = new JFileChooser();
        File selectedFile;
        String rec = "";

        int lineCount = 0;
        int wordCount = 0;
        int charCount = 0;

        try {
            File workingDirectory = new File(System.getProperty("user.dir"));
            File srcDir = new File(workingDirectory.getPath() + File.separator + "src");
            chooser.setCurrentDirectory(srcDir);

            // Show the file chooser and wait for the user to select a file
            if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                selectedFile = chooser.getSelectedFile();
                Path file = selectedFile.toPath();
                String fileName = selectedFile.getName();

                InputStream in =
                        new BufferedInputStream(Files.newInputStream(file, CREATE));
                BufferedReader reader =
                        new BufferedReader(new InputStreamReader(in));

                // Read the file line by line
                while (reader.ready()) {
                    rec = reader.readLine();
                    System.out.println(rec);

                    // Update all the counters
                    lineCount++;
                    charCount += rec.length();

                    String[] words = rec.split("\\s+");
                    if (!rec.trim().isEmpty()) {
                        wordCount += words.length;
                    }
                }
                reader.close(); // Close the file

                // Print the final summary report
                System.out.println("\nSummary Report");
                System.out.println("File Name: " + fileName);
                System.out.println("Number of lines: " + lineCount);
                System.out.println("Number of words: " + wordCount);
                System.out.println("Number of characters: " + charCount);

            } else {
                //if the user Cancel
                System.out.println("No file selected!!!\nRun the program again and select a file.");
            }
        }
        catch (FileNotFoundException e)
        {
            System.out.println("File not found");
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

}