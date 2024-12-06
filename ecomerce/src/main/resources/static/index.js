const API_URL_CATEGORIES = 'http://localhost:8080/api/categories';
const API_URL_PRODUCTS = 'http://localhost:8080/api/products';
const API_URL_LOGIN = 'http://localhost:8080/api/users/login';

document.addEventListener('DOMContentLoaded', () => {
    // Verificar se o usuário está logado
    checkLoginStatus();

    // Carregar categorias para o index.html
    if (window.location.pathname.includes("index.html")) {
        fetchCategoriesForNavbar();
    }

    // Adicionar evento ao botão "Adicionar Produto" se ele existir na página inicial
    const addProductButton = document.getElementById('add-product-btn');
    if (addProductButton) {
        addProductButton.addEventListener('click', () => {
            window.location.href = 'add-product.html';
        });
    }

    // Adicionar evento ao botão de login se ele existir
    const loginButton = document.getElementById('login-btn');
    if (loginButton) {
        loginButton.addEventListener('click', () => {
            const email = prompt("Digite seu email:");
            loginUser(email);
        });
    }

    // Adicionar evento ao botão de logout se ele existir
    const logoutButton = document.getElementById('logout-btn');
    if (logoutButton) {
        logoutButton.addEventListener('click', () => {
            logoutUser();
        });
    }
});

// Função para buscar as categorias e preencher a navbar do index.html
async function fetchCategoriesForNavbar() {
    try {
        const response = await fetch(API_URL_CATEGORIES);
        if (!response.ok) {
            throw new Error('Erro ao buscar categorias');
        }
        const categories = await response.json();
        renderCategoriesInNavbar(categories);  // Função que renderiza as categorias na navbar
    } catch (error) {
        console.error(error);
        document.getElementById('navbar').innerHTML = '<p>Erro ao carregar categorias.</p>';
    }
}

// Função para renderizar as categorias no menu da navbar (index.html)
function renderCategoriesInNavbar(categories) {
    const navbar = document.querySelector('#navbar ul');
    navbar.innerHTML = '';  // Limpa a navbar

    categories.forEach(category => {
        const categoryLink = document.createElement('li');
        const link = document.createElement('a');
        link.textContent = category.nome;
        link.setAttribute('href', '#');
        link.onclick = () => loadProductsByCategory(category.id);
        categoryLink.appendChild(link);
        navbar.appendChild(categoryLink);
    });
}

// Função para carregar produtos de uma categoria específica
async function loadProductsByCategory(categoryId) {
    try {
        const response = await fetch(`${API_URL_CATEGORIES}/${categoryId}`);
        if (!response.ok) {
            throw new Error('Erro ao buscar produtos');
        }
        const category = await response.json();
        renderProducts(category.products);
    } catch (error) {
        console.error(error);
        document.getElementById('categories-container').innerHTML = '<p>Erro ao carregar produtos da categoria.</p>';
    }
}

// Função para renderizar os produtos
function renderProducts(products) {
    const container = document.getElementById('categories-container');
    container.innerHTML = ''; // Limpar conteúdo anterior

    products.forEach(product => {
        const productDiv = document.createElement('div');
        productDiv.classList.add('category');

        const productTitle = document.createElement('h2');
        productTitle.textContent = product.nome;

        const productImage = document.createElement('img');
        productImage.setAttribute('src', product.imageUrl);
        productImage.setAttribute('alt', product.nome);

        const productDescricao = document.createElement('p');
        productDescricao.textContent = product.descricao;

        const productPreco = document.createElement('p');
        productPreco.textContent = `Preço: R$ ${product.preco.toFixed(2)}`;

        const addToCartButton = document.createElement('button');
        addToCartButton.textContent = 'Adicionar ao Carrinho';
        addToCartButton.onclick = () => addToCart(product.id);

        productDiv.appendChild(productTitle);
        productDiv.appendChild(productImage);
        productDiv.appendChild(productDescricao);
        productDiv.appendChild(productPreco);
        productDiv.appendChild(addToCartButton);

        container.appendChild(productDiv);
    });
}

// Função para adicionar o produto ao carrinho
async function addToCart(productId) {
    const user = JSON.parse(localStorage.getItem('user'));

    if (!user) {
        alert("Por favor, faça login para adicionar produtos ao carrinho.");
        return;
    }

    try {
        // Verificar se o usuário já tem um carrinho
        const responseCarrinho = await fetch(`http://localhost:8080/api/carrinhos/${user.id}`);

        let carrinho = null;

        if (responseCarrinho.ok) {
            carrinho = await responseCarrinho.json();
        }

        if (carrinho) {
            // Se o carrinho existir, adiciona o produto ao carrinho existente
            await addProductToExistingCarrinho(carrinho.id, productId);
        } else {
            // Caso não exista carrinho, cria um novo carrinho e adiciona o produto
            await createCarrinho(user.id, productId);
        }

    } catch (error) {
        console.error('Erro ao adicionar ao carrinho:', error);
        alert('Houve um erro ao adicionar o produto ao carrinho.');
    }
}

// Função para adicionar produto a um carrinho existente
async function addProductToExistingCarrinho(carrinhoId, productId) {
    try {
        const response = await fetch(`http://localhost:8080/api/carrinhos/${carrinhoId}/add-products`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify([productId]), // Envia o ID do produto em formato de lista
        });

        if (response.ok) {
            alert('Produto adicionado ao carrinho!');
        } else {
            alert('Erro ao adicionar produto ao carrinho.');
        }
    } catch (error) {
        console.error('Erro ao adicionar ao carrinho:', error);
        alert('Houve um erro ao adicionar o produto ao carrinho.');
    }
}

// Função para criar um novo carrinho para o usuário e adicionar o produto
async function createCarrinho(userId, productId) {
    try {
        const response = await fetch('http://localhost:8080/api/carrinhos', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                userId: userId,
                productIds: [productId], // Adiciona o ID do produto ao novo carrinho
            }),
        });

        if (response.ok) {
            alert('Novo carrinho criado e produto adicionado!');
        } else {
            alert('Erro ao criar carrinho.');
        }
    } catch (error) {
        console.error('Erro ao criar carrinho:', error);
        alert('Houve um erro ao criar o carrinho.');
    }
}

// Função para fazer login
async function loginUser(email) {
    const loginRequest = { email };

    try {
        const response = await fetch(API_URL_LOGIN, {
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
        } else {
            alert('Usuário não encontrado!');
        }
    } catch (error) {
        console.error('Erro ao fazer login:', error);
    }
}

// Função para verificar se o usuário está logado
function checkLoginStatus() {
    const user = JSON.parse(localStorage.getItem('user'));

    if (user) {
        // Mostrar nome do usuário logado
        const userInfo = document.getElementById('user-info');
        userInfo.innerHTML = `Bem-vindo, ${user.name} <button id="logout-btn">Deslogar</button>`;

        // Adicionar evento ao botão de logout
        const logoutButton = document.getElementById('logout-btn');
        logoutButton.addEventListener('click', () => {
            logoutUser();
        });
    } else {
        // Se não estiver logado, exibir botão de login
        const userInfo = document.getElementById('user-info');
        userInfo.innerHTML = `<button id="login-btn">Login</button>`;

        // Adicionar evento ao botão de login
        const loginButton = document.getElementById('login-btn');
        loginButton.addEventListener('click', () => {
            const email = prompt("Digite seu email:");
            loginUser(email);
        });
    }
}

// Função para deslogar
function logoutUser() {
    // Limpar os dados do usuário no localStorage
    localStorage.removeItem('user');
    checkLoginStatus(); // Atualizar a interface
}
