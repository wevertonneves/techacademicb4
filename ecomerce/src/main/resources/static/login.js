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
            const user = await response.json();
            // Armazena os dados completos do usuário no localStorage
            localStorage.setItem('user', JSON.stringify(user));
            alert(`Bem-vindo, ${user.name}!`);
            window.location.href = 'index.html'; // Redireciona para a página principal após login
        } else {
            alert('Usuário não encontrado.');
        }
    } catch (error) {
        console.error('Erro ao fazer login:', error);
        alert('Erro ao fazer login. Tente novamente.');
    }
}

// Evento do formulário de login
document.getElementById('login-form').addEventListener('submit', (event) => {
    event.preventDefault(); // Previne o envio do formulário
    loginUser();
});

// Verificar se o usuário já está logado ao carregar a página
document.addEventListener('DOMContentLoaded', () => {
    const user = JSON.parse(localStorage.getItem('user'));
    if (user) {
        alert(`Você já está logado como ${user.name}.`);
        window.location.href = 'index.html'; // Redireciona para a página principal
    }
});
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
            const user = await response.json();
            // Armazena os dados completos do usuário no localStorage
            localStorage.setItem('user', JSON.stringify(user));
            alert(`Bem-vindo, ${user.name}!`);
            window.location.href = 'index.html'; // Redireciona para a página principal após login
        } else {
            alert('Usuário não encontrado.');
        }
    } catch (error) {
        console.error('Erro ao fazer login:', error);
        alert('Erro ao fazer login. Tente novamente.');
    }
}

// Evento do formulário de login
document.getElementById('login-form').addEventListener('submit', (event) => {
    event.preventDefault(); // Previne o envio do formulário
    loginUser();
});

// Verificar se o usuário já está logado ao carregar a página
document.addEventListener('DOMContentLoaded', () => {
    const user = JSON.parse(localStorage.getItem('user'));
    if (user) {
        alert(`Você já está logado como ${user.name}.`);
        window.location.href = 'index.html'; // Redireciona para a página principal
    }
});
