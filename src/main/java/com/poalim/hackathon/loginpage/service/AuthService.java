package com.poalim.hackathon.loginpage.service;

import java.io.IOException;
import java.net.URISyntaxException;

public interface AuthService {

    boolean isValidUserAndOtp(String username, String otp) throws URISyntaxException, IOException, InterruptedException;
}
