package rosol.userservice.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import rosol.userservice.cfg.SystemConfiguration;
import rosol.userservice.models.AppUser;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class RefreshUsersTableController implements ActionListener {

    JTable usersTable;

    public RefreshUsersTableController( JTable usersTable) {
        this.usersTable = usersTable;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        startRefreshUsersTableProcess();
    }

    public void startRefreshUsersTableProcess() {
        try {
            clearTable();
            processRefreshResponse(sendRefreshRequest());
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Function to clear the Users' table before refreshing it
     */
    private void clearTable() {
        DefaultTableModel tableModel = (DefaultTableModel) this.usersTable.getModel();
        tableModel.setRowCount(0);
    }

    /**
     * Function to send the Refresh request to update the Users table
     *
     * @throws IOException exception management
     */
    private HttpURLConnection sendRefreshRequest() throws IOException {
        URL url = new URL("http://" + SystemConfiguration.propertiesFile().getProperty("serviceIP") + ":" +
                SystemConfiguration.propertiesFile().getProperty("servicePort") + "/users");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
            throw new RuntimeException("Failed : HTTP error code : "
                    + conn.getResponseCode());
        }

        return conn;
    }

    /**
     * Function to process the response coming from the backend service after the Refresh request
     *
     * @param conn connection that will be used to process the response coming from the backend service
     */
    private void processRefreshResponse(HttpURLConnection conn) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
            StringBuilder response = new StringBuilder();
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            JsonConverterController converterController = new JsonConverterController();
            TypeReference<List<AppUser>> listTypeReference = new TypeReference<List<AppUser>>() {
            };
            List<AppUser> users = converterController.getMapper().readValue(response.toString(), listTypeReference);
            DefaultTableModel tableModel = (DefaultTableModel) usersTable.getModel();
            for (AppUser user : users) {
                tableModel.addRow(new Object[]{
                        user.getId(),
                        user.getName(),
                        user.getLastname(),
                        user.getDateOfBirth(),
                        user.getGender(),
                        user.getNationality(),
                        user.getPhone(),
                        user.getEmail(),
                        user.getUsername(),
                        user.getPassword(),
                        user.getEntityId(),
                        user.getNationalId(),
                        user.getRole(),
                        user.getPermission(),
                        user.getMissionId(),
                        user.isActive()
                });
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}


