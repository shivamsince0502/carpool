
const form = document.getElementById('register-form');
form.addEventListener('submit', (e)=>{
    e.preventDefault();

    const formData = new FormData(form);
    // const data = new URLSearchParams(formData);
     //Create an object from the form data entries
  let formDataObject = Object.fromEntries(formData.entries());
    // Format the plain form data as JSON
    let formDataJsonString = JSON.stringify(formDataObject);

    if(formDataObject.ownerName === " " || formDataObject.ownerName === "   " || formDataObject.ownerName === "      ") {
      document.getElementById("err-details").innerHTML = "Name can't be Empty";
      // $("#error-modal").modal('show');
      alert("Name can't be empty.")
    }else if(formDataObject.ownerMob.length != 10) {
      alert("Phone number should be of equal to 10")
    }else if(formDataObject.userName === " " || formDataObject.userName === "   " || formDataObject.userName === "      "){
      alert("Username can't be empty.")
    }
    else {

    fetch(serverurl + 'owner/create', {
        method:'POST', 
         //Set the headers that specify you're sending a JSON body request and accepting JSON response
    headers: {
        "Content-Type": "application/json",
        Accept: "application/json",
      },
        body: formDataJsonString
    }).then(res => res.json())
      .then((res) => {
        let sub = "Registration for carpool as owner "
        let body = "You have registered for as owner on carpool "
        sendmail(formDataObject.ownerEmail, sub, body)
        
        console.log(data)
      
      })
      .catch(err => console.log(err));
    }
});

function sendmail(to, subjectE, bodyE) {
  Email.send({
      Host: "smtp.elasticemail.com",
      Username: "thenexus6969@gmail.com",
      Password: "2FC79D41401DD22806E97D74595A174D47AA",
      From: "kumar.shivam.cse@gmail.com",
      To: to,
      Subject: subjectE,
      Body: bodyE
  }).then(
      message => {
          if(message === 'OK' || message === 'ok' || message === 'Ok') {
            alert("Registered Successfully")
            window.location.href = 'OwnerLogin.html'
        }else {
            alert("User already exist or wrong data")
            window.location.href = 'OwnerRegistration.html'
        }
      }
  );
}


if(sessionStorage.getItem("loggedIn") == "true") {
  window.location.href = "OwnerDashboard.html";
}


function showToast(message, type) {
  console.log(message)
  var toast = document.createElement('div');
  toast.classList.add('toast');

  if (type === 'success') {
    toast.classList.add('toast-success');
  } else if (type === 'error') {
    toast.classList.add('toast-error');
  }

  toast.innerText = message;

  var toastContainer = document.getElementById('toast-container');
  toastContainer.appendChild(toast);

  setTimeout(function() {
    toast.remove();
  }, 3000);
}