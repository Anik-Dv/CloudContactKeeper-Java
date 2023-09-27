package com.project.cloudContactKeeper.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "CONTACT")
public class Contact {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int cId;
	@NotEmpty(message = "First Name is Empty! Must be Enter First Name!")
	@Size(min = 4, max = 12, message = "Your Name has been Must be Minimum 4 character and Miximum 16 Characters!")
	private String firstName;
	@NotEmpty(message = "Last Name is Empty! Must be Enter Last Name!")
	@Size(min = 4, max = 12, message = "Your Name has been Must be Minimum 4 character and Miximum 16 Characters!")
	private String lastName;
	@Column(length = 2500)
	@Size(min = 10, max = 2500, message = "Your Description has been Must be Minimum 10 character and Miximum 2500 Characters!")
	@NotEmpty(message = "Description is Empty! Must be Write Description!")
	private String description;
	@NotEmpty(message = "Phone Number is Empty! Must be Enter Phone Number!")
	@Size(min = 11, max = 11, message = "Please Enter Valid Mobile Number has Must be Miximum 11 Digits!")
	private String phoneNumber;
	@NotEmpty(message = "Work Field is Empty! Must be Enter Work!")
	private String work;
	@Email(regexp = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$", message = "You have entered an invalid email address!")
	@NotEmpty(message = "Must be Enter Valid Email Address!")
	private String contactEmail;
	
	// @NotEmpty(message = "Image Not Selected! Must be Select Profile Picture")
	private String profileImage;

	private String role;
	private boolean status;

	@ManyToOne
	private User user;

	public Contact() {
		super();
	}

	

	public Contact(int cId,String firstname, 
					String lastName, 
					String description, 
					String phoneNumber,
					String work,
					String contactEmail, 
					String profileImage, 
					String role, 
					boolean status, 
					User user) {
		super();
		this.cId = cId;
		this.firstName = firstname;
		this.lastName = lastName;
		this.description = description;
		this.phoneNumber = phoneNumber;
		this.work = work;
		this.contactEmail = contactEmail;
		this.profileImage = profileImage;
		this.role = role;
		this.status = status;
		this.user = user;
	}



	public int getcId() {
		return cId;
	}

	public void setcId(int cId) {
		this.cId = cId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstname) {
		this.firstName = firstname;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getWork() {
		return work;
	}

	public void setWork(String work) {
		this.work = work;
	}

	public String getContactEmail() {
		return contactEmail;
	}

	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	public String getProfileImage() {
		return profileImage;
	}

	public void setProfileImage(String profileImage) {
		this.profileImage = profileImage;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public boolean equals(Object obj) {
		return this.cId==((Contact)obj).getcId();
	}
	
}
