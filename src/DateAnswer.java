import java.util.ArrayList;
import java.util.List;

// Answer for a Valid Date question. Stores one or more dates in YYYY-MM-DD form.
public class DateAnswer extends Answer {
    private static final long serialVersionUID = 1L;

    private List<String> dates;

    public DateAnswer(String questionPrompt, List<String> dates) {
        super(questionPrompt);
        this.dates = new ArrayList<>(dates);
    }

    @Override
    public void display() {
        System.out.println(questionPrompt);
        System.out.println("A date should be entered in the following format: YYYY-MM-DD");
        for (int i = 0; i < dates.size(); i++) {
            if (dates.size() > 1) {
                System.out.println("  " + (char) ('A' + i) + ") " + dates.get(i));
            } else {
                System.out.println("Answer: " + dates.get(i));
            }
        }
    }

    public List<String> getDates() {
        return dates;
    }
}
