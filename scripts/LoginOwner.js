
const form = document.getElementById('login-form');
form.addEventListener('submit', (e)=>{
    e.preventDefault();

    const formData = new FormData(form);
    // const data = new URLSearchParams(formData);
     //Create an object from the form data entries
  let formDataObject = Object.fromEntries(formData.entries());
    // Format the plain form data as JSON
    let formDataJsonString = JSON.stringify(formDataObject);


    fetch('http://localhost:8080/CarPool/owner/loginowner', {
        method:'POST', 
         //Set the headers that specify you're sending a JSON body request and accepting JSON response
    headers: {
        "Content-Type": "application/json",
        Accept: "application/json",
      },
        body: formDataJsonString
    }).then((res) => {
      return res.json();
  }).then((res) => {
      console.log('response json data', res);
      console.log(res)
      if(res.ownerName) {
          sessionStorage.setItem("ownerName", res.ownerName);
          sessionStorage.setItem("ownerEmail", res.ownerEmail);
          sessionStorage.setItem("ownerMob", res.ownerMob);
          sessionStorage.setItem("username", res.userName)
          sessionStorage.setItem("ownerId", res.ownerId)
          sessionStorage.setItem("loggedIn", "true"); 
          window.location.href = 'ownerDashboard.html'
      }else {
          alert("User not found")
          window.location.href = 'ownerLogin.html'
      }
  })
      .catch((err) => {
          alert("username or password is wrong");
          console.log(err)
      });
});


if(sessionStorage.getItem("loggedIn" == "true")) {
    window.location.href = "OwnerDashboard.html";
}