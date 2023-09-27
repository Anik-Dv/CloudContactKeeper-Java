package com.project.cloudContactKeeper.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.project.cloudContactKeeper.helper.Message;
import com.project.cloudContactKeeper.model.Contact;
import com.project.cloudContactKeeper.model.User;
import com.project.cloudContactKeeper.repository.ContactRepo;
import com.project.cloudContactKeeper.repository.UserRepo;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/user")
public class UserController {

	// User Repository
	@Autowired
	UserRepo userRepo;

	// contact repository
	@Autowired
	ContactRepo contactRepo;

	// for sending common data handler
	@ModelAttribute
	public void commonData(Model model, Principal principal) {
		// fetch user from user repository
		User user = this.userRepo.getUserByUserName(principal.getName());
		model.addAttribute("user", user);
		
	}

	@GetMapping("/dashboard")
	public String userDashboard(Model model) {
		model.addAttribute("title", "User Dashboard | CLUSTER CALL CENTER");
		return "/normal/user_dashboard";
	}

	// view all contacts handler
	@GetMapping("/view-contacts/{page}")
	public String viewContacts(@PathVariable("page") Integer pageNum, Model model, Principal principal) {
		model.addAttribute("title", "View Contacts | CLUSTER CALL CENTER");
		
		// viewing all contact for logged user
		String userName = principal.getName();
		User user = this.userRepo.getUserByUserName(userName);
		
		Pageable page = PageRequest.of(pageNum, 4);
		
		
		Page<Contact> contacts = this.contactRepo.getContactsByUserName(user.getUserId(), page);
		model.addAttribute("contacts", contacts);
		
		
		// pagination include
		// we have two property 1st recode of number showing per page
		// 2nd-> current page
		
		// return current page i.g : 0.
		model.addAttribute("currentPage", pageNum);
		// Returns the number of total pages.
		model.addAttribute("totalPage", contacts.getTotalPages());
		
		return "/normal/view_contacts";
	}
	

	@GetMapping("/add-contact")
	public String addContact(Model model) {
		model.addAttribute("title", "Add Contact | CLUSTER CALL CENTER");
		model.addAttribute("contact", new Contact());
		return "normal/addContact";
	}

	// process add contact data handler
	@PostMapping("/process-contact-data")
	public String addContactHandler(
			@Valid
			@ModelAttribute Contact contact, BindingResult bindingResult,
			@RequestParam("profilePicture") MultipartFile file, Principal principal, 
			HttpSession session, Model model) {

		try {
			// save the database user add contact 
			
			// when been has errors then execute this condition
			if(bindingResult.hasErrors()) {
				
				System.out.println(bindingResult);
				
				model.addAttribute("contact", contact);
				
				return "normal/addContact";
			}
			
			// when contact is null then throw error
			if(contact == null) {
				throw new Exception("Sorry! Something Went Worng! Plase Try Again!!");
			}			
			
			// get user name
			String userName = principal.getName();
			// fetch user with userName
			User user = this.userRepo.getUserByUserName(userName);
			// add contact user of fetched user
			contact.setUser(user); // add from contact, contact user [By-direction]
			// added from user contact of user contacts 
			user.getContact().add(contact); // [uni-direction]
			
			// check file is empty or not
			if (file.isEmpty()) {
			// here file is empty
							
			// when user not set her/his avatar then set 
			// adding null default avatar
			contact.setProfileImage("avatar_null_user.avif");
			//throw new Exception("Contact Profile Image Not Select! Please Select An Image!");
			} else {
			
			// save the contact profile image name in database		
			contact.setProfileImage(file.getOriginalFilename());

			// save contact profile image file in folder 
			File staticPath = new ClassPathResource("static/images").getFile();

			Path path = Paths.get(staticPath.getAbsolutePath()+File.separator+file.getOriginalFilename());
			
			Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

			System.out.println("Profile Image Saved " + file.getOriginalFilename());
			}
			// and save/updated user with contacts from db
			User userResult = this.userRepo.save(user);			

			session.setAttribute("message", new Message("Contact Added Successfully ", "alert-success"));
			
			model.addAttribute("contact", contact);
			
			System.out.println("User Contact Save to database " + userResult);

		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("contact", contact);	
			session.setAttribute("message", new Message("Something Went Worng! " + e.getMessage(), "alert-danger"));			
			return "normal/addContact";	
		}

		return "normal/addContact";
	}
	
