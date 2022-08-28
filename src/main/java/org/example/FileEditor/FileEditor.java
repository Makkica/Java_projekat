package org.example.FileEditor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileEditor extends JDialog {
    private JPanel contentPane;
    private JButton topButtonOpen;

    private JButton bottomButtonOpen;
    private JButton buttonClose;
    private JButton buttonSave;
    private JTextArea textArea;
    private JTextArea textArea1;
    private JButton topToBottomSelection;

    private JButton bottomToTopSelection;
    String directory; // The default directory to display in the FileDialog
    String selection;
    public FileEditor() {
        setContentPane(contentPane);
        setModal(true);
    //    getRootPane().setDefaultButton(buttonOpen);

        topButtonOpen.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {onTopButtonOpen();
            }
        });
        bottomButtonOpen.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {onBottomButtonOpen();
            }
        });
        buttonSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onButtonSave();
            }
        });
        buttonClose.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onButtonClose();
            }
        });
        topToBottomSelection.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {topToBottomSelection();}
        });
        bottomToTopSelection.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {bottomToTopSelection();}
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
            onButtonClose();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onButtonClose();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    public void saveFile(String directory, String filename) {
        if ((filename == null) || (filename.length() == 0))
            return;
        File file;
        FileWriter out = null;
        try {
            file = new File(directory, filename); // Create a file object
            out = new FileWriter(file); // And a char stream to write it
            textArea.getLineCount(); // Get text from the text area
            String s = textArea.getText() + textArea1.getText();  //save both top and bottom texts into one file
            out.write(s);


        }
        // Display messages if something goes wrong
        catch (IOException e) {
            textArea.setText(e.getClass().getName() + ": " + e.getMessage());
            this.setTitle("FileViewer: " + filename + ": I/O Exception");
        }
        // Always be sure to close the input stream!
        finally {
            try {
                if (out != null)
                    out.close();
            }
            catch (IOException e) {
            }
        }
    }

    public void loadAndDisplayFileinTopTextField(String directory, String filename) {
        if ((filename == null) || (filename.length() == 0))
            return;
        File file;
        FileReader in = null;
        // Read and display the file contents. Since we're reading text, we
        // use a FileReader instead of a FileInputStream.
        try {
            file = new File(directory, filename); // Create a file object
            in = new FileReader(file); // And a char stream to read it
            char[] buffer = new char[4096]; // Read 4K characters at a time
            int len; // How many chars read each time
            textArea.setText(""); // Clear the text area
            while ((len = in.read(buffer)) != -1) { // Read a batch of chars
                String s = new String(buffer, 0, len); // Convert to a string
                textArea.append(s); // And display them
            }
            this.setTitle("FileViewer: " + filename); // Set the window title
            textArea.setCaretPosition(0); // Go to start of file
        }
        // Display messages if something goes wrong
        catch (IOException e) {
            textArea.setText(e.getClass().getName() + ": " + e.getMessage());
            this.setTitle("FileViewer: " + filename + ": I/O Exception");
        }
        // Always be sure to close the input stream!
        finally {
            try {
                if (in != null)
                    in.close();
            } catch (IOException e) {
            }
        }
    }

    public void loadAndDisplayFileinBottomTextField(String directory, String filename) {
        if ((filename == null) || (filename.length() == 0))
            return;
        File file;
        FileReader in = null;
        // Read and display the file contents. Since we're reading text, we
        // use a FileReader instead of a FileInputStream.
        try {
            file = new File(directory, filename); // Create a file object
            in = new FileReader(file); // And a char stream to read it
            char[] buffer = new char[4096]; // Read 4K characters at a time
            int len; // How many chars read each time
            textArea1.setText(""); // Clear the text area
            while ((len = in.read(buffer)) != -1) { // Read a batch of chars
                String s = new String(buffer, 0, len); // Convert to a string
                textArea1.append(s); // And display them
            }
            this.setTitle("FileViewer: " + filename); // Set the window title
            textArea1.setCaretPosition(0); // Go to start of file
        }
        // Display messages if something goes wrong
        catch (IOException e) {
            textArea1.setText(e.getClass().getName() + ": " + e.getMessage());
            this.setTitle("FileViewer: " + filename + ": I/O Exception");
        }
        // Always be sure to close the input stream!
        finally {
            try {
                if (in != null)
                    in.close();
            } catch (IOException e) {
            }
        }
    }

    private void onTopButtonOpen() {
        // Create a file dialog box to prompt for a new file to display
        FileDialog f = new FileDialog(this, "Otvori fajl", FileDialog.LOAD);
        f.setDirectory(directory); // Set the default directory
        // Display the dialog and wait for the user's response
        f.setVisible(true);
        directory = f.getDirectory(); // Remember new default directory
        loadAndDisplayFileinTopTextField(directory, f.getFile()); // Load and display selection
        f.dispose(); // Get rid of the dialog box
    }

    private void onBottomButtonOpen() {
        // Create a file dialog box to prompt for a new file to display
        FileDialog f = new FileDialog(this, "Otvori fajl", FileDialog.LOAD);
        f.setDirectory(directory); // Set the default directory
        // Display the dialog and wait for the user's response
        f.setVisible(true);
        directory = f.getDirectory(); // Remember new default directory
        loadAndDisplayFileinBottomTextField(directory, f.getFile()); // Load and display selection
        f.dispose(); // Get rid of the dialog box
    }

    private void onButtonSave() {
        // Create a file dialog box to prompt for a new file to display
        FileDialog f = new FileDialog(this, "Otvori fajl", FileDialog.SAVE);
        f.setDirectory(directory); // Set the default directory
        // Display the dialog and wait for the user's response
        f.setVisible(true);
        directory = f.getDirectory(); // Remember new default directory
        saveFile(directory, f.getFile()); // Load and display selection
        f.dispose(); // Get rid of the dialog box
    }

    private void onButtonClose() {
        // add your code here if necessary
        dispose();
    }

    private void onButtonGetSelection()
    {
        selection = textArea.getSelectedText();
    }
    //adding logic for copying selected text from top text field to bottom text field
    private void topToBottomSelection(){
        textArea1.setText(textArea1.getText()+textArea.getSelectedText());
    }

    //adding logic for copying selected text from bottom text field to top text field
    private void bottomToTopSelection(){
        textArea.setText(textArea.getText()+textArea1.getSelectedText());
    }

    public static void main(String[] args) {
        FileEditor dialog = new FileEditor();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }

}
