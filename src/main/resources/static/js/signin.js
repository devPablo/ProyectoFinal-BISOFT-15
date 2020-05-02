const endpoint = `https://bisoft15.azurewebsites.net/api/signin`;

function validation(name, lastname, email, password){
    return !(name == '' || lastname == '' || email == '' || password == '');
}

document.querySelector('#redirectlogin').addEventListener("click",
    function redireccionLogin() {
        let name = document.querySelector('#name').value;
        let lastname = document.querySelector('#lastname').value;
        let email = document.querySelector('#email').value;
        let password = document.querySelector('#password').value;
        let data = {};
        if (validation(name, lastname, email, password)){
            data.nombre = name;
            data.apellidos = lastname;
            data.username = email;
            data.password = password;
            data.enabled = true;
            postMethodToAPI(endpoint, data).done(f => {
                window.location.href = "https://bisoft15.azurewebsites.net";
            });
        }else{
            alert("Llene los campos vacios")
        }
    });