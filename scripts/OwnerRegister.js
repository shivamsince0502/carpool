
const form = document.getElementById('register-form');
form.addEventListener('submit', (e)=>{
    e.preventDefault();

    const formData = new FormData(form);
    // const data = new URLSearchParams(formData);
     //Create an object from the form data entries
  let formDataObject = Object.fromEntries(formData.entries());
    // Format the plain form data as JSON
    let formDataJsonString = JSON.stringify(formDataObject);


    fetch('http://localhost:8080/CarPool/owner/create', {
        method:'POST', 
         //Set the headers that specify you're sending a JSON body request and accepting JSON response
    headers: {
        "Content-Type": "application/json",
        Accept: "application/json",
      },
        body: formDataJsonString
    }).then(res => res.json())
      .then((res) => {
        if(res.ownerName) {
          alert("Registered Successfully")
          window.location.href = 'OwnerLogin.html'
      }else {
          alert("User already exist or wrong data")
          window.location.href = 'OwnerRegistration.html'
      }
        console.log(data)
      
      })
      .catch(err => console.log(err));
});