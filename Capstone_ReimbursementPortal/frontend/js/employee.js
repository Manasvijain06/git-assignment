let currentPage = 0;
let pageSize = 5;
let totalPages = 0;

document.addEventListener("DOMContentLoaded", function () {
    // 1. SECURITY & PROFILE INITIALIZATION
    const userDataString = localStorage.getItem("user");

    // If no user is logged in, redirect to login page
    if (!userDataString) {
        window.location.href = "../html/index.html";
        return;
    }

    const user = JSON.parse(userDataString);
    console.log("Logged in user:", user);

    // 2. INJECT USER PROFILE DATA
    if (user) {
        if (document.getElementById("managerWelcomeName"))
            document.getElementById("managerWelcomeName").innerText = user.name || 'Admin';

        if (document.getElementById("profileName"))
            document.getElementById("profileName").innerText = user.name || 'Admin User';

        if (document.getElementById("profileRole"))
            document.getElementById("profileRole").innerText = user.role || 'ADMIN';
    }


    // 3. FETCH DYNAMIC DASHBOARD STATS
    if (document.querySelector('.stats-grid')) {
        fetchDashboardStats(user);
    }
    loadEmployeeClaims();
    loadRecentClaims(user.id, 0);

    document.getElementById("prevBtn").addEventListener("click", function () {
        if (currentPage > 0) {
            loadRecentClaims(user.id, currentPage - 1);
        }
    });

    // NEXT BUTTON
    document.getElementById("nextBtn").addEventListener("click", function () {
        if (currentPage < totalPages - 1) {
            loadRecentClaims(user.id, currentPage + 1);
        }
    });
    // 5. ATTACH CLAIM FORM SUBMISSION LISTENER
    const claimForm = document.getElementById("claimForm");
    if (claimForm) {
        claimForm.addEventListener("submit", function (event) {
            event.preventDefault(); // Stop the form from reloading the page
            submitClaim(user);
        });
    }

    // 6. LOGOUT LOGIC
    const logoutBtn = document.getElementById("logoutBtn");
    if (logoutBtn) {
        logoutBtn.addEventListener("click", function (e) {
            e.preventDefault();
            localStorage.removeItem("user");
            window.location.href = "../html/index.html";
        });
    }
});

/**
 * Fetches real-time counts from the Backend API
 */
async function fetchDashboardStats(user) {
    try {
        // Fetch claims array for the employee
        const response = await fetch(`http://localhost:8080/api/claims/employee/${user.id}?page=0&size=1000`);
        const result = await response.json();

        const pageData = result.data;

        // IMPORTANT: extract content from Page
        const claims = Array.isArray(pageData)
            ? pageData
            : pageData?.content || [];

        // Calculate the stats from the claims array
        const total = claims.length;
        const submitted = claims.filter(c => c.status.toUpperCase() === 'SUBMITTED').length;
        const approved = claims.filter(c => c.status.toUpperCase() === 'APPROVED').length;
        const rejected = claims.filter(c => c.status.toUpperCase() === 'REJECTED').length;

        // Update the HTML DOM elements
        if (document.getElementById("totalClaims"))
            document.getElementById("totalClaims").innerText = total;
        if (document.getElementById("submittedClaims"))
            document.getElementById("submittedClaims").innerText = submitted;
        if (document.getElementById("approvedClaims"))
            document.getElementById("approvedClaims").innerText = approved;
        if (document.getElementById("rejectedClaims"))
            document.getElementById("rejectedClaims").innerText = rejected;

    } catch (error) {
        console.error("Error fetching or calculating stats:", error);
    }
}


/**
 * Handles the claim submission
 */
