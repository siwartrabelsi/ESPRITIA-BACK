package tn.esprit.services;

import tn.esprit.Dto.JwtAuthenticationResponse;
import tn.esprit.Dto.ResetPasswordRequest;
import tn.esprit.Dto.SiginRequest;
import tn.esprit.Dto.SignupRequest;
import tn.esprit.entities.User;

public interface IAuthenticationServices {
    public User signup(SignupRequest user);

    public JwtAuthenticationResponse sigin(SiginRequest user);

    public void sendResetPasswordSMS(String email);
    public void resetPassword(ResetPasswordRequest resetRequest);
}