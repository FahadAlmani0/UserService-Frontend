package rosol.userservice.cfg;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

/**
 * Configuration class to read the Properties file
 */
public class SystemConfiguration {

    public static Properties propertiesFile() {
        try {
            return readPropertiesFile("properties.properties");
        } catch (IOException e) {
            System.out.println("System configuration file not found");
            return null;
        }
    }

    /**
     * Function to retrieve the Properties file to access its information
     *
     * @param fileName the relative path where the system can find the Properties file
     * @return a Properties object to access the properties specified in the file
     * @throws IOException when the Properties file is not found
     */
    private static Properties readPropertiesFile(String fileName) throws IOException {
        InputStream fis = null;
        Properties prop = null;

        String cfg = System.getProperty("user.dir") + "\\src\\main\\resources\\";
        Path pathToCfgFile = Paths.get(cfg + fileName);

        try {
            /*if (!Files.exists(pathToCfgFile)) {
                ResourceLoader.createDir(Paths.get(cfg), C4ISTARLauncher.class.getClassLoader().getResource(fileName));
            }*/
            fis = Files.newInputStream(pathToCfgFile);
            prop = new Properties();
            prop.load(fis);
        } catch (IOException fnfe) {
            fnfe.printStackTrace();
        } finally {
            if (fis != null)
                fis.close();
        }
        return prop;
    }
}
