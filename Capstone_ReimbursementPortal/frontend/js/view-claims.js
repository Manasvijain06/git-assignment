const BASE_URL = "http://localhost:8080/api/claims";

window.onload = async function () {

    const user = JSON.parse(localStorage.getItem("user"));

    const tbody = document.querySelector("#table tbody");

    tbody.innerHTML = "<tr><td colspan='5'>Loading...</td></tr>";

    try {
        const response = await fetch(`${BASE_URL}/employee/${user.id}`);

        if (!response.ok) {
            throw new Error("Failed to fetch claims");
        }

        const data = await response.json();

        const claims = Array.isArray(data) ? data : (data.claims || data.data || []);

        tbody.innerHTML = "";

        if (claims.length === 0) {
            tbody.innerHTML = "<tr><td colspan='5'>No claims found</td></tr>";
            return;
        }

        claims.forEach(c => {
            tbody.innerHTML += `
                <tr>
                    <td>${c.id}</td>
                    <td>${c.amount}</td>
                    <td>${c.claimDate}</td>
                    <td>${c.description}</td>
                    <td>${c.status}</td>
                </tr>
            `;
        });

    } catch (err) {
        tbody.innerHTML = "<tr><td colspan='5'>Error loading data</td></tr>";
    }
};