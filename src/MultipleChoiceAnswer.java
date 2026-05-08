import java.util.ArrayList;
import java.util.List;

// Answer class for a Multiple Choice question.
public class MultipleChoiceAnswer extends Answer {
    private static final long serialVersionUID = 1L;

    private List<String> choices;
    private List<String> selectedChoices;

    public MultipleChoiceAnswer(String questionPrompt, List<String> choices, List<String> selectedChoices) {
        super(questionPrompt);
        this.choices = new ArrayList<>(choices);
        this.selectedChoices = new ArrayList<>(selectedChoices);
    }

    @Override
    public void display() {
        System.out.println(questionPrompt);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < choices.size(); i++) {
            sb.append((char) ('A' + i)).append(") ").append(choices.get(i));
            if (i < choices.size() - 1) sb.append("  ");
        }
        System.out.println(sb.toString());
        System.out.print("Answer: ");
        for (int i = 0; i < selectedChoices.size(); i++) {
            int idx = choices.indexOf(selectedChoices.get(i));
            if (idx >= 0) {
                System.out.print((char) ('A' + idx));
            } else {
                System.out.print("?");
            }
            if (i < selectedChoices.size() - 1) System.out.print(", ");
        }
        System.out.println();
    }

    public List<String> getSelectedChoices() {
        return selectedChoices;
    }
}
