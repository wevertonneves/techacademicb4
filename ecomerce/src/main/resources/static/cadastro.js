document.getElementById("cadastroForm").addEventListener("submit", function(event) {
    event.preventDefault(); // Impede o envio do formulário

    const name = document.getElementById("name").value;
    const email = document.getElementById("email").value;
    const phone = document.getElementById("phone").value;

    const user = {
        name: name,
        email: email,
        phone: phone
    };

    // Requisição POST para a API
    fetch("http://localhost:8080/api/users", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(user)
    })
    .then(response => {
        if (response.ok) {
            return response.json();
        } else {
            throw new Error("Erro ao cadastrar usuário");
        }
    })
    .then(data => {
        // Exibe uma mensagem de sucesso
        document.getElementById("message").style.display = "block";
        document.getElementById("message").innerText = "Usuário cadastrado com sucesso!";
        document.getElementById("cadastroForm").reset(); // Limpa o formulário
    })
    .catch(error => {
        // Exibe uma mensagem de erro
        document.getElementById("message").style.display = "block";
        document.getElementById("message").innerText = error.message;
    });
});
