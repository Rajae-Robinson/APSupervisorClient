package view;

import model.Advisor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AdvisorSelectionDialog extends JDialog {
    private JComboBox<Advisor> advisorComboBox;
    private JButton okButton;
    private Advisor selectedAdvisor;

    public AdvisorSelectionDialog(Frame owner, List<Advisor> advisors) {
        super(owner, "Select Advisor", true);

        advisorComboBox = new JComboBox<>();
        for (Advisor advisor : advisors) {
            advisorComboBox.addItem(advisor);
        }

        okButton = new JButton("OK");
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onOk();
            }
        });

        setLayout(new BorderLayout());
        add(advisorComboBox, BorderLayout.CENTER);
        add(okButton, BorderLayout.SOUTH);
        pack();
        setLocationRelativeTo(owner);
    }

    private void onOk() {
        selectedAdvisor = (Advisor) advisorComboBox.getSelectedItem();
        setVisible(false);
    }

    public Advisor getSelectedAdvisor() {
        return selectedAdvisor;
    }
}

