const BASE_URL = "http://localhost:8080/api";
document.addEventListener("DOMContentLoaded", function () {
    loadUserProfile();
    const user = JSON.parse(localStorage.getItem("user"));


    if (user.role === "ADMIN") {
        loadAdminClaims();   // 🔥 ADMIN sees ALL
    } else if (user.role === "MANAGER") {
        loadClaims(user.id); // manager only
    }

});

function loadUserProfile() {
    const user = JSON.parse(localStorage.getItem("user"));

    if (!user) return;

    const nameEl = document.getElementById("profileName").innerText = user.name;
    const roleEl = document.getElementById("profileRole").innerText = user.role;

}
/* ================= ADMIN CLAIMS ================= */
function goToDashboard() {
    const user = JSON.parse(localStorage.getItem("user"));

    if (!user) {
        window.location.href = "../html/index.html";
        return;
    }

    if (user.role === "ADMIN") {
        window.location.href = "../html/admin.html";
    }
    else if (user.role === "MANAGER") {
        window.location.href = "../html/manager.html";
    }
    else {
        window.location.href = "../html/index.html";
    }
}
function loadAdminClaims() {

    fetch(`${BASE_URL}/claims/admin/pending`)
        .then(res => res.json())
        .then(data => {

            const tableBody = document.getElementById("reviewTableBody");
            tableBody.innerHTML = "";

            const claims = data.data || [];

            if (claims.length === 0) {
                tableBody.innerHTML = `<tr><td colspan="6">No submitted claims</td></tr>`;
                return;
            }

            claims.forEach(claim => {

                const row = `
                    <tr>
                        <td>${claim.id}</td>
                        <td>${claim.employeeName}</td>
                        <td>₹${claim.amount}</td>
                        <td>${claim.claimDate || "-"}</td>
                        <td>${claim.description || "-"}</td>
                        <td>
                            <button onclick="openModal(${claim.id}, 'approve')" class="btn btn-success">Approve</button>
                            <button onclick="openModal(${claim.id}, 'reject')" class="btn btn-danger">Reject</button>
                        </td>
                    </tr>
                `;

                tableBody.innerHTML += row;
            });
        })
        .catch(err => console.error("Admin claims error:", err));
}
// Load claims assigned to manager
function loadClaims(managerId) {

    fetch(`${BASE_URL}/claims/manager/${managerId}/pending`)
        .then(res => res.json())
        .then(data => {

            const tableBody = document.getElementById("reviewTableBody");
            tableBody.innerHTML = "";

            if (!data.data || data.data.length === 0) {
                tableBody.innerHTML = `<tr><td colspan="6">No pending claims</td></tr>`;
                return;
            }

            data.data.forEach(claim => {

                const row = `
                    <tr>
                        <td>${claim.id}</td>
                        <td>${claim.employeeName || claim.employeeId}</td>
                        <td>₹${claim.amount}</td>
                        <td>${claim.claimDate || "-"}</td>
                        <td>${claim.description}</td>
                        <td>
                            <button class="btn btn-success" onclick="openModal(${claim.id}, 'approve')">Approve</button>
                            <button class="btn btn-danger" onclick="openModal(${claim.id}, 'reject')">Reject</button>
                        </td>
                    </tr>
                `;

                tableBody.innerHTML += row;
            });

        })
        .catch(err => {
            console.error("Error loading claims:", err);
        });
}

let selectedClaimId = null;
let selectedAction = null;

// Open modal
function openModal(claimId, action) {

    selectedClaimId = claimId;
    selectedAction = action;

    document.getElementById("actionModal").style.display = "block";
    document.getElementById("modalTitle").innerText =
        action === "approve" ? "Approve Claim" : "Reject Claim";

    document.getElementById("reviewComment").value = "";
}

// Close modal
function closeModal() {
    document.getElementById("actionModal").style.display = "none";
}

//Submit review
function submitReview(event) {
    event.preventDefault();

    const comment = document.getElementById("reviewComment").value;

    if (!comment) {
        showMessage("Comment is required", "error");
        return;
    }

    fetch(`http://localhost:8080/api/claims/${selectedClaimId}/${selectedAction}`, {
        method: "PUT",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            comment: comment
        })
    })
        .then(async (res) => {
            if (!res.ok) {
                const err = await res.text();
                throw new Error(err);
            }
            return res.json();
        })
        .then(() => {

            showMessage("Action completed successfully", "success");

            closeModal();

            setTimeout(() => {
                location.reload();
            }, 800); // small delay so toast is visible

        })
        .catch(err => {
            console.error("Error:", err);
            showMessage("Failed: " + err.message, "error");
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