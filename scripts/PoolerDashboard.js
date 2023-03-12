var carMap = new Map();
var ownerMap = new Map();
var ridecitiesMap = new Map();
var rideMap = new Map();
var rides;


if(sessionStorage.getItem("loggedIn") != "true"){
    window.location.href = "HomePage.html";
}

let userName = sessionStorage.getItem("username");
let poolerName = sessionStorage.getItem("poolerName")
let poolerEmail = sessionStorage.getItem("poolerEmail")
let poolerMob = sessionStorage.getItem("poolerMob");
let poolerId = sessionStorage.getItem("poolerId")

// console.log(userName);
document.getElementById("poolerName").innerHTML = poolerName;
document.getElementById("poolerEmail").innerHTML = poolerEmail
document.getElementById("poolerMob").innerHTML = poolerMob
document.getElementById("username").innerHTML = userName;

const form = document.getElementById('search-form');

const logout = document.getElementById("logoutbtn")
logout.addEventListener('click', (e) => {
    e.preventDefault();
    sessionStorage.clear()
    window.location.href = 'HomePage.html'
})

window.onload = getallcities;
const citiesdrop = document.getElementById("citiesdropdown")
const citiesdrop1 = document.getElementById("citiesdropdown1")
function getallcities() {
    fetch('http://localhost:8080/CarPool/city/allcities', {
        method: 'GET',
    }).then(res => res.json())
        .then((data) => {

            // console.log(data)

            for (let i = 0; i < data.length; i++) {
                let cityname = data[i].cityName
                // console.log(cityname)
                let option = document.createElement("option")
                option.setAttribute('value', cityname);

                let optionText = document.createTextNode(cityname);
                option.appendChild(optionText);

                citiesdrop.appendChild(option);

            }
            for (let i = 0; i < data.length; i++) {
                let cityname = data[i].cityName
                // console.log(cityname)
                let option = document.createElement("option")
                option.setAttribute('value', cityname);

                let optionText = document.createTextNode(cityname);
                option.appendChild(optionText);

                citiesdrop1.appendChild(option);

            }

        })
        .catch((err) => {
            alert(err);
            console.log(err)
        });
}


const cd1 = document.querySelector(`[id="citiesdropdown"]`);
cd1.addEventListener(`change`, (e) => {
    const select = e.target;
    const value = select.value;
    const desc = select.options[select.selectedIndex].text;
    document.getElementById("start").value = desc;
});

const cd2 = document.querySelector(`[id="citiesdropdown1"]`);
cd2.addEventListener(`change`, (e) => {
    const select = e.target;
    const value = select.value;
    const desc = select.options[select.selectedIndex].text;
    document.getElementById("end").value = desc;
    const formData = new FormData(form);
    let formDataObject = Object.fromEntries(formData.entries());
    // {dateOfJourney: '', start: 'Kankarbagh', end: 'Bermo'}
    sessionStorage.setItem("startPoint", formDataObject.start)
    sessionStorage.setItem("endPoint", formDataObject.end);
    sessionStorage.setItem("dateOfJourney", formDataObject.dateOfJourney)
    console.log(formDataObject)
    let formDataJsonString = JSON.stringify(formDataObject);

    fetch('http://localhost:8080/CarPool/ride/findcars', {
        method: 'POST',
        //Set the headers that specify you're sending a JSON body request and accepting JSON response
        headers: {
            "Content-Type": "application/json",
            Accept: "application/json",
        },
        body: formDataJsonString
    }).then((res) => {
        return res.json();
    }).then((ridesList) => {
        console.log('response json data', ridesList);


        ridesList.forEach((item) => {

            let ownerId = item.ownerId;
            let carId = item.carId;
            let rideId = item.rideId;
            let ourl = "http://localhost:8080/CarPool/owner/owner:" + ownerId;
            fetch(ourl, {
                method: 'GET',
                headers: {
                    "Content-Type": "application/json",
                    Accept: "application/json",
                }
            }).then((res) => res.json())
                .then((owner) => {
                    console.log(owner);
                    ownerMap.set(ownerId, owner);
                })

            let curl = "http://localhost:8080/CarPool/car/getcar" + carId;
            fetch(curl, {
                method: 'GET',
                headers: {
                    "Content-Type": "application/json",
                    Accept: "application/json",
                }
            }).then((res) => res.json())
                .then((car) => {
                    console.log(car);
                    carMap.set(carId, car);
                })

            let rcurl = "http://localhost:8080/CarPool/ride/allcities/" + rideId;
            fetch(rcurl, {
                method: 'GET',
                headers: {
                    "Content-Type": "application/json",
                    Accept: "application/json",
                }
            }).then((res) => res.json())
                .then((cities) => {
                    console.log(cities);
                    ridecitiesMap.set(rideId, cities);
                })
        });
        rides = ridesList;
    }).catch((err) => {
        console.log(err);
    })
});

