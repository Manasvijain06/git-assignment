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
    const nameElement = document.getElementById("profileName");
    const roleElement = document.getElementById("profileRole");
    const welcomeHeader = document.querySelector(".top-header h2");

    if (nameElement) nameElement.innerText = user.name;
    if (roleElement) roleElement.innerText = user.role;
    
    if (welcomeHeader) {
        // Friendly greeting using first name
        const firstName = user.name.split(' ')[0];
        welcomeHeader.innerText = `Welcome back, ${firstName}!`;
    }

    // 3. FETCH DYNAMIC DASHBOARD STATS
    if (document.querySelector('.stats-grid')) {
        fetchDashboardStats(user);
    }

    // 4. LOAD CLAIMS
   loadEmployeeClaims(); // Fills the main activity table
    loadRecentClaims(user.id); // Fills the recent activity panel

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
        const response = await fetch(`http://localhost:8080/api/claims/employee/${user.id}`);
        const result = await response.json();

        if (response.ok && result.data) {
            const claims = result.data;

            // Calculate the stats from the claims array
            const total = claims.length;
            const pending = claims.filter(c => c.status.toUpperCase() === 'PENDING').length;
            const approved = claims.filter(c => c.status.toUpperCase() === 'APPROVED').length;
            const rejected = claims.filter(c => c.status.toUpperCase() === 'REJECTED').length;

            // Update the HTML DOM elements
            if (document.getElementById("totalClaims")) 
                document.getElementById("totalClaims").innerText = total;
            if (document.getElementById("pendingClaims")) 
                document.getElementById("pendingClaims").innerText = pending;
            if (document.getElementById("approvedClaims")) 
                document.getElementById("approvedClaims").innerText = approved;
            if (document.getElementById("rejectedClaims")) 
                document.getElementById("rejectedClaims").innerText = rejected;
        }
    } catch (error) {
        console.error("Error fetching or calculating stats:", error);
    }
}

/**
 * Handles the claim submission
 */
async function submitClaim(user) {
    const btn = document.getElementById("submitClaimBtn");
    const amountVal = document.getElementById("amount").value;
    const dateVal = document.getElementById("date").value;
    const descVal = document.getElementById("description").value;

    const employeeId = user?.id || localStorage.getItem("userId") || localStorage.getItem("employeeId");

    if (!employeeId) {
        alert("Session expired. Please log in again.");
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
            alert("Claim Sent!");
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
            alert("Submission failed: " + errorText);
        }
    } catch (err) {
        console.error("Fetch error:", err);
        alert("Server Error: Unable to reach the server.");
    } finally {
        if (btn) {
            btn.disabled = false;
            btn.innerText = "Submit Claim";
        }
    }
}

async function loadRecentClaims(userId) {
    const tableBody = document.getElementById("recentClaimsTableBody");
    
    if (!tableBody) return;

    try {
        // Fetch claims for the specific employee
        const response = await fetch(`http://localhost:8080/api/claims/employee/${userId}`);
        const apiResponse = await response.json();
        const claims = apiResponse.data || [];

        tableBody.innerHTML = "";

        if (claims.length === 0) {
            tableBody.innerHTML = "<tr><td colspan='3' style='text-align:center;'>No recent claims found</td></tr>";
            return;
        }

        // Get the last 5 claims
        const recentClaims = claims.slice(-5).reverse();

        recentClaims.forEach(claim => {
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
                <td>#${claim.id}</td>
                <td>₹${claim.amount}</td>
                <td><span class="status-badge ${statusClass}">${statusIcon}${claim.status}</span></td>
                <td>${claim.description || 'N/A'}</td>
            `;
            tableBody.appendChild(row);
        });

    } catch (error) {
        console.error("Error fetching recent claims:", error);
        tableBody.innerHTML = "<tr><td colspan='3' style='color:red;'>Failed to load data</td></tr>";
    }
}
async function loadEmployeeClaims() {
    const employeeId = localStorage.getItem("userId") || localStorage.getItem("employeeId");

    try {
        const response = await fetch(`http://localhost:8080/api/claims/employee/${Id}`);
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
                    <td>#${claim.id}</td>
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