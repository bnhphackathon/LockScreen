package com.poalim.hackathon.loginpage;

import com.poalim.hackathon.loginpage.overlay.LoginScreenNew;

public class LoginApplication {
    public static void main(String[] args) {

        LoginScreenNew loginScreenNew = new LoginScreenNew();
        loginScreenNew.setVisible(true);
    }

    public static void stop()
    {
        System.exit(0);
    }
}