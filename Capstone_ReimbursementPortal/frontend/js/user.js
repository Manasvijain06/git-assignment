const BASE_URL = "http://localhost:8080/api/users";

document.addEventListener("DOMContentLoaded", () => {
    loadUserProfile();
    loadUsers();// Fetches real user profile data
});

function loadUserProfile() {
    const userDataString = localStorage.getItem("user");

    if (userDataString) {
        try {
            const user = JSON.parse(userDataString);
            if (document.getElementById("profileName"))
                document.getElementById("profileName").innerText = user.name;
            if (document.getElementById("profileRole"))
                document.getElementById("profileRole").innerText = user.role;
        } catch (e) {
            console.error("Error parsing profile data:", e);
        }
    }
}

async function loadUsers() {
    try {
        const res = await fetch(`${BASE_URL}`);
        const result = await res.json();

        // Adjust based on your API response structure (e.g., result.data or result)
        const users = result.data || result || [];

        const tbody = document.getElementById("userTableBody");
        tbody.innerHTML = "";

        if (users.length === 0) {
            tbody.innerHTML = `
                <tr>
                    <td colspan="5" style="text-align:center;">
                        No users found
                    </td>
                </tr>
            `;
            return;
        }

        users.forEach(user => {
            const row = document.createElement("tr");

            row.innerHTML = `
                <td>${user.id}</td>
                <td>${user.name}</td>
                <td>${user.email}</td>
                <td>${user.role}</td>
                <td>
                    <button class="btn-delete" onclick="deleteUser(${user.id})" style="color: #ef4444; background: #fee2e2; border: none; padding: 6px 12px; border-radius: 6px; font-weight: 500; cursor: pointer;">
                        Delete
                    </button>
                </td>
            `;

            tbody.appendChild(row);
        });

    } catch (err) {
        console.error("Error loading users:", err);
    }
}

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
            if (!res.ok) {
                return res.text().then(text => { throw new Error(text); });
            }
            return res.json();
        })
        .then(data => {
            // Success notification
            showNotification("User created successfully!", "success");
            document.getElementById("userForm").reset();
            loadUsers();
        })
        .catch(err => {
            console.error("Error in creating user", err);
            // Error notification (displays what went wrong)
            showNotification("Invalid Username or Password", "error");
        });
}
async function deleteUser(userId) {
    const userDataString = localStorage.getItem("user");
    let currentUserId = null;
    if (userDataString) {
        try {
            const currentUser = JSON.parse(userDataString);
            currentUserId = currentUser.id;
        } catch (e) {
            console.error("Error parsing current user:", e);
        }
    }
    if (currentUserId && userId === currentUserId) {
        showNotification("You cannot delete your own account.", "error");
        return;
    }

    const confirmDelete = confirm("Are you sure you want to delete this user?");
    if (!confirmDelete) return;
    try {
        // 1. Fetch all users to check the manager/employee assignment status
        const resAll = await fetch(`${BASE_URL}`);
        const allUsers = await resAll.json();
        const users = allUsers.data || allUsers || [];

        const targetUser = users.find(u => u.id === userId);
        if (!targetUser) {
            throw new Error("User not found.");
        }

        // 2. MANAGER RULE: Only allow delete if they have no assigned employees
        if (targetUser.role === "MANAGER") {
            const assignedEmployees = users.filter(u =>
                (u.manager && u.manager.id === userId) || u.managerId === userId
            );

            if (assignedEmployees.length > 0) {
                showNotification("Cannot delete manager as they have assigned employees.", "error");
                return;
            }
        }

        // 3. EMPLOYEE RULE: Unassign them first (if required), then delete

        // 4. Perform DELETE after ensuring no database constraints are violated
        const res = await fetch(`${BASE_URL}/${userId}`, {
            method: "DELETE"
        });

        if (!res.ok) {
            throw new Error("Cannot delete as it is assigned.");
        }

        // Success handling
        showNotification("User deleted successfully!", "success");
        loadUsers();

    } catch (err) {
        console.error("Delete action failed:", err);
        showNotification("Delete action failed, it may be assigned to someone.", "error");
    }
}
/* ---------------- DISPLAY PROFESSIONAL MESSAGE ---------------- */
function showNotification(msg, type) {
    const box = document.getElementById("notificationContainer");

    if (!box) return;

    const icon = type === "success" ? "✅" : "⚠️";
    box.innerHTML = `${icon} ${msg}`;
    box.className = `notification ${type}`;
    box.style.display = "block";

    setTimeout(() => {
        box.style.display = "none";
    }, 3000);
}

