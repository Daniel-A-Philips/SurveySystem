import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


 //A Response is a set of Answers produced by taking a Survey.

public class Response implements Serializable {
    private static final long serialVersionUID = 1L;

    private String surveyName;
    private List<Answer> answers;

    public Response(String surveyName) {
        this.surveyName = surveyName;
        this.answers = new ArrayList<>();
    }

    public void addAnswer(Answer a) {
        if (a != null) answers.add(a);
    }

    public void display() {
        System.out.println("=== Response to: " + surveyName + " ===");
        for (int i = 0; i < answers.size(); i++) {
            System.out.println((i + 1) + ") ");
            answers.get(i).display();
            System.out.println();
        }
    }

    public String getSurveyName() {
        return surveyName;
    }

    public List<Answer> getAnswers() {
        return answers;
    }
}
