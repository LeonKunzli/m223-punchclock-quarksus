const URL = 'http://localhost:8080';
let categories = [];
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
            }else if(role == "user") {
                location.href = "index.html";
            }
        });
        }
    });
}

$(document).ready(function() {
    fetchCategories();
    fetchRole();
});

function fetchCategories() {
    fetch(`${URL}/categories`, {
        //'Authorization': 'Bearer ' + localStorage.getItem("token"),
        method: 'GET'
    }).then((result) => {
        result.json().then((result) => {
            categories = result;
            loadCategories();
        });
    });
    loadCategories();
}

document.getElementById("addCategoryForm").addEventListener("submit", addCategory);
function addCategory(e){
    const data = {};
    const formData = new FormData(e.target);
    data["name"] = formData.get("name");
    e.preventDefault();
    fetch(`${URL}/categories`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    }).then((result) => {
        result.json().then((category) => {
            fetchCategories();
        });
    });
}

function deleteCategory(id) {
    fetch(`${URL}/categories/${id}`, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json'
        }
    }).then(function() {
        fetchCategories();
    });
}


function loadCategories() {
    let categoryDisplay = document.getElementById("categoryDisplay");
    categoryDisplay.innerHTML = "";
    categories.forEach(function(category) {
        const row = document.createElement('tr');
        const id = document.createElement('td');
        id.innerText = category.id;
        const name = document.createElement('td');
        name.innerText = category.name;
        const deleteBtn = document.createElement('button');
        deleteBtn.innerText = "Delete";
        deleteBtn.onclick = function() {
            deleteCategory(category.id);
        };
        row.appendChild(id);
        row.appendChild(name);
        row.appendChild(deleteBtn);
        categoryDisplay.append(row);
    });

    function openUpdateEntryForm() {
        document.getElementById("error").innerText = "";
        document.getElementById("createEntryForm").removeEventListener("submit", createEntry);
        document.getElementById("createEntryForm").addEventListener("submit", updateEntry);
        document.getElementById("formTitle").innerText = "Update entry";
        document.getElementById("updateBack").style.display = "block";
        document.getElementById("usersDropdown").style.display = "block";
    }

    function closeUpdateEntryForm() {
        document.getElementById("error").innerText = "";
        document.getElementById("createEntryForm").removeEventListener("submit", updateEntry);
        document.getElementById("createEntryForm").addEventListener("submit", createEntry);
        document.getElementById("formTitle").innerText = "Add entry";
        document.getElementById("updateBack").style.display = "none";
        document.getElementById("usersDropdown").style.display = "none";
    }

}