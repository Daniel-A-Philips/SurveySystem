/**
 * Driver class for the Survey System.
 * Run with:  java SurveyApp
 */
public class SurveyApp {
    private final Menu menu;

    public SurveyApp() {
        this.menu = new Menu();
    }

    public static void main(String[] args) {
        try {
            SurveyApp app = new SurveyApp();
            app.menu.run();
        } catch (Throwable t) {
            // last-resort safety net so we never crash to the JVM with a stack trace
            System.out.println("An unexpected error occurred: " + t.getMessage());
            System.out.println("The program will now exit.");
        }
    }
}
