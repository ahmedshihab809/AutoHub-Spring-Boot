package com.mycompany.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;
    public String sendEmail(String email){
        SimpleMailMessage message=new SimpleMailMessage();
        message.setFrom("ahmedshihab809@gmail.com");
        message.setSubject("OTP");
        Random random = new Random();
        Integer i= random.nextInt(9000)+1000;
        String otp=Integer.toString(i);
        message.setText("Your OTP is: " + otp);
        message.setTo(email);
        mailSender.send(message);
        System.out.println("Email Sent Successfully");
        return otp;
    }
}
