package controller;
import model.Advisor;
import model.Complaint;
import model.Query;

import view.AdvisorSelectionDialog;
import view.Supervisor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


import java.util.Collections;
import java.util.List;

public class Client {
    private final String serverAddress;
    private final int serverPort;

    public Client(String serverAddress, int serverPort) {
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
    }

    private Object performAction(String action, Object... args) {
        try (Socket socket = new Socket(serverAddress, serverPort);
             ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream input = new ObjectInputStream(socket.getInputStream())) {

            output.writeObject(action);

            for (Object arg : args) {
                output.writeObject(arg);
            }

            return input.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }


        public List<model.Query> fetchOutstandingQueriesList() {
            List<Query> outstandingQueriesList = (List<Query>) performAction("getOutstandingQueriesList");
            return outstandingQueriesList != null ? outstandingQueriesList : Collections.emptyList();
        }
        
        public List<model.Query> fetchResolvedQueriesList() {
            List<Query> resolvedQueriesList = (List<Query>) performAction("getResolvedQueriesList");
            return resolvedQueriesList != null ? resolvedQueriesList : Collections.emptyList();
        }

        public List<Complaint> fetchOutstandingComplaintsList() {
            List<Complaint> outstandingComplaintsList = (List<Complaint>) performAction("getOutstandingComplaintsList");
            return outstandingComplaintsList != null ? outstandingComplaintsList : Collections.emptyList();
        }
        
        public List<Complaint> fetchResolvedComplaintsList() {
            List<Complaint> resolvedComplaintsList = (List<Complaint>) performAction("getResolvedComplaintsList");
            return resolvedComplaintsList != null ? resolvedComplaintsList : Collections.emptyList();
        }

        public List<model.Query> fetchQueriesCategoryList() {
            List<Query> queriesCategoryList = (List<Query>) performAction("getQueriesCategoryList");
            return queriesCategoryList != null ? queriesCategoryList : Collections.emptyList();
        }

        public List<Complaint> fetchComplaintsCategoryList() {
            List<Complaint> complaintsCategoryList = (List<Complaint>) performAction("getComplaintsCategoryList");
            return complaintsCategoryList != null ? complaintsCategoryList : Collections.emptyList();
        }

        public List<model.Query> fetchAllQueries(String category) {
            return (List<Query>) performAction("getQueries", category);
        }

        public List<Advisor> fetchAdvisors() {
            return (List<Advisor>) performAction("getAdvisors");
        }

        public int showAdvisorSelectionDialog(List<Advisor> advisors) {
            AdvisorSelectionDialog dialog = new AdvisorSelectionDialog(null, advisors);
            dialog.setVisible(true);
            Advisor selectedAdvisor = dialog.getSelectedAdvisor();
            return selectedAdvisor != null ? selectedAdvisor.getAdvisorID() : -1;
        }

        public void assignAdvisorToComplaint(int advisorID, int complaintID) {
            performAction("assignAdvisorToComplaint", advisorID, complaintID);
        }

        public void assignAdvisorToQuery(int advisorID, int queryID) {
            performAction("assignAdvisorToQuery", advisorID, queryID);
        }


    }


