// GET USER FROM LOGIN
const user = JSON.parse(localStorage.getItem("user"));

document.getElementById("userInfo").innerText =
    "Logged in as: " + user.email + " (" + user.role + ")";

// SWITCH SECTIONS
function showSection(id) {
    document.querySelectorAll(".section").forEach(sec => {
        sec.classList.add("hidden");
    });

    document.getElementById(id).classList.remove("hidden");
}

// LOGOUT
function logout() {
    localStorage.clear();
    window.location.href = "index.html";
}

// SUBMIT CLAIM (dummy for now)
function submitClaim() {

    const claim = {
        amount: document.getElementById("amount").value,
        claimDate: document.getElementById("date").value,
        description: document.getElementById("description").value,
        employeeId: user.id
    };

    fetch("http://localhost:8080/api/claims", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(claim)
    })
    .then(res => res.json())
    .then(data => {
        alert("Claim submitted!");
    });
}