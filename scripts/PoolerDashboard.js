var carMap = new Map();
var ownerMap = new Map();
var ridecitiesMap = new Map();
var rideMap = new Map();
var rides;

const serverurl = 'http://localhost:8080/CarPool/'

// document.getElementById('title').value = poolerName

const dashbtn = document.getElementById("show-card-dash")
dashbtn.addEventListener('click', (e) => {
    document.getElementById('show-data-prev').style.display = "none"
    document.getElementById('show-data-curr').style.display = "none"
    document.getElementById('show-user-card').style.display = "block"
    document.getElementById('search-rides-div').style.display = "none"

})

document.getElementById('doj').min = new Date().toISOString().split("T")[0];

document.getElementById('edoj').min = new Date().toISOString().split("T")[0];
document.getElementById("myImage").setAttribute('src', sessionStorage.getItem('profileurl'))


if (sessionStorage.getItem("loggedIn") != "true") {
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
document.getElementById("side-username").innerHTML = poolerName;
document.getElementById("side-email").innerHTML = userName;


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
async function getallcities() {

    document.getElementById('show-data-prev').style.display = "none"
    document.getElementById('show-data-curr').style.display = "none"
    document.getElementById('search-rides-div').style.display = "none"
    document.getElementById('show-user-card').style.display = "block"

    const today = new Date();
    const myDateInput = document.getElementById("doj");
    myDateInput.value = today.toISOString().slice(0, 10);

    const myDateInput1 = document.getElementById("edoj");
    myDateInput1.value = today.toISOString().slice(0, 10);

    let allnotifurl = serverurl + 'pooler/allnotifofpooler/' + poolerId;
    let data = await fetch(allnotifurl);
    let allnotif = await data.json();
    const notifpop = document.getElementById("no-of-notif")
    const cmnt = document.getElementById("comment")
    if (allnotif.length <= 0) {
        notifpop.style.display = "none"
        cmnt.style.display = "none"
    }
    else {
        notifpop.style.display = "flex"
        cmnt.style.display = "flex"
        notifpop.innerHTML = allnotif.length
    }

    await fetch(serverurl + 'city/allcities', {
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
    let startD = formDataObject.dateOfJourney
    let endD = formDataObject.endOfJourney
    let startDate = new Date(startD)
    let startTs = startDate.getTime()
    let endDate = new Date(endD)
    let endTs = endDate.getTime()
    console.log(startTs)
    console.log(endTs)
    formDataObject.dateOfJourney = startTs
    formDataObject.endOfJourney = endTs
    // {dateOfJourney: '', start: 'Kankarbagh', end: 'Bermo'}
    sessionStorage.setItem("startPoint", formDataObject.start)
    sessionStorage.setItem("endPoint", formDataObject.end);
    sessionStorage.setItem("dateOfJourney", formDataObject.dateOfJourney)
    sessionStorage.setItem("endOfJourney", formDataObject.endOfJourney)
    console.log(formDataObject)
    let formDataJsonString = JSON.stringify(formDataObject);

    fetch(serverurl + 'ride/findcars', {
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
            let ourl = ser + "owner/owner:" + ownerId;
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

            let curl = serverurl + "car/getcar" + carId;
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

            let rcurl = serverurl + "ride/allcities/" + rideId;
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

    document.getElementById('show-data-curr').style.display = "none"
    document.getElementById('show-data-prev').style.display = "none"
    document.getElementById('show-user-card').style.display = "none"
    document.getElementById('search-rides-div').style.display = "block"

    const showData = document.getElementById("search-ride-tb")
    showData.innerHTML = ""
    var allRides = document.createElement('table');
    document.getElementById("head-tb-search-ride").innerHTML = "All Rides of your search " + poolerName;
    allRides.setAttribute('class', 'table-striped')

    allRides.setAttribute('class', 'table')
    allRides.setAttribute('id', 'allprevrides1');

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
    if (rides.length == 0) {
        document.getElementById("allprevrides1").style.backgroundImage = "url('no-result.jpg')";
    } else {

        rides.forEach((item) => {
            if (rides.length === 0) {
                document.getElementById('show-data-curr').style.display = "none"
                document.getElementById('show-data-prev').style.display = "none"
                document.getElementById('show-user-card').style.display = "none"
                document.getElementById('search-rides-div').style.display = "block"
                document.getElementById("head-tb-search-ride").innerHTML = "No rides four on your date. " + poolerName;
            }
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
    }
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
        let endDateOfJourneyp = sessionStorage.getItem("endOfJourney")
        var payload = {
            rideId: rideId,
            poolerId: poolerId,
            start: startP,
            end: endP,
            dateOfJourney: dateOfJourneyP,
            endDateOfJourney: endDateOfJourneyp
        }
        console.log(payload);
        payload = JSON.stringify(payload); // last 

        const requrl = serverurl + "ride/bookrequest";
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

                let ridefetchurl = serverurl + 'ride/getridebyrideid/' + rideId;
                let rideInfo = await fetch(ridefetchurl, { method: 'GET' })
                let rideData = await rideInfo.json();
                let carId = rideData.carId;
                let ownerId = rideData.ownerId
                let carinfourl = serverurl + 'car/getcar' + carId;
                let resp = await fetch(carinfourl, { method: "GET" })
                let carofride = await resp.json();
                console.log(carofride)
                let citiesinfourl = serverurl + 'ridecities/getallcitiesbyride/' + rideId;
                let respci = await fetch(citiesinfourl, { method: "GET" })
                let allcitiesofride = await respci.json();
                let ownerurlride = serverurl + 'owner/owner:' + ownerId;
                let ownerofride = await fetch(ownerurlride, { method: 'GET' })
                let owner = await ownerofride.json()
                let sub = "Pool Request by " + poolerName
                let bodyMail = "There is pool request from " + poolerName + " with mail id : " +
                    poolerEmail + " and mobile no " + poolerMob + " for your ride with " + rideId +
                    " from " + startP + " to " + endP + " on " + rideData.rideDate;
                console.log(bodyMail)

                let subP = "Pool Request sent to " + owner.ownerName;
                let bodyP = "A pool Request is sent to " + owner.ownerName + " for the requested ride from " + startP + " to " + endP + " on " + rideData.rideDate;
                sendmail(poolerEmail, subP, bodyP);

                sendmail(owner.ownerEmail, sub, bodyMail);

                document.getElementById("owner-name").innerHTML = "Owner Name : " + owner.ownerName;
                document.getElementById("owner-number").innerHTML = "Owner Number : " + owner.ownerMob;
                document.getElementById("car-name").innerHTML = "Car Name : " + carofride.carName;
                document.getElementById("car-color").innerHTML = "Car Color : " + carofride.carColor
                document.getElementById("car-number").innerHTML = "Car Number : " + carofride.carNumber



                let citieslistride = document.getElementById("listofcitiesride")
                for (i = 0; i < allcitiesofride.length; ++i) {
                    var li = document.createElement('li');
                    li.setAttribute('class', 'list-group-item')
                    li.innerText = allcitiesofride[i].cityName;
                    citieslistride.appendChild(li);
                }

                $("#afterbooking").modal('show');
                // after booking
            }).catch((err) => {
                alert('You cant book same ride again');
                console.log(err);
            })
    } else {
        alert('no seats available')
    }

}

function sendmail(to, subjectE, bodyE) {
    Email.send({
        Host: "smtp.elasticemail.com",
        Username: "thenexus6969@gmail.com",
        Password: "2FC79D41401DD22806E97D74595A174D47AA",
        From: "kumar.shivam.cse@gmail.com",
        To: to,
        Subject: subjectE,
        Body: bodyE
    }).then(
        message => {
            console.log(message)
            // alert(message)
        }
    );
}

const editUser = document.getElementById("editDetail")
editUser.addEventListener('click', (e) => {
    e.preventDefault();
    document.getElementById("user-name").value = userName;
    document.getElementById("user-email").value = poolerEmail
    document.getElementById("user-mob").value = poolerMob
    document.getElementById("pooler-name").value = poolerName
})



const saveChages = document.getElementById("saveChanges")
const updateuser = document.getElementById("updateUser")
saveChages.addEventListener('click', (e) => {
    const formData = new FormData(updateuser);
    let formDataObject = Object.fromEntries(formData.entries());
    let formDataJsonString = JSON.stringify(formDataObject);
    const updateuserurl = serverurl + "pooler/updatepooler/" + poolerId;
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
            window.location.href = "PoolerDashboard.html";
            console.log(res);

        }).catch(err => alert(err))
})



const currjourneybtn = document.getElementById("curr-ride")
currjourneybtn.addEventListener('click', async (e) => {
    e.preventDefault();
    document.getElementById('show-user-card').style.display = "none"
    document.getElementById('show-data-prev').style.display = "none"
    document.getElementById('show-data-curr').style.display = "block"


    document.getElementById("head-tb-curr").innerHTML = "All Current Rides of the " + poolerName;
    const showuprides = document.getElementById("select-ride")
    showuprides.innerHTML = ""
    var alluprides = document.createElement('table');
    alluprides.setAttribute('class', 'table-striped')
    alluprides.setAttribute('class', 'table-striped1')
    alluprides.setAttribute('class', 'table')
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

    let uprideurl = serverurl + "pooler/getallupridebypoolerid/" + poolerId;
    await fetch(uprideurl, {
        method: "GET"
    }).then((res) => res.json())
        .then(async (uprides) => {
            for (const item of uprides) {
                let rideId = item.rideId;
                let ridefetchurl = serverurl + 'ride/getridebyrideid/' + rideId;
                let rideInfo = await fetch(ridefetchurl, { method: 'GET' })
                let rideData = await rideInfo.json();
                let ownerId = rideData.ownerId
                let tr = alluprides.insertRow(-1);
                let carId = rideData.carId;
                let rideDate = rideData.rideDate;
                let carinfourl = serverurl + 'car/getcar' + carId;
                let resp = await fetch(carinfourl, { method: "GET" })
                let carofride = await resp.json();
                let oInfourl = serverurl + 'owner/owner:' + ownerId;
                let reso = await fetch(oInfourl, { method: "GET" })
                let owner = await reso.json()
                let citiyrl = serverurl + 'city/getcitybyid/'
                let ny1 = citiyrl + item.startCityId;
                let rescity1 = await fetch(ny1, { method: "GET" })
                let city1 = await rescity1.json()
                let ny2 = citiyrl + item.endCityId
                let rescity2 = await fetch(ny2, { method: "GET" })
                let city2 = await rescity2.json()
                var tableDataArrayOfRide = new Array();
                tableDataArrayOfRide = [rideId, carofride.carName, owner.ownerName, owner.ownerMob, city1.cityName, city2.cityName, rideDate];

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
            }
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
    let finishurl = serverurl + 'ride/finishrideforpooler/' + rideId + "/" + poolerId;
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
    let finishurl = serverurl + 'ride/unbookride/' + rideId + "/" + poolerId;
    let res = await fetch(finishurl, { method: 'POST' })
    let data = await res.json();
    if (data.rideId)
        alert('Ride UnBooked')
    else
        alert('Un Booking Failed')
}



const prevbtn = document.getElementById("prev-ride")
prevbtn.addEventListener('click', async (e) => {
    e.preventDefault();
    console.log("prev ride pressed")

    document.getElementById('show-data-curr').style.display = "none"
    document.getElementById('show-user-card').style.display = "none"
    document.getElementById('show-data-prev').style.display = "block"

    const showprevrides = document.getElementById("prev-rides")
    showprevrides.innerHTML = ""
    var allprevrides = document.createElement('table');
    document.getElementById("head-tb-prev").innerHTML = "All Previous Rides of " + poolerName;
    allprevrides.setAttribute('class', 'table-striped')
    allprevrides.setAttribute('class', 'table')
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

    let uprideurl = serverurl + "pooler/getallprevridebypoolerid/" + poolerId;
    await fetch(uprideurl, {
        method: "GET"
    }).then(async (res) => await res.json())
        .then(async (uprides) => {
            console.log(uprides)
            for (const item of uprides) {
                let rideId = item.rideId;
                let ridefetchurl = serverurl + 'ride/getridebyrideid/' + rideId;
                let rideInfo = await fetch(ridefetchurl, { method: 'GET' })
                let rideData = await rideInfo.json();

                console.log(rideData)
                let ownerId = rideData.ownerId
                let tr = allprevrides.insertRow(-1);
                let carId = rideData.carId;
                let rideDate = rideData.rideDate;
                let carinfourl = serverurl + 'car/getcar' + carId;
                let resp = await fetch(carinfourl, { method: "GET" })
                let carofride = await resp.json();
                let oInfourl = serverurl + 'owner/owner:' + ownerId;
                let reso = await fetch(oInfourl, { method: "GET" })
                let owner = await reso.json()
                let citiyrl = serverurl + 'city/getcitybyid/'
                let ny1 = citiyrl + item.startCityId;
                let rescity1 = await fetch(ny1, { method: "GET" })
                let city1 = await rescity1.json()
                let ny2 = citiyrl + item.endCityId
                let rescity2 = await fetch(ny2, { method: "GET" })
                let city2 = await rescity2.json()


                var tableDataArrayOfRide = new Array();
                tableDataArrayOfRide = [rideId, carofride.carName, owner.ownerName, owner.ownerMob, city1.cityName, city2.cityName, rideDate];

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
            }
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
    let delrideurl = serverurl + 'pooler/deleteride/' + rideId + '/' + poolerId;
    let res = await fetch(delrideurl, { method: 'POST' })
    let nres = res.json();
    console.log(nres)
    alert('Ride Deleted')
}





const bellicon = document.getElementById("bell-icon")
bellicon.addEventListener('click', async (e) => {
    e.preventDefault();
    let allnotifurl = serverurl + 'pooler/allnotifofpooler/' + poolerId;
    let data = await fetch(allnotifurl);
    let allnotif = await data.json();
    console.log(allnotif)


    const notifytablediv = document.getElementById("notif-table-div")
    notifytablediv.innerHTML = "";
    var notifytable = document.createElement('table');
    notifytable.setAttribute('class', 'table-striped')
    notifytable.setAttribute('class', 'table')
    notifytable.setAttribute('class', 'table-hover')
    notifytable.setAttribute('id', 'notify-table');
    notifytablediv.appendChild(notifytable);

    var notifytablehead = notifytable.insertRow(0);
    var tableHeadArray = new Array();
    tableHeadArray = ['Msg no', 'Message'];
    // console.log(tableHeadArray)
    for (var i = 0; i < tableHeadArray.length; i++) {
        var th = document.createElement('th');
        th.setAttribute('scope', 'row');
        th.innerHTML = tableHeadArray[i];
        notifytablehead.appendChild(th);
    }
    console.log(notifytablehead)


    allnotif.forEach(async (message) => {
        let msg = message.message;

        var notiftabledata = new Array();
        notiftabledata = [message.notificationId, msg];

        var tr = notifytable.insertRow(-1)

        for (var i = 0; i < notiftabledata.length; i++) {

            var td = tr.insertCell(-1);
            td.innerHTML = notiftabledata[i];
        }
        var td = tr.insertCell(-1);
        var button = document.createElement('button');
        button.setAttribute('type', 'button');
        button.setAttribute('class', 'btn btn-dark')
        button.innerHTML = 'Ok';
        button.setAttribute('onclick', 'readMsg(this)');
        td.appendChild(button);

    })

})


async function readMsg(el) {
    var uTable = document.getElementById('notify-table');
    let index = el.parentNode.parentNode.rowIndex;
    var oCells = uTable.rows.item(index).cells;
    let msgId = oCells[0].innerHTML;
    let readurl = serverurl + 'pooler/msgread/' + msgId;
    let data = await fetch(readurl, { method: 'POST' })
    let res = await data.json();
    console.log(res);
}


function changeImage() {
    var fileInput = document.createElement("input");
    fileInput.type = "file";
    fileInput.accept = "image/*";
    fileInput.onchange = function (event) {
        var file = event.target.files[0];
        var reader = new FileReader();
        reader.onload = function () {
            var image = document.getElementById("myImage");
            sessionStorage.setItem('profileurl', reader.result);
            image.src = sessionStorage.getItem('profileurl');
        };
        reader.readAsDataURL(file);
    };
    fileInput.click();
}


