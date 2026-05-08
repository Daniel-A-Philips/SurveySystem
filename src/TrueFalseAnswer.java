// stores a single boolean answer for a True/False question.
public class TrueFalseAnswer extends Answer {
    private static final long serialVersionUID = 1L;

    private boolean value;

    public TrueFalseAnswer(String questionPrompt, boolean value) {
        super(questionPrompt);
        this.value = value;
    }

    @Override
    public void display() {
        System.out.println(questionPrompt);
        System.out.println("T/F");
        System.out.println("Answer: " + (value ? "T" : "F"));
    }

    public boolean getValue() {
        return value;
    }
}
