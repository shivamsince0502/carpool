

const form = document.getElementById('login-form');
form.addEventListener('submit', (e)=>{
    e.preventDefault();

    const formData = new FormData(form);
    // const data = new URLSearchParams(formData);
     //Create an object from the form data entries
  let formDataObject = Object.fromEntries(formData.entries());
    // Format the plain form data as JSON
    let formDataJsonString = JSON.stringify(formDataObject);


    fetch(serverurl + 'owner/loginowner', {
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
          sessionStorage.setItem("profileurl", "images/user.png");
          showToast("Welcome " + res.ownerName, 'success')
          window.location.href = 'ownerdashboard'
      }else {
          showToast("User not found", 'error')
      }
  })
      .catch((err) => {
          showToast("username or password is wrong", 'error');
          console.log(err)
      });
});


if(sessionStorage.getItem("loggedIn") == "true") {
    window.location.href = "ownerdashboard";
}

function showToast(message, type) {
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
  