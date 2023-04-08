/**
 * Author: Odane Walters
 */
package view;


import controller.Client;
import model.Advisor;
import model.Complaint;
import Sclient.DatabaseConnection;
import model.Query;
import model.Student;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;


public class Dashboard extends JFrame {


    private final JButton[] menuButtons;
    private final JPanel[] panels;
    private final String supervisorID;
    private Client client;




    private final Border defaultBorder = BorderFactory.createMatteBorder(1, 0, 1, 0, new Color(46, 49, 49));
    private final Border yellowBorder = BorderFactory.createMatteBorder(5, 5, 5, 6, Color.BLACK);

    public Dashboard(String supervisorID) {
        this.supervisorID = supervisorID;
        this.client = new Client("localhost", 8888);
        setTitle("Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        JFrame.setDefaultLookAndFeelDecorated(true);
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            UIManager.put("InternalFrame.activeTitleBackground", Color.RED); // Change title color
            SwingUtilities.updateComponentTreeUI(this);
        } catch (Exception e) {
            e.printStackTrace();
        }


        // Create menu buttons
        menuButtons = new JButton[5];
        menuButtons[0] = new JButton("Services", new ImageIcon("src/admin.png"));//"C:\\Users\\odane\\Desktop\\AP\\admin.png"));
        menuButtons[1] = new JButton("Assignments", new ImageIcon("src/add.png"));//"C:\\Users\\odane\\Desktop\\AP\\add.png"));
        menuButtons[2] = new JButton("View Categories", new ImageIcon("src/view.png"));//"C:\\Users\\odane\\Desktop\\AP\\view.png"));
        menuButtons[3] = new JButton("View Student", new ImageIcon("src/folder.png"));//"C:\\Users\\odane\\Desktop\\AP\\folder.png"));
        menuButtons[4] = new JButton("Video Chat", new ImageIcon("src/setting.png"));//"C:\\Users\\odane\\Desktop\\AP\\setting.png"));

        int buttonWidth = 120;

        // Set button bounds
        menuButtons[0].setBounds(70, 180, buttonWidth, 20);
        menuButtons[1].setBounds(70, 180, buttonWidth, 20);
        menuButtons[2].setBounds(70, 180, buttonWidth, 20);
        menuButtons[3].setBounds(70, 180, buttonWidth, 20);
        menuButtons[4].setBounds(70, 180, buttonWidth, 20);

        JPanel centerPanel = new JPanel(new CardLayout());

        menuButtons[0].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int outstandingComplaints = client.fetchOutstandingComplaintsList().size();
                int resolvedComplaints = client.fetchResolvedComplaintsList().size();
                int outstandingQueries = client.fetchOutstandingQueriesList().size();
                int resolvedQueries = client.fetchResolvedQueriesList().size();


                updateServicesPanel(panels[0], outstandingQueries, resolvedQueries, outstandingComplaints, resolvedComplaints);
                ((CardLayout) centerPanel.getLayout()).show(centerPanel, menuButtons[0].getText());
            }
        });


        menuButtons[1].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Query> outstandingQueriesList = client.fetchOutstandingQueriesList();
                List<Complaint> outstandingComplaintsList = client.fetchOutstandingComplaintsList();

                updateAssignmentsPanel(panels[1], outstandingQueriesList, outstandingComplaintsList);
                ((CardLayout) centerPanel.getLayout()).show(centerPanel, menuButtons[1].getText());
            }
        });

        menuButtons[2].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                List<Query> QueriescategoryList = client.fetchQueriesCategoryList();
                List<Complaint> ComplaintscategoryList = client.fetchComplaintsCategoryList();

                updateAssignmentsPanel_new(panels[2], QueriescategoryList, ComplaintscategoryList);
                ((CardLayout) centerPanel.getLayout()).show(centerPanel, menuButtons[2].getText());
            }
        });

        menuButtons[3].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Student> students = client.fetchStudents();
                //List<Complaint> outstandingComplaintsList = client.fetchOutstandingComplaintsList();

                Allstudents(panels[3], students);
                ((CardLayout) centerPanel.getLayout()).show(centerPanel, menuButtons[3].getText());
            }
        });


        // Create panels
        panels = new JPanel[5];
        panels[0] = new JPanel(new BorderLayout());
        panels[1] = new JPanel(new BorderLayout());
        panels[2] = new JPanel(new BorderLayout());
        panels[3] = new JPanel(new BorderLayout());
        panels[4] = new JPanel(new BorderLayout());

        panels[0].setBorder(yellowBorder);
        panels[1].setBorder(yellowBorder);
        panels[2].setBorder(yellowBorder);
        panels[3].setBorder(yellowBorder);
        panels[4].setBorder(yellowBorder);
        // Add components to content pane
        JPanel contentPane = new JPanel(new BorderLayout());
        JPanel menuPanel = new JPanel(new GridLayout(5, 1));
        // JPanel centerPanel = new JPanel(new CardLayout());


        for (int i = 0; i < 5; i++) {
            JButton button = menuButtons[i];
            JPanel panel = panels[i];

            // Add action to button
            if (i == 1 || i == 2 || i == 3) {
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        ((CardLayout) centerPanel.getLayout()).show(centerPanel, button.getText());
                    }

                });
            }


            // Add components to menu and center panels
            menuPanel.add(button);
            centerPanel.add(panel, button.getText());

            // Set panel background color
            panel.setBackground(Color.WHITE);

            // Add content to panels
            JLabel label = new JLabel(button.getText());
            label.setFont(new Font("Arial", Font.BOLD, 24));
            label.setHorizontalAlignment(JLabel.CENTER);
            panel.add(label, BorderLayout.CENTER);
        }

        contentPane.add(menuPanel, BorderLayout.WEST);
        contentPane.add(centerPanel, BorderLayout.CENTER);

        setContentPane(contentPane);
        setVisible(true);
    }

    private void updateServicesPanel(JPanel panel, int outstandingQueries, int resolvedQueries,
                                     int outstandingComplaints, int resolvedComplaints) {
        panel.removeAll();

        JLabel titleLabel = new JLabel("Services");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);

        JLabel outstandingQueriesLabel = new JLabel("Outstanding Queries: " + outstandingQueries);
        outstandingQueriesLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        outstandingQueriesLabel.setHorizontalAlignment(JLabel.CENTER);

        JLabel resolvedQueriesLabel = new JLabel("Resolved Queries: " + resolvedQueries);
        resolvedQueriesLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        resolvedQueriesLabel.setHorizontalAlignment(JLabel.CENTER);

        JLabel outstandingComplaintsLabel = new JLabel("Outstanding Complaints: " + outstandingComplaints);
        outstandingComplaintsLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        outstandingComplaintsLabel.setHorizontalAlignment(JLabel.CENTER);

        JLabel resolvedComplaintsLabel = new JLabel("Resolved Complaints: " + resolvedComplaints);
        resolvedComplaintsLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        resolvedComplaintsLabel.setHorizontalAlignment(JLabel.CENTER);

        panel.setLayout(new GridLayout(5, 1));
        panel.add(titleLabel);
        panel.add(outstandingQueriesLabel);
        panel.add(resolvedQueriesLabel);
        panel.add(outstandingComplaintsLabel);
        panel.add(resolvedComplaintsLabel);

        panel.revalidate();
        panel.repaint();
    }

    private void updateAssignmentsPanel(JPanel panel, List<Query> outstandingQueriesList, List<Complaint> outstandingComplaintsList) {
        panel.removeAll();

        JTable outstandingQueriesTable = new JTable();
        DefaultTableModel outstandingQueriesTableModel = new DefaultTableModel(new Object[]{"Query ID", "Student ID", "Details"}, 0);


        for (Query query : outstandingQueriesList) {
            outstandingQueriesTableModel.addRow(new Object[]{query.getQueryID(), query.getStudentID(), query.getDetails()});
        }

        outstandingQueriesTable.setModel(outstandingQueriesTableModel);


        JTable outstandingComplaintsTable = new JTable();
        DefaultTableModel outstandingComplaintsTableModel = new DefaultTableModel(new Object[]{"Complaint ID", "Student ID", "Details"}, 0);

        for (Complaint complaint : outstandingComplaintsList) {
            outstandingComplaintsTableModel.addRow(new Object[]{complaint.getComplaintID(), complaint.getStudentID(), complaint.getDetails()});
        }

        outstandingComplaintsTable.setModel(outstandingComplaintsTableModel);

        JButton assignQueryAdvisorButton = new JButton("Assign Advisor");
        assignQueryAdvisorButton.addActionListener(e -> {
            int selectedRow = outstandingQueriesTable.getSelectedRow();
            if (selectedRow >= 0) {
                int queryID = (int) outstandingQueriesTableModel.getValueAt(selectedRow, 0);
                List<Advisor> advisors = client.fetchAdvisors();
                int advisorID = showAdvisorSelectionDialog(advisors);
                if (advisorID != -1) {
                    client.assignAdvisorToQuery(advisorID, queryID);
                }
            }
        });

// Add an "Assign Advisor" button to the complaints table
        JButton assignComplaintAdvisorButton = new JButton("Assign Advisor");
        assignComplaintAdvisorButton.addActionListener(e -> {
            int selectedRow = outstandingComplaintsTable.getSelectedRow();
            if (selectedRow >= 0) {
                int complaintID = (int) outstandingComplaintsTableModel.getValueAt(selectedRow, 0);
                List<Advisor> advisors = client.fetchAdvisors();
                int advisorID = showAdvisorSelectionDialog(advisors);
                if (advisorID != -1) {
                    client.assignAdvisorToComplaint(advisorID, complaintID);
                }
            }
        });

        // Queries table
        String[] queriesColumnNames = {"Query ID", "Student ID", "Details"};
        DefaultTableModel queriesTableModel = new DefaultTableModel(queriesColumnNames, 0);
        for (Query q : outstandingQueriesList) {
            queriesTableModel.addRow(new Object[]{q.getQueryID(), q.getStudentID(), q.getDetails()});
        }
        JTable queriesTable = new JTable(queriesTableModel);

        // Complaints table
        String[] complaintsColumnNames = {"Complaint ID", "Student ID", "Details"};
        DefaultTableModel complaintsTableModel = new DefaultTableModel(complaintsColumnNames, 0);
        for (Complaint c : outstandingComplaintsList) {
            complaintsTableModel.addRow(new Object[]{c.getComplaintID(), c.getStudentID(), c.getDetails()});
        }
        JTable complaintsTable = new JTable(complaintsTableModel);


        JLabel titleLabel = new JLabel("Assignments");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);


        // Layout
        panel.setLayout(new BorderLayout());
        panel.add(titleLabel, BorderLayout.NORTH);

        JPanel tablesPanel = new JPanel(new GridLayout(2, 1));
        JScrollPane queriesScrollPane = new JScrollPane(outstandingQueriesTable);
        JScrollPane complaintsScrollPane = new JScrollPane(outstandingComplaintsTable);

        JPanel queriesPanel = new JPanel(new BorderLayout());
        queriesPanel.add(queriesScrollPane, BorderLayout.CENTER);
        queriesPanel.add(assignQueryAdvisorButton, BorderLayout.SOUTH);

        JPanel complaintsPanel = new JPanel(new BorderLayout());
        complaintsPanel.add(complaintsScrollPane, BorderLayout.CENTER);
        complaintsPanel.add(assignComplaintAdvisorButton, BorderLayout.SOUTH);

        tablesPanel.add(queriesPanel);
        tablesPanel.add(complaintsPanel);

        panel.add(tablesPanel, BorderLayout.CENTER);

        panel.revalidate();
        panel.repaint();
    }

    private int showAdvisorSelectionDialog(List<Advisor> advisors) {
        JDialog dialog = new JDialog(this, "Select Advisor", true);
        dialog.setSize(300, 400);
        dialog.setLocationRelativeTo(this);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        DefaultListModel<String> model = new DefaultListModel<>();
        for (Advisor advisor : advisors) {
            model.addElement(advisor.getAdvisorID() + " - " + advisor.getFirstName() + " " + advisor.getLastName());
        }

        JList<String> advisorList = new JList<>(model);
        advisorList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> dialog.dispose());

        dialog.setLayout(new BorderLayout());
        dialog.add(new JScrollPane(advisorList), BorderLayout.CENTER);
        dialog.add(okButton, BorderLayout.SOUTH);
        dialog.setVisible(true);

        int selectedIndex = advisorList.getSelectedIndex();
        if (selectedIndex >= 0) {
            return advisors.get(selectedIndex).getAdvisorID();
        } else {
            return -1;
        }
    }

    private int showQueryDialog(List<Query> queries) {
        JDialog dialog = new JDialog(this, "QUERIES", true);
        dialog.setSize(500, 600);
        dialog.setLocationRelativeTo(this);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        DefaultListModel<String> model = new DefaultListModel<>();
        for (Query query : queries) {
            model.addElement(query.getQueryID() + " - " + query.getStudentID() + " " + query.getDetails());
        }

        JList<String> advisorList = new JList<>(model);
        advisorList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> dialog.dispose());

        dialog.setLayout(new BorderLayout());
        dialog.add(new JScrollPane(advisorList), BorderLayout.CENTER);
        dialog.add(okButton, BorderLayout.SOUTH);
        dialog.setVisible(true);

        return 0;
    }


    private int showComplaintDialog(List<Complaint> complaints) {
        JDialog dialog = new JDialog(this, "Complaints", true);
        dialog.setSize(500, 600);
        dialog.setLocationRelativeTo(this);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        DefaultListModel<String> model = new DefaultListModel<>();
        for (Complaint complaint : complaints) {
            model.addElement(complaint.getComplaintID() + " - " + complaint.getStudentID() + " " + complaint.getDetails());
        }

        JList<String> advisorList = new JList<>(model);
        advisorList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> dialog.dispose());

        dialog.setLayout(new BorderLayout());
        dialog.add(new JScrollPane(advisorList), BorderLayout.CENTER);
        dialog.add(okButton, BorderLayout.SOUTH);
        dialog.setVisible(true);

        return 0;
    }


    private void updateAssignmentsPanel_new(JPanel panel, List<Query> QueriescategoryList, List<Complaint> ComplaintscategoryList) {
        panel.removeAll();

        JTable outstandingQueriesTable = new JTable();
        DefaultTableModel outstandingQueriesTableModel = new DefaultTableModel(new Object[]{"Query Category"}, 0);
        Set<String> categoriesAdded = new HashSet<>();

        for (Query query : QueriescategoryList) {
            String category = query.getCategory();
            if (!categoriesAdded.contains(category)) {
                outstandingQueriesTableModel.addRow(new Object[]{category});
                categoriesAdded.add(category);
            }
        }

        outstandingQueriesTable.setModel(outstandingQueriesTableModel);

        JTable outstandingComplaintsTable = new JTable();
        DefaultTableModel outstandingComplaintsTableModel = new DefaultTableModel(new Object[]{"Complaint Categories"}, 0);
        categoriesAdded.clear();

        for (Complaint complaint : ComplaintscategoryList) {
            String category = complaint.getCategory();
            if (!categoriesAdded.contains(category)) {
                outstandingComplaintsTableModel.addRow(new Object[]{category});
                categoriesAdded.add(category);
            }
        }

        outstandingComplaintsTable.setModel(outstandingComplaintsTableModel);

        JButton assignQueryAdvisorButton = new JButton("View");
        assignQueryAdvisorButton.addActionListener(e -> {
            int selectedRow = outstandingQueriesTable.getSelectedRow();
            if (selectedRow >= 0) {
                String selectedCategory = (String) outstandingQueriesTableModel.getValueAt(selectedRow, 0); // Replace <categoryColumnIndex>

                if (selectedCategory != null) {
                    List<Query> queries = client.fetchAllQueries(selectedCategory);
                    queries.removeIf(query -> !Objects.equals(query.getCategory(), selectedCategory));
                    showQueryDialog(queries);
                }
            }
        });

        /*JButton assignComplaintAdvisorButton = new JButton("View");
        assignQueryAdvisorButton.addActionListener(e -> {
            int selectedRow = outstandingComplaintsTable.getSelectedRow();
            if (selectedRow >= 0) {
                String selectedCategory = (String) outstandingComplaintsTableModel.getValueAt(selectedRow, 0); // Replace <categoryColumnIndex>

                //List<Query> queries = DatabaseConnection.fetchAllQueries(categories);
                if (selectedCategory != null) {
                    List<Complaint> complaints = client.fetchComplaintsCategoryList();//fetchAllComplaints(selectedCategory) ;
                    showComplaintDialog(complaints);
                    //showQueryDialog(queries);
                }
            }


        });*/
        JButton assignComplaintAdvisorButton = new JButton("View");
        assignComplaintAdvisorButton.addActionListener(e -> {

            int selectedRow = outstandingComplaintsTable.getSelectedRow();
            if (selectedRow >= 0) {
                String selectedCategory = (String) outstandingComplaintsTableModel.getValueAt(selectedRow, 0);

                if (selectedCategory != null) {
                    List<Complaint> complaints = client.fetchAllComplaints(selectedCategory);
                    complaints.removeIf(complaint -> !Objects.equals(complaint.getCategory(), selectedCategory));
                    showComplaintDialog(complaints);
                }
            }
        });



        // Queries table
        String[] queriesColumnNames = {"Query Category"};
        DefaultTableModel queriesTableModel = new DefaultTableModel(queriesColumnNames, 0);
        for (Query q : QueriescategoryList) {
            queriesTableModel.addRow(new Object[]{q.getCategory()});
        }
        JTable queriesTable = new JTable(queriesTableModel);

        // Complaints table
        String[] complaintsColumnNames = {"Complaint Category"};
        DefaultTableModel complaintsTableModel = new DefaultTableModel(complaintsColumnNames, 0);
        for (Complaint c : ComplaintscategoryList) {
            complaintsTableModel.addRow(new Object[]{c.getCategory()});    //{c.getComplaintID(), c.getStudentID(), c.getDetails()});
        }
        JTable complaintsTable = new JTable(complaintsTableModel);


        JLabel titleLabel = new JLabel("View Categories");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);


        // Layout
        panel.setLayout(new BorderLayout());
        panel.add(titleLabel, BorderLayout.NORTH);

        JPanel tablesPanel = new JPanel(new GridLayout(2, 1));
        JScrollPane queriesScrollPane = new JScrollPane(outstandingQueriesTable);
        JScrollPane complaintsScrollPane = new JScrollPane(outstandingComplaintsTable);

        JPanel queriesPanel = new JPanel(new BorderLayout());
        queriesPanel.add(queriesScrollPane, BorderLayout.CENTER);
        queriesPanel.add(assignQueryAdvisorButton, BorderLayout.SOUTH);

        JPanel complaintsPanel = new JPanel(new BorderLayout());
        complaintsPanel.add(complaintsScrollPane, BorderLayout.CENTER);
        complaintsPanel.add(assignComplaintAdvisorButton, BorderLayout.SOUTH);

        tablesPanel.add(queriesPanel);
        tablesPanel.add(complaintsPanel);

        panel.add(tablesPanel, BorderLayout.CENTER);

        panel.revalidate();
        panel.repaint();
    }


    private void Allstudents(JPanel panel, List<Student> studentList) {
        panel.removeAll();
        // List<Student> students = client.fetchStudents();
        // Create table model
        DefaultTableModel tableModel = new DefaultTableModel(new Object[]{"Student ID", "First Name", "Last Name"}, 0);
        for (Student student : studentList) {
            tableModel.addRow(new Object[]{student.getStudentID(), student.getFirstName(), student.getLastName()});
        }

        // Create table
        JTable table = new JTable(tableModel);

        // Create search bar
        JTextField searchField = new JTextField(20);
        JButton searchButton = new JButton("Search");
        JPanel searchPanel = new JPanel(new FlowLayout());
        searchPanel.add(new JLabel("Search by Student ID: "));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        // Create select button
        JButton selectButton = new JButton("Select");
        selectButton.setEnabled(false);  // Disable button until a row is selected
        selectButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                int studentID = (int) tableModel.getValueAt(selectedRow, 0);
                List<Query> queries = client.fetchQueries(studentID);
                List<Complaint> complaints = client.fetchComplaints(studentID);

                // Create table model for queries
                DefaultTableModel queryTableModel = new DefaultTableModel(new Object[]{"Query ID", "Category", "Details"}, 0);
                for (Query query : queries) {
                    if (query.getStudentID() == studentID) {
                        queryTableModel.addRow(new Object[]{query.getQueryID(), query.getCategory(), query.getDetails()});
                    }
                }

                // Create table model for complaints
                DefaultTableModel complaintTableModel = new DefaultTableModel(new Object[]{"Complaint ID", "Category", "Details"}, 0);
                for (Complaint complaint : complaints) {
                    if (complaint.getStudentID() == studentID) {
                        complaintTableModel.addRow(new Object[]{complaint.getComplaintID(), complaint.getCategory(), complaint.getDetails()});
                    }
                }

                // Create table for queries
                JTable queryTable = new JTable(queryTableModel);

                // Create table for complaints
                JTable complaintTable = new JTable(complaintTableModel);

                // Create tabs for queries and complaints
                JTabbedPane tabbedPane = new JTabbedPane();
                tabbedPane.addTab("Queries", new JScrollPane(queryTable));
                tabbedPane.addTab("Complaints", new JScrollPane(complaintTable));

                // Show the tabbed pane in a dialog box
                JOptionPane.showMessageDialog(panel, tabbedPane, "Queries and Complaints for Student " + studentID, JOptionPane.PLAIN_MESSAGE);
            }
        });

        // Add listeners for search field and table selection
        searchField.addActionListener(e -> searchTable(table, tableModel, searchField.getText()));
        searchButton.addActionListener(e -> searchTable(table, tableModel, searchField.getText()));
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                boolean rowSelected = table.getSelectedRow() >= 0;
                selectButton.setEnabled(rowSelected);
            }
        });

        // Create table panel
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.add(new JScrollPane(table), BorderLayout.CENTER);
        tablePanel.add(selectButton, BorderLayout.SOUTH);

        // Add components to main panel
        panel.setLayout(new BorderLayout());
        panel.add(searchPanel, BorderLayout.NORTH);
        panel.add(tablePanel, BorderLayout.CENTER);

        panel.revalidate();
        panel.repaint();
    }

    private void searchTable(JTable table, DefaultTableModel tableModel, String searchText) {
        for (int row = 0; row < table.getRowCount(); row++) {
            int studentID = (int) tableModel.getValueAt(row, 0);
            if (String.valueOf(studentID).contains(searchText)) {
                table.getSelectionModel().setSelectionInterval(row, row);
                return;
            }
        }
        // If search text not found, clear selection
        table.getSelectionModel().clearSelection();
    }




    public static void main(String[] args) {
        new Dashboard("supervisor1");
    }
}
