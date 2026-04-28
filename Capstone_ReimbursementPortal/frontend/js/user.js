const BASE_URL = "http://localhost:8080/api/users";

/* ---------------- CREATE USER ---------------- */
function createUser() {

    const user = {
        name: document.getElementById("name").value,
        email: document.getElementById("email").value,
        password: document.getElementById("password").value,
        role: document.getElementById("role").value
    };

    fetch(BASE_URL, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(user)
    })
    .then(res => {
        if (!res.ok) throw new Error("Failed to create user");
        return res.json();
    })
    .then(data => {
        alert("User created successfully!");
        loadUsers();
    })
    .catch(err => {
        console.error(err);
        alert("Error creating user");
    });
}

/* ---------------- GET ALL USERS ---------------- */
function loadUsers() {

    fetch(BASE_URL)
    .then(res => res.json())
    .then(data => {

        console.log("API RESPONSE:", data);

            // SAFE CHECK
            if (!Array.isArray(data)) {
                console.error("Not an array:", data);
                return;
            }

            let table = "";

            data.forEach(user => {
                table += `
                    <tr>
                        <td>${user.id}</td>
                        <td>${user.name}</td>
                        <td>${user.email}</td>
                        <td>${user.role}</td>
                    </tr>
                `;
            });

            document.getElementById("userTable").innerHTML = table;
        })
        .catch(err => console.error(err));
    }

/* AUTO LOAD USERS */
window.onload = loadUsers;