const searchridesmoda = document.getElementById("search-rides")

searchridesmoda.addEventListener('click', async (e) => {
    e.preventDefault();

    const showData = document.getElementById("select-ride")
    var allRides = document.createElement('table');
    allRides.setAttribute('id', 'ridestable');
    showData.appendChild(allRides);

    var tableHeadRow = allRides.insertRow(0);

    var tableHeadArray = new Array();
    tableHeadArray = ['Ride no', 'Owner Name', 'Car Name', 'Car Color', 'Start Location', 'End Location', 'Number of Seats', 'Date', 'Book Now'];

    for (var i = 0; i < tableHeadArray.length; i++) {
        var th = document.createElement('th');
        th.innerHTML = tableHeadArray[i];
        tableHeadRow.appendChild(th);
    }
    //add border
    // allRides.setAttribute('border', '');

    //add cell padding
    allRides.setAttribute('cellpadding', '10px');



    rides.forEach((item) => {
        console.log(item);
        let tr = allRides.insertRow(-1);
        let ownerId = item.ownerId;
        let carId = item.carId;
        let rideId = item.rideId;
        rideMap.set(rideId, item)
        let owner = ownerMap.get(ownerId)
        let car = carMap.get(carId)
        let cities = ridecitiesMap.get(rideId)
        let no_of_seats = item.noOfSeats
        let rideDate = item.rideDate;
        var tableDataArray = new Array();
        tableDataArray = [rideId, owner.ownerName, car.carName, car.carColor, cities[0], cities[cities.length - 1], no_of_seats, rideDate];

        for (var i = 0; i < tableDataArray.length; i++) {

            var td = tr.insertCell(-1);
            td.innerHTML = tableDataArray[i];
        }

        var td = tr.insertCell(-1);

        // add a button
        var button = document.createElement('button');

        // set button attributes.
        button.setAttribute('type', 'button');
        button.setAttribute('class', 'btn btn-dark')
        button.innerHTML = 'Book';
        // set onclick event.
        button.setAttribute('onclick', 'bookRide(this)');
        td.appendChild(button);
    })

    // console.log(rideMap )
});

async function bookRide(el) {
    var uTable = document.getElementById('ridestable');
    // uTable.deleteRow(el.parentNode.parentNode.rowIndex);
    let index = el.parentNode.parentNode.rowIndex;
    var oCells = uTable.rows.item(index).cells;
    console.log(index);
    console.log(oCells)
    let rideId = oCells[0].innerHTML;
    let noOfSeats = oCells[6].innerHTML;
    if (noOfSeats > 0 && rideMap.size > 0) {
        let poolerId = sessionStorage.getItem("poolerId");
        let startP = sessionStorage.getItem("startPoint");
        let endP = sessionStorage.getItem("endPoint")
        let dateOfJourneyP = sessionStorage.getItem("dateOfJourney")
        var payload = {
            rideId: rideId,
            poolerId: poolerId,
            start: startP,
            end: endP,
            dateOfJourney: dateOfJourneyP
        }
        payload = JSON.stringify(payload); // last 

        const requrl = "http://localhost:8080/CarPool/ride/bookrequest";
        await fetch(requrl, {
            method: 'POST',
            //Set the headers that specify you're sending a JSON body request and accepting JSON response
            headers: {
                "Content-Type": "application/json",
                Accept: "application/json",
            },
            body: payload
        }).then(res => res.json())
            .then(async (data) => {
                // {"rideId":78,"ownerId":2,"carId":2,"noOfSeats":3,"isActive":true,"rideDate":"Mar 20, 2023"}

                let rideId = data.rideId;

                let ridefetchurl = 'http://localhost:8080/CarPool/ride/getridebyrideid/' + rideId;
                let rideInfo = await fetch(ridefetchurl, { method: 'GET' })
                let rideData = await rideInfo.json();
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
                
                let citieslistride = document.getElementById("listofcitiesride")
                for (i = 0; i < allcitiesofride.length; ++i) {
                    var li = document.createElement('li');
                    li.innerText = allcitiesofride[i].cityName;
                    citieslistride.appendChild(li);
                }

                $("#afterbooking").modal('show');
                // after booking
            }).catch((err) => {
                alert(err);
                console.log(err);
            })
    } else {
        alert('no seats available')
    }

}

