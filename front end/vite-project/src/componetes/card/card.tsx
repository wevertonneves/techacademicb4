import "./card.css";


interface CardProps {
    nome: string; // Corrigido de "nome" para "name"
    imagem: string;
    preco: number; // Corrigido de "imagem" para "image"
  }
  
  export function Card({ imagem, nome , preco }: CardProps) {
    return (
      <div className="card">
        <img src={imagem} alt={nome} />
        <h2>{nome}</h2>
        <p>Preço: R$ {preco.toFixed(2)}</p>
        
      </div>
    );
  }
  