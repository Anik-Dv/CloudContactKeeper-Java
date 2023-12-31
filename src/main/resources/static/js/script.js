/* START - User Dashboard Sidebar toggle function*/

const toggleSidebar = () => {
	// checking sideber is visible or not
	if($(".sidebar").is(":visible")) {
		// true
		$(".sidebar").css("display", "none");
		$(".content").css("margin-left", "0%");
	} else {
		// false
		$(".sidebar").css("display", "block");
		$(".content").css("margin-left", "20%");
	}
}

/* END - User Dashboard Sidebar toggle function*/

/* START - User Contact delete show popup alert function*/
function deleteContact(page, id) {

	Swal.fire({
		title: 'Are you sure?',
		text: "You won't be able to revert this!",
		icon: 'warning',
		showCancelButton: true,
		confirmButtonColor: '#3085d6',
		cancelButtonColor: '#d33',
		confirmButtonText: 'Yes, delete it!'
	}).then((result) => {
		if (result.isConfirmed) {
			window.location = "/user/delete-contact/"+page+"/"+id;
			Swal.fire(
				'Deleted!',
				'Your file has been deleted.',
				'success'
			)
		} else {
			Swal.fire(
				'Safe',
				'Your file has been Safe.',
				'success'
			)
		}
	});

}

/*Log out Alert Message*/
function logoutComfirm() {

	Swal.fire({
		title: 'Are you sure?',
		text: "Are you sure you want to log out of your account?",
		icon: 'warning',
		showCancelButton: true,
		confirmButtonColor: '#3085d6',
		cancelButtonColor: '#d33',
		confirmButtonText: 'Yes, Logout Now!'
	}).then((result) => {
		if (result.isConfirmed) {
			window.location = "/logout";
			Swal.fire(
				'Logout Success!',
				'Have You Nice Day Bye :)',
				'success'
			)
		} else {
			Swal.fire(
				'Safe',
				'Take a rest! And Do Some Great Work.',
				'success'
			)
		}
	});

}


/* END - User Contact delete show popup alert function*/



/*////////////////////////////////////////////////////*/


/*START Search User Contact */

const searchContact = () => {
	let query = $("#input-val").val();
	if (query == "") {
		// skip
		$(".search-result").hide();

	} else {
		// search 				
		/*send request to server for fetch data*/
		let url = `http://localhost:8282/search/${query}`;

		fetch(url)
			.then((response) => {
				return response.json();
			})
			.then((data) => {

				let element = `<div class='list-group'> `;

				data.forEach((contact) => {
					element += `<a href='http://localhost:8282/user/contact-profile/0/${contact.cId}' class='list-group-item list-group-item-action'>${contact.firstName} ${contact.lastName}</a>`;
				})
				
				if(data == '') {
					element += `<span class='text-center'>No Contact Found!<span>`;
				}

				element += `</div>`;

				$(".search-result").html(element);

				$(".search-result").show();
			})
	}
}


/*END Search User Contact */




/*START User Profile Update Profile Image progress btn*/

let sleep = (time) => new Promise((resolve) => {
	setTimeout(resolve, time);
});

let upload = document.querySelector(".upload");
let uploadBtn = document.querySelector(".upload__button");

uploadBtn.addEventListener("click", async () => {
	upload.classList.add("uploading");
	await sleep(3000);
	upload.classList.add("uploaded");
	await sleep(2000);
	upload.classList.remove("uploading");
	upload.classList.add("uploaded-after");
	await sleep(1000);
	upload.className = "upload";
});

/*END User Profile Update Image Progress Btn*/





