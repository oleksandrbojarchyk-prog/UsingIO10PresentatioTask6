import java.util.Scanner;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter first 3 digits of bank account: ");
        String bankNumber = scanner.nextLine().trim();

        if (!bankNumber.matches("\\d{3}")) {
            System.out.println("Please enter exactly 3 digits.");
            scanner.close();
            return;
        }

        String url = "https://ewib.nbp.pl/plewibnra?dokNazwa=plewibnra.txt";

        boolean found = false;

        try {
            URL website = new URL(url);

            BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                    website.openStream(),
                    Charset.forName("IBM852")
                )
            );

            String line;

            while ((line = reader.readLine()) != null) {

                String[] parts = line.split("\\t");

                if (parts.length > 1) {

                    String code = parts[0].trim();
                    String bankName = parts[1].trim();

                    if (code.equals(bankNumber)) {

                        System.out.println("\nBank found!");

                        System.out.println(
                            "Abbreviated bank number: " + code
                        );

                        System.out.println(
                            "Bank name: " + bankName
                        );

                        found = true;
                        break;
                    }
                }
            }

            reader.close();

            if (!found) {
                System.out.println("Bank not found!");
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        scanner.close();
    }
}
