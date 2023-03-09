
let userName = sessionStorage.getItem("userName");
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
startJournery.addEventListener('submit', (e) => {

  e.preventDefault();

  var formHTML = 
  `<form id="journey">
                <input type="hidden" id="ownerid" name="ownerId" value='${ownerId}'>
				<label for="noOfSeats">No of Seats</label>
				<input type="number" id="seats" name="noOfSeats">

            <div class="form-group fg3">
            <label for="car">Cars</label>
            <div class="dropdown">
              <select id="carsdropdown">
              </select>
            </div>
            <input type="text" name="carName" class="form-control" id="car" placeholder="Car">
          </div>

                <div class="form-group fg1">
                      <label for="start">Start Point</label>
                      <div class="dropdown">
                        <select id="citiesdropdown1">
                        </select>
                      </div>
                      <input type="text" name="startPoint" class="form-control" id="start" placeholder="Start Location">
                    </div>
                    <div class="form-group fg2">
                    <label for="inter1">Intermediate Point</label>
                    <div class="dropdown">
                      <select id="citiesdropdown2">
                      </select>
                    </div>
                    <input type="text" name="inter1Point" class="form-control" id="inter1" placeholder="Intermediary Location">
                  </div>
                
                  <div class="form-group fg3">
                  <label for="inter2">Intermediate Point</label>
                  <div class="dropdown">
                    <select id="citiesdropdown3">
                    </select>
                  </div>
                  <input type="text" name="inter2Point" class="form-control" id="inter2" placeholder="Intermediary Location">
                </div>

                <div class="form-group fg4">
                      <label for="inter3">Intermediate Point</label>
                      <div class="dropdown">
                        <select id="citiesdropdown4">
                        </select>
                      </div>
                      <input type="text" name="inter3Point" class="form-control" id="inter3" placeholder="Intermediary Location">
                    </div>

                    <div class="form-group fg5">
                      <label for="inter3">Intermediate Point</label>
                      <div class="dropdown">
                        <select id="citiesdropdown5">
                        </select>
                      </div>
                      <input type="text" name="inter4Point" class="form-control" id="inter4" placeholder="Intermediary Location">
                    </div>
                
                    <div class="form-group fg6">
                    <label for="end">End Point</label>
                    <div class="dropdown">
                      <select id="citiesdropdown6">
                      </select>
                    </div>
                    <input type="text" name="endPoint" class="form-control" id="end" placeholder="End Location">
                  </div>
                
                <button type="submit"class="btn btn-primary">Begin</button>
			</form>
		`;
  document.getElementById("create-journery").innerHTML = formHTML;
  document.getElementById("ownerid").value = ownerId;

  const startj = document.getElementById("journey");
startj.addEventListener('submit', (e) => {
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






const addcar = document.getElementById("add-car")
addcar.addEventListener('submit', (e)=>{
  e.preventDefault();

  const addcarform = 
`<form id="add-car-form">
    <div class="form-group">
      <label for="carname">Car Name</label>
      <input type="text" name="carName" class="form-control" id="carname" aria-describedby="carname" placeholder="Car Name">
    </div>
    <div class="form-group">
      <label for="carcolor">Car Color</label>
      <input type="text" name="carColor" class="form-control" id="carcolor" placeholder="Car Color">
    </div>
    <div class="form-group">
      <label for="carnumber">Car Number</label>
      <input type="text" name="carNumber" class="form-control" id="carnumber" placeholder="Car Number">
    </div>
    <button type="submit" class="btn btn-dark">Submit</button> 
</form>`
  document.getElementById("addcar").innerHTML = addcarform;
  const addcaraction = document.getElementById("add-car-form")
addcaraction.addEventListener('submit', (e) => {
  e.preventDefault();
  const formData = new FormData(addcaraction);
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
})



