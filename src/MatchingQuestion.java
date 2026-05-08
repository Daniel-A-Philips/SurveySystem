import java.io.Serial;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MatchingQuestion extends Question {
    @Serial
    private static final long serialVersionUID = 1L;

    private List<String> leftColumn;
    private List<String> rightColumn;

    public MatchingQuestion(String prompt, List<String> left, List<String> right) {
        super(prompt, (left == null) ? 0 : left.size());
        this.leftColumn = (left == null) ? new ArrayList<>() : new ArrayList<>(left);
        this.rightColumn = (right == null) ? new ArrayList<>() : new ArrayList<>(right);
    }

    @Override
    public void display() {
        System.out.println(prompt);
        int rows = Math.max(leftColumn.size(), rightColumn.size());
        // figure out left-column width for alignment
        int leftWidth = 0;
        for (String s : leftColumn) {
            if (s.length() > leftWidth) leftWidth = s.length();
        }
        for (int i = 0; i < rows; i++) {
            String leftItem = (i < leftColumn.size())
                    ? ((char) ('A' + i) + ") " + leftColumn.get(i))
                    : "";
            String rightItem = (i < rightColumn.size())
                    ? ((i + 1) + ") " + rightColumn.get(i))
                    : "";

            // pad left column so right column lines up
            // Make it so: letter + ") " + content + spacing
            int padTo = leftWidth + 4;
            StringBuilder line = new StringBuilder();
            line.append(leftItem);

            while (line.length() < padTo) line.append(' ');
            line.append("  ").append(rightItem);
            System.out.println(line.toString());
        }
    }

    @Override
    public void modify(InputHelper input) {
        System.out.println("Current prompt: " + prompt);
        if (input.getBoolean("Do you wish to modify the prompt? (Y/N): ")) {
            this.prompt = input.getString("Enter a new prompt: ");
            System.out.println("Prompt updated.");
        }
        if (input.getBoolean("Do you wish to modify the left column? (Y/N): ")) {
            modifyColumn(input, leftColumn, "left", true);
        }
        if (input.getBoolean("Do you wish to modify the right column? (Y/N): ")) {
            modifyColumn(input, rightColumn, "right", false);
        }
        // keep numResponses in sync with the smaller column
        this.numResponses = Math.min(leftColumn.size(), rightColumn.size());
    }

    private void modifyColumn(InputHelper input, List<String> column, String label, boolean lettered) {
        while (true) {
            System.out.println("Current " + label + " column:");
            for (int i = 0; i < column.size(); i++) {
                String marker = lettered ? String.valueOf((char) ('A' + i)) : String.valueOf(i + 1);
                System.out.println("  " + marker + ") " + column.get(i));
            }
            int idx = input.getInt(
                    "Which item to modify? (1-" + column.size() + ", 0 to finish): ",
                    0, column.size());
            if (idx == 0) break;
            String newVal = input.getString("Enter the new value: ");
            column.set(idx - 1, newVal);
            System.out.println("Item updated.");
            if (!input.getBoolean("Modify another item in this column? (Y/N): ")) break;
        }
    }

    @Override
    public Answer take(InputHelper input) {
        display();
        Map<String, String> pairs = new LinkedHashMap<>();
        System.out.println("Enter your matches as 'letter number' pairs (one per line).");
        for (int i = 0; i < leftColumn.size(); i++) {
            char letter = (char) ('A' + i);
            while (true) {
                String line = input.getString("Match for " + letter + " (e.g. " + letter + " 1): ").trim();
                String[] parts = line.split("\\s+");
                if (parts.length < 2) {
                    System.out.println("Please enter a letter and a number, separated by a space.");
                    continue;
                }
                String letterPart = parts[0].toUpperCase();
                String numberPart = parts[1];
                if (letterPart.length() != 1) {
                    System.out.println("First token should be a single letter.");
                    continue;
                }
                int leftIndex = letterPart.charAt(0) - 'A';
                if (leftIndex < 0|| leftIndex >= leftColumn.size()) {
                    System.out.println("Letter out of range.");
                    continue;
                }
                int rightIndex;
                try {
                    rightIndex = Integer.parseInt(numberPart) - 1;
                } catch (NumberFormatException e) {
                    System.out.println("Second token should be a number.");
                    continue;
                }
                if (rightIndex < 0 || rightIndex >= rightColumn.size()) {
                    System.out.println("Number out of range.");
                    continue;
                }
                pairs.put(leftColumn.get(leftIndex), rightColumn.get(rightIndex));
                break;
            }
        }
        return new MatchingAnswer(prompt, leftColumn, rightColumn, pairs);
    }

    public List<String> getLeftColumn() {
        return leftColumn;
    }

    public List<String> getRightColumn() {
        return rightColumn;
    }
}
