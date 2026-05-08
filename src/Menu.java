import java.util.ArrayList;
import java.util.List;


// Text menu for the application
// Holds currently loaded survey and delegates to handlers for each menu option.

public class Menu {
    private Survey currentSurvey;
    private final SurveyFileManager fileManager;
    private final InputHelper input;
    private boolean running;

    public Menu() {
        this.currentSurvey = null;
        this.fileManager = new SurveyFileManager();
        this.input = new InputHelper();
        this.running = true;
    }

    public void run() {
        System.out.println("=== Welcome to the Survey System ===");
        while (running) {
            displayMainMenu();
            int choice = input.getInt("Enter your choice (1-7): ", 1, 7);
            switch (choice) {
                case 1: handleCreate();  break;
                case 2: handleDisplay(); break;
                case 3: handleLoad();    break;
                case 4: handleSave();    break;
                case 5: handleTake();    break;
                case 6: handleModify();  break;
                case 7:
                    running = false;
                    System.out.println("Goodbye!");
                    break;
            }
        }
        input.close();
    }

    private void displayMainMenu() {
        System.out.println();
        System.out.println("---- Main Menu ----");
        System.out.println("Currently loaded survey: "
                + (currentSurvey == null ? "(none)" : currentSurvey.getName()));
        System.out.println("1) Create a new Survey");
        System.out.println("2) Display an existing Survey");
        System.out.println("3) Load an existing Survey");
        System.out.println("4) Save the current Survey");
        System.out.println("5) Take the current Survey");
        System.out.println("6) Modify the current Survey");
        System.out.println("7) Quit");
    }

    private void displaySurveyMenu() {
        System.out.println();
        System.out.println("---- Add a Question ----");
        System.out.println("1) Add a new T/F question");
        System.out.println("2) Add a new multiple-choice question");
        System.out.println("3) Add a new short answer question");
        System.out.println("4) Add a new essay question");
        System.out.println("5) Add a new date question");
        System.out.println("6) Add a new matching question");
        System.out.println("7) Return to previous menu");
    }

    // ---------- Option 1: Create ----------
    private void handleCreate() {
        String name = input.getString("Enter a name for the new survey: ");
        currentSurvey = new Survey(name);
        System.out.println("Created new survey: " + name);
        boolean addingQuestions = true;
        while (addingQuestions) {
            displaySurveyMenu();
            int choice = input.getInt("Enter your choice (1-7): ", 1, 7);
            switch (choice) {
                case 1: currentSurvey.addQuestion(buildTrueFalse());    break;
                case 2: currentSurvey.addQuestion(buildMultipleChoice()); break;
                case 3: currentSurvey.addQuestion(buildShortAnswer());  break;
                case 4: currentSurvey.addQuestion(buildEssay());    break;
                case 5: currentSurvey.addQuestion(buildDate()); break;
                case 6: currentSurvey.addQuestion(buildMatching()); break;
                case 7: addingQuestions = false;    break;
            }
            if (choice >= 1 && choice <= 6) System.out.println("Question added.");
        }
    }

    // ---------- Question builders ----------
    private TrueFalseQuestion buildTrueFalse() {
        String prompt = input.getString("Enter the prompt for your True/False question: ");
        return new TrueFalseQuestion(prompt);
    }

