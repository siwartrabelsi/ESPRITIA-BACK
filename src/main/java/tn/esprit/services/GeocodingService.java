package tn.esprit.services;

import com.google.maps.model.LatLng;
import org.springframework.beans.factory.annotation.Value;

public class GeocodingService {
    @Value("${google.maps.apikey}")
    private String googleMapsApiKey;

    public LatLng geocodeAddress(String address) {
        // Utiliser l'API de géocodage (par exemple, Google Maps API) pour convertir l'adresse en coordonnées
        // Exemple fictif :
        double latitude = 0.0;
        double longitude = 0.0;
        // Appeler l'API et récupérer les coordonnées
        return new LatLng(latitude, longitude);
    }
}
