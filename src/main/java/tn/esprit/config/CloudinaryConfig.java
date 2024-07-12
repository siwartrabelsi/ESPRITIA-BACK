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
                "cloud_name", "dwivpm06h",
                "api_key", "514615145733972",
                "api_secret", "Tb38_jNuNDQNCk21vJcgwXkEmdU"
        ));
    }
}
