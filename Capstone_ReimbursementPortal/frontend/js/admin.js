let currentPage = 0;
let totalPages = 0;
let pageSize = 5;
let allClaims = [];
let allClaimsForStats = [];

document.addEventListener("DOMContentLoaded", function () {
    // 1. Retrieve the logged-in user
    const userDataString = localStorage.getItem("user");

    if (!userDataString) {
        window.location.href = "../html/index.html";
        return;
    }

    const user = JSON.parse(userDataString);

    // 2. Route protection: Ensure only ADMIN access
    if (user.role !== 'ADMIN') {
        alert("Unauthorized access! Redirecting to login.");
        localStorage.removeItem("user");
        window.location.href = "../html/index.html";
        return;
    }

    // 3. Set up Profile Details in the header
    document.getElementById("managerWelcomeName").innerText = user.name || 'Admin';
    document.getElementById("profileName").innerText = user.name || 'Admin User';
    document.getElementById("profileRole").innerText = user.role || 'ADMIN';

    // Load first page
    fetchAdminStats(0);

    document.getElementById("prevBtn").addEventListener("click", function () {
        if (currentPage > 0) {
            fetchAdminStats(currentPage - 1);
        }
    });

    // NEXT BUTTON
    document.getElementById("nextBtn").addEventListener("click", function () {
        if (currentPage < totalPages - 1) {
            fetchAdminStats(currentPage + 1);
        }
    });

    //--------------------------------------
    document.getElementById("claimSearch").addEventListener("input", filterClaims);

    //-------------------Logout-------------------------
    document.getElementById("logoutLink").addEventListener("click", function (e) {
        e.preventDefault();
        localStorage.removeItem("user");
        window.location.href = "../html/index.html";
    });

});

// Global array to store claims for fast client-side filtering
async function loadAllClaimsForStats() {
    let page = 0;
    const size = 100;
    let all = [];
    let totalPages = 1;

    do {
        const res = await fetch(`http://localhost:8080/api/claims?page=${page}&size=${size}`);
        const data = await res.json();

        const pageData = data.data;

        all = all.concat(pageData.content);
        totalPages = pageData.totalPages;
        page++;
    } while (page < totalPages);

    return all;
}

async function fetchAdminStats(page = 0) {
    try {
        // 1. Fetch Users Data
        const usersResponse = await fetch('http://localhost:8080/api/users');
        const users = (await usersResponse.json()).data || [];


        // 2. Fetch Claims Data
        const claimsResponse = await fetch(`http://localhost:8080/api/claims?page=${page}&size=${pageSize}`);
        const claimsData = await claimsResponse.json();

        const pageData = claimsData.data || claimsData;
        const claims = pageData.content || [];
        allClaims = claims;

        currentPage = pageData.number;
        totalPages = pageData.totalPages;

        // TABLE ONLY
        renderClaimsTable(claims);

        // ⭐ FULL DATA FOR STATS (IMPORTANT FIX)
        allClaimsForStats = await loadAllClaimsForStats();

        const totalUsers = users.length;
        const totalClaims = allClaimsForStats.length;

        const pendingClaims = allClaimsForStats.filter(c =>
            c.status?.toUpperCase() === "SUBMITTED"
        ).length;

        const approvedClaims = allClaimsForStats.filter(c =>
            c.status?.toUpperCase() === "APPROVED"
        ).length;

        // UPDATE UI
        document.getElementById("totalUsers").innerText = totalUsers;
        document.getElementById("totalClaims").innerText = totalClaims;
        document.getElementById("pendingClaims").innerText = pendingClaims;
        document.getElementById("approvedClaims").innerText = approvedClaims;

        document.getElementById("pageInfo").innerText =
            `Page ${currentPage + 1} / ${totalPages}`;

    } catch (err) {
        console.error(err);
    }
}

function renderClaimsTable(claims) {
    const tbody = document.getElementById("adminClaimsTableBody");
    if (!tbody) return;

    tbody.innerHTML = '';

    if (claims.length === 0) {
        tbody.innerHTML = '<tr><td colspan="6" style="text-align:center;">No recent claims found.</td></tr>';
        return;
    }

    claims.forEach(claim => {
        const row = document.createElement("tr");

        // Use backend claim variables
        const employeeName = claim.employeeName || (claim.employee ? claim.employee.name : 'Unknown');
        const assignedTo = claim.reviewerName || 'Unassigned';
        const formattedAmount = claim.amount ? `₹${claim.amount}` : '₹0';

        // Determine claim status classes
        let statusIcon = '';
        let statusClass = '';

        switch ((claim.status || '').toUpperCase()) {
            case 'APPROVED':
                statusIcon = '✅';
                statusClass = 'status-approved';
                break;
            case 'REJECTED':
                statusIcon = '❌';
                statusClass = 'status-rejected';
                break;
            default:
                statusIcon = '🕒';
                statusClass = 'status-pending'; break;
        }

        row.innerHTML = `
            <td>${claim.id}</td>
            <td>${employeeName}</td>
            <td>${formattedAmount}</td>
            <td>${claim.claimDate || claim.date || 'N/A'}</td>
            <td>
                <span class="status-badge ${statusClass}">
                    ${claim.status || 'PENDING'}
                </span>
            </td>
            <td>${assignedTo}</td>
        `;

        tbody.appendChild(row);
    });
}

function filterClaims() {
    const input = document.getElementById("claimSearch").value.trim().toLowerCase();
    const sourceData = allClaimsForStats;

    if (!input) {
        fetchAdminStats(currentPage);
        return;
    }

    const filteredClaims = sourceData.filter(claim => {
        const id = String(claim.id ?? '').toLowerCase();

        const name = (
            claim.employeeName ||
            claim.employee?.name ||
            ''
        ).toLowerCase();

        return id.includes(input) || name.includes(input);
    });

    renderClaimsTable(filteredClaims);
}