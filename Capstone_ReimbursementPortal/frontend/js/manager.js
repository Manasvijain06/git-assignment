const BASE_URL = "http://localhost:8080/api";

let currentPage = 0;
let totalPages = 0;
let pageSize = 5;
let allClaims = [];
let allClaimsForStats = [];

document.addEventListener("DOMContentLoaded", function () {

    const userData = localStorage.getItem("user");

    if (!userData) {
        window.location.href = "../html/index.html";
        return;
    }


    const user = JSON.parse(userData);

    if (!user || !user.id) {
        showMessage("Invalid session");
        return;
    }

    // 👤 Profile setup
    document.getElementById("managerWelcomeName").innerText = user.name || "Manager";
    document.getElementById("profileName").innerText = user.name;
    document.getElementById("profileRole").innerText = user.role;

    loadTotalUsers(user.id);
    // 🚀 Load claims for THIS manager only
    loadManagerClaims(user.id, 0);

    document.getElementById("prevBtn").addEventListener("click", function () {
        if (currentPage > 0) {
            loadManagerClaims(user.id, currentPage - 1);
        }
    });

    // NEXT BUTTON
    document.getElementById("nextBtn").addEventListener("click", function () {
        if (currentPage < totalPages - 1) {
            loadManagerClaims(user.id, currentPage + 1);
        }
    });

    // 🔓 Logout
    const logoutBtn = document.getElementById("logoutLink");
    if (logoutBtn) {
        logoutBtn.addEventListener("click", function (e) {
            e.preventDefault();
            localStorage.removeItem("user");
            window.location.href = "../html/index.html";
        });
    }
});
async function loadTotalUsers(managerId) {
    try {
        const res = await fetch(`${BASE_URL}/users/manager/${managerId}`);
        const result = await res.json();

        const users = result.data || [];

        document.getElementById("totalUsers").innerText = users.length;

    } catch (err) {
        console.error(err);
        document.getElementById("totalUsers").innerText = 0;
    }
}
async function loadAllManagerClaims(managerId) {
    let page = 0;
    const size = 100;
    let all = [];
    let totalPages = 1;

    do {
        const res = await fetch(`${BASE_URL}/claims/manager/${managerId}?page=${page}&size=${size}`);
        const data = await res.json();

        const pageData = data.data;

        all = all.concat(pageData.content);
        totalPages = pageData.totalPages;
        page++;
    } while (page < totalPages);

    return all;
}

/* ================= FETCH MANAGER CLAIMS ================= */
async function loadManagerClaims(managerId, page = 0) {
    try {
        console.log("Fetching claims for manager:", managerId);

        const res = await fetch(`${BASE_URL}/claims/manager/${managerId}?page=${page}&size=${pageSize}`);


        const result = await res.json();

        const pageData = result.data;

        const claims = pageData.content || [];

        // 🔥 update pagination state
        currentPage = pageData.number;
        totalPages = pageData.totalPages;

        renderClaims(claims);
        allClaimsForStats = await loadAllManagerClaims(managerId);
        updateStats(allClaimsForStats);



        document.getElementById("pageInfo").innerText =
            `Page ${currentPage + 1} / ${totalPages}`;

    } catch (err) {
        console.error(err);
    }
}



/* ================= UPDATE DASHBOARD STATS ================= */
function updateStats(claims) {

    const totalClaims = claims.length;

    const pendingClaims = claims.filter(c =>
        c.status && c.status.toUpperCase() === "SUBMITTED"
    ).length;

    const approvedClaims = claims.filter(c =>
        c.status && c.status.toUpperCase() === "APPROVED"

    ).length;
    const rejectedClaims = claims.filter(c =>
        c.status?.toUpperCase() === "REJECTED"
    ).length;

    const uniqueUsers = new Set(
        claims.map(c => c.employeeId || c.employeeName)
    );

    document.getElementById("totalUsers").innerText = uniqueUsers.size;
    // NOTE: totalUsers not needed for manager → optional
    document.getElementById("totalClaims").innerText = totalClaims;
    document.getElementById("pendingClaims").innerText = pendingClaims;
    document.getElementById("approvedClaims").innerText = approvedClaims;
}


/* ================= RENDER CLAIM TABLE ================= */
function renderClaims(claims) {

    const tbody = document.getElementById("managerTableBody");

    if (!tbody) {
        console.error("Table body not found!");
        return;
    }

    tbody.innerHTML = "";

    if (!claims || claims.length === 0) {
        tbody.innerHTML = `
            <tr>
                <td colspan="6" style="text-align:center;">
                    No claims assigned to you
                </td>
            </tr>
        `;
        return;
    }

    claims.forEach(c => {

        let statusClass = "";

        switch ((c.status || "").toUpperCase()) {
            case "APPROVED":
                statusClass = "status-approved";
                break;
            case "REJECTED":
                statusClass = "status-rejected";
                break;
            default:
                statusClass = "status-pending";
        }

        const row = document.createElement("tr");

        row.innerHTML = `
            <td>#${c.id}</td>
            <td>${c.employeeName}</td>
            <td>₹${c.amount}</td>
            <td>${c.claimDate || "N/A"}</td>
            <td>${c.description}</td>
            <td>
                <span class="status-badge ${statusClass}">
                    ${c.status}
                </span>
            </td>
        `;

        tbody.appendChild(row);
    });
}