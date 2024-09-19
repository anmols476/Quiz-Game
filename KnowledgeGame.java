import java.util.Random;

/**
 * The QuizGame class represents the model for a simple quiz game. It
 * contains questions, options, answers, and tracks the score and
 * answer counts.
 *
 * @author Anmol Singh, Created on 7 July 2024.
 */
public class KnowledgeGame {
    /** Array of quiz questions */
    private String[] questions;
    /** 2D array of answer options corresponding to each question */
    private String[][] options;
    /** Array of correct answers for the questions */
    private String[] answers;
    /** Tracks the directory */
    private int currentQuestionIndex;
    /** present standing of consumer */
    private int score;
    /** Count of correct answers given by the user */
    private int correctAnswers;
    /** Count of incorrect answers given by the user */
    private int incorrectAnswers;
    /** Random object for selecting questions randomly */
    private Random random;

    /**
     * Constructor to initialize the quiz game with predefined questions,
     * options, and answers. Also initializes the score and answer counts.
     */
    public KnowledgeGame() {
        // Initialize the array of questions
        questions = new String[]{
                "What is the capital of France?",
                "Who wrote 'To Kill a Mockingbird'?",
                "What is 5 + 7?",
                "What is the square root of 16?",
                "Who painted the Mona Lisa?",
                "What is the largest planet in our solar system?",
                "What is 9 * 8?",
                "Who developed the theory of relativity?",
                "What is the chemical symbol for water?",
                "Who is known as the father of computers?",
                "What year did the Titanic sink?",
                "The first individual to walk on the moon?",
                "What's the capital of Japan?",
                "What is 12 * 12?",
                "What is the freezing point of water in Celsius?",
                "Who discovered penicillin?",
                "What is the largest ocean on Earth?",
                "What is the capital of Australia?",
                "What is 15 - 6?"
        };

        // Initialize the 2D array of answer options
        options = new String[][]{
                {"Paris", "Berlin", "Madrid", "Rome"},
                {"Harper Lee", "Mark Twain", "J.K. Rowling", "Ernest Hemingway"},
                {"10", "11", "12", "13"},
                {"3", "4", "5", "6"},
                {"Vincent van Gogh", "Pablo Picasso", "Leonardo da Vinci", "Claude Monet"},
                {"Earth", "Mars", "Jupiter", "Saturn"},
                {"72", "81", "64", "73"},
                {"Isaac Newton", "Albert Einstein", "Galileo Galilei", "Nikola Tesla"},
                {"O2", "H2O", "CO2", "NaCl"},
                {"Alan Turing", "John von Neumann", "Charles Babbage", "Ada Lovelace"},
                {"1910", "1912", "1914", "1916"},
                {"Buzz Aldrin", "Neil Armstrong", "Yuri Gagarin", "Michael Collins"},
                {"Seoul", "Beijing", "Tokyo", "Bangkok"},
                {"121", "122", "143", "144"},
                {"0", "-1", "-5", "-10"},
                {"Marie Curie", "Alexander Fleming", "Louis Pasteur", "Joseph Lister"},
                {"Atlantic", "Indian", "Pacific", "Arctic"},
                {"Sydney", "Melbourne", "Canberra", "Perth"},
                {"8", "9", "10", "11"}
        };

        // Initialize the array of correct answers
        answers = new String[]{
                "Paris",
                "Harper Lee",
                "12",
                "4",
                "Leonardo da Vinci",
                "Jupiter",
                "72",
                "Albert Einstein",
                "H2O",
                "Charles Babbage",
                "1912",
                "Neil Armstrong",
                "Tokyo",
                "144",
                "0",
                "Alexander Fleming",
                "Pacific",
                "Canberra",
                "9"
        };

        // Initialize the random object and game state variables
        random = new Random();
        currentQuestionIndex = -1;
        score = 0;
        correctAnswers = 0;
        incorrectAnswers = 0;
    }

    /**
     * Selects the next question randomly from the list of questions.
     */
    public void nextQuestion() {
        currentQuestionIndex = random.nextInt(questions.length);
    }

    /**
     * Returns the current question. If no question has been selected yet,
     * selects a question first.
     *
     * @return The current question
     */
    public String getCurrentQuestion() {
        if (currentQuestionIndex == -1) {
            nextQuestion();
        }
        return questions[currentQuestionIndex];
    }

    /**
     * Gives back the available answers for the present inquiry.
     *
     * @return Array of sol options for the present inquiry.
     */
    public String[] getCurrentOptions() {
        return options[currentQuestionIndex];
    }

    /**
     * verifies that, response given is accurate,
     * updating solution count and points appropriately.
     *
     * @param answer The answer to check
     * @return true if the response is accurate; false if not
     */
    public boolean verifySolution(String answer) {
        boolean isRight = answers[currentQuestionIndex].equalsIgnoreCase(answer.trim());
        if (isRight) {
            score++;
            correctAnswers++;
        } else {
            incorrectAnswers++;
        }
        nextQuestion();
        return isRight;
    }

    /**
     * Gives Present Points.
     *
     * @return present points
     */
    public int getScore() {
        return score;
    }

    /**
     * Gives the right sol count by consumer.
     *
     * @return right sol
     */
    public int getCorrectAnswers() {
        return correctAnswers;
    }

    /**
     * Returns the count of incorrect answers given by the user.
     *
     * @return The count of incorrect answers
     */
    public int getIncorrectAnswers() {
        return incorrectAnswers;
    }
}
