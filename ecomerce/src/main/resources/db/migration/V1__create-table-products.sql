-- Criação da tabela 'category'
CREATE TABLE category (
    id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(200) NOT NULL
);

-- Criação da tabela 'Products'
CREATE TABLE Products (
    id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(200) NOT NULL,
    descricao VARCHAR(4000),
    image_url VARCHAR(255),
    nota DECIMAL(10, 2),
    preco DECIMAL(10, 2),
    category_id INT,
    CONSTRAINT fk_category FOREIGN KEY (category_id) REFERENCES category(id)
);

-- Criação da tabela 'users'
CREATE TABLE users (
    id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100),
    email VARCHAR(100),
    phone VARCHAR(15)
);

-- Criação da tabela 'carrinho'
CREATE TABLE carrinho (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(id)
);

-- Criação da tabela 'carrinho_produtos' para a relação muitos-para-muitos entre 'carrinho' e 'products'
CREATE TABLE carrinho_produtos (
    carrinho_id BIGINT NOT NULL,
    produto_id INT NOT NULL,
    PRIMARY KEY (carrinho_id, produto_id),
    CONSTRAINT fk_carrinho FOREIGN KEY (carrinho_id) REFERENCES carrinho(id),
    CONSTRAINT fk_produto FOREIGN KEY (produto_id) REFERENCES products(id)
);
