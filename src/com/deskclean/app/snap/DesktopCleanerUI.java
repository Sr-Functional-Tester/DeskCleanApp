package com.deskclean.app.snap;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;



public class DesktopCleanerUI{
    public static void main(String[] args) {
        // Create frame
        JFrame frame = new JFrame("Desktop cleaner and backup");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);  // Set frame size
      //  frame.set
       
        

        // Create a panel to display the background image
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Load and draw the background image
                ImageIcon backgroundImage = new ImageIcon(getClass().getResource("/background.jpg"));  // Path to image
                Image img = backgroundImage.getImage();
                g.drawImage(img, 0, 0, getWidth(), getHeight(), this);  // Stretch image to fill the entire panel
            }
        };

        // Set the panel as the content pane and use null layout for absolute positioning of components
        frame.setContentPane(panel);
        panel.setLayout(null);  // Use null layout to manually position components

        ImageIcon icon = new ImageIcon(DesktopCleanerUI.class.getResource("/3tasks.png"));
        frame.setIconImage(icon.getImage());
        // Make the panel background transparent
        panel.setOpaque(false);
       
//        panel.setMaximumSize(new Dimension(400,200));
//        panel.setSize(new Dimension(400,200));
//        panel.setAutoscrolls(false);

        // Create checkboxes
        JCheckBox checkbox1 = new JCheckBox("desktop");
        checkbox1.setToolTipText("<html>Select this option to clear the desktop.<br>Backup files to the specified path.</html>");
        JCheckBox checkbox2 = new JCheckBox("folder");
        checkbox2.setToolTipText("<html>Select this option to back up a folder.<br>Choose a folder to back up.</html>");
        JCheckBox checkbox3 = new JCheckBox("organized");
        checkbox3.setToolTipText("<html>Select this option to organize your files.<br>Choose folder to backup (default is desktop folder)</html>");
        // Set checkbox positions using setBounds(x, y, width, height)
        checkbox1.setBounds(50, 50, 89, 20);
        checkbox2.setBounds(160, 50, 80, 20);
        checkbox3.setBounds(250, 50, 89, 20);

        // Set transparency for checkboxes and change text color for visibility
        checkbox1.setOpaque(false);
        checkbox2.setOpaque(false);
        checkbox3.setOpaque(false);
        checkbox1.setForeground(Color.GREEN);  // Set checkbox text color to white
        checkbox2.setForeground(Color.GREEN);
        checkbox3.setForeground(Color.GREEN);

        // Add checkboxes to the panel
        panel.add(checkbox1);
        panel.add(checkbox2);
        panel.add(checkbox3);

        // Create a text field (initially disabled)
        JTextField textField = new JTextField(15);
        textField.setEnabled(false);
        textField.setText("Enter folder path. eg: c:\\documents");
        textField.setForeground(Color.WHITE); // Set text color to white
        textField.setBounds(70, 80, 250, 25); // Position the text field

        // Browse button
        JButton browseButton = new JButton("Browse");
        browseButton.setBounds(321, 80, 78, 25);
        frame.add(browseButton);

