document.addEventListener("DOMContentLoaded", function () {
    // Get user data from local storage
    const userDataString = localStorage.getItem("user");

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
        }
    }


    // 3. Dynamic Back Navigation Logic
    const backBtn = document.getElementById('backBtn');
    if (backBtn) {
        backBtn.addEventListener('click', (e) => {
            e.preventDefault();

            // Retrieve the stored page history from sessionStorage
            const previousPage = sessionStorage.getItem('prevPage');

            if (previousPage) {
                // Clear and navigate to the previous page from dashboard
                sessionStorage.removeItem('prevPage');
                window.location.href = previousPage;
            } else {
                // Otherwise fall back to browser history
                window.history.back();
            }
        });
    }
});