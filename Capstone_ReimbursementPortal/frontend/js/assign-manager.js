const BASE_URL = 'http://localhost:8080/api';
let assignmentTimeMap = {};
document.addEventListener("DOMContentLoaded", () => {
    loadUserProfile();
    loadUsers();
    loadMappings();

    const form = document.getElementById("assignManagerForm")
    if (form) {
        form.addEventListener("submit", assignManager);
    }
});

function loadUserProfile() {
    const user = JSON.parse(localStorage.getItem("user"));

    if (!user) return;

    const nameEl = document.getElementById("profileName").innerText = user.name;
    const roleEl = document.getElementById("profileRole").innerText = user.role;

}


/* ---------------- POPULATE USER DROPDOWNS ---------------- */
async function loadUsers() {
    try {
        const res = await fetch(`${BASE_URL}/users`);
        const result = await res.json();

        const users = result.data || [];

        const empSelect = document.getElementById("employeeSelect");
        const manSelect = document.getElementById("managerSelect");

        empSelect.innerHTML = `<option disabled selected>Select Employee</option>`;
        manSelect.innerHTML = `<option disabled selected>Select Manager</option>`;

        users.forEach(u => {
            const option = document.createElement("option");
            option.value = u.id;
            option.text = `${u.name} (ID: ${u.id})`;

            if (u.role === "EMPLOYEE") {
                empSelect.appendChild(option);
            }

            if (u.role === "MANAGER") {
                manSelect.appendChild(option.cloneNode(true));
            }
        });

    } catch (err) {
        console.error("User load error:", err);
    }
}

/* ---------------- LOAD TABLE ---------------- */
async function loadMappings() {
    try {
        const res = await fetch(`${BASE_URL}/users`);
        const result = await res.json();

        const users = result.data || [];

        const tbody = document.getElementById("mappingTableBody");
        tbody.innerHTML = "";

        const employees = users
            .filter(u => u.role === "EMPLOYEE")
            .sort((a, b) => {
                const timeA = assignmentTimeMap[a.id] || 0;
                const timeB = assignmentTimeMap[b.id] || 0;

                return timeB - timeA;
            });
        if (employees.length === 0) {
            tbody.innerHTML = `<tr><td colspan="4">No employees found</td></tr>`;
            return;
        }

        employees.forEach(emp => {
            const row = document.createElement("tr");

            row.innerHTML = `
                <td>${emp.id}</td>
                <td>${emp.name}</td>
                <td>${emp.email}</td>
                <td>${emp.managerName || "Not Assigned"}</td>
            `;

            tbody.appendChild(row);
        });

    } catch (err) {
        console.error("Mapping error:", err);
    }
}

/* ---------------- ASSIGN MANAGER ---------------- */
async function assignManager(event) {
    event.preventDefault();

    const employeeId = document.getElementById("employeeSelect").value;
    const managerId = document.getElementById("managerSelect").value;

    if (!employeeId || !managerId) {
        showNotification("Please select both employee and manager", "error");
        return;
    }

    try {
        const res = await fetch(
            `${BASE_URL}/users/assign-manager?employeeId=${employeeId}&managerId=${managerId}`,
            {
                method: "PUT"
            }
        );

        if (!res.ok) {
            const errorText = await res.text();
            console.error("Server error:", errorText);
            throw new Error(errorText);
        }

        const data = await res.json();
        console.log("Success:", data);
        const now = new Date().toISOString();
        showNotification("Manager assigned successfully", "success");
        assignmentTimeMap[employeeId] = new Date().getTime();


        loadMappings();
        loadUsers();


    } catch (err) {
        console.error(err);
        showNotification("Please Check Before Assigning", "error");
    }
}

/* ---------------- NOTIFICATION ---------------- */
function showNotification(msg, type) {
    const box = document.getElementById("notificationContainer");

    if (!box) return;

    box.innerText = msg;
    box.className = `notification ${type}`;
    box.style.display = "block";

    setTimeout(() => {
        box.style.display = "none";
    }, 3000);
}

