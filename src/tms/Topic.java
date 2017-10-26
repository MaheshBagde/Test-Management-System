package tms;

import java.util.ArrayList;

/**
 * This class represents a Topic in a Course
 *
 * @author Mahesh Bagde
 */
public class Topic {

    /**
     * Represents the Primary Key assigned by the database
     */
    private int topicID;

    /**
     * Represents the name of a Course
     */
    private String courseName;

    /**
     * Represents the name of a Topic
     */
    private String topicName;

    /**
     * Represents a collection of Question objects
     */
    private ArrayList<Question> questions;

    /**
     * Constructor that initializes the instance variables
     *
     * @param topicID a Primary Key assigned by the database
     * @param courseName a String value representing the name of a Course
     * @param topicName a String value representing the name of a Topic
     */
    public Topic(int topicID, String courseName, String topicName) {
        this.topicID = topicID;
        this.courseName = courseName;
        this.topicName = topicName;
        this.questions = new ArrayList<>();
    } // end of constructor

    /**
     * Returns the topic ID which is the primary key assigned by the database
     *
     * @return an int value representing the topic ID
     */
    public int getTopicID() {
        return topicID;
    } // end of method getTopicID

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
     * Gets a collection of Question objects
     *
     * @return a collection of questions
     */
    public ArrayList<Question> getQuestions() {
        return questions;
    } // end of method getQuestions

    /**
     * Sets a collection of Question objects
     *
     * @param questions a collection of questions
     */
    public void setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
    } // end of method setQuestions

    /**
     * Adds a new Question to the collection of questions in a Topic
     *
     * @param questionID Primary Key assigned by the database
     * @param courseName a String value representing the name of a Course
     * @param topicName a String value representing the name of a Topic
     * @param questionDescription a String value representing a description of the question
     * @param multipleChoiceAnswers an ArrayList representing a collection of Answer objects
     */
    public void addQuestion(int questionID, String courseName, String topicName, 
            String questionDescription, ArrayList<Answer> multipleChoiceAnswers) {
        questions.add(new Question(questionID, courseName, topicName, questionDescription, multipleChoiceAnswers));
    } // end of method addQuestion

    /**
     * Adds a new Question to the collection of questions in a Topic, this method is used while retrieving 
     * Question objects from the Database for loading into the program
     *
     * @param currentQuestion the Question object to be added to a Topic
     */
    public void addQuestion(Question currentQuestion) {
        questions.add(currentQuestion);
    } // end of method addQuestion

    /**
     * Returns a string representation of the Topic object
     *
     * @return a String value showing the Topic ID and name
     */
    @Override
    public String toString() {
        return "Topic - {" + "topicID: " + topicID + ", topicName: " + topicName + '}';
    } // end of method toString

}  // end of class Topic
