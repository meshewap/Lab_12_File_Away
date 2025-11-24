import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;


import static java.nio.file.StandardOpenOption.CREATE;

public class DataSaver {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // This is the ArrayList to hold our CSV records
        ArrayList<String> records = new ArrayList<>();
        boolean adding = true;

        System.out.println("CSV Data Collector");

        // Loop to collect data from the user
        while (adding) {
            String fName = SafeInput.getNonZeroLenString(in, "Enter First Name");
            String lName = SafeInput.getNonZeroLenString(in, "Enter Last Name");
            String id = SafeInput.getRegExString(in, "Enter ID (6 digits)", "\\d{6}");
            String email = SafeInput.getNonZeroLenString(in, "Enter Email");
            int yob = SafeInput.getRangedInt(in, "Enter Year of Birth", 1900, 2024);

            // Format the data as a single CSV string
            String csvRecord = String.format("%s,%s,%s,%s,%d", fName, lName, id, email, yob);

            // Add the new record to our list
            records.add(csvRecord);
            System.out.println("Record added: " + csvRecord);

            // Ask to add another
            adding = SafeInput.getYNConfirm(in, "Add another record?");
        }

        // Only write if we actually added records
        if (records.isEmpty()) {
            System.out.println("No records to save.");
            return;
        }

        // Get the file name from the user
        String fileName = SafeInput.getNonZeroLenString(in, "Enter file name to save (e.g., data.csv)");
        if (!fileName.toLowerCase().endsWith(".csv")) {
            fileName += ".csv";
        }


        File workingDirectory = new File(System.getProperty("user.dir"));
        Path file = Paths.get(workingDirectory.getPath() + File.separator + "src" + File.separator + fileName);

        try {
            OutputStream out =
                    new BufferedOutputStream(Files.newOutputStream(file, CREATE));
            BufferedWriter writer =
                    new BufferedWriter(new OutputStreamWriter(out));

            // Write each record from the ArrayList
            for (String rec : records) {
                writer.write(rec, 0, rec.length());
                writer.newLine();
            }
            writer.close();
            System.out.println("\nSuccessfully saved data to " + file.toAbsolutePath());

        } catch (IOException e) {
            e.printStackTrace();
        }

        in.close();
    }
}