const editUser = document.getElementById("editDetail")
editUser.addEventListener('click', (e) => {
    e.preventDefault();
    document.getElementById("user-name").value = userName;
    document.getElementById("user-email").value = poolerEmail
    document.getElementById("user-mob").value = poolerMob
    document.getElementById("pooler-name").value = poolerName
})

document.getElementById('doj').min = new Date().toISOString().split("T")[0];


const saveChages = document.getElementById("saveChanges")
const updateuser = document.getElementById("updateUser")
saveChages.addEventListener('click', (e) => {
    const formData = new FormData(updateuser);
    let formDataObject = Object.fromEntries(formData.entries());
    let formDataJsonString = JSON.stringify(formDataObject);
    const updateuserurl = "http://localhost:8080/CarPool/pooler/updatepooler/" + poolerId;
    fetch(updateuserurl, {
        method: 'POST',
        headers: {
            "Content-Type": "application/json",
            Accept: "application/json",
        },
        body: formDataJsonString
    }).then(res => res.json())
        .then((res) => {
            sessionStorage.setItem("poolerName", res.poolerName);
            sessionStorage.setItem("poolerEmail", res.poolerEmail);
            sessionStorage.setItem("poolerMob", res.poolerMob);
            sessionStorage.setItem("username", res.userName)
            sessionStorage.setItem("poolerId", res.poolerId)
            alert('user update')
            console.log(res);

        }).catch(err => alert(err))
})



