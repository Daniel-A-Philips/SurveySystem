import java.util.ArrayList;
import java.util.List;


 // Answer for a Short Answer question. Stores one or more text responses.
public class ShortAnswerAnswer extends Answer {
    private static final long serialVersionUID = 1L;

    private List<String> responses;

    public ShortAnswerAnswer(String questionPrompt, List<String> responses) {
        super(questionPrompt);
        this.responses = new ArrayList<>(responses);
    }

    @Override
    public void display() {
        System.out.println(questionPrompt);
        for (int i = 0; i < responses.size(); i++) {
            if (responses.size() > 1) {
                System.out.println("  " + (char) ('A' + i) + ") " + responses.get(i));
            } else {
                System.out.println("Answer: " + responses.get(i));
            }
        }
    }

    public List<String> getResponses() {
        return responses;
    }
}
