const API_URL_CATEGORIES = 'http://localhost:8080/api/categories';
const API_URL_PRODUCTS = 'http://localhost:8080/api/products';

document.addEventListener('DOMContentLoaded', () => {
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

        const productResumo = document.createElement('p');
        productResumo.textContent = product.resumo;

        const productPreco = document.createElement('p');
        productPreco.textContent = `Preço: R$ ${product.preco.toFixed(2)}`;

        productDiv.appendChild(productTitle);
        productDiv.appendChild(productImage);
        productDiv.appendChild(productResumo);
        productDiv.appendChild(productPreco);

        container.appendChild(productDiv);
    });
}
