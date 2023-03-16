
if(sessionStorage.getItem("loggedIn") == "true") {
    window.location.href = "PoolerDashboard.html";
}

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


    fetch('http://localhost:8080/CarPool/pooler/loginpooler', {
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
            window.location.href = 'PoolerDashboard.html'
        }else {
            alert("User not found")
            window.location.href = 'PoolerLogin.html'
        }
    })
        .catch((err) => {
            alert(err);
            console.log(err)
        });


        
});