	// creating contact profile
	@GetMapping("/contact-profile/{page}/{c_id}")
	public String contactProfileHandler(@PathVariable("page") Integer page, @PathVariable("c_id") Integer c_id,
			Principal principal, Model model, HttpSession session) {

		try {
			String userName = principal.getName();
			User user = this.userRepo.getUserByUserName(userName);

			Optional<Contact> contactdata = this.contactRepo.findById(c_id);

			Contact contact = contactdata.get();
			// when contact id is null then throw exception
			if (c_id == null) {
				throw new Exception();
			}
			// when client request with unvalid id then throw exception
			if (user.getUserId() != contact.getUser().getUserId()) {
				throw new Exception();
			}
			// when contact id and user contact id is same then is valid request
			if (user.getUserId() == contact.getUser().getUserId()) {
				// adding contact data from model
				model.addAttribute("contactData", contact);
				model.addAttribute("currentPage", page);
			}

			model.addAttribute("title", contact.getFirstName() + " " + contact.getLastName() + " | Contact Profile");
		} catch (Exception err) {
			err.printStackTrace();
			session.setAttribute("message", new Message("Oops! That Page Can't be Found! ", "alert-danger"));
		}

		return "normal/contactProfile";
	}
	
	
	// viewing update form
	@PostMapping("/do-update/{page}/{c_id}")
	public String viewUpdateForm(@PathVariable("page")int page,
			@PathVariable("c_id") int c_id,
			Principal principal , Model model) {
		
		model.addAttribute("currentPage", page);
		// fetch current logged user name
		String userName = principal.getName();
		// and get user by user name
		User user = this.userRepo.getUserByUserName(userName);
		
		// get contact by contact id
		Contact Resultcontact = this.contactRepo.findById(c_id).get();
		
		model.addAttribute("title", "Update Contact " +"| "+ Resultcontact.getFirstName()+" "+Resultcontact.getLastName());
		model.addAttribute("contact",Resultcontact);
		model.addAttribute("currentPage", page);
		
		return "normal/updateContact";
	}
	
	
	// update process contact handler
	@PostMapping("/update-contact/{page}/{c_id}")
	public String updateContactHandler(@ModelAttribute Contact contact, @PathVariable("page") int page,
			@RequestParam("profileimage") MultipartFile file,
			Principal principal, Model model, HttpSession session) {
		
		try {
			model.addAttribute("currentPage", page);
			// fetch current logged user name
			// and get user by user name
			User user = this.userRepo.getUserByUserName(principal.getName());
			
			// get contact by contact id
			Contact oldContact = this.contactRepo.findById(contact.getcId()).get();
			
				// contact data send view form
				model.addAttribute("contact", oldContact);				
				
				if(!file.isEmpty()) {
					// DELETE EXITING IMAGES ALREADY.
					// and when user set new images then delete old images
					File classPath = new ClassPathResource("static/images").getFile();
					File oldFile = new File(classPath, oldContact.getProfileImage());
					oldFile.delete();
					
					// upload new file
					File staticPath = new ClassPathResource("static/images").getFile();
					Path path = Paths.get(staticPath.getAbsolutePath()+File.separator+file.getOriginalFilename());
					Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
					
					// and set database new file name
					contact.setProfileImage(file.getOriginalFilename());
					
				} else {
					// when user not set images as new then set old images as was it.
					contact.setProfileImage(oldContact.getProfileImage());
				}
				
				// set contact this user
				contact.setUser(user);
				
				// process for update details
				this.contactRepo.save(contact);

				// send session success message
				session.setAttribute("message", new Message("Your Contact Has Updated", "alert-success"));
				
		} catch (Exception e) {
			e.printStackTrace();
			session.setAttribute("message", new Message("Your Contact Has Not Updated", "alert-danger"));
		}
		
		return "redirect:/user/contact-profile/"+page+"/"+contact.getcId();
	}
	 
	
	// delete contact handler
	@GetMapping("/delete-contact/{page}/{c_id}")
	public String deleteContactHandler(@PathVariable("page") int page, @PathVariable("c_id") Integer c_id, 
			Principal principal, Model model, HttpSession session) {
		
		try {
			User user = this.userRepo.getUserByUserName(principal.getName());			
			
			Contact contact = this.contactRepo.findById(c_id).get();
			
			// check user is valid for delete contact is his/her contact or not.
			if(user.getUserId() == contact.getUser().getUserId()) {
				
				// getting all user contacts and checking which contact is unlinked/relationship of user then remove that.
				user.getContact().remove(contact);
				
				// when contact was deleted then contact file also be deleted
				File staticFile = new ClassPathResource("static/images").getFile();
				File file = new File(staticFile.getAbsolutePath()+File.separator+contact.getProfileImage());
				file.delete();
				
				// when setting those operation and then update the user
				this.userRepo.save(user);
				
				session.setAttribute("message", new Message("contact has been successfully deleted", "alert-success"));
				
			} else {
				session.setAttribute("message", new Message("Sorry! Contact is Not Deleted!", "alert-danger"));
			}
			
			
		} catch(Exception e) {
			e.printStackTrace();
			session.setAttribute("message", new Message("Something Went Wrong!", "alert-danger"));
		}
		
		return "redirect:/user/view-contacts/"+page;
	}
	
	
	// View User Profile Handler
	@GetMapping("/profile/{page}")
	public String viewUserProfile(@PathVariable("page") int page, Model model, Principal principal) {
		model.addAttribute("title", "User Profile | Cluster Call Center");
		
		User user = this.userRepo.getUserByUserName(principal.getName());
		
		Pageable pages = PageRequest.of(page, 2);
		
		Page<Contact> contacts = this.contactRepo.getContactsByUserName(user.getUserId(), pages);
		model.addAttribute("contacts", contacts);
		model.addAttribute("currentPage", page);
		// Returns the number of total pages.
		model.addAttribute("totalPage", contacts.getTotalPages());
		return "normal/userProfile";
	}
	
