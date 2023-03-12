
let userName = sessionStorage.getItem("username");
let ownerName = sessionStorage.getItem("ownerName")
let ownerEmail = sessionStorage.getItem("ownerEmail")
let ownerMob = sessionStorage.getItem("ownerMob");
let ownerId = sessionStorage.getItem("ownerId");

if(sessionStorage.getItem("loggedIn") != "true"){
  window.location.href = "HomePage.html";
}

document.getElementById('doj').min = new Date().toISOString().split("T")[0];
document.getElementById('edit-doj').min = new Date().toISOString().split("T")[0];

// console.log(userName);
document.getElementById("ownerName").innerHTML = ownerName;
document.getElementById("ownerEmail").innerHTML = ownerEmail
document.getElementById("ownerMob").innerHTML = ownerMob
document.getElementById("username").innerHTML = userName;

const logout = document.getElementById("logoutbtn")
logout.addEventListener('click', (e) => {
  e.preventDefault();
  sessionStorage.clear()
  window.location.href = 'HomePage.html'
});

var citiesofride = [];

const startJournery = document.getElementById("st-jn");
startJournery.addEventListener('click', async (e) => {
  // location.onload();

  const startj = document.getElementById("journey");
  const startI = document.getElementById("start-modal-ride")
  e.preventDefault();

  startI.addEventListener('click', async (e) => {
    e.preventDefault();
    const formData = new FormData(startj);
    let formDataObject = Object.fromEntries(formData.entries());
    formDataObject.ownerId = ownerId;
    formDataObject.citiesList = citiesofride;
    let formDataJsonString = JSON.stringify(formDataObject);
    console.log(formDataJsonString);
    console.log(formDataObject)

    if (formDataObject.noOfSeats && formDataObject.carName && citiesofride.length > 1) {
      await fetch('http://localhost:8080/CarPool/ride/createride', {
        method: 'POST',
        //Set the headers that specify you're sending a JSON body request and accepting JSON response
        headers: {
          "Content-Type": "application/json",
          Accept: "application/json",
        },
        body: formDataJsonString
      }).then((res) => res.json())
        .then(async (data) => {

          if (data.rideId === null) {
            alert("Ride on same date with same car cannot be created.")
          } else {
            let rideId = data.rideId;
            let rideData = data
            let carId = rideData.carId;
            let ownerId = rideData.ownerId
            let carinfourl = 'http://localhost:8080/CarPool/car/getcar' + carId;
            let resp = await fetch(carinfourl, { method: "GET" })
            let carofride = await resp.json();
            console.log(carofride)
            let citiesinfourl = 'http://localhost:8080/CarPool/ridecities/getallcitiesbyride/' + rideId;
            let respci = await fetch(citiesinfourl, { method: "GET" })
            let allcitiesofride = await respci.json();
            let ownerurlride = 'http://localhost:8080/CarPool/owner/owner:' + ownerId;
            let ownerofride = await fetch(ownerurlride, { method: 'GET' })
            let owner = await ownerofride.json()
            document.getElementById("owner-name").innerHTML = "Owner Name : " + owner.ownerName;
            document.getElementById("owner-number").innerHTML = "Owner Number : " + owner.ownerMob;
            document.getElementById("car-name").innerHTML = "Car Name : " + carofride.carName;
            document.getElementById("car-color").innerHTML = "Car Color : " + carofride.carColor
            document.getElementById("car-number").innerHTML = "Car Number : " + carofride.carNumber
            document.getElementById("dateofjourney").innerHTML = "Date Of Journey : " + rideData.rideDate
            let citieslistride = document.getElementById("listofcitiesride")
            for (i = 0; i < allcitiesofride.length; ++i) {
              var li = document.createElement('li');
              li.innerText = allcitiesofride[i].cityName;
              citieslistride.appendChild(li);
            }

            $("#modalAfterBooking").modal('show')

          }

        }).catch((err) => {
          alert("Ride on same date with same car cannot be created.")
          console.log(err)

        })
    } else {
      alert('Please fill start point, end point and no of seats and car')
    }
  })

  const carsMap = new Map();
  const citiesdrop1 = document.getElementById("citiesdropdown1")
  const carsdrop = document.getElementById("carsdropdown")

  await fetch('http://localhost:8080/CarPool/car/getallcarsofowner/' + ownerId, {
    method: 'GET'
  }).then(res => res.json())
    .then((data) => {
      let cname = "Select"
      let option = document.createElement("option")
      option.setAttribute('value', cname);

      let optionText = document.createTextNode(cname);
      option.appendChild(optionText);

      carsdrop.appendChild(option);
      for (let i = 0; i < data.length; i++) {
        let carname = data[i].carName
        carsMap.set(data[i].carId, data[i])
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

  let alldata = await fetch('http://localhost:8080/CarPool/city/allcities', { method: 'GET' })
  let data = await alldata.json();

  let cname = "Select"
  let option = document.createElement("option")
  option.setAttribute('value', cname);

  let optionText = document.createTextNode(cname);
  option.appendChild(optionText);

  for (let i = 0; i < data.length; i++) {
    let cityname = data[i].cityName
    let option = document.createElement("option")
    option.setAttribute('value', cityname);

    let optionText = document.createTextNode(cityname);
    option.appendChild(optionText);

    citiesdrop1.appendChild(option);

  }

  const citesmapride = document.getElementById("cities-select")
  var addcities = document.createElement('table');
  addcities.setAttribute('id', 'addcitieslist');
  addcities.setAttribute('class', 'table table-hover')
  citesmapride.appendChild(addcities);

  var tableHead = addcities.insertRow(0);

  var tableHeadArray = new Array();
  tableHeadArray = ['City Name', 'Action'];
  let idx = 1;
  for (var i = 0; i < tableHeadArray.length; i++) {
    var tht = document.createElement('th');
    tht.innerHTML = tableHeadArray[i];
    tableHead.appendChild(tht);
  }

  const cd1 = document.getElementById("citiesdropdown1");
  cd1.addEventListener(`change`, async (e) => {
    const select = e.target;
    const value = select.value;
    const desc = select.options[select.selectedIndex].text;
    if (desc != citiesofride[citiesofride.length - 1]) {
      document.getElementById("start").value = desc;
      citiesofride.push(desc)
      var newtr = addcities.insertRow(-1);
      var ntrdata = newtr.insertCell(-1)
      ntrdata.innerHTML = desc;
      var ntricon = newtr.insertCell(-1);
      // add a button
      var button = document.createElement('button');
      // set button attributes.
      button.setAttribute('type', 'button');
      button.setAttribute('class', 'btn btn-dark')
      button.innerHTML = 'Delete';
      // set onclick event.
      button.setAttribute('onclick', 'deletecity(this)');
      ntricon.appendChild(button);
    }

  });

})


const addcarform = document.getElementById("add-car-form")
const addcaraction = document.getElementById("add-car-modal")
addcaraction.addEventListener('click', async (e) => {
  e.preventDefault();
  const formData = new FormData(addcarform);
  let formDataObject = Object.fromEntries(formData.entries());
  formDataObject.ownerId = ownerId;
  let formDataJsonString = JSON.stringify(formDataObject);
  if (formDataObject.carName) {
    await fetch('http://localhost:8080/CarPool/car/createcar', {

      method: 'POST',
      //Set the headers that specify you're sending a JSON body request and accepting JSON response
      headers: {
        "Content-Type": "application/json",
        Accept: "application/json",
      },
      body: formDataJsonString
    }).then((res) => res.json())
      .then((data) => {
        if (data.carName)
          alert("Car Added")
        else
          alert('Car Details wrong or already exists')
        console.log(data)
      }).catch((err) => {
        alert(err);
      })
  } else {
    alert('please fill credentials')
  }
});



const editUser = document.getElementById("editDetails")
editUser.addEventListener('click', (e) => {
  e.preventDefault();
  document.getElementById("user-name").value = userName;
  document.getElementById("user-email").value = ownerEmail
  document.getElementById("user-mob").value = ownerMob
  document.getElementById("pooler-name").value = ownerName
})

const saveChages = document.getElementById("saveChanges")
const updateuser = document.getElementById("updateUser")
saveChages.addEventListener('click', async (e) => {
  const formData = new FormData(updateuser);
  let formDataObject = Object.fromEntries(formData.entries());
  let formDataJsonString = JSON.stringify(formDataObject);
  const updateuserurl = "http://localhost:8080/CarPool/owner/updateowner/" + ownerId;
  await fetch(updateuserurl, {
    method: 'POST',
    headers: {
      "Content-Type": "application/json",
      Accept: "application/json",
    },
    body: formDataJsonString
  }).then(res => res.json())
    .then((res) => {
      sessionStorage.setItem("ownerName", res.ownerName);
      sessionStorage.setItem("ownerEmail", res.ownerEmail);
      sessionStorage.setItem("ownerMob", res.ownerMob);
      sessionStorage.setItem("username", res.userName)
      sessionStorage.setItem("ownerId", res.ownerId)
      alert('user update')
      console.log(res);

    }).catch(err => alert(err))
})

async function deletecity(el) {

  var uTable = document.getElementById('addcitieslist');
  let index = el.parentNode.parentNode.rowIndex;
  var oCells = uTable.rows.item(index).cells;
  console.log(index)
  console.log(oCells)
  let delcityname = oCells[0].innerHTML
  console.log(delcityname)
  let eleidx = citiesofride.indexOf(delcityname)
  if (eleidx > -1) citiesofride.splice(eleidx, 1);
  uTable.deleteRow(el.parentNode.parentNode.rowIndex);
}



const myjourneybtn = document.getElementById("my-jn")
myjourneybtn.addEventListener('click', async (e) => {

  const showprevride = document.getElementById("up-journeys")
  var allprevrides = document.createElement('table');
  allprevrides.setAttribute('id', 'allprevrides');
  showprevride.appendChild(allprevrides);
  document.getElementById("tabletitle").innerHTML = 'All Previous Journeys of ' + ownerName
  var prevridetablehead = allprevrides.insertRow(0);

  var tableHeadArray = new Array();
  tableHeadArray = ['Ride no', 'Car Name', 'Car Color', 'Start Location', 'End Location', 'Date', 'Delete'];
  console.log(tableHeadArray)
  for (var i = 0; i < tableHeadArray.length; i++) {
    var th = document.createElement('th');
    th.innerHTML = tableHeadArray[i];
    prevridetablehead.appendChild(th);
  }
  console.log(prevridetablehead)
  //add border
  // allRides.setAttribute('border', '');

  //add cell padding
  allprevrides.setAttribute('cellpadding', '10px');

  let prevrideurl = "http://localhost:8080/CarPool/owner/getallprevrides/" + ownerId;
  await fetch(prevrideurl, {
    method: "GET"
  }).then((res) => res.json())
    .then((prevrides) => {
      console.log(prevrides)
      prevrides.forEach(async (item) => {
        console.log(item);
        let tr = allprevrides.insertRow(-1);
        let carId = item.carId;
        let rideId = item.rideId;
        let no_of_seats = item.noOfSeats
        let rideDate = item.rideDate;


        let carinfourl = 'http://localhost:8080/CarPool/car/getcar' + carId;
        let resp = await fetch(carinfourl, { method: "GET" })
        let carofride = await resp.json();
        console.log(carofride)
        let citiesinfourl = 'http://localhost:8080/CarPool/ridecities/getallcitiesbyride/' + rideId;
        let respci = await fetch(citiesinfourl, { method: "GET" })
        let allcitiesofride = await respci.json();
        console.log(allcitiesofride)
        var tableDataArrayOfRide = new Array();
        tableDataArrayOfRide = [rideId, carofride.carName, carofride.carColor, allcitiesofride[0].cityName, allcitiesofride[allcitiesofride.length - 1].cityName, rideDate];

        for (var i = 0; i < tableDataArrayOfRide.length; i++) {

          var td = tr.insertCell(-1);
          td.innerHTML = tableDataArrayOfRide[i];
        }

        var td = tr.insertCell(-1);

        // add a button
        var button = document.createElement('button');

        // set button attributes.
        button.setAttribute('type', 'button');
        button.setAttribute('class', 'btn btn-dark')
        button.innerHTML = 'Delete';
        // set onclick event.
        button.setAttribute('onclick', 'delThisRide(this)');
        td.appendChild(button);
      })

    })

})

async function delThisRide(el) {
  var uTable = document.getElementById('allprevrides');
  // uTable.deleteRow(el.parentNode.parentNode.rowIndex);
  let index = el.parentNode.parentNode.rowIndex;
  var oCells = uTable.rows.item(index).cells;
  console.log(index);
  console.log(oCells)
  let rideId = oCells[0].innerHTML;
  console.log(rideId);
  let delrideurl = 'http://localhost:8080/CarPool/ride/deleteride/' + rideId;
  let res = await fetch(delrideurl, { method: 'POST' })
  let nres = res.json();
  console.log(nres)
  alert('Ride Deleted')
}



const currjourneybtn = document.getElementById("up-jn")
currjourneybtn.addEventListener('click', async (e) => {

  const showuprides = document.getElementById("up-journeys")
  document.getElementById("tabletitle").innerHTML = 'All current and upcoming journeys of ' + ownerName
  var alluprides = document.createElement('table');
  alluprides.setAttribute('id', 'alluprides');
  showuprides.appendChild(alluprides);

  var upridetablehead = alluprides.insertRow(0);

  var tableHeadArray = new Array();
  tableHeadArray = ['Ride no', 'Car Name', 'Car Color', 'Start Location', 'End Location', 'Date', 'Edit', 'Complete'];
  // console.log(tableHeadArray)
  for (var i = 0; i < tableHeadArray.length; i++) {
    var th = document.createElement('th');
    th.innerHTML = tableHeadArray[i];
    upridetablehead.appendChild(th);
  }
  console.log(upridetablehead)
  //add border
  // allRides.setAttribute('border', '');

  //add cell padding
  alluprides.setAttribute('cellpadding', '10px');

  let uprideurl = "http://localhost:8080/CarPool/owner/getAllUpRides/" + ownerId;
  console.log(ownerId)
  await fetch(uprideurl, {
    method: "GET"
  }).then((res) => res.json())
    .then((uprides) => {
      uprides.forEach(async (item) => {
        console.log(item);
        let tr = alluprides.insertRow(-1);
        let carId = item.carId;
        let rideId = item.rideId;
        let rideDate = item.rideDate;


        let carinfourl = 'http://localhost:8080/CarPool/car/getcar' + carId;
        let resp = await fetch(carinfourl, { method: "GET" })
        let carofride = await resp.json();
        console.log(carofride)
        let citiesinfourl = 'http://localhost:8080/CarPool/ridecities/getallcitiesbyride/' + rideId;
        let respci = await fetch(citiesinfourl, { method: "GET" })
        let allcitiesofride = await respci.json();
        console.log(allcitiesofride)
        var tableDataArrayOfRide = new Array();
        tableDataArrayOfRide = [rideId, carofride.carName, carofride.carColor, allcitiesofride[0].cityName, allcitiesofride[allcitiesofride.length - 1].cityName, rideDate];

        for (var i = 0; i < tableDataArrayOfRide.length; i++) {

          var td = tr.insertCell(-1);
          td.innerHTML = tableDataArrayOfRide[i];
        }

        var td = tr.insertCell(-1);

        // add a button
        var button = document.createElement('button');

        // set button attributes.
        button.setAttribute('type', 'button');
        button.setAttribute('class', 'btn btn-dark')
        button.innerHTML = 'Edit';
        // set onclick event.
        button.setAttribute('onclick', 'editThisRide(this)');
        td.appendChild(button);

        var td1 = tr.insertCell(-1);

        // add a button
        var button1 = document.createElement('button');

        // set button attributes.
        button1.setAttribute('type', 'button');
        button1.setAttribute('class', 'btn btn-dark')
        button1.innerHTML = 'Finish';
        // set onclick event.
        button1.setAttribute('onclick', 'finishThisRide(this)');
        td1.appendChild(button1);
        console.log(td)
        console.log(td1)
      })
    })


})


async function editThisRide(el) {

  var uTable = document.getElementById('alluprides');
  // uTable.deleteRow(el.parentNode.parentNode.rowIndex);
  let index = el.parentNode.parentNode.rowIndex;
  var oCells = uTable.rows.item(index).cells;
  let rideId = oCells[0].innerHTML;
  console.log(rideId);
  let getrideurl = 'http://localhost:8080/CarPool/ride/getridebyrideid/' + rideId
  let res = await fetch(getrideurl, { method: 'GET' })
  let rideDetails = await res.json();
  let carId = rideDetails.carId;
  let carinfourl = 'http://localhost:8080/CarPool/car/getcar' + carId;
  let resp = await fetch(carinfourl, { method: "GET" })
  let carofride = await resp.json();
  console.log(carofride)
  let citiesinfourl = 'http://localhost:8080/CarPool/ridecities/getallcitiesbyride/' + rideId;
  let respci = await fetch(citiesinfourl, { method: "GET" })
  let allcitiesofride = await respci.json();
  console.log(allcitiesofride)
  console.log(rideDetails)
  console.log(carofride)
  document.getElementById("editseats").value = rideDetails.noOfSeats;
  document.getElementById("editcar").value = carofride.carName;
  document.getElementById("editRideId").value = rideId;

  const editcitiesrideidv = document.getElementById("edit-cities")
  var editcities = document.createElement('table');
  editcities.setAttribute('id', 'edit-cities-list');
  editcities.setAttribute('class', 'table table-hover')
  editcitiesrideidv.appendChild(editcities);

  var tableHeadRow = editcities.insertRow(0);

  var tableHeadArray = new Array();
  tableHeadArray = ['City Id', 'City Name'];

  for (var i = 0; i < tableHeadArray.length; i++) {
    var th = document.createElement('th');
    th.innerHTML = tableHeadArray[i];
    tableHeadRow.appendChild(th);
  }


  allcitiesofride.forEach((element) => {
    let cityName = element.cityName;
    let cityId = element.cityId;
    let tra = editcities.insertRow(-1);
    var tdarr = new Array();
    tdarr = [cityId, cityName];
    for (var i = 0; i < tdarr.length; i++) {
      var tdd = tra.insertCell(-1);
      tdd.innerHTML = tdarr[i];
    }

  });

  let editcarsdrop = document.getElementById("editcarsdropdown")


  await fetch('http://localhost:8080/CarPool/car/getallcarsofowner/' + ownerId, {
    method: 'GET'
  }).then(res => res.json())
    .then((data) => {
      let cname = "Select"
      let option = document.createElement("option")
      option.setAttribute('value', cname);

      let optionText = document.createTextNode(cname);
      option.appendChild(optionText);

      editcarsdrop.appendChild(option);
      for (let i = 0; i < data.length; i++) {
        let carname = data[i].carName
        // console.log(cityname)
        let option = document.createElement("option")
        option.setAttribute('value', carname);

        let optionText = document.createTextNode(carname);
        option.appendChild(optionText);

        editcarsdrop.appendChild(option);

      }
      const carsdrop2 = document.getElementById("editcarsdropdown");
      carsdrop2.addEventListener(`change`, (e) => {
        const select = e.target;
        const value = select.value;
        const desc = select.options[select.selectedIndex].text;
        document.getElementById("editcar").value = desc;
      });

    })

  // document.ge

  $("#editride").modal('show');


}


async function finishThisRide(el) {
  var uTable = document.getElementById('alluprides');
  console.log(uTable)
  // uTable.deleteRow(el.parentNode.parentNode.rowIndex);
  let index = el.parentNode.parentNode.rowIndex;
  var oCells = uTable.rows.item(index).cells;
  console.log(index);
  console.log(oCells)
  let rideId = oCells[0].innerHTML;
  console.log(rideId);
  let finishurl = 'http://localhost:8080/CarPool/ride/finishride/' + rideId;
  let res = await fetch(finishurl, { method: 'POST' })
  let data = await res.json();
  if (data.rideId)
    alert('Ride Completed')
  else
    alert('Ride not completed')
}


const editmodalbtn = document.getElementById('edit-modal-btn')
editmodalbtn.addEventListener('click', async (e) => {
  e.preventDefault();
  const editjourneyform = document.getElementById("edit-journey")
  const formData = new FormData(editjourneyform);
  let formDataObject = Object.fromEntries(formData.entries());
  formDataObject.ownerId = ownerId;
  let formDataJsonString = JSON.stringify(formDataObject);
  console.log(formDataJsonString)
  let rideupdateurl = 'http://localhost:8080/CarPool/ride/updateride';
  let res = await fetch(rideupdateurl, {
    method: 'POST',
    //Set the headers that specify you're sending a JSON body request and accepting JSON response
    headers: {
      "Content-Type": "application/json",
      Accept: "application/json",
    },
    body: formDataJsonString
  })
  let updatedride = await res.json();
  console.log(updatedride)
  if (res.ok) {
    alert('Ride updated')
  } else {
    alert('Error occured in updation')
  }


})



