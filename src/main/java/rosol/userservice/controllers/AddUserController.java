package rosol.userservice.controllers;

import rosol.userservice.cfg.SystemConfiguration;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * Controller to handle the addition of new Users when clicking the ADD button
 */
public class AddUserController implements ActionListener {

    JTextField nameTextField;
    JTextField lastnameTextField;
    JTextField birthdayTextField;
    JTextField genderTextField;
    JTextField nationalityTextField;
    JTextField phoneTextField;
    JTextField emailTextField;
    JTextField usernameTextField;
    JTextField passwordTextField;
    JTextField entityIdTextField;
    JTextField nationalIdTextField;
    JTextField roleTextField;
    JTextField permissionTextField;
    JTextField missionIdTextField;
    JTable usersTable;
    RefreshUsersTableController refreshUsersTableController;

    public AddUserController(JTextField nameTextField, JTextField lastnameTextField, JTextField birthdayTextField, JTextField genderTextField,
                             JTextField nationalityTextField, JTextField phoneTextField, JTextField emailTextField, JTextField usernameTextField,
                             JTextField passwordTextField, JTextField entityIdTextField, JTextField nationalIdTextField, JTextField roleTextField,
                             JTextField permissionTextField, JTextField missionIdTextField, JTable usersTable,
                             RefreshUsersTableController refreshUsersTableController) {
        this.nameTextField = nameTextField;
        this.lastnameTextField = lastnameTextField;
        this.birthdayTextField = birthdayTextField;
        this.genderTextField = genderTextField;
        this.nationalityTextField = nationalityTextField;
        this.phoneTextField = phoneTextField;
        this.emailTextField = emailTextField;
        this.usernameTextField = usernameTextField;
        this.passwordTextField = passwordTextField;
        this.entityIdTextField = entityIdTextField;
        this.nationalIdTextField = nationalIdTextField;
        this.roleTextField = roleTextField;
        this.permissionTextField = permissionTextField;
        this.missionIdTextField = missionIdTextField;
        this.usersTable = usersTable;
        this.refreshUsersTableController = refreshUsersTableController;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (emptyFields()) {
                showPopup("Empty fields", "Fill fields before adding a new user");

            } else {
                processAddResponse(sendAddRequest());
                this.refreshUsersTableController.startRefreshUsersTableProcess();
                showPopup("Submitted", "User Registered");
            }

        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Function to show a popup displaying the action performed in the User Interface
     *
     * @param title   title to be displayed in the popup
     * @param message message to be displayed in the popup
     */
    private void showPopup(String title, String message) {
        JOptionPane optionPane = new JOptionPane(message, JOptionPane.PLAIN_MESSAGE);
        JDialog dialog = optionPane.createDialog(title);
        dialog.setLocation(-3, 10);
        dialog.setVisible(true);
    }

    /**
     * Function to prepare and send the Add request coming from the user interface
     *
     * @return connection to process the response later
     * @throws IOException exception management
     */
    private HttpURLConnection sendAddRequest() throws IOException {
        URL url = new URL("http://" + SystemConfiguration.propertiesFile().getProperty("serviceIP") + ":" +
                SystemConfiguration.propertiesFile().getProperty("servicePort") + "/newactive");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.setRequestProperty("Content-Type", "application/json");

        String input = textfieldsInputs();

        OutputStream os = conn.getOutputStream();
        os.write(input.getBytes(StandardCharsets.UTF_8), 0, input.length());
        os.flush();

        return conn;
    }

    /**
     * Function to process the response coming from the backend service after the Add request
     *
     * @param conn connection that will be used to process the response coming from the backend service
     * @throws IOException exception management
     */
    private void processAddResponse(HttpURLConnection conn) throws IOException {
        if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
            throw new RuntimeException("Failed : HTTP error code : "
                    + conn.getResponseCode());
        }

        try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
            StringBuilder response = new StringBuilder();
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        clearFields();
    }

    /**
     * Function to build the input for the request
     * @return the input already built
     */
    private String textfieldsInputs() {
        return "{\"name\":\"" + nameTextField.getText() + "\",\"lastname\":\"" + lastnameTextField.getText() + "\"," +
                "\"dateOfBirth\":\"" + birthdayTextField.getText() + "\",\"gender\":\"" + genderTextField.getText() + "\"," +
                "\"nationality\":\"" + nationalityTextField.getText() + "\",\"phone\":\"" + phoneTextField.getText() + "\"," +
                "\"email\":\"" + emailTextField.getText() + "\",\"username\":\"" + usernameTextField.getText() + "\"," +
                "\"password\":\"" + passwordTextField.getText() + "\",\"entityId\":\"" + entityIdTextField.getText() + "\"," +
                "\"nationalId\":\"" + nationalIdTextField.getText() + "\",\"role\":\"" + roleTextField.getText() + "\"," +
                "\"permission\":\"" + permissionTextField.getText() + "\",\"missionId\":\"" + missionIdTextField.getText() + "\"" +
                "}";
    }

    /**
     * Function to check if there are empty fields before sending the UPDATE request
     * @return true if exist empty fields. False otherwise
     */
    private boolean emptyFields() {
        return (this.nameTextField.getText().isEmpty() || this.lastnameTextField.getText().isEmpty() || this.birthdayTextField.getText().isEmpty() ||
                this.genderTextField.getText().isEmpty() || this.nationalityTextField.getText().isEmpty() || this.phoneTextField.getText().isEmpty() ||
                this.emailTextField.getText().isEmpty() || this.usernameTextField.getText().isEmpty() || this.passwordTextField.getText().isEmpty() ||
                this.entityIdTextField.getText().isEmpty() || this.nationalIdTextField.getText().isEmpty() || this.roleTextField.getText().isEmpty() ||
                this.permissionTextField.getText().isEmpty() || this.missionIdTextField.getText().isEmpty());
    }

    /**
     * Function to clear all UI fields after adding a new user
     */
    private void clearFields() {
        this.nameTextField.setText("");
        this.lastnameTextField.setText("");
        this.birthdayTextField.setText("");
        this.genderTextField.setText("");
        this.nationalityTextField.setText("");
        this.phoneTextField.setText("");
        this.emailTextField.setText("");
        this.usernameTextField.setText("");
        this.passwordTextField.setText("");
        this.entityIdTextField.setText("");
        this.nationalIdTextField.setText("");
        this.roleTextField.setText("");
        this.permissionTextField.setText("");
        this.missionIdTextField.setText("");
    }
}
