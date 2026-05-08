// A True/False question. By definition has a single response.
public class TrueFalseQuestion extends Question {
    private static final long serialVersionUID = 1L;

    public TrueFalseQuestion(String prompt) {
        super(prompt, 1);
    }

    @Override
    public void display() {
        System.out.println(prompt);
        System.out.println("T/F");
    }

    @Override
    public void modify(InputHelper input) {
        System.out.println("Current prompt: " + prompt);
        if (input.getBoolean("Do you wish to modify the prompt? (Y/N): ")) {
            String newPrompt = input.getString("Enter a new prompt: ");
            this.prompt = newPrompt;
            System.out.println("Prompt updated.");
        }
    }

    @Override
    public Answer take(InputHelper input) {
        display();
        while (true) {
            String response = input.getString("Your answer (T/F): ").trim().toLowerCase();
            if (response.equals("t") || response.equals("true")) {
                return new TrueFalseAnswer(prompt, true);
            }
            if (response.equals("f") || response.equals("false")) {
                return new TrueFalseAnswer(prompt, false);
            }
            System.out.println("Please answer T or F.");
        }
    }
}
