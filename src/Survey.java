import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

// A Survey is named collection of Question objects.
public class Survey implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String name;
    private List<Question> questions;

    public Survey(String name) {
        this.name = name;
        this.questions = new ArrayList<>();
    }

    public void addQuestion(Question q) {
        if (q != null) questions.add(q);
    }

    public void removeQuestion(int index) {
        if (index >= 0 && index < questions.size()) {
            questions.remove(index);
        }
    }

    public void modifyQuestion(int index, InputHelper input) {
        if (index >= 0 && index < questions.size()) {
            questions.get(index).modify(input);
        }
    }

    // Display the entire survey, numbering each question.
    public void display() {
        System.out.println("=== Survey: " + name + " ===");
        if (questions.isEmpty()) {
            System.out.println("(This survey has no questions yet.)");
            return;
        }
        for (int i = 0; i < questions.size(); i++) {
            System.out.println((i + 1) + ") " + questions.get(i).getPrompt());
            // delegate the type-specific bit
            questions.get(i).display();
            System.out.println();
        }
    }

    // Take the survey, asking each question and returning a Response.
    public Response take(InputHelper input) {
        Response response = new Response(name);
        System.out.println("Taking survey: " + name);
        for (int i = 0; i < questions.size(); i++) {
            System.out.println();
            System.out.println("Question " + (i + 1) + " of " + questions.size() + ":");
            Answer a = questions.get(i).take(input);
            response.addAnswer(a);
        }
        return response;
    }

    public String getName() {
        return name;
    }

    public List<Question> getQuestions() {
        return questions;
    }
}
