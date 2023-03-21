package Sclient;


import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.List;


public class Dashboard extends JFrame {


    private final JButton[] menuButtons;
    private final JPanel[] panels;
    private final String supervisorID;

    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;


    private final Border defaultBorder = BorderFactory.createMatteBorder(1, 0, 1, 0, new Color(46, 49, 49));
    private final Border yellowBorder = BorderFactory.createMatteBorder(5, 5, 5, 6, Color.BLACK);

    public Dashboard(String supervisorID) {
        this.supervisorID = supervisorID;
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
        menuButtons[0] = new JButton("Services", new ImageIcon("C:\\Users\\odane\\Desktop\\AP\\admin.png"));
        menuButtons[1] = new JButton("Assignments", new ImageIcon("C:\\Users\\odane\\Desktop\\AP\\add.png"));
        menuButtons[2] = new JButton("View Categories", new ImageIcon("C:\\Users\\odane\\Desktop\\AP\\view.png"));
        menuButtons[3] = new JButton("View Student", new ImageIcon("C:\\Users\\odane\\Desktop\\AP\\folder.png"));
        menuButtons[4] = new JButton("Video Chat", new ImageIcon("C:\\Users\\odane\\Desktop\\AP\\setting.png"));

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
                int outstandingQueries = DatabaseConnection.fetchOutstandingQueries();
                int resolvedQueries = DatabaseConnection.fetchResolvedQueries();
                int outstandingComplaints = DatabaseConnection.fetchOutstandingComplaints();
                int resolvedComplaints = DatabaseConnection.fetchResolvedComplaints();

                updateServicesPanel(panels[0], outstandingQueries, resolvedQueries, outstandingComplaints, resolvedComplaints);
                ((CardLayout) centerPanel.getLayout()).show(centerPanel, menuButtons[0].getText());
            }
        });


        menuButtons[1].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Query> outstandingQueriesList = DatabaseConnection.fetchOutstandingQueriesList();
                List<Complaint> outstandingComplaintsList = DatabaseConnection.fetchOutstandingComplaintsList();

                updateAssignmentsPanel(panels[1], outstandingQueriesList, outstandingComplaintsList);
                ((CardLayout) centerPanel.getLayout()).show(centerPanel, menuButtons[1].getText());
            }
        });

        menuButtons[2].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Query> QueriescategoryList = DatabaseConnection.fetchQueriescategoryList();
                List<Complaint> ComplaintscategoryList = DatabaseConnection.fetchComplaintscategoryList();

                updateAssignmentsPanel_new(panels[2], QueriescategoryList, ComplaintscategoryList);
                ((CardLayout) centerPanel.getLayout()).show(centerPanel, menuButtons[2].getText());
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
                List<Advisor> advisors = DatabaseConnection.fetchAdvisors();
                String advisorID = showAdvisorSelectionDialog(advisors);
                if (advisorID != null) {
                    DatabaseConnection.assignAdvisorToQuery(advisorID, queryID);
                }
            }
        });

