const BASE_URL = "http://localhost:8080/api/login";

async function login() {

    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;

    const payload = {
        email: email,
        password: password
    };

    try {
        const response = await fetch(BASE_URL + "/login", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(payload)
        });

        if (response.ok) {
            const data = await response.json();

            alert("Login successful!");

            // store token if JWT used later
            localStorage.setItem("token", data.token);

            // redirect to dashboard
            window.location.href = "dashboard.html";

        } else {
            alert("Invalid credentials");
        }

    } catch (error) {
        console.error(error);
        alert("Server not reachable");
    }
}