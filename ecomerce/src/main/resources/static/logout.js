// Função para logout
function logout() {
    localStorage.removeItem('userId'); // Remove o userId do localStorage
    window.location.href = 'login.html'; // Redireciona para a página de login
}

// Chama a função de logout quando a página for carregada
logout();
