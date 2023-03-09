let userName = sessionStorage.getItem("userName");
let poolerName = sessionStorage.getItem("poolerName")
let poolerEmail = sessionStorage.getItem("poolerEmail")
let poolerMob = sessionStorage.getItem("poolerMob");

// console.log(userName);
document.getElementById("poolerName").innerHTML = poolerName;
document.getElementById("poolerEmail").innerHTML = poolerEmail
document.getElementById("poolerMob").innerHTML = poolerMob
document.getElementById("username").innerHTML = userName;