let isSubmitting = false;
async function submitClaim(user) {

    if (isSubmitting) return;
    isSubmitting = true;
    const btn = document.getElementById("submitClaimBtn");

    const amountVal = document.getElementById("amount").value;
    const dateVal = document.getElementById("date").value;
    const descVal = document.getElementById("description").value;

    const employeeId = user?.id || localStorage.getItem("userId") || localStorage.getItem("employeeId");

    if (!employeeId) {
        showMessage("Session expired. Please log in again.");
        window.location.href = '../html/index.html';
        return;
    }

    const data = {
        amount: Number(amountVal),
        claimDate: dateVal,
        description: descVal,
        employeeId: Number(employeeId)
    };

    if (btn) {
        btn.disabled = true;
        btn.innerText = "Submitting...";
    }

    try {
        const res = await fetch("http://localhost:8080/api/claims", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(data)
        });

        if (res.ok) {
            showMessage("Claim Added!");
            document.getElementById("claimForm").reset();

            // Refresh tables after submission
            loadEmployeeClaims();
            if (user && user.id) {
                loadRecentClaims(user.id);
            }
            if (typeof fetchDashboardStats === 'function') {
                fetchDashboardStats(user);
            }
        } else {
            const errorText = await res.text();
            showMessage("Submission failed: " + errorText);
        }
    } catch (err) {
        console.error("Fetch error:", err);
        showMessage("Server Error: Unable to reach the server.");
    } finally {
        if (btn) {
            btn.disabled = false;
            btn.innerText = "Submit Claim";
        }
    }
}

async function loadRecentClaims(userId, page = 0) {
    const tableBody = document.getElementById("recentClaimsTableBody");

    if (!tableBody) return;

    try {
        // Fetch claims for the specific employee
        const response = await fetch(`http://localhost:8080/api/claims/employee/${userId}?page=${page}&size=${pageSize}`);

        const apiResponse = await response.json();

        const pageData = apiResponse.data;
        const claims = pageData.content || [];

        currentPage = pageData.number;
        totalPages = pageData.totalPages;


        tableBody.innerHTML = "";

        if (claims.length === 0) {
            tableBody.innerHTML = "<tr><td colspan='3' style='text-align:center;'>No recent claims found</td></tr>";
            return;
        }



        claims.forEach(claim => {
            const row = document.createElement("tr");

            let statusIcon = '';
            let statusClass = '';

            switch (claim.status.toUpperCase()) {
                case 'APPROVED':
                    statusIcon = '✅';
                    statusClass = 'status-approved';
                    break;
                case 'REJECTED':
                    statusIcon = '❌';
                    statusClass = 'status-rejected';
                    break;
                default: // PENDING or others
                    statusIcon = '🕒';
                    statusClass = 'status-pending';
                    break;
            }


            row.innerHTML = `
                <td>${claim.id}</td>
                <td>₹${claim.amount}</td>
                <td>${claim.claimDate}</td>
                <td>${claim.description || 'N/A'}</td>
                <td><span class="status-badge ${statusClass}">${statusIcon}${claim.status}</span></td>
                <td>${claim.comment || '-'}</td>
            `;
            tableBody.appendChild(row);
        });
        const pageInfo = document.getElementById("pageInfo");
        if (pageInfo) {
            pageInfo.innerText = `Page ${currentPage + 1} / ${totalPages}`;
        }

    } catch (error) {
        console.error("Error fetching recent claims:", error);
        tableBody.innerHTML = "<tr><td colspan='3' style='color:red;'>Failed to load data</td></tr>";
    }
}
async function loadEmployeeClaims() {
    const user = JSON.parse(localStorage.getItem("user"));

    try {
        const response = await fetch(`http://localhost:8080/api/claims/employee/${user.id}`);
        const apiResponse = await response.json();
        const claims = apiResponse.data || [];

        const tableBody = document.getElementById("claimsTableBody");

        if (tableBody) {
            tableBody.innerHTML = "";

            if (claims.length === 0) {
                tableBody.innerHTML = `<tr><td colspan="4" style="text-align: center;">No recent activity found.</td></tr>`;
                return;
            }

            claims.forEach(claim => {
                const row = document.createElement("tr");
                row.innerHTML = `
                    <td>${claim.id}</td>
                    <td>$${claim.amount}</td>
                    <td>${claim.status}</td>
                    <td>${claim.description}</td>
                `;
                tableBody.appendChild(row);
            });
        }
    } catch (err) {
        console.error("Error loading claims:", err);
        const tableBody = document.getElementById("claimsTableBody");
        if (tableBody) {
            tableBody.innerHTML = `<tr><td colspan="4" style="color:red; text-align:center;">Failed to load recent activity.</td></tr>`;
        }
    }
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