// Driver class for the Survey System.
// Run with:  java SurveyApp
public class SurveyApp {
    private final Menu menu;

    public SurveyApp() {
        this.menu = new Menu();
    }

    public static void main(String[] args) {
        try {
            SurveyApp app = new SurveyApp();
            app.menu.run();
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }
}
