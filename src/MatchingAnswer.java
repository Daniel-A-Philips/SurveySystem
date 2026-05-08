import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

 //Answer for a Matching question. Stores the columns presented and the left-to-right pairings the user chose.

public class MatchingAnswer extends Answer {
    private static final long serialVersionUID = 1L;

    private List<String> leftColumn;
    private List<String> rightColumn;
    private Map<String, String> pairs;

    public MatchingAnswer(String questionPrompt, List<String> leftColumn,
                          List<String> rightColumn, Map<String, String> pairs) {
        super(questionPrompt);
        this.leftColumn = new ArrayList<>(leftColumn);
        this.rightColumn = new ArrayList<>(rightColumn);
        this.pairs = new LinkedHashMap<>(pairs);
    }

    @Override
    public void display() {
        System.out.println(questionPrompt);
        for (int i = 0; i < leftColumn.size(); i++) {
            String left = (char) ('A' + i) + ") " + leftColumn.get(i);
            String right = (i < rightColumn.size()) ? (i + 1) + ") " + rightColumn.get(i) : "";
            System.out.println(left + "\t\t" + right);
        }
        System.out.println("Matches:");
        for (Map.Entry<String, String> e : pairs.entrySet()) {
            int leftIdx = leftColumn.indexOf(e.getKey());
            int rightIdx = rightColumn.indexOf(e.getValue());
            String letter = (leftIdx >= 0) ? String.valueOf((char) ('A' + leftIdx)) : "?";
            String number = (rightIdx >= 0) ? String.valueOf(rightIdx + 1) : "?";
            System.out.println("  " + letter + " -> " + number);
        }
    }

    public Map<String, String> getPairs() {
        return pairs;
    }
}
