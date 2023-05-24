package com.mycompany.Service;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SessionService {
    public HttpSession invalidate(HttpSession session){
        session.invalidate();
        return session;
    }
    public boolean isValid(HttpSession session){
        if(session.getAttribute("username").equals(null)){
            return false;
        }
        else{
            return true;
        }
    }
}