	// View profile setting handler
	@GetMapping("/profile-setting")
	public String viewUserProfileSetting(Model model) {
		model.addAttribute("title", "User Profile Setting | Cluster Call Center");
		return "normal/userProfileSetting";
	}
	
	// update profile handler
	@PostMapping("/update-user")
	public String updateProfileHandler(@ModelAttribute User user, Principal principal,
			 @RequestParam("userEmail") String email, @RequestParam("userProfileImage") MultipartFile file, HttpSession session, Model model) {

		try {
			// get old user
			User oldUser = this.userRepo.findById(user.getUserId()).get();
			model.addAttribute("user", oldUser);

			// check user have already profile picture or not
			if (!file.isEmpty()) {
				
				// DELETE EXITING IMAGES ALREADY.
				// and when user set new images then delete old images
				File classPath = new ClassPathResource("static/images").getFile();
				File oldFile = new File(classPath, oldUser.getImage());
				oldFile.delete();
				

				// upload new image
				File staticPath = new ClassPathResource("static/images").getFile();
				Path path = Paths.get(staticPath.getAbsolutePath()+File.separator+file.getOriginalFilename());
				Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
				// set new image name in database
				user.setImage(file.getOriginalFilename());
				
			} else {
				// set old image as was well
				user.setImage(oldUser.getImage());
			}
			
			// when user current email and user new email not same then update email
			if(!oldUser.getEmail().equals(email)) {
				// set the new email
				user.setEmail(email);
				this.userRepo.save(user);
				session.setAttribute("message", new Message("User Infomation Successfully Updated. And Please Login Again With Your Updated Email! Your Updated Email is [ "+user.getEmail()+" ]", "alert-success"));
				return "redirect:/login";
			} else {
			
			
			// and update user info.
			this.userRepo.save(user);
			session.setAttribute("message", new Message("User Infomation Successfully Updated", "alert-success"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("user", user);
			session.setAttribute("message", new Message("SORRY! User Infomation Not Updated", "alert-danger"));
		}

		return "redirect:/user/profile-setting";
	}
	
	
	
}
