// Função para carregar o carrinho
async function loadCarrinho() {
    const user = JSON.parse(localStorage.getItem('user')); // Pega o usuário logado

    if (!user) {
        alert("Você precisa estar logado para ver o carrinho.");
        return;
    }

    try {
        // Solicita o carrinho com base no ID do usuário
        const response = await fetch(`http://localhost:8080/api/carrinhos/${user.id}`); // Corrigir a URL para usar user.id
        if (!response.ok) {
            throw new Error('Erro ao carregar o carrinho');
        }

        const carrinho = await response.json(); // Pega os dados do carrinho

        // Verificar se o carrinho tem a propriedade productIds definida e não vazia
        if (carrinho && Array.isArray(carrinho.productIds)) {
            renderCarrinhoItems(carrinho.productIds); // Renderiza os produtos no carrinho
        } else {
            renderCarrinhoItems([]); // Se não houver produtos, mostra a mensagem de carrinho vazio
        }
    } catch (error) {
        console.error('Erro ao carregar o carrinho:', error);
        alert('Houve um erro ao carregar o carrinho.');
    }
}

// Função para renderizar os itens do carrinho
function renderCarrinhoItems(productIds) {
    const carrinhoLista = document.getElementById('carrinho-lista');
    carrinhoLista.innerHTML = ''; // Limpar a lista atual

    if (productIds.length === 0) {
        carrinhoLista.innerHTML = '<li>Seu carrinho está vazio.</li>';
        return;
    }

    // Requisitar os detalhes de cada produto
    productIds.forEach(async (productId) => {
        try {
            const response = await fetch(`http://localhost:8080/api/products/${productId}`);
            if (response.ok) {
                const product = await response.json();
                const listItem = document.createElement('li');
                listItem.textContent = `${product.nome} - R$ ${product.preco.toFixed(2)}`;
                carrinhoLista.appendChild(listItem);
            } else {
                throw new Error('Erro ao buscar o produto');
            }
        } catch (error) {
            console.error('Erro ao buscar produto:', error);
        }
    });
}

// Função para verificar se o usuário está logado
function checkLoginStatus() {
    const user = JSON.parse(localStorage.getItem('user')); // Obtém as informações do usuário

    const userInfo = document.getElementById('user-info');
    if (user) {
        // Se o usuário estiver logado, exibe o nome e um botão de logout
        userInfo.innerHTML = `Bem-vindo, ${user.name} <button id="logout-btn">Deslogar</button>`;

        // Adiciona o evento de logout
        const logoutButton = document.getElementById('logout-btn');
        logoutButton.addEventListener('click', () => {
            logoutUser();
        });
    } else {
        // Se não estiver logado, exibe um botão de login
        userInfo.innerHTML = `<button id="login-btn">Login</button>`;

        // Adiciona o evento de login
        const loginButton = document.getElementById('login-btn');
        loginButton.addEventListener('click', () => {
            const email = prompt("Digite seu email:");
            loginUser(email);
        });
    }
}

// Função para fazer login
async function loginUser(email) {
    const loginRequest = { email };

    try {
        const response = await fetch('http://localhost:8080/api/users/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(loginRequest),
        });

        if (response.ok) {
            const user = await response.json();
            // Armazenar dados do usuário no localStorage
            localStorage.setItem('user', JSON.stringify(user));
            checkLoginStatus();
            loadCarrinho(); // Carrega o carrinho após login
        } else {
            alert('Usuário não encontrado!');
        }
    } catch (error) {
        console.error('Erro ao fazer login:', error);
    }
}

// Função para deslogar
function logoutUser() {
    localStorage.removeItem('user'); // Remove os dados do usuário
    checkLoginStatus(); // Atualiza a interface para exibir o botão de login
    loadCarrinho(); // Atualiza a lista de itens do carrinho após o logout
}

// Carrega os itens do carrinho ao carregar a página
document.addEventListener('DOMContentLoaded', () => {
    checkLoginStatus(); // Verifica se o usuário está logado
    loadCarrinho(); // Carrega os itens do carrinho
});
