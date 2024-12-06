const API_URL_CATEGORIES = 'http://localhost:8080/api/categories';
const API_URL_MOVIES = 'http://localhost:8080/api/movies';

document.addEventListener('DOMContentLoaded', () => {
    // Carregar as categorias ao inicializar a página
    fetchCategories();

    // Adicionar evento ao botão "Adicionar Filme" se ele existir
    const addMovieButton = document.getElementById('add-movie-btn');
    if (addMovieButton) {
        addMovieButton.addEventListener('click', () => {
            window.location.href = 'add-movie.html';
        });
    }
});

// Função para buscar as categorias
async function fetchCategories() {
    try {
        const response = await fetch(API_URL_CATEGORIES);
        if (!response.ok) {
            throw new Error('Erro ao buscar categorias');
        }
        const categories = await response.json();
        renderCategories(categories);
    } catch (error) {
        console.error(error);
        document.getElementById('categories-container').innerHTML = '<p>Erro ao carregar categorias.</p>';
    }
}

// Função para renderizar as categorias no menu
function renderCategories(categories) {
    const navbar = document.querySelector('#navbar ul');
    navbar.innerHTML = '';

    categories.forEach(category => {
        const categoryLink = document.createElement('li');
        const link = document.createElement('a');
        link.textContent = category.nome;
        link.setAttribute('href', '#');
        link.onclick = () => loadMoviesByCategory(category.id);
        categoryLink.appendChild(link);
        navbar.appendChild(categoryLink);
    });
}

// Função para carregar filmes de uma categoria específica
async function loadMoviesByCategory(categoryId) {
    try {
        const response = await fetch(`${API_URL_CATEGORIES}/${categoryId}`);
        if (!response.ok) {
            throw new Error('Erro ao buscar filmes');
        }
        const category = await response.json();
        renderMovies(category.movies);
    } catch (error) {
        console.error(error);
        document.getElementById('categories-container').innerHTML = '<p>Erro ao carregar filmes da categoria.</p>';
    }
}

// Função para renderizar os filmes na página
function renderMovies(movies) {
    const container = document.getElementById('categories-container');
    container.innerHTML = ''; // Limpar o conteúdo anterior

    movies.forEach(movie => {
        const movieDiv = document.createElement('div');
        movieDiv.classList.add('category');

        const movieTitle = document.createElement('h2');
        movieTitle.textContent = movie.nome;

        const movieImage = document.createElement('img');
        movieImage.setAttribute('src', movie.imageUrl);  // URL da imagem
        movieImage.setAttribute('alt', movie.nome);

        const movieResumo = document.createElement('p');
        movieResumo.textContent = movie.resumo;

        const moviePreco = document.createElement('p');
        moviePreco.textContent = `Preço: R$ ${movie.preco.toFixed(2)}`;  // Exibir preço com 2 casas decimais

        movieDiv.appendChild(movieTitle);
        movieDiv.appendChild(movieImage);
        movieDiv.appendChild(movieResumo);
        movieDiv.appendChild(moviePreco);

        container.appendChild(movieDiv);
    });
}
