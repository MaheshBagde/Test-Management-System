package tms;

/**
 * This class represents an answer for the multiple choice options
 *
 * @author Mahesh Bagde
 */
public class Answer {

    /**
     * Represents the Primary Key assigned by the database
     */
    private int answerID;

    /**
     * Represents a single answer
     */
    private String answerChoice;

    /**
     * Stores information about the answer whether it is true or false
     */
    private boolean correctAnswer;

    /**
     * Constructor that initializes the instance variables
     *
     * @param answerID a Primary Key assigned by the database
     * @param answerChoice a String value representing an answer
     * @param correctAnswer a boolean value that states whether the answer is true or false
     */
    public Answer(int answerID, String answerChoice, boolean correctAnswer) {
        this.answerID = answerID;
        this.answerChoice = answerChoice;
        this.correctAnswer = correctAnswer;
    } // end of constructor

    /**
     * Returns the answer ID which is the primary key assigned by the database
     *
     * @return an int value representing the answer ID
     */
    public int getAnswerID() {
        return answerID;
    } // end of method getAnswerID

    /**
     * Gets a String value representing the answer
     *
     * @return the answer
     */
    public String getAnswerChoice() {
        return answerChoice;
    } // end of method getAnswerChoice

    /**
     * Sets the value of field answerChoice
     *
     * @param answerChoice the answer
     */
    public void setAnswerChoice(String answerChoice) {
        this.answerChoice = answerChoice;
    } // end of method setAnswerChoice

    /**
     * Returns a boolean value representing whether the answer is true or false
     *
     * @return true if the answer is correct or false otherwise
     */
    public boolean isCorrectAnswer() {
        return correctAnswer;
    } // end of method isCorrectAnswer

    /**
     * Sets the value of field correctAnswer
     *
     * @param correctAnswer true if the answer is correct or false otherwise
     */
    public void setCorrectAnswer(boolean correctAnswer) {
        this.correctAnswer = correctAnswer;
    } // end of method setCorrectAnswer

    /**
     * Returns a string representation of the Answer object
     *
     * @return String value showing the Answer ID, the answer and whether the answer is true or false
     */
    @Override
    public String toString() {
        return "Answer - {" + "answerID: " + answerID + ", answerChoice: " + answerChoice + ", correctAnswer: " + correctAnswer + '}';
    } // end of method setCorrectAnswer

} // end of class Answer
