package Sclient;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DatabaseConnection {

    private static final String CONNECTION_URL = "jdbc:mysql://localhost:3306/student_services?useSSL=false";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "0danewalter$";

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(CONNECTION_URL, DB_USERNAME, DB_PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean authenticate(String userID, String userPass) {
        String query = "SELECT * FROM Authentication WHERE userID = ? AND userPass = ?";

        try (Connection connection = getConnection()) {
            if (connection != null) {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, userID);
                preparedStatement.setString(2, userPass);

                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static int fetchOutstandingQueries() {
        String query = "SELECT COUNT(*) FROM Queries WHERE response IS NULL";
        return executeCountQuery(query);
    }

    public static int fetchResolvedQueries() {
        String query = "SELECT COUNT(*) FROM Queries WHERE response IS NOT NULL";
        return executeCountQuery(query);
    }

    public static int fetchOutstandingComplaints() {
        String query = "SELECT COUNT(*) FROM Complaint WHERE response IS NULL";
        return executeCountQuery(query);
    }

    public static int fetchResolvedComplaints() {
        String query = "SELECT COUNT(*) FROM Complaint WHERE response IS NOT NULL";
        return executeCountQuery(query);
    }



    private static int executeCountQuery(String query) {
        try (Connection connection = getConnection()) {
            if (connection != null) {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    return resultSet.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }

    public static List<Query> fetchOutstandingQueriesList() {
        List<Query> queries = new ArrayList<>();
        String query = "SELECT queryID, studentID, details FROM Queries WHERE response IS NULL";

        try (Connection connection = getConnection()) {
            if (connection != null) {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    Query q = new Query(resultSet.getInt("queryID"), resultSet.getString("studentID"), resultSet.getString("details"));
                    queries.add(q);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return queries;
    }


    public static List<Complaint> fetchOutstandingComplaintsList() {
        List<Complaint> complaints = new ArrayList<>();
        String query = "SELECT complaintID, studentID, details FROM Complaint WHERE response IS NULL";

        try (Connection connection = getConnection()) {
            if (connection != null) {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    Complaint c = new Complaint(resultSet.getInt("complaintID"), resultSet.getString("studentID"), resultSet.getString("details"));
                    complaints.add(c);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return complaints;
    }


    public static List<Advisor> fetchAdvisors() {
        List<Advisor> advisors = new ArrayList<>();
        String query = "SELECT advisorID, firstName, lastName FROM Advisor";

        try (Connection connection = getConnection()) {
            if (connection != null) {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    String advisorID = resultSet.getString("advisorID");
                    String firstName = resultSet.getString("firstName");
                    String lastName = resultSet.getString("lastName");

                    Advisor advisor = new Advisor(advisorID, firstName, lastName);
                    advisors.add(advisor);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return advisors;
    }

    public static void assignAdvisorToQuery(String advisorID, int queryID) {
        String query = "UPDATE Queries SET responderID = ? WHERE queryID = ?";

        try (Connection connection = getConnection()) {
            if (connection != null) {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, advisorID);
                preparedStatement.setInt(2, queryID);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void assignAdvisorToComplaint(String advisorID, int complaintID) {
        String query = "UPDATE Complaint SET responderID = ? WHERE complaintID = ?";

        try (Connection connection = getConnection()) {
            if (connection != null) {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, advisorID);
                preparedStatement.setInt(2, complaintID);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Query> fetchQueriescategoryList() {
        List<Query> queries = new ArrayList<>();
        String query = "SELECT category FROM Queries";

        try (Connection connection = getConnection()) {
            if (connection != null) {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    Query q = new Query(resultSet.getString("category"));
                    queries.add(q);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return queries;
    }

    public static List<Complaint> fetchComplaintscategoryList() {
        List<Complaint> complaints = new ArrayList<>();
        String query = "SELECT category FROM Complaint ";

        try (Connection connection = getConnection()) {
            if (connection != null) {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    Complaint c = new Complaint(resultSet.getString("category"));
                    complaints.add(c);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return complaints;
    }

    public static List<Query> fetchAllQueries(String category) {
        List<Query> queries = new ArrayList<>();
        String query = "SELECT queryID, studentID,details, responseDate, responderID, response FROM Queries WHERE category =? ";

        try (Connection connection = getConnection()) {
            if (connection != null) {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, category);
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    int queryID = resultSet.getInt("queryID");
                    String studentID = resultSet.getString("studentID");
                    String details = resultSet.getString("details");
                    Date responseDate = resultSet.getDate("responseDate");
                    int responderID = resultSet.getInt("responderID");
                    String response = resultSet.getString("response");


                    Query q = new Query(queryID,studentID,details,responseDate,responderID,response);
                    queries.add(q);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return queries;
    }





}