    private MultipleChoiceQuestion buildMultipleChoice() {
        String prompt = input.getString("Enter the prompt for your multiple-choice question: ");
        int n = input.getInt("Enter the number of choices for your multiple-choice question (2-26): ", 2, 26);
        List<String> choices = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            String c = input.getString("Enter choice #" + (i + 1) + ": ");
            choices.add(c);
        }
        int responses;
        if (input.getBoolean("Should this question accept multiple responses? (Y/N): ")) {
            responses = input.getInt("How many responses should the user provide? (1-" + n + "): ", 1, n);
        } else responses = 1;
        return new MultipleChoiceQuestion(prompt, choices, responses);
    }

    private ShortAnswerQuestion buildShortAnswer() {
        String prompt = input.getString("Enter the prompt for your short answer question: ");
        int charLimit = input.getInt("Enter the character limit for the response (1-1000): ", 1, 1000);
        int responses;
        if (input.getBoolean("Should this question accept multiple responses? (Y/N): ")) {
            responses = input.getInt("How many responses should the user provide? (1-10): ", 1, 10);
        } else responses = 1;
        return new ShortAnswerQuestion(prompt, charLimit, responses);
    }

    private EssayQuestion buildEssay() {
        String prompt = input.getString("Enter the prompt for your essay question: ");
        int responses;
        if (input.getBoolean("Should this question accept multiple responses? (Y/N): ")) {
            responses = input.getInt("How many responses should the user provide? (1-10): ", 1, 10);
        } else responses = 1;
        return new EssayQuestion(prompt, responses);
    }

    private ValidDateQuestion buildDate() {
        String prompt = input.getString("Enter the prompt for your date question: ");
        int responses;
        if (input.getBoolean("Should this question accept multiple responses? (Y/N): ")) {
            responses = input.getInt("How many responses should the user provide? (1-10): ", 1, 10);
        } else responses = 1;
        return new ValidDateQuestion(prompt, responses);
    }

    private MatchingQuestion buildMatching() {
        String prompt = input.getString("Enter the prompt for your matching question: ");
        int n = input.getInt("How many pairs are in this matching question? (2-26): ", 2, 26);
        List<String> left = new ArrayList<>();
        List<String> right = new ArrayList<>();
        System.out.println("Now enter the LEFT column items (lettered A-" + (char) ('A' + n - 1) + "):");
        for (int i = 0; i < n; i++) {
            left.add(input.getString("Left column item " + (char) ('A' + i) + ": "));
        }
        System.out.println("Now enter the RIGHT column items (numbered 1-" + n + "):");
        for (int i = 0; i < n; i++) {
            right.add(input.getString("Right column item " + (i + 1) + ": "));
        }
        return new MatchingQuestion(prompt, left, right);
    }

    // Option 2: Display
    private void handleDisplay() {
        if (currentSurvey == null) {
            System.out.println("You must have a survey loaded in order to display it.");
            return;
        }
        currentSurvey.display();
    }

    // Option 3: Load
    private void handleLoad() {
        List<String> files = fileManager.listSurveys();
        if (files.isEmpty()) {
            System.out.println("No saved surveys were found in the '" + SurveyFileManager.SURVEY_DIR + "' directory.");
            return;
        }
        System.out.println("Please select a file to load:");
        for (int i = 0; i < files.size(); i++) {
            System.out.println((i + 1) + ") " + files.get(i));
        }
        System.out.println((files.size() + 1) + ") Cancel");
        int choice = input.getInt("Enter your choice: ", 1, files.size() + 1);
        if (choice == files.size() + 1) {
            System.out.println("Load cancelled.");
            return;
        }
        try {
            currentSurvey = fileManager.loadSurvey(files.get(choice - 1));
            System.out.println("Loaded survey: " + currentSurvey.getName());
        } catch (Exception e) {
            System.out.println("Could not load that file: " + e.getMessage());
        }
    }

    //Option 4: Save
    private void handleSave() {
        if (currentSurvey == null) {
            System.out.println("You must have a survey loaded in order to save it.");
            return;
        }
        String defaultName = currentSurvey.getName();
        String fileName = input.getString("Enter a file name for the survey (default '" + defaultName + "', .ser will be appended): ");
        try {
            fileManager.saveSurvey(currentSurvey, fileName);
            System.out.println("Saved.");
        } catch (Exception e) {
            System.out.println("Could not save survey: " + e.getMessage());
        }
    }

    //option 5: Take
    private void handleTake() {
        if (currentSurvey == null) {
            System.out.println("You must have a survey loaded in order to take it.");
            return;
        }
        String taker = input.getString("Enter the name of the survey-taker: ");
        Response response = currentSurvey.take(input);
        // automatically save the response
        String filename = currentSurvey.getName() + "_" + taker + "_" + System.currentTimeMillis();
        try {
            fileManager.saveResponse(response, filename);
            System.out.println();
            System.out.println("Survey complete. Response saved to '"
                    + SurveyFileManager.RESPONSE_DIR + "' as " + filename + ".ser");
        } catch (Exception e) {
            System.out.println("Could not save response: " + e.getMessage());
        }
    }

    //Option 6: modify
    private void handleModify() {
        if (currentSurvey == null) {
            System.out.println("You must have a survey loaded in order to modify it.");
            return;
        }
        List<Question> qs = currentSurvey.getQuestions();
        if (qs.isEmpty()) {
            System.out.println("This survey has no questions to modify.");
            return;
        }
        System.out.println("What question do you wish to modify?");
        for (int i = 0; i < qs.size(); i++) {
            System.out.println("  " + (i + 1) + ") " + qs.get(i).getPrompt());
        }
        System.out.println("  " + (qs.size() + 1) + ") Cancel");
        int choice = input.getInt("Enter your choice: ", 1, qs.size() + 1);
        if (choice == qs.size() + 1) {
            System.out.println("Modify cancelled.");
            return;
        }
        currentSurvey.modifyQuestion(choice - 1, input);
        System.out.println("Question modification complete.");
    }
}
