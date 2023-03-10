
let userName = sessionStorage.getItem("username");
let ownerName = sessionStorage.getItem("ownerName")
let ownerEmail = sessionStorage.getItem("ownerEmail")
let ownerMob = sessionStorage.getItem("ownerMob");
let ownerId = sessionStorage.getItem("ownerId");

// console.log(userName);
document.getElementById("ownerName").innerHTML = ownerName;
document.getElementById("ownerEmail").innerHTML = ownerEmail
document.getElementById("ownerMob").innerHTML = ownerMob
document.getElementById("username").innerHTML = userName;

const logout = document.getElementById("logoutbtn")
logout.addEventListener('click', () => {
  sessionStorage.clear()
  window.location.href = 'HomePage.html'
});

const startJournery = document.getElementById("st-jn");
startJournery.addEventListener('click', (e) => {


  const startj = document.getElementById("journey");
  const startI = document.getElementById("start-modal-ride")
  e.preventDefault();
  startI.addEventListener('click', (e) => {
    e.preventDefault();
  const formData = new FormData(startj);
  let formDataObject = Object.fromEntries(formData.entries());
  formDataObject.ownerId = ownerId;
  let formDataJsonString = JSON.stringify(formDataObject);
  console.log(formDataJsonString);
  fetch('http://localhost:8080/CarPool/ride/createride', {
    method: 'POST',
    //Set the headers that specify you're sending a JSON body request and accepting JSON response
    headers: {
        "Content-Type": "application/json",
        Accept: "application/json",
    },
    body: formDataJsonString
  }).then((res)=> res.json())
  .then((data)=>{
    alert('Journey Created')
    console.log(data)
  }).catch((err)=>{
    alert(err);
  })
})
  
  const citiesdrop1 = document.getElementById("citiesdropdown1")
  const citiesdrop2 = document.getElementById("citiesdropdown2")
  const citiesdrop3 = document.getElementById("citiesdropdown3")
  const citiesdrop4 = document.getElementById("citiesdropdown4")
  const citiesdrop5 = document.getElementById("citiesdropdown5")
  const citiesdrop6 = document.getElementById("citiesdropdown6")
  const carsdrop = document.getElementById("carsdropdown")

  fetch('http://localhost:8080/CarPool/car/getallcarsofowner/' + ownerId, {
    method:'GET'
  }).then(res=>res.json())
    .then((data)=>{
      let cname = "Select"
      let option = document.createElement("option")
        option.setAttribute('value', cname);

        let optionText = document.createTextNode(cname);
        option.appendChild(optionText);

        carsdrop.appendChild(option);
      for (let i = 0; i < data.length; i++) {
        let carname = data[i].carName
        // console.log(cityname)
        let option = document.createElement("option")
        option.setAttribute('value', carname);

        let optionText = document.createTextNode(carname);
        option.appendChild(optionText);

        carsdrop.appendChild(option);
        // document.getElementById("car").value = carname;
        
      }
      const carsdrop1 = document.getElementById("carsdropdown");
  carsdrop1.addEventListener(`change`, (e) => {
    const select = e.target;
    const value = select.value;
    const desc = select.options[select.selectedIndex].text;
    document.getElementById("car").value = desc;
  });
      
    })

  fetch('http://localhost:8080/CarPool/city/allcities', {
    method: 'GET',
  }).then(res => res.json())
    .then((data) => {

      // console.log(data)


      for (let i = 0; i < data.length; i++) {
        let cityname = data[i].cityName
        let option = document.createElement("option")
        option.setAttribute('value', cityname);

        let optionText = document.createTextNode(cityname);
        option.appendChild(optionText);

        citiesdrop1.appendChild(option);

      }
      for (let i = 0; i < data.length; i++) {
        let cityname = data[i].cityName
        // console.log(cityname)
        let option = document.createElement("option")
        option.setAttribute('value', cityname);

        let optionText = document.createTextNode(cityname);
        option.appendChild(optionText);

        citiesdrop2.appendChild(option);

      }
      for (let i = 0; i < data.length; i++) {
        let cityname = data[i].cityName
        // console.log(cityname)
        let option = document.createElement("option")
        option.setAttribute('value', cityname);

        let optionText = document.createTextNode(cityname);
        option.appendChild(optionText);

        citiesdrop3.appendChild(option);

      }
      for (let i = 0; i < data.length; i++) {
        let cityname = data[i].cityName
        // console.log(cityname)
        let option = document.createElement("option")
        option.setAttribute('value', cityname);

        let optionText = document.createTextNode(cityname);
        option.appendChild(optionText);

        citiesdrop4.appendChild(option);

      }
      for (let i = 0; i < data.length; i++) {
        let cityname = data[i].cityName
        // console.log(cityname)
        let option = document.createElement("option")
        option.setAttribute('value', cityname);

        let optionText = document.createTextNode(cityname);
        option.appendChild(optionText);

        citiesdrop5.appendChild(option);

      }
      for (let i = 0; i < data.length; i++) {
        let cityname = data[i].cityName
        // console.log(cityname)
        let option = document.createElement("option")
        option.setAttribute('value', cityname);

        let optionText = document.createTextNode(cityname);
        option.appendChild(optionText);

        citiesdrop6.appendChild(option);

      }
    }).catch((err) => {
      alert(err);
      console.log(err)
    });

  const cd1 = document.getElementById("citiesdropdown1");
  cd1.addEventListener(`change`, (e) => {
    const select = e.target;
    const value = select.value;
    const desc = select.options[select.selectedIndex].text;
    document.getElementById("start").value = desc;
  });
  const cd2 = document.getElementById("citiesdropdown2");
  cd2.addEventListener(`change`, (e) => {
    const select = e.target;
    const value = select.value;
    const desc = select.options[select.selectedIndex].text;
    document.getElementById("inter1").value = desc;
  });
  const cd3 = document.getElementById("citiesdropdown3");
  cd3.addEventListener(`change`, (e) => {
    const select = e.target;
    const value = select.value;
    const desc = select.options[select.selectedIndex].text;
    document.getElementById("inter2").value = desc;
  });
  const cd4 = document.getElementById("citiesdropdown4");
  cd4.addEventListener(`change`, (e) => {
    const select = e.target;
    const value = select.value;
    const desc = select.options[select.selectedIndex].text;
    document.getElementById("inter3").value = desc;
  });
  const cd5 = document.getElementById("citiesdropdown5");
  cd5.addEventListener(`change`, (e) => {
    const select = e.target;
    const value = select.value;
    const desc = select.options[select.selectedIndex].text;
    document.getElementById("inter4").value = desc;
  });

  const cd6 = document.getElementById("citiesdropdown6");
  cd6.addEventListener(`change`, (e) => {
    const select = e.target;
    const value = select.value;
    const desc = select.options[select.selectedIndex].text;
    document.getElementById("end").value = desc;
  });

})