const currjourneybtn = document.getElementById("curr-rides")
currjourneybtn.addEventListener('click', async (e) => {

  const showuprides = document.getElementById("select-ride")
  var alluprides = document.createElement('table');
  alluprides.setAttribute('id', 'alluprides');
  showuprides.appendChild(alluprides);

  var upridetablehead = alluprides.insertRow(0);

  var tableHeadArray = new Array();
  tableHeadArray = ['Ride no', 'Car Name', 'Owner Name', 'Owner Number', 'Start Location', 'End Location', 'Date', 'Un Book', 'Complete'];
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

  let uprideurl = "http://localhost:8080/CarPool/pooler/getallupridebypoolerid/" + poolerId;
  await fetch(uprideurl, {
    method: "GET"
  }).then((res) => res.json())
    .then((uprides) => {
      uprides.forEach(async (item) => {
        let rideId = item.rideId;
        let ridefetchurl = 'http://localhost:8080/CarPool/ride/getridebyrideid/' + rideId;
        let rideInfo = await fetch(ridefetchurl, { method: 'GET' })
        let rideData = await rideInfo.json();
        let ownerId = rideData.ownerId
        let tr = alluprides.insertRow(-1);
        let carId = rideData.carId;
        let rideDate = rideData.rideDate;
        let carinfourl = 'http://localhost:8080/CarPool/car/getcar' + carId;
        let resp = await fetch(carinfourl, { method: "GET" })
        let carofride = await resp.json();
        let oInfourl = 'http://localhost:8080/CarPool/owner/owner:' + ownerId;
        let reso = await fetch(oInfourl, {method : "GET"})
        let owner = await reso.json()
        let citiyrl = 'http://localhost:8080/CarPool/city/getcitybyid/'
        let ny1 = citiyrl + item.startCityId;
        let rescity1 = await fetch(ny1, {method : "GET"})
        let city1 = await rescity1.json()
        let ny2 = citiyrl + item.endCityId
        let rescity2 = await fetch(ny2, {method : "GET"})
        let city2 = await rescity2.json()
        var tableDataArrayOfRide = new Array();
        tableDataArrayOfRide = [rideId, carofride.carName,owner.ownerName, owner.ownerMob, city1.cityName, city2.cityName, rideDate];

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
        button.innerHTML = 'Un Book';
        // set onclick event.
        button.setAttribute('onclick', 'unBook(this)');
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
    let finishurl = 'http://localhost:8080/CarPool/ride/finishrideforpooler/' + rideId +"/"+ poolerId;
    let res = await fetch(finishurl, { method: 'POST' })
    let data = await res.json();
    if (data.rideId)
      alert('Ride Completed')
    else
      alert('Ride not completed')
  }

async function unBook(el) {
    var uTable = document.getElementById('alluprides');
    console.log(uTable)
    // uTable.deleteRow(el.parentNode.parentNode.rowIndex);
    let index = el.parentNode.parentNode.rowIndex;
    var oCells = uTable.rows.item(index).cells;
    console.log(index);
    console.log(oCells)
    let rideId = oCells[0].innerHTML;
    let finishurl = 'http://localhost:8080/CarPool/ride/unbookride/' + rideId +"/"+ poolerId;
    let res = await fetch(finishurl, { method: 'DELETE' })
    let data = await res.json();
    if (data.rideId)
      alert('Ride UnBooked')
    else
      alert('Un Booking Failed')
}
  



const prevjourneybtn = document.getElementById("prev-rides")
prevjourneybtn.addEventListener('click', async (e) => {

  const showprevrides = document.getElementById("select-ride")
  var allprevrides = document.createElement('table');
  allprevrides.setAttribute('id', 'allprevrides');
  showprevrides.appendChild(allprevrides);

  var upridetablehead = allprevrides.insertRow(0);

  var tableHeadArray = new Array();
  tableHeadArray = ['Ride no', 'Car Name', 'Owner Name', 'Owner Number', 'Start Location', 'End Location', 'Date', 'Delete'];
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
  allprevrides.setAttribute('cellpadding', '10px');

  let uprideurl = "http://localhost:8080/CarPool/pooler/getallprevridebypoolerid/" + poolerId;
  await fetch(uprideurl, {
    method: "GET"
  }).then((res) => res.json())
    .then((uprides) => {
      uprides.forEach(async (item) => {
        let rideId = item.rideId;
        let ridefetchurl = 'http://localhost:8080/CarPool/ride/getridebyrideid/' + rideId;
        let rideInfo = await fetch(ridefetchurl, { method: 'GET' })
        let rideData = await rideInfo.json();
        let ownerId = rideData.ownerId
        let tr = allprevrides.insertRow(-1);
        let carId = rideData.carId;
        let rideDate = rideData.rideDate;
        let carinfourl = 'http://localhost:8080/CarPool/car/getcar' + carId;
        let resp = await fetch(carinfourl, { method: "GET" })
        let carofride = await resp.json();
        let oInfourl = 'http://localhost:8080/CarPool/owner/owner:' + ownerId;
        let reso = await fetch(oInfourl, {method : "GET"})
        let owner = await reso.json()
        let citiyrl = 'http://localhost:8080/CarPool/city/getcitybyid/'
        let ny1 = citiyrl + item.startCityId;
        let rescity1 = await fetch(ny1, {method : "GET"})
        let city1 = await rescity1.json()
        let ny2 = citiyrl + item.endCityId
        let rescity2 = await fetch(ny2, {method : "GET"})
        let city2 = await rescity2.json()
        var tableDataArrayOfRide = new Array();
        tableDataArrayOfRide = [rideId, carofride.carName,owner.ownerName, owner.ownerMob, city1.cityName, city2.cityName, rideDate];

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
    let delrideurl = 'http://localhost:8080/CarPool/pooler/deleteride/' + rideId +"/"+poolerId;
    let res = await fetch(delrideurl, { method: 'POST' })
    let nres = res.json();
    console.log(nres)
    alert('Ride Deleted')
  }