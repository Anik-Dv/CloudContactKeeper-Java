package com.project.cloudContactKeeper.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.project.cloudContactKeeper.model.User;

@Controller
public class ViewsController {

	// for index page Handler
	@GetMapping("/")
	public ModelAndView homeHandler() {
		ModelAndView model = new ModelAndView();
		// set model
		model.addObject("title", "Home | CLUSTER CALL CENTER");
		model.setViewName("home");

		return model;
	}

	// for about page Handler
	@GetMapping("/about")
	public ModelAndView aboutHandler() {
		ModelAndView model = new ModelAndView();
		// set model
		model.addObject("title", "About | CLUSTER CALL CENTER");
		model.setViewName("about");

		return model;
	}

	// for faq page Handler
	@GetMapping("/faq")
	public ModelAndView faqHandler() {
		ModelAndView model = new ModelAndView();
		// set model
		model.addObject("title", "FAQ | CLUSTER CALL CENTER");
		model.setViewName("faq");

		return model;
	}

	// for signup page Handler
	@GetMapping("/signup")
	public ModelAndView signupHandler() {
		ModelAndView model = new ModelAndView();
		// set model
		model.addObject("title", "User Signup | CLUSTER CALL CENTER");
		model.addObject("user", new User());
		model.setViewName("signup");

		return model;
	}

	// for login page handler
	@GetMapping("/login")
	public ModelAndView loginHandler() {
		ModelAndView model = new ModelAndView();
		// set model
		model.addObject("title", "User Login | CLUSTER CALL CENTER");
		model.setViewName("login");

		return model;
	}

}
