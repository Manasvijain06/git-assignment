const BASE_URL = "http://localhost:8080/api";

function switchTab(type) {
    const loginSec = document.getElementById('loginSection');
    const signupSec = document.getElementById('signupSection');
    const tabs = document.querySelectorAll('.tab-btn');

    if (type === 'login') {
        loginSec.style.display = 'block';
        signupSec.style.display = 'none';
        tabs[0].classList.add('active');
        tabs[1].classList.remove('active');
    } else {
        loginSec.style.display = 'none';
        signupSec.style.display = 'block';
        tabs[1].classList.add('active');
        tabs[0].classList.remove('active');
    }
}

// Login logic
const loginForm = document.getElementById("loginForm");
if (loginForm) {
    loginForm.addEventListener("submit", async function (e) {
        e.preventDefault();

        const email = document.getElementById("loginEmail").value.trim();
        const password = document.getElementById("loginPassword").value.trim();
        const btn = document.getElementById("loginBtn");
        const errorMsg = document.getElementById("errorMsg");

        errorMsg.innerText = "";

        // Validate formats
        const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        if (!emailPattern.test(email)) {
            errorMsg.innerText = "Please enter a valid email address";
            return;
        }

        btn.disabled = true;
        btn.innerText = "Logging in...";

        try {
            const response = await fetch(`${BASE_URL}/auth/login`, {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ email: email, password: password })
            });

            if (!response.ok) {
                throw new Error("Invalid credentials");
            }

            const data = await response.json();

            // Save to local storage
            localStorage.setItem("user", JSON.stringify(data));


            // Role redirects
            if (data.role === "ADMIN") {
                window.location.href = "../html/admin.html";
            } else if (data.role === "MANAGER") {
                window.location.href = "../html/manager.html";
            } else {
                window.location.href = "../html/employee.html";
            }

        } catch (err) {
            errorMsg.innerText = err.message;
        } finally {
            btn.disabled = false;
            btn.innerText = "Access Portal";
        }
    });
}

// Signup Logic
const signupForm = document.getElementById("signupForm");
if (signupForm) {
    signupForm.addEventListener("submit", async function (e) {
        e.preventDefault();

        const name = document.getElementById("regName").value.trim();
        const email = document.getElementById("regEmail").value.trim();
        const password = document.getElementById("regPassword").value.trim();
        const btn = document.getElementById("signupBtn");
        const errorMsg = document.getElementById("signupErrorMsg");

        errorMsg.innerText = "";

        btn.disabled = true;
        btn.innerText = "Registering...";

        try {
            const response = await fetch(`${BASE_URL}/users`, {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({
                    name: name,
                    email: email,
                    password: password,
                    role: "EMPLOYEE"
                })
            });

            const passwordPattern = /^\d{6}$/;
            if (response.ok) {
                showMessage("Registration Successful! Redirecting to login.");
                switchTab('login');
                signupForm.reset();
            }
            if (!passwordPattern.test(password)) {
                errorMsg.innerText = "Password must be exactly 6 digits";
            }
            else {
                throw new Error("Use your company's email");
            }
        } catch (err) {
            errorMsg.innerText = err.message;
        } finally {
            btn.disabled = false;
            btn.innerText = "Register Now";
        }
    });

}
function showMessage(message, type = "success") {

    let container = document.getElementById("toastContainer");

    // create container if not exists
    if (!container) {
        container = document.createElement("div");
        container.id = "toastContainer";
        document.body.appendChild(container);
    }

    const toast = document.createElement("div");
    toast.className = `toast ${type}`;
    toast.innerText = message;

    container.appendChild(toast);

    setTimeout(() => {
        toast.remove();
    }, 3000);
}