/**
 * Author: Odane Walters
 */
package view;



import Sclient.DatabaseConnection;
import controller.Client;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;


public class Supervisor {


    private JFrame frame;
    private JPanel panel;
    private JCheckBox checkBoxShowPass;
    private JPasswordField passwordField;
    private JTextField textFieldUsername;
    private Client client;

    public Supervisor() {
        client = new Client("localhost", 8888);
        createLoginForm();
    }


        private void createLoginForm() {
        frame = new JFrame("Login Form");
        panel = new JPanel();
        checkBoxShowPass = new JCheckBox("Show Password");
        passwordField = new JPasswordField();
        textFieldUsername = new JTextField();
        JLabel jLabel_close = new JLabel();
        JLabel jLabel_user = new JLabel("ID:");
        JLabel jLabel_pass = new JLabel("Password");
        jLabel_pass.setPreferredSize(new Dimension(200, 50));
        JLabel jLabel_showPass = new JLabel();
        JLabel jLabel_up = new JLabel();
        JLabel jLabel_title = new JLabel("Login Form");




        // add the login button to the panel
        JButton loginButton1 = new JButton("Login");


// position the login button below the show password checkbox
        loginButton1.setBounds(70, 180, 80, 20);


        // set layout
        panel.setLayout(null);

        // add components to the panel
        panel.add(jLabel_close);
        panel.add(jLabel_user);
        panel.add(jLabel_pass);
        panel.add(jLabel_showPass);
        panel.add(loginButton1);
        panel.add(jLabel_up);
        panel.add(jLabel_title);
        panel.add(checkBoxShowPass);
        panel.add(passwordField);
        panel.add(textFieldUsername);

        // display the login form in the center of the screen
        frame.setLocationRelativeTo(null);

        // set icons


            // jLabel_user.setIcon(new ImageIcon(getClass().getResource("admin.png")));
        // jLabel_pass.setIcon(new ImageIcon(getClass().getResource("../IMAGES/lock.png")));
        //jLabel_showPass.setIcon(new ImageIcon(getClass().getResource("../IMAGES/eye.png")));
        //jLabel_up.setIcon(new ImageIcon(getClass().getResource("../IMAGES/up.png")));

        // set borders
        Border panelBorder = BorderFactory.createMatteBorder(5, 5, 5, 5, Color.black);
        panel.setBorder(panelBorder);

        Border titleBorder = BorderFactory.createMatteBorder(4, 4, 4, 4, Color.gray);
        jLabel_title.setBorder(titleBorder);

        // add the panel to the frame and set the frame properties
        frame.add(panel);
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // position the components
        jLabel_close.setBounds(370, 10, 20, 20);
        jLabel_title.setBounds(30, 30, 120, 30);
        jLabel_user.setBounds(40, 80, 50, 20);
        textFieldUsername.setBounds(70, 80, 160, 20);
        jLabel_pass.setBounds(40, 120, 80, 20);
        passwordField.setBounds(100, 120, 160, 20);
        checkBoxShowPass.setBounds(70, 150, 120, 20);
        jLabel_showPass.setBounds(240, 120, 20, 20);
        jLabel_up.setBounds(240, 80, 20, 20);

        // add action listener to show/hide password checkbox
        checkBoxShowPass.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                togglePasswordFieldVisibility(checkBoxShowPass, passwordField);
            }
        });


        loginButton1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // handle the login button click event
                String userID = textFieldUsername.getText().trim();
                String userPass = new String(passwordField.getPassword());

                // validate the username and password
                if (client.authenticate(userID, userPass)) {
                    JOptionPane.showMessageDialog(frame, "Login Successful!");
                    //Dashboard dashboard = new Dashboard();
                    String supervisorID = userID;
                    Dashboard dashboard = new Dashboard(supervisorID);
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid Username or Password");
                }
            }
        });


    }

    private void togglePasswordFieldVisibility(JCheckBox checkBox, JPasswordField passwordField) {
        char echoChar = checkBox.isSelected() ? (char) 0 : '*';
        passwordField.setEchoChar(echoChar);
    }








    // Main method for testing
    public static void main(String[] args) {
        Supervisor supervisor = new Supervisor();
    }
}
