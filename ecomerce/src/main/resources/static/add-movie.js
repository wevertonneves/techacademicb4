const API_URL_CATEGORIES = 'http://localhost:8080/api/categories';
const API_URL_PRODUCTS = 'http://localhost:8080/api/products';

document.addEventListener('DOMContentLoaded', () => {
    // Carregar categorias para o formulário de add-product.html
    if (window.location.pathname.includes("add-product.html")) {
        fetchCategoriesForAddProduct();
    }

    // Adicionar evento para o envio do formulário
    const addProductForm = document.getElementById('add-product-form');
    if (addProductForm) {
        addProductForm.addEventListener('submit', handleFormSubmit);
    }
});

// Função para buscar as categorias e preencher o formulário de adição de produto
async function fetchCategoriesForAddProduct() {
    try {
        const response = await fetch(API_URL_CATEGORIES);
        if (!response.ok) {
            throw new Error('Erro ao buscar categorias');
        }
        const categories = await response.json();
        populateCategorySelect(categories);  // Função que preenche o select do formulário
    } catch (error) {
        console.error(error);
        alert('Erro ao carregar categorias.');
    }
}

// Função para preencher o campo de seleção de categoria no formulário de adição de produto
function populateCategorySelect(categories) {
    const select = document.getElementById('product-category');
    if (select) {
        categories.forEach(category => {
            const option = document.createElement('option');
            option.value = category.id;
            option.textContent = category.nome;
            select.appendChild(option);
        });
    }
}

// Função para capturar os dados do formulário e enviar para o backend
async function handleFormSubmit(event) {
    event.preventDefault();

    const nome = document.getElementById('product-name').value;
    const resumo = document.getElementById('product-summary').value;
    const imageUrl = document.getElementById('product-image-url').value;
    const preco = parseFloat(document.getElementById('product-price').value);
    const categoryId = parseInt(document.getElementById('product-category').value);

    const productData = {
        nome: nome,
        resumo: resumo,
        imageUrl: imageUrl,
        preco: preco,
        categoryId: categoryId // Envia o ID da categoria selecionada
    };

    try {
        const response = await fetch(API_URL_PRODUCTS, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(productData),
        });

        if (response.ok) {
            alert('Produto adicionado com sucesso!');
            document.getElementById('add-product-form').reset();
        } else {
            throw new Error('Erro ao adicionar produto');
        }
    } catch (error) {
        console.error(error);
        alert('Erro ao adicionar produto');
    }
}
