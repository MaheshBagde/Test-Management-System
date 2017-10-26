package tms;

import java.util.ArrayList;

/**
 * This class represents a Course
 *
 * @author Mahesh Bagde
 */
public class Course {

    /**
     * Represents the Primary Key assigned by the database
     */
    private int courseID;

    /**
     * Represents the name of a Course
     */
    private String courseName;

    /**
     * Represents a collection of Topic objects
     */
    private ArrayList<Topic> topics;

    /**
     * Constructor that initializes the instance variables
     *
     * @param courseID a Primary Key assigned by the database
     * @param courseName a String value representing the name of a Course
     */
    public Course(int courseID, String courseName) {
        this.courseID = courseID;
        this.courseName = courseName;
        this.topics = new ArrayList<>();
    } // end of constructor

    /**
     * Returns the course ID which is the primary key assigned by the database
     *
     * @return an int value representing the course ID
     */
    public int getCourseID() {
        return courseID;
    } // end of method getCourseID

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
     * Gets a collection of Topic objects
     *
     * @return a collection of topics
     */
    public ArrayList<Topic> getTopics() {
        return topics;
    } // end of method getTopics

    /**
     * Sets a collection of Topic objects
     *
     * @param topics a collection of topics
     */
    public void setTopics(ArrayList<Topic> topics) {
        this.topics = topics;
    } // end of method setTopics

    /**
     * Adds a new Topic to the collection of topics in a Course
     *
     * @param topicID Primary Key assigned by the database
     * @param courseName a String value representing the name of a Course
     * @param topicName a String value representing the name of a Topic
     */
    public void addTopic(int topicID, String courseName, String topicName) {
        topics.add(new Topic(topicID, courseName, topicName));
    } // end of method addTopic

    /**
     * Returns a string representation of the Course object
     *
     * @return String value showing the Course ID and name
     */
    @Override
    public String toString() {
        return "Course - {" + "courseID: " + courseID + ", courseName: " + courseName + '}';
    } // end of method toString

} // end of class Course
