package rosol.userservice.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import rosol.userservice.cfg.SystemConfiguration;
import rosol.userservice.models.AppUser;
import rosol.userservice.models.ApplicationInfo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class RefreshUsersTableController implements ActionListener {

    JTable usersTable;
    private ObjectMapper objectMapper;


    public RefreshUsersTableController( JTable usersTable) {
        this.usersTable = usersTable;
        this.objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());


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
        ApplicationInfo instance = getServiceDataController.getServiceData("USER-SERVICE");

        URL url = new URL("http://" + instance.getIpAddr() + ":" + instance.getPort().get$() + "/users");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");

        if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
            throw new RuntimeException("Failed : HTTP error code : "
                    + conn.getResponseCode());
        }
        return conn;
    }

    private ApplicationInfo getServiceData(String serviceName) throws IOException {
        String IP = SystemConfiguration.propertiesFile().getProperty("serviceIP");
        String PORT = SystemConfiguration.propertiesFile().getProperty("servicePort");

        URL url = new URL("http://" + IP + ":" + PORT + "/eureka/apps/" + serviceName);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");

        if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
            throw new RuntimeException("Failed : HTTP error code : "
                    + conn.getResponseCode());
        }

        InputStream inputStream = conn.getInputStream();
        ApplicationInfo applicationInfo = objectMapper.readValue(inputStream, ApplicationInfo.class);
        return applicationInfo;
    }



    /**
     * Function to process the response coming from the backend service after the Refresh request
     *
     * @param conn connection that will be used to process the response coming from the backend service
     */
    private void processRefreshResponse(HttpURLConnection conn) {
            try (InputStream inputStream = conn.getInputStream()) {
                List<AppUser> users = objectMapper.readValue(inputStream, new TypeReference<List<AppUser>>() {});
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


