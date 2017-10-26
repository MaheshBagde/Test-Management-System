package tms;

import java.util.ArrayList;

/**
 * This class represents a Question
 *
 * @author Mahesh Bagde
 */
public class Question {

    /**
     * Represents the Primary Key assigned by the database
     */
    private int questionID;

    /**
     * Represents the name of a Course
     */
    private String courseName;

    /**
     * Represents the name of a Topic
     */
    private String topicName;

    /**
     * Represents a description of the question
     */
    private String questionDescription;

    /**
     * Represents a collection of Answer objects
     */
    private ArrayList<Answer> multipleChoiceAnswers;

    /**
     * Represents a collection of TestLabel objects
     */
    private ArrayList<TestLabel> testLabels;

    /**
     * Constructor that initializes the instance variables
     *
     * @param questionID Primary Key assigned by the database
     * @param courseName the name of the Course
     * @param topicName the name of the Topic
     * @param questionDescription a description of the question
     * @param multipleChoiceAnswers collection of Answer objects
     */
    public Question(int questionID, String courseName, String topicName, String questionDescription, 
            ArrayList<Answer> multipleChoiceAnswers) {
        this.questionID = questionID;
        this.courseName = courseName;
        this.topicName = topicName;
        this.questionDescription = questionDescription;
        this.multipleChoiceAnswers = multipleChoiceAnswers;
        this.testLabels = new ArrayList<>();
    } // end of constructor

    /**
     * This constructor is used while retrieving Question objects from the Database for loading into the program
     *
     * @param questionID Primary Key assigned by the database
     * @param courseName the name of the Course
     * @param topicName the name of the Topic
     * @param questionDescription a description of the question
     */
    public Question(int questionID, String courseName, String topicName, String questionDescription) {
        this.questionID = questionID;
        this.courseName = courseName;
        this.topicName = topicName;
        this.questionDescription = questionDescription;
        this.multipleChoiceAnswers = new ArrayList<>();
        this.testLabels = new ArrayList<>();
    } // end of constructor

    /**
     * This constructor is used while importing questions from a text file
     *
     * @param questionDescription a description of the question
     * @param multipleChoiceAnswers collection of Answer objects
     */
    public Question(String questionDescription, ArrayList<Answer> multipleChoiceAnswers) {
        this.questionDescription = questionDescription;
        this.multipleChoiceAnswers = multipleChoiceAnswers;
    } // end of constructor

    /**
     * Returns the question ID which is the primary key assigned by the database
     *
     * @return an int value representing the question ID
     */
    public int getQuestionID() {
        return questionID;
    } // end of method getQuestionID

    /**
     * Gets a String value representing the course name
     *
     * @return the course name
     */
    public String getCourseName() {
        return courseName;
    } // end of method getCourseName

    /**
     * Sets the value of field courseName
     *
     * @param courseName the course name
     */
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    } // end of method setCourseName

    /**
     * Gets a String value representing the Topic name
     *
     * @return the topic name
     */
    public String getTopicName() {
        return topicName;
    } // end of method getTopicName

    /**
     * Sets the value of field topicName
     *
     * @param topicName the topic name
     */
    public void setTopicName(String topicName) {
        this.topicName = topicName;
    } // end of method setTopicName

    /**
     * Gets a String value representing the question description
     *
     * @return the question description
     */
    public String getQuestionDescription() {
        return questionDescription;
    } // end of method getQuestionDescription

    /**
     * Sets the value of field questionDescription
     *
     * @param questionDescription the question description
     */
    public void setQuestionDescription(String questionDescription) {
        this.questionDescription = questionDescription;
    } // end of method setQuestionDescription

    /**
     * Gets a collection of Answer objects
     *
     * @return a collection of answers
     */
    public ArrayList<Answer> getMultipleChoiceAnswers() {
        return multipleChoiceAnswers;
    } // end of method getMultipleChoiceAnswers

    /**
     * Sets a collection of Answer objects
     *
     * @param multipleChoiceAnswers a collection of answers
     */
    public void setMultipleChoiceAnswers(ArrayList<Answer> multipleChoiceAnswers) {
        this.multipleChoiceAnswers = multipleChoiceAnswers;
    } // end of method setMultipleChoiceAnswers

    /**
     * Gets a collection of TestLabel objects
     *
     * @return a collection of test labels
     */
    public ArrayList<TestLabel> getTestLabels() {
        return testLabels;
    } // end of method getTestLabels

    /**
     * Sets a collection of TestLabel objects
     *
     * @param testLabels a collection of test labels
     */
    public void setTestLabels(ArrayList<TestLabel> testLabels) {
        this.testLabels = testLabels;
    } // end of method setTestLabels

    /**
     * Adds a new Answer to the collection of multiple choice answers in a Question
     *
     * @param answerID a Primary Key assigned by the database
     * @param answerChoice a String value representing an answer
     * @param correctAnswer a boolean value that states whether the answer is true or false
     */
    public void addMultipleChoiceAnswer(int answerID, String answerChoice, boolean correctAnswer) {
        multipleChoiceAnswers.add(new Answer(answerID, answerChoice, correctAnswer));
    } // end of method addMultipleChoiceAnswer

    /**
     * Adds a new TestLabel to the collection of test labels in a Question
     *
     * @param testLabelID Primary Key assigned by the database
     * @param year an int value representing the year
     * @param term an Enum of the Term such as Fall, Winter, Spring
     * @param testType an Enum of the TestType such as Q4, A9, T2, E1
     */
    public void addTestLabel(int testLabelID, int year, Term term, TestType testType) {
        testLabels.add(new TestLabel(testLabelID, year, term, testType));
    } // end of method addTestLabel

    /**
     * Returns a string representation of the Question object
     *
     * @return String value showing the Question ID and description
     */
    @Override
    public String toString() {
        return "Question - " + "questionID: " + questionID + ", questionDescription: " + questionDescription + '}';
    } // end of method 

} // end of class Question
