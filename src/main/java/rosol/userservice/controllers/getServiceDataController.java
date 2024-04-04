package rosol.userservice.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import rosol.userservice.cfg.SystemConfiguration;
import rosol.userservice.models.AppUser;
import rosol.userservice.models.ApplicationInfo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class getServiceDataController {
    private static ObjectMapper objectMapper = new ObjectMapper();
    public static ApplicationInfo getServiceData(String serviceName) throws IOException {
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

        String Json = formatJson(conn.getInputStream());
        List<ApplicationInfo> applicationInfo = objectMapper.readValue(Json, new TypeReference<List<ApplicationInfo>>() {});
        return applicationInfo.get(0);
    }

    private static String formatJson(InputStream inputStream) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
        }
        return stringBuilder.toString().split("\"instance\":")[1];
    }
}
