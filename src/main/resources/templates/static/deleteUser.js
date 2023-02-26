const form_del = document.getElementById('formForDeleting');
const id_del = document.getElementById('id_del');
const name_del = document.getElementById('name_del');
const lastName_del = document.getElementById('lastName_del');
const age_del = document.getElementById('age_del');
const email_del = document.getElementById('email_del');
const password_del = document.getElementById('password_del');


async function deleteModalData(id) {
    $('#deleteModal').modal('show');
    const  urlForDel = 'http://localhost:8081/admin/users/' + id;
    let usersPageDel = await fetch(urlForDel);
    if (usersPageDel.ok) {
            await usersPageDel.json().then(user => {
                id_del.value = `${user.id}`;
                name_del.value = `${user.name}`;
                lastName_del.value = `${user.lastName}`;
                age_del.value = `${user.age}`;
                email_del.value = `${user.email}`;
                password_del.value = `${user.password}`;
            })
    } else {
        alert(`Error, ${usersPageDel.status}`)
    }
}
async function deleteUser() {
    form_del.addEventListener('submit', deletingUser);

    function deletingUser(event) {
        event.preventDefault();
        let urlDel = 'http://localhost:8081/admin/users/' + id_del.value;
        let method = {
            method: 'DELETE',
            headers: {
                "Content-Type": "application/json"
            }
        }


        fetch(urlDel, method).then(() => {
            $('#deleteCloseBtn').click();
            getAdminPage();
        })
    }
}