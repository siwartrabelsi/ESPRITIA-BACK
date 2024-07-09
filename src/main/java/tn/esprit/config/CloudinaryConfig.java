package tn.esprit.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {
    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dezhtjsmy",
                "api_key", "869891597625733",
                "api_secret", "l89febPZI2jC09KbX0FsGMjCiMk"
        ));
    }
}
