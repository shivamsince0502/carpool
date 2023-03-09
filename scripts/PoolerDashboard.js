var carMap = new Map();
var ownerMap = new Map();
var ridecitiesMap = new Map();
var rideMap = new Map();
var rides;

let userName = sessionStorage.getItem("userName");
let poolerName = sessionStorage.getItem("poolerName")
let poolerEmail = sessionStorage.getItem("poolerEmail")
let poolerMob = sessionStorage.getItem("poolerMob");

// console.log(userName);
document.getElementById("poolerName").innerHTML = poolerName;
document.getElementById("poolerEmail").innerHTML = poolerEmail
document.getElementById("poolerMob").innerHTML = poolerMob
document.getElementById("username").innerHTML = userName;

const form = document.getElementById('search-form');

const logout = document.getElementById("logoutbtn")
logout.addEventListener('click', () => {
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
    }).catch((err)=>{
        console.log(err);
    })
});


form.addEventListener('submit', (e) => {
    e.preventDefault();

        const showData = document.getElementById("select-ride")
        var allRides = document.createElement('table');
        allRides.setAttribute('id', 'ridestable');
        showData.appendChild(allRides);

        var tableHeadRow = allRides.insertRow(0);

        var tableHeadArray = new Array();
        tableHeadArray = ['Ride no', 'Owner Name', 'Car Name', 'Car Color', 'Start Location', 'End Location','Number of Seats', 'Book Now'];

        for (var i = 0; i < tableHeadArray.length; i++) {
            var th = document.createElement('th');
            th.innerHTML = tableHeadArray[i];
            tableHeadRow.appendChild(th);
        }
        //add border
        allRides.setAttribute('border', '1');

        //add cell padding
        allRides.setAttribute('cellpadding', '10px');

        rides.forEach((item)=>{
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
            var tableDataArray = new Array();
            tableDataArray = [rideId, owner.ownerName, car.carName, car.carColor, cities[0], cities[cities.length-1], no_of_seats];
           
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


});

function bookRide(el) {
    var uTable = document.getElementById('ridestable');
    // uTable.deleteRow(el.parentNode.parentNode.rowIndex);
    let index = el.parentNode.parentNode.rowIndex;
    var oCells = uTable.rows.item(index).cells;
    console.log(index);
    console.log(oCells)
    let rideId = oCells[0].innerHTML;
    let noOfSeats = oCells[6].innerHTML;
    if(noOfSeats > 0){
    let poolerId = sessionStorage.getItem("poolerId");
    var payload = {
        rideId : rideId, 
        poolerId : poolerId
    }
    payload = JSON.stringify(payload);
    // console.log(rideId);
    // console.log(rideMap)
    // let ride = rideMap.get(rideId);
    // console.log(ride)
    // let owner = ownerMap.get(ride.ownerId);
    // let car = carMap.get(ride.carId);
    
    // sessionStorage.setItem("owner", owner);
    // sessionStorage.setItem("ride", ride);
    // sessionStorage.setItem("car", car);
    // alert("Booked Successfully")
    // window.location.href = 'PoolerAfterBooking.html'
    // console.log(rideId)
    // console.log(poolerId)
    // console.log(payload)

    const requrl = "http://localhost:8080/CarPool/ride/bookrequest";
    fetch(requrl, {
        method: 'POST',
        //Set the headers that specify you're sending a JSON body request and accepting JSON response
        headers: {
            "Content-Type": "application/json",
            Accept: "application/json",
        },
        body: payload
    }).then(res => res.json())
      .then((data)=>{
        alert('Booking Confirmed')
        location.reload();
        console.log(data)
      }).catch((err)=>{
        alert(err);
        console.log(err);
      })
    }else{
        alert('no seats available')
    }
    
}
