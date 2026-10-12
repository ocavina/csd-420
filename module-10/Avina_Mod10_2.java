// Oscar Avina
// 10/11/2026
// Module 10.2 Assignment (Late submission)
// This program displays and updates fan records in a MySQL database and tests the interface.

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.sql.*;
import javax.swing.*;

public class Avina_Mod10_2 extends JFrame {
    // creates the fields, buttons, and status message for the fan information
    private JTextField idField = new JTextField(20);
    private JTextField firstNameField = new JTextField(20);
    private JTextField lastNameField = new JTextField(20);
    private JTextField favoriteTeamField = new JTextField(20);
    private JButton displayButton = new JButton("Display");
    private JButton updateButton = new JButton("Update");
    private JLabel statusLabel = new JLabel("Enter an ID and click Display.");

    // stores the displayed fan's ID, or null when no record has been loaded
    private Integer displayedId = null;

    public Avina_Mod10_2() {
        super("Fan Information");

        // places the labels and fields in rows with the two buttons at the bottom
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        formPanel.add(new JLabel("ID:"));
        formPanel.add(idField);
        formPanel.add(new JLabel("First name:"));
        formPanel.add(firstNameField);
        formPanel.add(new JLabel("Last name:"));
        formPanel.add(lastNameField);
        formPanel.add(new JLabel("Favorite team:"));
        formPanel.add(favoriteTeamField);
        formPanel.add(displayButton);
        formPanel.add(updateButton);

        // places the status message below the fields and buttons
        add(formPanel, BorderLayout.CENTER);
        add(statusLabel, BorderLayout.SOUTH);

        // displays or update a fan when a button is clicked
        displayButton.addActionListener(event -> displayFan());
        updateButton.addActionListener(event -> updateFan());

        // sets the window size and position and exits the program when it closes
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 240);
        setLocationRelativeTo(null);
    }

    // opens a connection using the required database name, user ID, and password
    private Connection connectToDatabase() throws SQLException {
        return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/databasedb", "student1", "pass");
    }

    // clears the previous fan's information before another record is displayed
    private void clearFanFields() {
        displayedId = null;
        firstNameField.setText("");
        lastNameField.setText("");
        favoriteTeamField.setText("");
    }

    private void displayFan() {
        clearFanFields();

        try {
            // converts the entered ID to an integer and builds the query for that fan
            int fanId = Integer.parseInt(idField.getText().trim());
            String query = "SELECT ID, firstname, lastname, favoriteteam "
                    + "FROM fans WHERE ID = " + fanId;

            // opens the connection and reads the fan data
            // closes the database resources automatically when this block ends
            try (Connection connection = connectToDatabase();
                 Statement statement = connection.createStatement();
                 ResultSet result = statement.executeQuery(query)) {

                // fills the fields when the query finds a matching fan
                if (result.next()) {
                    firstNameField.setText(result.getString("firstname"));
                    lastNameField.setText(result.getString("lastname"));
                    favoriteTeamField.setText(result.getString("favoriteteam"));
                    displayedId = fanId;
                    statusLabel.setText("Fan displayed.");
                } else {
                    statusLabel.setText("No fan found with that ID.");
                }
            }
        } catch (NumberFormatException exception) {
            // displays a message when the entered ID is not a valid integer
            statusLabel.setText("Enter a valid integer ID.");
        } catch (SQLException exception) {
            // displays the database error details in the console
            statusLabel.setText("Database error. See the console.");
            System.out.println(exception.getMessage());
        }
    }

    private void updateFan() {
        try {
            int fanId = Integer.parseInt(idField.getText().trim());

            // makes sure the entered ID still matches the fan that was displayed
            if (displayedId == null || fanId != displayedId) {
                statusLabel.setText("Display this ID before updating.");
                return;
            }

            // gets the edited values from the three text fields
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String favoriteTeam = favoriteTeamField.getText();

            // checks that each value fits the database's 25-character limit
            if (firstName.length() > 25 || lastName.length() > 25
                    || favoriteTeam.length() > 25) {
                statusLabel.setText("Use 25 characters or fewer in each text field.");
                return;
            }

            String query = "SELECT ID, firstname, lastname, favoriteteam "
                    + "FROM fans WHERE ID = " + fanId;

            // reads the fan's record using a result set that allows updates
            try (Connection connection = connectToDatabase();
                 Statement statement = connection.createStatement(
                         ResultSet.TYPE_SCROLL_INSENSITIVE,
                         ResultSet.CONCUR_UPDATABLE);
                 ResultSet result = statement.executeQuery(query)) {

                if (result.next()) {
                    // changes the three values in the current row
                    result.updateString("firstname", firstName);
                    result.updateString("lastname", lastName);
                    result.updateString("favoriteteam", favoriteTeam);
                    // saves the edited row in the database
                    result.updateRow();
                    statusLabel.setText("Fan updated.");
                } else {
                    clearFanFields();
                    statusLabel.setText("No fan found with that ID.");
                }
            }
        } catch (NumberFormatException exception) {
            statusLabel.setText("Enter a valid integer ID.");
        } catch (SQLException exception) {
            statusLabel.setText("Database error. See the console.");
            System.out.println(exception.getMessage());
        }
    }

    // displays whether the test passed and returns the result
    private boolean checkTest(String testName, boolean passed) {
        if (passed) {
            System.out.println(testName + ": PASS");
        } else {
            System.out.println(testName + ": FAIL");
        }
        return passed;
    }

    // tests the interface using my sample info
    private void runTests() {
        // checks that the window, fields, and buttons are visible
        checkTest("Interface is visible", isVisible()
                && idField.isShowing() && firstNameField.isShowing()
                && lastNameField.isShowing() && favoriteTeamField.isShowing()
                && displayButton.isShowing() && updateButton.isShowing());

        // clicks Display and checks the fields against the sample record
        idField.setText("1");
        displayButton.doClick();
        boolean sampleLoaded = statusLabel.getText().equals("Fan displayed.")
                && firstNameField.getText().equals("Alex")
                && lastNameField.getText().equals("Smith")
                && favoriteTeamField.getText().equals("Team1");

        // stops the remaining tests if the expected sample record was not loaded
        if (!checkTest("Display sample fan", sampleLoaded)) {
            System.out.println("Tests stopped. Check the connection and sample data.");
            return;
        }

        try {
            // changes all three fields and clicks Update to save the new values
            firstNameField.setText("Testy");
            lastNameField.setText("McTesterson");
            favoriteTeamField.setText("Team3");
            updateButton.doClick();
            boolean updated = statusLabel.getText().equals("Fan updated.");

            // clicks Display again to check that the new values were saved
            displayButton.doClick();
            checkTest("Update and reload all three fields", updated
                    && statusLabel.getText().equals("Fan displayed.")
                    && firstNameField.getText().equals("Testy")
                    && lastNameField.getText().equals("McTesterson")
                    && favoriteTeamField.getText().equals("Team3"));

            // checks that an invalid ID displays a message and clears the fields
            idField.setText("abc");
            displayButton.doClick();
            checkTest("Invalid ID clears the fields",
                    statusLabel.getText().equals("Enter a valid integer ID.")
                    && displayedId == null && firstNameField.getText().isEmpty()
                    && lastNameField.getText().isEmpty()
                    && favoriteTeamField.getText().isEmpty());
        } finally {
            // attempts to restore the original sample values after testing
            idField.setText("1");
            displayButton.doClick();
            firstNameField.setText("Alex");
            lastNameField.setText("Smith");
            favoriteTeamField.setText("Team1");
            updateButton.doClick();
            // reads the record again to check that the original values were restored
            boolean restored = statusLabel.getText().equals("Fan updated.");
            displayButton.doClick();
            checkTest("Sample record restored", restored
                    && statusLabel.getText().equals("Fan displayed.")
                    && firstNameField.getText().equals("Alex")
                    && lastNameField.getText().equals("Smith")
                    && favoriteTeamField.getText().equals("Team1"));
        }
    }

    public static void main(String[] args) {
        // creates and displays the window on Swing's event thread
        SwingUtilities.invokeLater(() -> {
            Avina_Mod10_2 application = new Avina_Mod10_2();
            application.setVisible(true);

            // runs the tests and closes the window when the test argument is supplied
            if (args.length == 1 && args[0].equals("test")) {
                application.runTests();
                application.dispose();
            }
        });
    }
}