const addcarform = document.getElementById("add-car-form")
  const addcaraction = document.getElementById("add-car-modal")
addcaraction.addEventListener('click', (e) => {
  e.preventDefault();
  const formData = new FormData(addcarform);
  let formDataObject = Object.fromEntries(formData.entries());
  formDataObject.ownerId = ownerId;
  let formDataJsonString = JSON.stringify(formDataObject);
  if(formDataObject.carName){
  fetch('http://localhost:8080/CarPool/car/createcar', {

  method: 'POST',
  //Set the headers that specify you're sending a JSON body request and accepting JSON response
  headers: {
      "Content-Type": "application/json",
      Accept: "application/json",
  },
  body: formDataJsonString   
  }).then((res)=> res.json())
    .then((data)=>{
      if(data.carName)
        alert("Car Added")
        console.log(data)
    }).catch((err)=>{
      alert(err);
    })
  }else{
    alert('please fill credentials')
  }
});



const editUser = document.getElementById("editDetails")
editUser.addEventListener('click', (e)=>{
    e.preventDefault();
    document.getElementById("user-name").value = userName;
    document.getElementById("user-email").value = ownerEmail
    document.getElementById("user-mob").value = ownerMob
    document.getElementById("pooler-name").value = ownerName
})

const saveChages = document.getElementById("saveChanges")
const updateuser = document.getElementById("updateUser")
saveChages.addEventListener('click', (e)=>{
    const formData = new FormData(updateuser);
    let formDataObject = Object.fromEntries(formData.entries());
    let formDataJsonString = JSON.stringify(formDataObject);
    const updateuserurl = "http://localhost:8080/CarPool/owner/updateowner/" + ownerId;
    fetch(updateuserurl, {
        method:'POST', 
        headers: {
            "Content-Type": "application/json",
            Accept: "application/json",
        },
        body: formDataJsonString
    }).then(res => res.json())
      .then((res)=>{
        sessionStorage.setItem("ownerName", res.ownerName);
        sessionStorage.setItem("ownerEmail", res.ownerEmail);
        sessionStorage.setItem("ownerMob", res.ownerMob);
        sessionStorage.setItem("username", res.userName)
        sessionStorage.setItem("ownerId", res.ownerId)
        alert('user update')
        console.log(res);
        
      }).catch(err => alert(err))
})


