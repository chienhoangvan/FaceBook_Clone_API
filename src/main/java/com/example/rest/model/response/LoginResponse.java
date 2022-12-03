package com.example.rest.model.response;

import java.util.Random;

public class LoginResponse extends BaseResponse{
    private String username;
    private String token;
    private String avatar;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public static void main(String[] args) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        String numbers = "0123456789";
        Random rnd = new Random();
        int randomNumber = rnd.nextInt(6);
        if(randomNumber == 0){
            randomNumber ++;
        }
        StringBuilder sbChar = new StringBuilder(6 - randomNumber);
        StringBuilder sbNumber = new StringBuilder(randomNumber);

        for (int i = 0; i < 6 - randomNumber; i++)
            sbChar.append(chars.charAt(rnd.nextInt(chars.length())));
        for (int i = 0; i < randomNumber; i++)
            sbNumber.append(numbers.charAt(rnd.nextInt(numbers.length())));
        StringBuilder sbExchange = sbNumber.append(sbChar);
        StringBuilder sbResult = new StringBuilder();
        for (int i = 0; i < 6; i++)
            sbResult.append(sbExchange.charAt(rnd.nextInt(sbExchange.length())));
        System.out.println(sbResult);
    }
}
