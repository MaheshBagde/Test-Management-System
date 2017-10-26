package tms;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class manages the interaction with the Database
 *
 * @author Mahesh Bagde
 */
public class DatabaseManager {

    /**
     * Specifies the location where the database will be stored.
     */
    private static final String URL = "jdbc:sqlite:C:\\Users\\User\\Documents\\Test Management System\\QuestionsBank.db";

    private static final DatabaseManager databaseManager = new DatabaseManager();
    private TestBank testBank = TestBank.getInstance();

    /**
     * A private constructor that retrieves information from the database and loads it into the application
     */
    private DatabaseManager() {
        createCourseTable();
        createTopicTable();
        createQuestionTable();
        createAnswerTable();
        createTestLabelTable();
        retrieveCoursesFromDatabase();
        retrieveTopicsFromDatabase();
        retrieveQuestionsAndAnswersFromDatabase();
        retrieveTestLabelsFromDatabase();
    } // end of constructor

    /**
     * This method returns a static instance of this class to ensure a singleton design pattern
     *
     * @return a DatabaseManager object
     */
    public static DatabaseManager getInstance() {
        return databaseManager;
    } // end of method getInstance

    /**
     * Creates a new table in the database called 'Course'
     */
    private void createCourseTable() {

        String query = "CREATE TABLE IF NOT EXISTS course"
                + "(course_ID INTEGER PRIMARY KEY,"
                + "course_name TEXT NOT NULL);";

        try (Connection connection = DriverManager.getConnection(URL);
                Statement statement = connection.createStatement()) {
            statement.execute(query);
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
        } // end of try catch block

    } // end of method createCourseTable

    /**
     * Inserts data into the 'Course' table
     * 
     * @param courseName name of the Course
     * @return primary key for the 'Course' added
     */
    public int addCourseToDatabase(String courseName) {
        int courseID = 0;
        String query = "INSERT INTO course (course_name) VALUES (?)";

        try (Connection connection = DriverManager.getConnection(URL);
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, courseName);
            preparedStatement.executeUpdate();
            courseID = preparedStatement.getGeneratedKeys().getInt(1);

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
        } // end of try catch block

