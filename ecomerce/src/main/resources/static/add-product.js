const API_URL_CATEGORIES = 'http://localhost:8080/api/categories';
const API_URL_PRODUCTS = 'http://localhost:8080/api/products';

// Função para buscar categorias e preencher o select
async function fetchCategoriesForAddProduct() {
    try {
        const response = await fetch(API_URL_CATEGORIES);
        if (!response.ok) {
            throw new Error('Erro ao carregar categorias');
        }
        const categories = await response.json();
        populateCategorySelect(categories);
    } catch (error) {
        console.error(error);
        alert('Erro ao carregar categorias.');
    }
}

// Função para preencher o select com as categorias
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

// Enviar os dados do formulário para salvar o produto
document.getElementById('add-product-form')?.addEventListener('submit', async (event) => {
    event.preventDefault();

    const nome = document.getElementById('product-name').value;
    const resumo = document.getElementById('product-summary').value;
    const imageUrl = document.getElementById('product-image-url').value;
    const preco = parseFloat(document.getElementById('product-price').value);
    const categoryId = parseInt(document.getElementById('product-category').value);  // Obter o id da categoria selecionada

    if (!nome || !resumo || !imageUrl || isNaN(preco) || !categoryId) {
        alert('Todos os campos são obrigatórios');
        return;
    }

    const productData = {
        nome: nome,
        descricao: resumo,
        imageUrl: imageUrl,
        preco: preco,
        categoryId: categoryId  // Enviar o id da categoria
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
            const errorResponse = await response.json();
            alert(`Erro ao adicionar produto: ${errorResponse.message || 'Erro desconhecido'}`);
        }
    } catch (error) {
        console.error(error);
        alert('Erro ao adicionar produto');
    }
});

// Carregar as categorias ao inicializar a página
document.addEventListener('DOMContentLoaded', () => {
    fetchCategoriesForAddProduct();
});
