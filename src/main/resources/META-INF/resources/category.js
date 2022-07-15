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
            }
            else if(role=="category"){
                location.href = "index.html";
            }
            indexCategories();
        });
        }
    });
}

$(document).ready(function(){
    fetchRole();
});



function openUpdateCategoryForm() {
    document.getElementById("error").innerText = "";
    document.getElementById("createCategoryForm").removeEventListener("submit", createCategory);
    document.getElementById("createCategoryForm").addEventListener("submit", updateCategory);
    document.getElementById("formTitle").innerText = "Update category";
}

function closeUpdateCategoryForm() {
    document.getElementById("error").innerText = "";
    document.getElementById("createCategoryForm").removeEventListener("submit", updateCategory);
    document.getElementById("createCategoryForm").addEventListener("submit", createCategory);
    document.getElementById("formTitle").innerText = "Add category";
}

const createCategory = (e) => {
    e.preventDefault();
    const formData = new FormData(e.target);
    let data = {};
    data["name"] = formData.get("name");

    fetch(`${URL}/categories`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': "Bearer " + localStorage.getItem("token")

        },
        body: JSON.stringify(data)
    }).then((result) => {
        result.json().then((category) => {
            if(category.id == undefined) {
                document.getElementById("error").innerText = category.parameterViolations[0].message;
            }else {
                indexCategories();
            }
        });
    }).catch((result) => {
        result.json().then((response) => {
            document.getElementById("error").innerText = response.parameterViolations[0].message;
        });
    });
};

const updateCategory = (e) => {
    e.preventDefault();
    const formData = new FormData(e.target);
    let data = {};
    data["id"] = formData.get("id");
    data["name"] = formData.get("name");
    fetch(`${URL}/categories`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    }).then((result) => {
        closeUpdateCategoryForm();
        indexCategories();
    }).catch((result) => {
        result.json().then((response) => {
            document.getElementById("errorUpdate").innerText = response.parameterViolations[0].message;
        });
    });
};

const deleteCategory = (id) => {
    fetch(`${URL}/categories/${id}`, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json'
        }
    }).then(() => {
        indexCategories();
    });
}

const indexCategories = () => {
    fetch(`${URL}/categories`, {
        method: 'GET',
        headers: {'Authorization': "Bearer " + localStorage.getItem("token")}
    }).then((result) => {
        result.json().then((result) => {
            categories = result;
            renderCategories();
        });
    });
    renderCategories();
};

const createCell = (text) => {
    const cell = document.createElement('td');
    cell.innerText = text;
    return cell;
};

const renderCategories = () => {
    const display = document.querySelector('#categoryDisplay');
    display.innerHTML = '';
    categories.forEach((category) => {
        const row = document.createElement('tr');
        row.appendChild(createCell(category.id));
        row.appendChild(createCell(category.name));

        const deleteButton = document.createElement('button');
        deleteButton.innerText = "Delete";
        deleteButton.onclick = function() {
            deleteCategory(category.id);
            indexCategories();
        };
        row.appendChild(deleteButton); 

        const updateButton = document.createElement('button');
        updateButton.innerText = "Update";
        updateButton.onclick = function() {
            document.getElementById("id").value = category.id;
            document.getElementById("name").value = category.name
            openUpdateCategoryForm();
        }
        row.appendChild(updateButton);
        display.appendChild(row);

    });
};

document.addEventListener('DOMContentLoaded', function(){
    closeUpdateCategoryForm();
});