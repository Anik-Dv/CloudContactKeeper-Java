package com.project.cloudContactKeeper.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "USER")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int userId;
	
	@NotBlank(message = "You Can Not Enter Your Name!")
	@NotEmpty(message = "Your Name is Empty! Must be Enter Your Name!")
	@Size(min=4, max = 16, message = "Your Name has been Must be Minimum 4 character and Miximum 16 Characters!")
	private String name;
	
	@NotBlank(message = "You Can Not Enter Your Sure Name!")
	@NotEmpty(message = "Your Name is Empty! Must be Enter Your Sure Name!")
	@Size(min= 4, max = 8, message = "Your Name has been Must be Minimum 4 character and Miximum 8 Characters!")
	private String sureName;
	
	@NotBlank(message = "You Can Not Enter Your Email Address!")
	@NotEmpty(message = "Your Eamil Address is Empty! Must be Enter Your Email Address!")
	//@UniqueElements(message = "This Email Already Exits! /n Your Email Email Address Must be Unique!")
	@Column(unique = true)
	private String email;
	
	@NotBlank(message = "You Can Not Enter Your Phone Number!")
	@Size(min=11, max=11, message = "Please Enter Valid Mobile Number has Must be Miximum 11 Digits!")
	private String pnumber;
	
	@NotBlank(message = "You Can Not Enter Your Address!")
	private String address;
	
	@NotBlank(message = "You Can Not Enter Your country!")
	private String country;
	
	@NotBlank(message = "You Can Not Enter Your state!")
	private String state;
	
	
	@NotBlank(message = "You Can Not Enter Your Password!")
	@NotEmpty(message = "Your Password is Empty! Must be Enter Your Password!")
	@Size(min=4, message = "Your Password has been Must be Minimum 4 character and Miximum 16 Characters!")
	private String password;
	
	//@NotBlank(message = "You Can Not Upload Your Image!")
	//@NotEmpty(message = "Your Can Not Upload Your Image! Must be Upload Your Image.")
	private String image;
	
	@NotBlank(message = "You Can Not Enter Your About!")
	@NotEmpty(message = "Your About is Empty! Must be Enter Your About!")
	@Size(min = 5, max = 2000)
	@Column(length = 2000)
	private String about;
	
	private boolean status;
	private String role;
	
	@AssertTrue(message = "You Can Not Check Agreement!! You Must be Agree Terms And Conditions!")
	private boolean agreement;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "user", orphanRemoval = true)
	private List<Contact> contact = new ArrayList<>();

	public User() {
		super();
	}

	public User(int userId, String name, String email, String password, String image, String about, boolean status,
			String role, String pnumber, String address, String country, String state) {
		super();
		this.userId = userId;
		this.name = name;
		this.email = email;
		this.password = password;
		this.image = image;
		this.about = about;
		this.status = status;
		this.role = role;
		this.pnumber = pnumber;
		this.address = address;
		this.country = country;
		this.state = state;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public List<Contact> getContact() {
		return contact;
	}

	public void setContact(List<Contact> contact) {
		this.contact = contact;
	}

	public boolean isAgreement() {
		return agreement;
	}

	public void setAgreement(boolean agreement) {
		this.agreement = agreement;
	}

	public String getPnumber() {
		return pnumber;
	}

	public void setPnumber(String pnumber) {
		this.pnumber = pnumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getSureName() {
		return sureName;
	}

	public void setSureName(String sureName) {
		this.sureName = sureName;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", name=" + name + ", sureName=" + sureName + ", email=" + email
				+ ", pnumber=" + pnumber + ", address=" + address + ", country=" + country + ", state=" + state
				+ ", password=" + password + ", image=" + image + ", about=" + about + ", status=" + status + ", role="
				+ role + ", agreement=" + agreement + ", contact=" + contact + "]";
	}

}
