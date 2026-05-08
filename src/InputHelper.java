import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.Scanner;

/**
 * Centralized input helper that wraps a Scanner.
 * All user input goes through this class so that improper input is
 * handled gracefully and consistently.
 */
public class InputHelper {
    private Scanner scanner;

    public InputHelper() {
        this.scanner = new Scanner(System.in);
    }
     // Read a non-empty string from the user. Re-prompts on blank input.
     // f standard input is exhausted (EOF) the program exits cleanly.
    public String getString(String prompt) {
        while (true) {
            System.out.print(prompt);
            if (!scanner.hasNextLine()) {
                System.out.println();
                System.out.println("End of input reached. Exiting.");
                System.exit(0);
            }
            String line = scanner.nextLine();
            if (line != null && !line.trim().isEmpty()) {
                return line.trim();
            }
            System.out.println("Input cannot be empty. Please try again.");
        }
    }

    /**
     * Read an integer in [min, max] inclusive. Re-prompts on bad input.
     */
    public int getInt(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            if (!scanner.hasNextLine()) {
                System.out.println();
                System.out.println("End of input reached. Exiting.");
                System.exit(0);
            }
            String line = scanner.nextLine();
            try {
                int value = Integer.parseInt(line.trim());
                if (value < min || value > max) {
                    System.out.println("Please enter a number between " + min + " and " + max + ".");
                    continue;
                }
                return value;
            } catch (NumberFormatException e) {
                System.out.println("That was not a valid number. Please try again.");
            }
        }
    }

    /**
     * Read a yes/no answer from the user (Y/N, Yes/No, true/false).
     */
    public boolean getBoolean(String prompt) {
        while (true) {
            System.out.print(prompt);
            if (!scanner.hasNextLine()) {
                System.out.println();
                System.out.println("End of input reached. Exiting.");
                System.exit(0);
            }
            String line = scanner.nextLine();
            String token = line.trim().toLowerCase();
            if (token.equals("y") || token.equals("yes") || token.equals("true") || token.equals("t")) {
                return true;
            }
            if (token.equals("n") || token.equals("no") || token.equals("false") || token.equals("f")) {
                return false;
            }
            System.out.println("Please answer yes (Y) or no (N).");
        }
    }

    /**
     * Read a free-form string that may be empty. Used for things like
     * letting the user accept a previous value by pressing Enter.
     */
    public String getRawLine(String prompt) {
        System.out.print(prompt);
        return scanner.hasNextLine() ? scanner.nextLine() : "";
    }

    /**
     * Read a date in YYYY-MM-DD format. Re-prompts until valid.
     */
    public String getDate(String prompt) {
        while (true) {
            System.out.print(prompt);
            if (!scanner.hasNextLine()) {
                System.out.println();
                System.out.println("End of input reached. Exiting.");
                System.exit(0);
            }
            String line = scanner.nextLine();
            if (line != null && isValidDate(line.trim())) {
                return line.trim();
            }
            System.out.println("That is not a valid date. Please use YYYY-MM-DD.");
        }
    }

    /**
     * Validate that a string is a real calendar date in YYYY-MM-DD form.
     */
    public static boolean isValidDate(String date) {
        if (date == null) return false;
        try {
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("uuuu-MM-dd")
                    .withResolverStyle(ResolverStyle.STRICT);
            LocalDate.parse(date, fmt);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /** Close the underlying scanner. */
    public void close() {
        try {
            scanner.close();
        } catch (Exception ignored) {
        }
    }
}
