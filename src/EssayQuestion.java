import java.io.Serial;
import java.util.ArrayList;
import java.util.List;


// An Essay question
// Like a short answer but allows much longer text and supports multiple responses (e.g. multiple paragraphs).
public class EssayQuestion extends Question {
    @Serial
    private static final long serialVersionUID = 1L;

    public EssayQuestion(String prompt, int numResponses) {
        super(prompt, numResponses);
    }

    @Override
    public void display() {
        System.out.println(prompt);
        System.out.println("(Essay response"
                + (numResponses > 1 ? " - " + numResponses + " paragraphs required" : "")
                + ")");
    }

    @Override
    public void modify(InputHelper input) {
        System.out.println("Current prompt: " + prompt);
        if (input.getBoolean("Do you wish to modify the prompt? (Y/N): ")) {
            this.prompt = input.getString("Enter a new prompt: ");
            System.out.println("Prompt updated.");
        }
        if (input.getBoolean("Do you wish to modify the number of expected responses (currently " + numResponses + ")? (Y/N): ")) {
            this.numResponses = input.getInt("Enter the new number of responses (1-10): ", 1, 10);
            System.out.println("Number of responses updated.");
        }
    }

    @Override
    public Answer take(InputHelper input) {
        display();
        List<String> responses = new ArrayList<>();
        for (int i = 0; i < numResponses; i++) {
            String r = input.getString("Response #" + (i + 1) + ": ");
            responses.add(r);
        }
        return new EssayAnswer(prompt, responses);
    }
}
