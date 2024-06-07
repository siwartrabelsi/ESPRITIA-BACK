package tn.esprit.services;

import tn.esprit.Dto.SignupRequest;
import tn.esprit.entities.User;

public interface IAuthenticationServices {
    public User signup(SignupRequest user);
}