//        // Action listener for button
//        browseButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                JFileChooser fileChooser = new JFileChooser();
//                int result = fileChooser.showOpenDialog(null);
//
//                if (result == JFileChooser.APPROVE_OPTION) {
//                    File selectedFile = fileChooser.getSelectedFile();
//                    textField.setText(selectedFile.getAbsolutePath());
//                }
//            }
//        });
//        
        
        // Action listener for folder selection
        browseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser folderChooser = new JFileChooser();
                folderChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); // 👈 important part
                folderChooser.setAcceptAllFileFilterUsed(false); // optional, disables "All files" filter

                int result = folderChooser.showOpenDialog(null);

                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFolder = folderChooser.getSelectedFile();
                    textField.setText(selectedFolder.getAbsolutePath());
                }
            }
        });
        
        
        
        
        
        
        
        
        
        // Make the text field background transparent and set border
        textField.setOpaque(false);
        textField.setBorder(BorderFactory.createLineBorder(Color.WHITE)); // Add a white border

        // Add the text field to the panel
        panel.add(textField);

        // Create a submit button (initially disabled)
        JButton submitButton = new JButton("Submit");
        submitButton.setEnabled(false); // Initially disable the submit button
        submitButton.setBounds(145, 110, 100, 25); // Position the submit button

        // Make the submit button background transparent, add border, and change text color
        submitButton.setOpaque(false);
        submitButton.setContentAreaFilled(false);
        submitButton.setBorderPainted(true);
        submitButton.setBorder(BorderFactory.createLineBorder(Color.WHITE)); // Add a white border
        submitButton.setForeground(Color.WHITE); // Set button text color to white

        // Add the submit button to the panel
        panel.add(submitButton);

        // Group checkboxes to allow only one selection
        checkbox1.addActionListener(e -> {
            checkbox2.setSelected(false);
            checkbox3.setSelected(false);
            textField.setEnabled(true);
            textField.setText("Enter backup folder path. eg: c:\\documents");
            textField.setForeground(Color.WHITE);  // Ensure text is white
            submitButton.setEnabled(true); // Enable the submit button when option 1 is selected
        });

        checkbox2.addActionListener(e -> {
            checkbox1.setSelected(false);
            checkbox3.setSelected(false);
            textField.setEnabled(true);
            textField.setText("Enter folder path. eg: c:\\documents");
            textField.setForeground(Color.WHITE);  // Ensure text is white
            submitButton.setEnabled(false); // Disable submit button until data is entered
        });

        checkbox3.addActionListener(e -> {
            checkbox1.setSelected(false);
            checkbox2.setSelected(false);
            textField.setEnabled(true);
            textField.setText("Enter folder path. eg: c:\\documents");
            textField.setForeground(Color.WHITE);  // Ensure text is white
            submitButton.setEnabled(true); // Disable submit button until data is entered
        });

        // Add focus listener to remove the default message when user clicks into the text field
        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textField.getText().contains("Enter")) {
                    textField.setText("");
                    textField.setForeground(Color.WHITE); // Ensure text is white when user starts typing
                }
            }
        });
        
     // Add mouse listener to the text field
        textField.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                // If the text field contains placeholder text, clear it when clicked
                if (textField.getText().contains("Enter")) {
                    textField.setText(""); // Clear the text field
                    textField.setForeground(Color.WHITE); // Set text color to white
                }
            }
        });

        
     // Create a MouseListener to detect clicks on the panel
        panel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                // Get the position of the mouse click
                Point clickPoint = e.getPoint();

                // Check if the click was not on the checkboxes or the submit button
                if (textField.getText().isEmpty() && !checkbox1.getBounds().contains(clickPoint) &&
                    !checkbox2.getBounds().contains(clickPoint) &&
                    !checkbox3.getBounds().contains(clickPoint) &&
                    !textField.getBounds().contains(clickPoint) &&
                    !submitButton.getBounds().contains(clickPoint)) {
                    
                    // Trigger event (e.g., set text in the text field)
                	if(checkbox1.isSelected())
                		textField.setText("Enter backup folder path. eg: c:\\documents");
                	else if(checkbox2.isSelected())
                		textField.setText("Enter folder path. eg: c:\\documents");
                	else
                		textField.setText("Enter folder path. eg: c:\\documents");
                    textField.setForeground(Color.WHITE); // Ensure the text color is white
                }
            }
        });


        // Add key listener to remove the default message when the user starts typing
        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (textField.getText().contains("Enter")) {
                    textField.setText("");
                    textField.setForeground(Color.WHITE); // Ensure text is white when user starts typing
                    submitButton.setForeground(Color.GREEN); 
                   
                }
            }
        });

        // Enable the submit button when the user enters data
        textField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                enableSubmitButton();
            }

            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                enableSubmitButton();
            }

            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                enableSubmitButton();
            }

            private void enableSubmitButton() {
                if (textField.getText().length() > 0) {
                    submitButton.setEnabled(true); // Enable submit button when text is entered
                    submitButton.setForeground(Color.GREEN); 
                } else {
                    submitButton.setEnabled(false); // Keep it disabled if no text is entered
                }
            }
        });

        // Add action listener to submit button
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedOption = "";
                if (checkbox1.isSelected()) {
                    try {
                    	if(textField.getText().contains("Enter"))
                    		   textField.setText("");
                      String status = ClearDesktopBackupTask.clearDesktopAndBackup("clear desktop to path "+ textField.getText(), 1);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                } else if (checkbox2.isSelected()) {
                    try {
                        String status = ClearDesktopBackupTask.clearDesktopAndBackup("desktop backup to path " + textField.getText(), 0);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                } else if (checkbox3.isSelected()) {
                	if(textField.getText().contains("Enter"))
             		   textField.setText("");
                    FileOrganizer.organizedBackup("organized backup to path " + textField.getText());
                }
                // Print the result to the console
                System.out.println("Selected: " + selectedOption);
            }
        });

        // Make the frame visible
        frame.setVisible(true);
    }
}
