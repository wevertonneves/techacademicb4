
ALTER TABLE carrinho_produtos
ADD COLUMN quantidade INT NOT NULL DEFAULT 1;

-- Adicionando uma constraint para garantir que a quantidade seja maior que 0
ALTER TABLE carrinho_produtos
ADD CONSTRAINT check_quantidade CHECK (quantidade > 0);
