package Sclient;


import java.util.Date;

public class Query {
    private int queryID;
    private String studentID;
    private String details;
    private String category;
    private Date responseDate;
    private int responderID;
    private String response;

    public Query(int queryID, String studentID, String details) {
        this.queryID = queryID;
        this.studentID = studentID;
        this.details = details;

    }

    public Query(String category) {
        this.category= category;
    }

    public Query(int queryID,String studentID, String details,Date responseDate, int responderID,String response ){
        this.queryID= queryID;
        this.studentID = studentID;
        this.details = details;
        this.responseDate= responseDate;
        this.responderID = responderID;
        this.response = response;
    }

    public int getQueryID() {
        return queryID;
    }

    public String getStudentID() {
        return studentID;
    }

    public String getDetails() {
        return details;
    }

    public String getCategory() {
        return category;
    }

    public Date getResponseDate() {
        return responseDate;
    }

    public int getResponderID(){return responderID;}

    public String getResponse(){return response;}
}





