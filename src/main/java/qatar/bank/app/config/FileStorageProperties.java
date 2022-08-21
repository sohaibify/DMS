package qatar.bank.app.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * author: sohaib yasir
 * It contains property to save PDF files on file system.
 */
@Configuration
@ConfigurationProperties("storage")
public class FileStorageProperties {
    private String location = "upload-dir";

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
