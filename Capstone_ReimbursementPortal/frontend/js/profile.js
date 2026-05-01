document.addEventListener("DOMContentLoaded", function() {
    // Get user data from local storage
    const userDataString = localStorage.getItem("user");

    if (!userDataString) {
        window.location.href = "../html/index.html";
        return;
    }

    const user = JSON.parse(userDataString);

    // Map data to HTML elements
    document.getElementById("displayFullName").innerText = user.name;
    document.getElementById("displayUserRole").innerText = user.role;
    
    document.getElementById("infoName").innerText = user.name;
    document.getElementById("infoEmail").innerText = user.email;
    document.getElementById("infoRole").innerText = user.role;
    document.getElementById("infoID").innerText = `EMP-${user.id.toString().padStart(4, '0')}`;

    const dashboardLink = document.getElementById("dashboardLink");
    if (dashboardLink) {
        switch (user.role) {
            case "ADMIN":
                dashboardLink.href = "admin.html";
                break;
            case "MANAGER":
                dashboardLink.href = "manager.html";
                break;
            case "EMPLOYEE":
                dashboardLink.href = "employee.html";
                break;
            default:
                dashboardLink.href = "../html/index.html";
                break;
        }
    }

    // Logout Handler (Re-used from dashboard)
    const logoutBtn = document.getElementById("logoutBtn");
    if (logoutBtn) {
        logoutBtn.addEventListener("click", function(e) {
            e.preventDefault();
            localStorage.removeItem("user");
            window.location.href = "../html/index.html";
        });
    }
});
