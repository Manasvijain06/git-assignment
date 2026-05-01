const BASE_URL = "http://localhost:8080/api/claims";

const form = document.getElementById("claimForm");

form.addEventListener("submit", async function(e) {
    e.preventDefault();

    const user = JSON.parse(localStorage.getItem("user"));

    const amount = document.getElementById("amount").value;
    const claimDate = document.getElementById("claimDate").value;
    const description = document.getElementById("description").value;

    try {
        const response = await fetch(`${BASE_URL}/create`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                amount: amount,
                claimDate: claimDate,
                description: description,
                employeeId: user.id
            })
        });

        if (!response.ok) {
            throw new Error("Failed to submit claim");
        }

        alert("Claim submitted successfully!");
        form.reset();

    } catch (err) {
        alert(err.message);
    }
});