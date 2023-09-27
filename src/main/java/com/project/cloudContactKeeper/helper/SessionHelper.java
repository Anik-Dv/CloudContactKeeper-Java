package com.project.cloudContactKeeper.helper;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpSession;

@Component
public class SessionHelper {
	
	// removing session message
	public void removeMessage(String mgs) {
		try {
			HttpSession session = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession();
			session.removeAttribute(mgs);
			
		} catch(Exception err) {
			err.printStackTrace();
		}
	}
}
