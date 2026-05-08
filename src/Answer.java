import java.io.Serializable;

// Abstract base class for all answer types.
// Each Answer remembers the prompt of the question it answered, so a
// Response can be displayed in human-readable form even after loading.
public abstract class Answer implements Serializable {
    private static final long serialVersionUID = 1L;

    protected String questionPrompt;

    public Answer(String questionPrompt) {
        this.questionPrompt = questionPrompt;
    }

    // Display this answer (prompt + the user's response) to the screen.
    public abstract void display();

    public String getQuestionPrompt() {
        return questionPrompt;
    }
}
