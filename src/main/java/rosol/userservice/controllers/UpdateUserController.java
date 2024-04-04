package rosol.userservice.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import rosol.userservice.cfg.SystemConfiguration;
import rosol.userservice.models.ApplicationInfo;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * Controller to handle the update of existing Users when clicking the UPDATE button
 */
public class UpdateUserController implements ActionListener {

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
    private ObjectMapper objectMapper;

    public UpdateUserController(JTextField nameTextField, JTextField lastnameTextField, JTextField birthdayTextField, JTextField genderTextField,
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
        this.objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (this.usersTable.getSelectionModel().isSelectionEmpty()){
                showPopup("No selection", "Select an element from the table before clicking UPDATE");

            } else if (emptyFields()){
                showPopup("Empty fields", "Fill fields before updating a user");

            } else {
                long userId = Long.parseLong(this.usersTable.getModel().getValueAt(this.usersTable.getSelectedRow(), 0).toString());
                processUpdateResponse(sendUpdateRequest(String.valueOf(userId)));
                this.refreshUsersTableController.startRefreshUsersTableProcess();
                showPopup("User Updated", "Updated");
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
     * Function to prepare and send the Update request coming from the user interface
     * @param userId url String to be sent to the backend service to be processed
     * @return connection to process the response later
     * @throws IOException exception management
     */
    private HttpURLConnection sendUpdateRequest(String userId) throws IOException {
        ApplicationInfo instance = getServiceDataController.getServiceData("USER-SERVICE");
        URL url = new URL("http://" + instance.getIpAddr() + ":" + instance.getPort().get$() + "/update/" + userId);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("PUT");
        conn.setDoOutput(true);
        conn.setRequestProperty("Content-Type", "application/json");

        String input = textfieldsInputs();

        OutputStream os = conn.getOutputStream();
        os.write(input.getBytes(StandardCharsets.UTF_8), 0, input.length());
        os.flush();

        return conn;
    }

    /**
     * Function to process the response coming from the backend service after the request
     * @param conn connection that will be used to process the response coming from the backend service
     * @throws IOException exception management
     */
    private void processUpdateResponse(HttpURLConnection conn) throws IOException {
        if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
            throw new RuntimeException("Failed : HTTP error code : "
                    + conn.getResponseCode());
        }

        try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
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
