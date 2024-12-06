const API_URL_CATEGORIES = 'http://localhost:8080/api/categories';
const API_URL_PRODUCTS = 'http://localhost:8080/api/products';  // Alterado para o endpoint de produtos

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

    // Validação básica dos dados
    if (!nome || !resumo || !imageUrl || isNaN(preco) || !categoryId) {
        alert('Por favor, preencha todos os campos corretamente.');
        return;
    }

    const productData = {
        nome: nome,
        resumo: resumo,
        imageUrl: imageUrl,
        preco: preco,
        categoryId: categoryId  // Enviar o id da categoria
    };

    try {
        // Depuração: Exibir os dados que serão enviados
        console.log("Dados a serem enviados:", productData);

        const response = await fetch(API_URL_PRODUCTS, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(productData),
        });

        // Verifique a resposta do servidor
        if (response.ok) {
            alert('Produto adicionado com sucesso!');
            document.getElementById('add-product-form').reset();
        } else {
            const errorDetails = await response.text();  // Captura a resposta de erro detalhada
            throw new Error(`Erro ao adicionar produto: ${errorDetails}`);
        }
    } catch (error) {
        console.error(error);
        alert('Erro ao adicionar produto: ' + error.message);
    }
});

// Carregar as categorias ao inicializar a página
document.addEventListener('DOMContentLoaded', () => {
    fetchCategoriesForAddProduct();
});
