import { useState } from "react";
import { useProdutosData } from "./hooks/useProdutosData";
import { Card } from "./componetes/card/card";
import { CreateModal } from "./componetes/create-modal/create-modal";

function App() {
  const { data } = useProdutosData();
  const [isModalOpen, setIsModalOpen] = useState(false);

  const handleOpenModal = () => {
    setIsModalOpen((prev) => !prev);
  };

  return (
    <div className="container">
      <h1>E-commerce</h1>
      <div className="card-grid">
        {data?.map((produto) => (
          <Card key={produto.id} nome={produto.nome} imagem={produto.imagem} preco={produto.preco}/>
        ))}
      </div>

      {isModalOpen && <CreateModal />}
      <button onClick={handleOpenModal}>Novo</button>
    </div>
  );
}

export default App;
