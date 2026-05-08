import java.util.Arrays;

public class SampleSurveyBuilder {
    public static void main(String[] args) throws Exception {
        Survey s = new Survey("SampleSurvey");

        // 1) True/False
        s.addQuestion(new TrueFalseQuestion("This is an example of a T/F question?"));

        // 2) Multiple choice (single response)
        s.addQuestion(new MultipleChoiceQuestion(
                "This is an example of a multiple-choice question with 3 choices?",
                Arrays.asList("Choice 1", "Choice 2", "Choice 3"),
                1));

        // 3) Multiple choice (multiple responses)
        s.addQuestion(new MultipleChoiceQuestion(
                "What teams are evil in football? Please give two choices:",
                Arrays.asList("Dallas Cowboys", "New England Patriots", "Philadelphia Eagles"),
                2));

        // 4) Short answer (single response)
        s.addQuestion(new ShortAnswerQuestion("What is your favorite movie?", 50, 1));

        // 5) Essay (multiple responses)
        s.addQuestion(new EssayQuestion(
                "Give two reasons why Star Wars is better than Star Trek:", 2));

        // 6) Date (single response)
        s.addQuestion(new ValidDateQuestion("What is today's date?", 1));

        // 7) Matching
        s.addQuestion(new MatchingQuestion(
                "Match the team with the city",
                Arrays.asList("Yankees", "Phillies", "Red Sox"),
                Arrays.asList("Philadelphia", "New York", "Boston")));

        SurveyFileManager fm = new SurveyFileManager();
        fm.saveSurvey(s, "SampleSurvey");
        System.out.println("Wrote surveys/SampleSurvey.ser");
    }
}
