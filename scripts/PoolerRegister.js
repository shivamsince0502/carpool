
const form = document.getElementById('register-form');
form.addEventListener('submit', (e) => {
  e.preventDefault();

  const formData = new FormData(form);
  // const data = new URLSearchParams(formData);
  //Create an object from the form data entries
  let formDataObject = Object.fromEntries(formData.entries());
  // Format the plain form data as JSON
  let formDataJsonString = JSON.stringify(formDataObject);

  if(formDataObject.poolerName === " " || formDataObject.poolerName === "   " || formDataObject.poolerName === "      ") {
    // document.getElementById("err-details").innerHTML = "Name can't be Empty";
    // $("#error-modal").modal('show');
    alert("Name can't be empty.")
  }else if(formDataObject.poolerMob.length != 10) {
    alert("Phone number should be of equal to 10")
  }else if(formDataObject.username === " " || formDataObject.username === "   " || formDataObject.username === "      "){
    alert("Username can't be empty.")
  }
  else {
  fetch('http://localhost:8080/CarPool/pooler/create', {
    method: 'POST',
    //Set the headers that specify you're sending a JSON body request and accepting JSON response
    headers: {
      "Content-Type": "application/json",
      Accept: "application/json",
    },
    body: formDataJsonString
  }).then(res => res.json())
    .then((res) => {
      if (res.poolerName) {
        alert("Registered Successfully")
        window.location.href = 'PoolerLogin.html'
      } else {
        alert("User already exist or wrong data")
        window.location.href = 'PoolerRegistration.html'
      }
      console.log(res)
    })
    .catch((err) => {
      alert(err);
      console.log(err)
    });
  }
});


if(sessionStorage.getItem("loggedIn") == "true") {
  window.location.href = "PoolerDashboard.html";
}