const URL = 'http://localhost:8080';
let users = [];
let role = "user";

function fetchRole() {
    fetch(`${URL}/users/currentuserrole`, {
        method: 'GET',
        headers: {'Authorization': "Bearer " + localStorage.getItem("token")}
    }).then((response) => {
        if(response.status == 401) {
            location.href = "auth.html";
        }
        if(!response.ok){
            role = null;
        }
        else{
         
        response.text().then((temp) => {
            role = temp;
            if(role == null) {
                location.href = "auth.html";
            }
            else if(role=="user"){
                location.href = "index.html";
            }
            indexUsers();
        });
        }
    });
}

$(document).ready(function(){
    fetchRole();
});



function openUpdateUserForm() {
    document.getElementById("error").innerText = "";
    document.getElementById("createUserForm").removeEventListener("submit", createUser);
    document.getElementById("createUserForm").addEventListener("submit", updateUser);
    document.getElementById("formTitle").innerText = "Update user";
}

function closeUpdateUserForm() {
    document.getElementById("error").innerText = "";
    document.getElementById("createUserForm").removeEventListener("submit", updateUser);
    document.getElementById("createUserForm").addEventListener("submit", createUser);
    document.getElementById("formTitle").innerText = "Add user";
}

const createUser = (e) => {
    e.preventDefault();
    const formData = new FormData(e.target);
    let data = {};
    data["username"] = formData.get("username");
    data["password"] = formData.get("password");
    data["role"] = roles[formData.get("role")];

    fetch(`${URL}/users`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': "Bearer " + localStorage.getItem("token")

        },
        body: JSON.stringify(data)
    }).then((result) => {
        result.json().then((user) => {
            if(user.id == undefined) {
                document.getElementById("error").innerText = user.parameterViolations[0].message;
            }else {
                indexUsers();
            }
        });
    }).catch((result) => {
        result.json().then((response) => {
            document.getElementById("error").innerText = response.parameterViolations[0].message;
        });
    });
};

const updateUser = (e) => {
    e.preventDefault();
    const formData = new FormData(e.target);
    let data = {};
    data["id"] = formData.get("id");
    data["username"] = formData.get("username");
    data["password"] = formData.get("password");
    data["role"] = roles[formData.get("role")];
    fetch(`${URL}/users`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    }).then((result) => {
        closeUpdateUserForm();
        indexUsers();
    }).catch((result) => {
        result.json().then((response) => {
            document.getElementById("errorUpdate").innerText = response.parameterViolations[0].message;
        });
    });
};

const deleteUser = (id) => {
    fetch(`${URL}/users/${id}`, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json'
        }
    });
}

const indexUsers = () => {
    fetch(`${URL}/users`, {
        method: 'GET',
        headers: {'Authorization': "Bearer " + localStorage.getItem("token")}
    }).then((result) => {
        result.json().then((result) => {
            users = result;
            renderUsers();
        });
    });
    renderUsers();
};

const createCell = (text) => {
    const cell = document.createElement('td');
    cell.innerText = text;
    return cell;
};

function renderRoleDropdown() {
    let dropdown = document.getElementById("rolesDropdown");
    for(let i = 0;i<roles.length;i++) {
        const roleIndex = i;
        let option = document.createElement("option");
        option.innerText = roles[roleIndex].role;
        option.value = roleIndex;
        dropdown.appendChild(option);
    }
}

const loadRoles = () => {
    fetch(`${URL}/roles`, {
        method: 'GET'
    }).then((result) => {
        result.json().then((result) => {
            roles = result;
            renderRoleDropdown();
        });
    });
}

const renderUsers = () => {
    const display = document.querySelector('#userDisplay');
    display.innerHTML = '';
    users.forEach((user) => {
        const row = document.createElement('tr');
        row.appendChild(createCell(user.id));
        row.appendChild(createCell(user.username));
        row.appendChild(createCell(user.password));
        row.appendChild(createCell(user.roles[0].role));

        const deleteButton = document.createElement('button');
        deleteButton.innerText = "Delete";
        deleteButton.onclick = function() {
            deleteUser(user.id);
            indexUsers();
        };
        row.appendChild(deleteButton); 

        const updateButton = document.createElement('button');
        updateButton.innerText = "Update";
        updateButton.onclick = function() {
            document.getElementById("id").value = user.id;
            document.getElementById("username").value = user.username
            document.getElementById("password").value = user.password
            document.getElementById("rolesDropdown").selectedIndex = roles.map(function(e) { return e.id; }).indexOf(user.roles[0].id);
            openUpdateUserForm();
        }
        row.appendChild(updateButton);
        display.appendChild(row);

    });
};

document.addEventListener('DOMContentLoaded', function(){
    closeUpdateUserForm();
    loadRoles();
});