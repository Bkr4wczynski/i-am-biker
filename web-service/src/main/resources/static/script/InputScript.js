const form = document.getElementById("form");
form.addEventListener("submit", function(e) {
    let today = new Date().toISOString().split("T")[0];
    let selected = document.getElementById("registry_date").value;
    if (selected > today) {
        e.preventDefault();
        alert("Registry date cannot be in the future!");
    }
});


