


const form = document.getElementById('login-form');
form.addEventListener('submit', (e) => {
    e.preventDefault();

    const formData = new FormData(form);
    // const data = new URLSearchParams(formData);
    //Create an object from the form data entries
    let formDataObject = Object.fromEntries(formData.entries());
    // Format the plain form data as JSON
    let formDataJsonString = JSON.stringify(formDataObject);

    console.log(formData);
    console.log(formDataObject)

    console.log(formDataJsonString);


    fetch(serverurl + 'pooler/loginpooler', {
        method: 'POST',
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
        if(res.poolerName) {
            sessionStorage.setItem("poolerName", res.poolerName);
            sessionStorage.setItem("poolerEmail", res.poolerEmail);
            sessionStorage.setItem("poolerMob", res.poolerMob);
            sessionStorage.setItem("username", res.userName)
            sessionStorage.setItem("poolerId", res.poolerId)
            sessionStorage.setItem("loggedIn", "true"); 
            sessionStorage.setItem("profileurl", "images/user.png");
            window.location.href = 'poolerDashboard'
        }else {
            alert("User not found")
        }
    })
        .catch((err) => {
            alert(err);
            console.log(err)
        });


        
});

if(sessionStorage.getItem("loggedIn") == "true") {
    window.location.href = "poolerdashboard";
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