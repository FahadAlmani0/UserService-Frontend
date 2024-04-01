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
 * Controller to handle the deletion of existing Users when clicking the DELETE button
 */
public class DeleteUserController implements ActionListener {

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

    public DeleteUserController(JTextField nameTextField, JTextField lastnameTextField, JTextField birthdayTextField, JTextField genderTextField,
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
            if (this.usersTable.getSelectionModel().isSelectionEmpty()) {
                showPopup("No selection", "Select an element from the table before clicking DELETE");
            } else {
                long userId = Long.parseLong(this.usersTable.getModel().getValueAt(this.usersTable.getSelectedRow(), 0).toString());
                processDeleteResponse(sendDeleteRequest(String.valueOf(userId)));
                this.refreshUsersTableController.startRefreshUsersTableProcess();
                showPopup("Delete", "User Deleted");
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Function to show a popup displaying the action performed in the User Interface
     * @param title title to be displayed in the popup
     * @param message message to be displayed in the popup
     */
    private void showPopup(String title, String message) {
        JOptionPane optionPane = new JOptionPane(message, JOptionPane.PLAIN_MESSAGE);
        JDialog dialog = optionPane.createDialog(title);
        dialog.setLocation(-3, 10);
        dialog.setVisible(true);
    }

    /**
     * Function to prepare and send the Delete request coming from the user interface
     * @param userId url String to be sent to the backend service to be processed
     * @return connection to process the response later
     * @throws IOException exception management
     */
    private HttpURLConnection sendDeleteRequest(String userId) throws IOException {
        URL url = new URL("http://" + SystemConfiguration.propertiesFile().getProperty("serviceIP") + ":" +
                SystemConfiguration.propertiesFile().getProperty("servicePort") + "/deleteactive/" + userId);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("PUT");
        return conn;
    }

    /**
     * Function to process the response coming from the backend service after the request
     * @param conn connection that will be used to process the response coming from the backend service
     * @throws IOException exception management
     */
    private void processDeleteResponse(HttpURLConnection conn) throws IOException {
        if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
            throw new RuntimeException("Failed : HTTP error code : "
                    + conn.getResponseCode());
        }

        try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(),
                StandardCharsets.UTF_8))) {
            StringBuilder response = new StringBuilder();
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                //This response is not used in this example, but shows how the response can be retrieved to process it if needed
                response.append(responseLine.trim());
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        clearFields();
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
