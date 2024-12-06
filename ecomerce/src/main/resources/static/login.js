const API_URL_LOGIN = 'http://localhost:8080/api/users/login'; // URL para login

// Função para fazer login
async function loginUser() {
    const email = document.getElementById('email').value;

    const loginData = {
        email: email
    };

    try {
        const response = await fetch(API_URL_LOGIN, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(loginData),
        });

        if (response.ok) {
            const data = await response.json();
            localStorage.setItem('userId', data.id); // Armazena o userId no localStorage
            localStorage.setItem('userEmail', data.email); // Armazena o email do usuário
            window.location.href = 'index.html'; // Redireciona para a página principal após login
        } else {
            alert('Usuário não encontrado.');
        }
    } catch (error) {
        console.error(error);
        alert('Erro ao fazer login. Tente novamente.');
    }
}

// Evento do formulário de login
document.getElementById('login-form').addEventListener('submit', (event) => {
    event.preventDefault(); // Previne o envio do formulário

    loginUser();
});
