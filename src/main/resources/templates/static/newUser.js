const form_new = document.getElementById('formForNewUser');

async function newUser() {
    $('#newModal').modal('show');
    form_new.addEventListener('submit', addNewUser);
}

async function addNewUser(event) {
    event.preventDefault();
    const urlNew = 'http://localhost:8081/admin/users';
    let listOfRole = []
    for (let i=0; i<form_new.roles.options.length; i++) {
        if (form_ed.rolesForEditing.options[i].selected) {
            listOfRole.push("ROLE_" + form_ed.roles.options[i].value);
        }
    }
    let method = {
        method: 'POST',
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            name: form_new.name.value,
            lastName: form_new.lastName.value,
            age: form_new.age.value,
            email: form_new.email.value,
            password: form_new.password.value,
            roles: listOfRole
        })
    }
    await fetch(urlNew,method).then(() => {
        form_new.reset();
        getAdminPage();
    });
}
