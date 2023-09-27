package com.project.cloudContactKeeper.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.project.cloudContactKeeper.helper.Message;
import com.project.cloudContactKeeper.model.User;
import com.project.cloudContactKeeper.repository.UserRepo;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class UserRegisterController {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	BCryptPasswordEncoder passwordEncoder;

	@PostMapping("/process-signup")
	public ModelAndView registerHandler(@Valid @ModelAttribute("user") User user, BindingResult bindingResult,
			@RequestParam(value = "password") String pws, @RequestParam(value = "c_password") String cPWS,
			HttpSession session) {

		// create a model object for send data controller to view
		ModelAndView model = new ModelAndView();

		try {

			// setting model object
			model.addObject("title", "Dashboard | CLUSTER CALL CENTER");

			if (bindingResult.hasErrors()) {
				// session.setAttribute("message", bindingResult);
				System.out.println(bindingResult);

				model.addObject("user", user);
				// and then return again signup view
				model.setViewName("signup");
				// throw new Exception("Sorry! Something Went Worng! Plase Try Again!");
			}

			// if password and confirm password is same then continue to register the user
			// otherwise return error message
			if (!pws.equals(cPWS)) {
				throw new Exception("Your Password Is Not Same To Confirm Password!");
			}

			// if password field is blank
			if (pws.isBlank() || cPWS.isBlank()) {
				throw new Exception("Something Went Worng!");
			}

			// if any user field has have null or empty then showing error message!
			if (user == null || user.getName().isEmpty() || user.getEmail().isEmpty() || user.getAbout().isEmpty()
					|| user.getPassword().isEmpty()) {
				throw new Exception("Sorry! Something Went Worng! Plase Try Again!");
			}

			// processing form data for save database			
			user.setRole("ROLE_USER");
			user.setStatus(true);
			// Encode user password
			user.setPassword(passwordEncoder.encode(user.getPassword()));

			// Register the user and save data from database.
			User result = this.userRepo.save(user);
			session.setAttribute("message", new Message("Your Account Was Created Successfully \n Go for Login ", "alert-success"));
			model.setViewName("signup");

			// add new user object
			model.addObject("user", result);

		} catch (Exception err) {
			// showing error on console
			err.printStackTrace();
			// add model object of user
			model.addObject("user", user);
			// add message for showing error message
			session.setAttribute("message", new Message("Some Error Occurred! " + err.getMessage(), "alert-danger"));
			// and then return again signup view
			model.setViewName("signup");
		}

		return model;
	}

}
