import java.io.Serializable;


 // Abstract base class for all question types in a Survey.
public abstract class Question implements Serializable {
    private static final long serialVersionUID = 1L;

    protected String prompt;
    protected int numResponses;

    public Question(String prompt, int numResponses) {
        this.prompt = prompt;
        this.numResponses = (numResponses < 1) ? 1 : numResponses;
    }

    // Display this question to the screen.
    public abstract void display();

    // Allow the user to modify this question (prompt and/or specific fields).
    public abstract void modify(InputHelper input);

    // Have the user take (answer) this question, returning an Answer.
    public abstract Answer take(InputHelper input);

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public int getNumResponses() {
        return numResponses;
    }

    public void setNumResponses(int numResponses) {
        this.numResponses = (numResponses < 1) ? 1 : numResponses;
    }
}