        return courseID;
    } // end of method addCourseToDatabase 

    /**
     * Retrieves records from the 'Course' table and loads it into the application
     */
    private void retrieveCoursesFromDatabase() {
        String query = "SELECT course_ID, course_name from course";

        try (Connection connection = DriverManager.getConnection(URL);
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                testBank.addCourse(resultSet.getInt("course_ID"), resultSet.getString("course_name"));
            } // end while loop

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
        } // end of try catch block

    } // end of method retrieveCoursesFromDatabase 

    /**
     * Creates a new table in the database called 'Topic'
     */
    private void createTopicTable() {

        String query = "CREATE TABLE IF NOT EXISTS topic"
                + "(topic_ID INTEGER PRIMARY KEY,"
                + "course_ID INTEGER NOT NULL,"
                + "topic_name TEXT NOT NULL,"
                + "FOREIGN KEY (course_ID) REFERENCES course (course_ID));";

        try (Connection connection = DriverManager.getConnection(URL);
                Statement statement = connection.createStatement()) {
            statement.execute(query);
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
        } // end of try catch block

    } // end of method createTopicTable 

    /**
     * Inserts data into the 'Topic' table
     * 
     * @param courseID foreign key 
     * @param topicName name of the Topic
     * @return primary key for the 'Topic' added
     */
    public int addTopicToDatabase(int courseID, String topicName) {
        int topicID = 0;
        String query = "INSERT INTO topic (course_ID, topic_name) VALUES (?, ?)";

        try (Connection connection = DriverManager.getConnection(URL);
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, courseID);
            preparedStatement.setString(2, topicName);
            preparedStatement.executeUpdate();
            topicID = preparedStatement.getGeneratedKeys().getInt(1);

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
        } // end of try catch block

        return topicID;
    } // end of method addTopicToDatabase 

    /**
     * Retrieves records from the 'Topic' table and loads it into the application
     */
    private void retrieveTopicsFromDatabase() {
        String query = "SELECT * from topic";

        try (Connection connection = DriverManager.getConnection(URL);
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                for (int index = 0; index < testBank.getCourses().size(); index++) {
                    if (resultSet.getInt("course_ID") == (testBank.getCourses().get(index).getCourseID())) {
                        testBank.getCourses().get(index).addTopic(resultSet.getInt("topic_ID"),
                                testBank.getCourses().get(index).getCourseName(),
                                resultSet.getString("topic_name"));
                        index = testBank.getCourses().size();
                    } // end if statement
                } // end for each loop
            } // end while loop

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
        } // end of try catch block

    } // end of method retrieveTopicsFromDatabase 

    /**
     * Creates a new table in the database called 'Question'
     */
    private void createQuestionTable() {

        String query = "CREATE TABLE IF NOT EXISTS question"
                + "(question_ID INTEGER PRIMARY KEY,"
                + "topic_ID INTEGER NOT NULL,"
                + "question_description TEXT NOT NULL,"
                + "FOREIGN KEY (topic_ID) REFERENCES topic (topic_ID)"
                + " ON DELETE CASCADE);";

        try (Connection connection = DriverManager.getConnection(URL);
                Statement statement = connection.createStatement()) {
            statement.execute(query);
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
        } // end of try catch block

    } // end of method createQuestionTable 

    /**
     * Inserts data into the 'Question' table
     * 
     * @param topicID foreign key
     * @param questionDescription the question description
     * @return primary key for the 'Question' added
     */
    public int addQuestionToDatabase(int topicID, String questionDescription) {
        int questionID = 0;
        String query = "INSERT INTO question (topic_ID, question_description) VALUES (?, ?)";

        try (Connection connection = DriverManager.getConnection(URL);
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, topicID);
            preparedStatement.setString(2, questionDescription);
            preparedStatement.executeUpdate();
            questionID = preparedStatement.getGeneratedKeys().getInt(1);

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
        } // end of try catch block

        return questionID;
    } // end of method addQuestionToDatabase 

    /**
     * Creates a new table in the database called 'Answer'
     */
    private void createAnswerTable() {

        String query = "CREATE TABLE IF NOT EXISTS answer"
                + "(answer_ID INTEGER PRIMARY KEY,"
                + "question_ID INTEGER NOT NULL,"
                + "answer_choice TEXT NOT NULL,"
                + "correct_answer INTEGER NOT NULL,"
                + "FOREIGN KEY (question_ID) REFERENCES question (question_ID));";

        try (Connection connection = DriverManager.getConnection(URL);
                Statement statement = connection.createStatement()) {
            statement.execute(query);
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
        } // end of try catch block

    } // end of method createAnswerTable 

    /**
     * Inserts data into the 'Answer' table
     * 
     * @param questionID foreign key
     * @param answerChoice a single answer
     * @param correctAnswer information about the answer whether it is true or false
     * @return primary key for the 'Answer' added
     */
    public int addAnswerToDatabase(int questionID, String answerChoice, int correctAnswer) {
        int answerID = 0;
        String query = "INSERT INTO answer (question_ID, answer_choice, correct_answer) VALUES (?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(URL);
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, questionID);
            preparedStatement.setString(2, answerChoice);
            preparedStatement.setInt(3, correctAnswer);
            preparedStatement.executeUpdate();
            answerID = preparedStatement.getGeneratedKeys().getInt(1);

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
        } // end of try catch block

        return answerID;
    } // end of method addAnswerToDatabase 

    /**
     * Retrieves all the Questions & Answers and loads it into the application
     */
    private void retrieveQuestionsAndAnswersFromDatabase() {

        String query = "SELECT course.course_ID, topic.topic_ID, question.question_ID, question_description, "
                + "answer_ID, answer_choice, correct_answer from course "
                + "JOIN topic ON course.course_ID = topic.course_ID "
                + "JOIN question ON topic.topic_ID = question.topic_ID "
                + "JOIN answer ON question.question_ID = answer.question_ID "
                + "ORDER BY course.course_ID, topic.topic_ID, question.question_ID, answer_ID;";

        try (Connection connection = DriverManager.getConnection(URL);
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query)) {

            int currentQuestionID = 0;
            Course course = null;
            Topic topic = null;
            Question question = null;

            while (resultSet.next()) {

                if (currentQuestionID != resultSet.getInt("question_ID")) {
                    if (question != null) {
                        topic.addQuestion(question);
                    } // end if statement
                    course = findCourse(resultSet.getInt("course_ID"), testBank.getCourses());
                    topic = findTopic(resultSet.getInt("topic_ID"), course);
                    currentQuestionID = resultSet.getInt("question_ID");
                    question = new Question(currentQuestionID, course.getCourseName(),
                            topic.getTopicName(), resultSet.getString("question_description"));
                    question.addMultipleChoiceAnswer(resultSet.getInt("answer_ID"), resultSet.getString("answer_choice"),
                            (resultSet.getInt("correct_answer") == 1) ? true : false);
                } else {
                    question.addMultipleChoiceAnswer(resultSet.getInt("answer_ID"), resultSet.getString("answer_choice"),
                            (resultSet.getInt("correct_answer") == 1) ? true : false);
                } // end if else statement

            } // end while loop : until resultSet has more records
            if (question != null) {
                topic.addQuestion(question);
            } // end if statement

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
        } // end of try catch block

    } // end of method retrieveQuestionsAndAnswersFromDatabase 

    /**
     * Creates a new table in the database called 'test_label'
     */
    private void createTestLabelTable() {

        String query = "CREATE TABLE IF NOT EXISTS test_label"
                + "(test_label_ID INTEGER PRIMARY KEY,"
                + "question_ID INTEGER NOT NULL,"
                + "year INTEGER NOT NULL,"
                + "term TEXT NOT NULL,"
                + "test_type TEXT NOT NULL,"
                + "FOREIGN KEY (question_ID) REFERENCES question (question_ID));";

        try (Connection connection = DriverManager.getConnection(URL);
                Statement statement = connection.createStatement()) {
            statement.execute(query);
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
        } // end of try catch block

    } // end of method createTestLabelTable

    /**
     * Inserts data into the 'test_label' table
     * 
     * @param questionID foreign key
     * @param year the year
     * @param term Term such as Fall, Winter, Spring
     * @param testType TestType such as Q4, A9, T2, E1
     * @return primary key for the 'test_label' added
     */
    public int addTestLabelToDatabase(int questionID, int year, String term, String testType) {
        int testLabelID = 0;
        String query = "INSERT INTO test_label (question_ID, year, term, test_type) VALUES (?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(URL);
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, questionID);
            preparedStatement.setInt(2, year);
            preparedStatement.setString(3, term);
            preparedStatement.setString(4, testType);
            preparedStatement.executeUpdate();
            testLabelID = preparedStatement.getGeneratedKeys().getInt(1);

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
        } // end of try catch block

        return testLabelID;
    } // end of method addTestLabelToDatabase 

    /**
     * Retrieves all the Test Labels and loads it into the application
     */
    private void retrieveTestLabelsFromDatabase() {

        String query = "SELECT course.course_ID, topic.topic_ID, question.question_ID, "
                + "test_label_ID, year, term, test_type from course "
                + "JOIN topic ON course.course_ID = topic.course_ID "
                + "JOIN question ON topic.topic_ID = question.topic_ID "
                + "JOIN test_label ON question.question_ID = test_label.question_ID "
                + "ORDER BY course.course_ID, topic.topic_ID, question.question_ID, test_label_ID;";

        try (Connection connection = DriverManager.getConnection(URL);
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query)) {

            int currentQuestionID = 0;
            Course course = null;
            Topic topic = null;
            Question question = null;
            Term term = null;
            TestType testType = null;

            while (resultSet.next()) {

                if (currentQuestionID != resultSet.getInt("question_ID")) {
                    currentQuestionID = resultSet.getInt("question_ID");
                    course = findCourse(resultSet.getInt("course_ID"), testBank.getCourses());
                    topic = findTopic(resultSet.getInt("topic_ID"), course);
                    question = findQuestion(currentQuestionID, topic);
                    term = Term.getTerm(resultSet.getString("term"));
                    testType = TestType.getTestType(resultSet.getString("test_type"));
                    int year = resultSet.getInt("year");
                    question.addTestLabel(resultSet.getInt("test_label_ID"), resultSet.getInt("year"), term, testType);

                } else {
                    term = Term.getTerm(resultSet.getString("term"));
                    testType = TestType.getTestType(resultSet.getString("test_type"));
                    int year = resultSet.getInt("year");
                    question.addTestLabel(resultSet.getInt("test_label_ID"), resultSet.getInt("year"), term, testType);
                } // end if else statement

            } // end while loop : until resultSet has more records

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
        } // end of try catch block

    } // end of method retrieveTestLabelsFromDatabase  

    /**
     * A helper method for CreateTestController class that retrieves all Questions that have the given Test Label
     * The year, term, test type information is passed in as parameters which is combined to determine the label
     * 
     * @param year the year
     * @param term Term such as Fall, Winter, Spring
     * @param testType TestType such as Q4, A9, T2, E1
     * @return an ArrayList of Question objects
     */
    public ArrayList<Question> getSavedTestQuestionsFromDatabase(int year, Term term, TestType testType) {
        String query = "SELECT course.course_ID, topic.topic_ID, question.question_ID from course "
                + "JOIN topic ON course.course_ID = topic.course_ID "
                + "JOIN question ON topic.topic_ID = question.topic_ID "
                + "JOIN test_label ON question.question_ID = test_label.question_ID "
                + "WHERE year = ? AND term = ? AND test_type = ? "
                + "ORDER BY course.course_ID, topic.topic_ID, question.question_ID;";

        ArrayList<Question> questions = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(URL);
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, year);
            preparedStatement.setString(2, String.valueOf(term));
            preparedStatement.setString(3, String.valueOf(testType));

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                Course course = null;
                Topic topic = null;
                Question question = null;

                while (resultSet.next()) {
                    if (course == null) {
                        course = findCourse(resultSet.getInt("course_ID"), testBank.getCourses());
                    } // end if statement
                    topic = findTopic(resultSet.getInt("topic_ID"), course);
                    question = findQuestion(resultSet.getInt("question_ID"), topic);
                    questions.add(question);
                } // end while loop : until resultSet has more records
            } // end of ResultSet try with resources

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
        } // end of try catch block

        return questions;
    } // end of method getSavedTestQuestionsFromDatabase  

    /**
     * This method is called when the user deletes a question from the Test List
     * 
     * @param testLabelID primary key for the test_label
     * @return an int value that indicates success or failure of the transaction
     */
    public int deleteTestLabelInDatabase(int testLabelID) {
        int status = -1;
        String query = "DELETE FROM test_label WHERE test_label_ID = ?";

        try (Connection connection = DriverManager.getConnection(URL);
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, testLabelID);
            status = preparedStatement.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
        } // end of try catch block

        return status;
    } // end of method deleteTestLabelInDatabase

    /**
     * A helper method to find Course object using courseID
     * 
     * @param courseID primary key of the Course
     * @param courses a collection of Course objects
     * @return a Course object
     */
    private Course findCourse(int courseID, ArrayList<Course> courses) {
        Course course = null;
        for (int index = 0; index < courses.size(); index++) {
            if (courseID == (courses.get(index).getCourseID())) {
                course = testBank.getCourses().get(index);
                index = courses.size();
            } // end if statement
        } // end for loop
        return course;
    } // end of method findCourse

    /**
     * A helper method to find Topic object using topicID
     * 
     * @param topicID primary key of the Topic
     * @param course a Course object that contains the collection of Topic objects
     * @return a Topic object
     */
    private Topic findTopic(int topicID, Course course) {
        Topic topic = null;
        ArrayList<Topic> topics = course.getTopics();
        for (int index = 0; index < topics.size(); index++) {
            if (topicID == (topics.get(index).getTopicID())) {
                topic = course.getTopics().get(index);
                index = topics.size();
            } // end if statement
        } // end for loop
        return topic;
    } // end of method findTopic

    /**
     * A helper method to find question object using questionID
     * 
     * @param questionID primary key of the Question
     * @param topic a Topic object that contains the collection of Question objects
     * @return a Question object
     */
    private Question findQuestion(int questionID, Topic topic) {
        Question question = null;
        ArrayList<Question> questions = topic.getQuestions();
        for (int index = 0; index < questions.size(); index++) {
            if (questionID == (questions.get(index).getQuestionID())) {
                question = topic.getQuestions().get(index);
                index = questions.size();
            } // end if statement
        } // end for loop
        return question;
    } // end of method findQuestion

} // end of class DatabaseManager