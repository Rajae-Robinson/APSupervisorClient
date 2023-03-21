package Sclient;

import java.util.Date;

public class Query_category {
    private int queryID;
    private String studentID;
    private String details;
    private Date responseDate ;
    private int responderID;
    private String response;

    public Query_category(int queryID, String studentID, String details,Date responseDate,int responderID,String response) {
        this.queryID = queryID;
        this.studentID = studentID;
        this.details = details;
        this.responseDate = responseDate;
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
}



