import { useState } from "react";
import { useProdutosDataM } from "../../hooks/useProdutosDataM";
import { produtosData } from "../../interface/produtosData";

interface InputProps {
  label: string;
  value: string | number;
  updateValue: (value: any) => void;
}

const Input = ({ label, value, updateValue }: InputProps) => {
  return (
    <div>
      <label>{label}</label>
      <input
        value={value}
        onChange={(e) => updateValue(e.target.value)}
        type={typeof value === "number" ? "number" : "text"} // Define o tipo do input dinamicamente
      />
    </div>
  );
};

export function CreateModal() {
  const [nome, setNome] = useState("");
  const [preco, setPreco] = useState(0); // Corrigido o estado para o preço
  const [imagem, setImagem] = useState("");
  const { mutate } = useProdutosDataM();

  const submit = () => {
    const produtoData: produtosData = {
      nome,
      preco, // Adicionado o preço aqui
      imagem,
    };
    mutate(produtoData);
  };

  return (
    <div className="modal-overflow">
      <div className="modal-body">
        <h2>Cadastrar um novo produto</h2>
        <form
          className="input-container"
          onSubmit={(e) => {
            e.preventDefault(); // Evita o reload da página
            submit();
          }}
        >
          <Input label="Título" value={nome} updateValue={setNome} />
          <Input
            label="Preço"
            value={preco}
            updateValue={(value) => setPreco(parseFloat(value))} // Converte o valor para número
          />
          <Input label="Imagem" value={imagem} updateValue={setImagem} />
          <button type="submit">Salvar</button>
        </form>
      </div>
    </div>
  );
}