// Add an "Assign Advisor" button to the complaints table
        JButton assignComplaintAdvisorButton = new JButton("Assign Advisor");
        assignComplaintAdvisorButton.addActionListener(e -> {
            int selectedRow = outstandingComplaintsTable.getSelectedRow();
            if (selectedRow >= 0) {
                int complaintID = (int) outstandingComplaintsTableModel.getValueAt(selectedRow, 0);
                List<Advisor> advisors = DatabaseConnection.fetchAdvisors();
                String advisorID = showAdvisorSelectionDialog(advisors);
                if (advisorID != null) {
                    DatabaseConnection.assignAdvisorToComplaint(advisorID, complaintID);
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

    private String showAdvisorSelectionDialog(List<Advisor> advisors) {
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
            return null;
        }
    }

    private int showQueryDialog(List<Query> queries) {
        JDialog dialog = new JDialog(this, "QUERIES", true);
        dialog.setSize(500, 600);
        dialog.setLocationRelativeTo(this);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        DefaultListModel<String> model = new DefaultListModel<>();
        for (Query query : queries) {
            model.addElement(query.getQueryID() + " - " + query.getStudentID() + " " + query.getDetails() + " " + query.getResponseDate() + " "
                    + query.getResponderID() + " " + query.getResponse());
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


    private void updateSettingsPanel(JPanel panel) {
        panel.removeAll();

        JLabel titleLabel = new JLabel("Settings");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);

        JButton liveChatButton = new JButton("Start Live Video Chat");
        liveChatButton.addActionListener(e -> {
            // List<Student> students = DatabaseConnection.fetchStudents();
            //  showStudentSelectionDialog(students);
        });


        panel.setLayout(new GridLayout(3, 1));
        panel.add(titleLabel);
        panel.add(liveChatButton);


        panel.revalidate();
        panel.repaint();
    }

    private void updateAssignmentsPanel_new(JPanel panel, List<Query> QueriescategoryList, List<Complaint> ComplaintscategoryList) {
        panel.removeAll();

        JTable outstandingQueriesTable = new JTable();
        DefaultTableModel outstandingQueriesTableModel = new DefaultTableModel(new Object[]{"Category"}, 0);

        for (Query query : QueriescategoryList) {
            outstandingQueriesTableModel.addRow(new Object[]{query.getCategory()});
        }

        outstandingQueriesTable.setModel(outstandingQueriesTableModel);


        JTable outstandingComplaintsTable = new JTable();
        DefaultTableModel outstandingComplaintsTableModel = new DefaultTableModel(new Object[]{"Categories"}, 0);

        for (Complaint complaint : ComplaintscategoryList) {
            outstandingComplaintsTableModel.addRow(new Object[]{complaint.getCategory()});
        }

        outstandingComplaintsTable.setModel(outstandingComplaintsTableModel);

        JButton assignQueryAdvisorButton = new JButton("View");
        assignQueryAdvisorButton.addActionListener(e -> {
            int selectedRow = outstandingQueriesTable.getSelectedRow();
            if (selectedRow >= 0) {
                String selectedCategory = (String) outstandingQueriesTableModel.getValueAt(selectedRow, 0); // Replace <categoryColumnIndex>

                //List<Query> queries = DatabaseConnection.fetchAllQueries(categories);
                if (selectedCategory != null) {
                    List<Query> queries = DatabaseConnection.fetchAllQueries(selectedCategory);
                    showQueryDialog(queries);
                    //showQueryDialog(queries);
                }
            }


        });

// Add an "Assign Advisor" button to the complaints table
        JButton assignComplaintAdvisorButton = new JButton("View");
        assignComplaintAdvisorButton.addActionListener(e -> {
            int selectedRow = outstandingComplaintsTable.getSelectedRow();
            if (selectedRow >= 0) {
                int complaintID = (int) outstandingComplaintsTableModel.getValueAt(selectedRow, 0);
                List<Advisor> advisors = DatabaseConnection.fetchAdvisors();
                String advisorID = showAdvisorSelectionDialog(advisors);
                if (advisorID != null) {
                    DatabaseConnection.assignAdvisorToComplaint(advisorID, complaintID);
                }
            }
        });

        // Queries table
        String[] queriesColumnNames = {"Category"};
        DefaultTableModel queriesTableModel = new DefaultTableModel(queriesColumnNames, 0);
        for (Query q : QueriescategoryList) {
            queriesTableModel.addRow(new Object[]{q.getCategory()});
        }
        JTable queriesTable = new JTable(queriesTableModel);

        // Complaints table
        String[] complaintsColumnNames = {"Category"};
        DefaultTableModel complaintsTableModel = new DefaultTableModel(complaintsColumnNames, 0);
        for (Complaint c : ComplaintscategoryList) {
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





    public static void main(String[] args) {
        new Dashboard("supervisor1");
    }
}
