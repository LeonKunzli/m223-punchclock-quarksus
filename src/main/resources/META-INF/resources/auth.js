const URL = 'http://localhost:8080';

document.getElementById("signUpForm").addEventListener("submit", signIn);
function signIn(e){
    const data = {};
    const formData = new FormData(e.target);
    data["username"] = formData.get("username");
    data["password"] = formData.get("password");
    e.preventDefault();
    fetch(`${URL}/auth`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    });
}
