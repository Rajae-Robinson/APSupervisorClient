package model;


import java.io.Serializable;
import java.util.Date;

public class Query implements Serializable {
	private static final long serialVersionUID = 8485189943949795110L;
	
    private int queryID;
    private int studentID;
    private String details;
    private String category;
    private Date responseDate;
    private Integer responderID;
    private String response;

    public Query(int queryID, int studentID, String details) {
        this.queryID = queryID;
        this.studentID = studentID;
        this.details = details;

    }

    public Query(String category) {
        this.category= category;
    }

    public Query(int queryID, int studentID, String details,Date responseDate, int responderID,String response ){
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

    public int getStudentID() {
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

    public Integer getResponderID(){return responderID;}

    public String getResponse(){return response;}
}





