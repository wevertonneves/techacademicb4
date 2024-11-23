import axios from "axios";
import { produtosData } from "../interface/produtosData";
import { useQuery } from "@tanstack/react-query";

const API_URL = "http://localhost:8080";

const fetchData = async (): Promise<produtosData[]> => {
  const response = await axios.get<produtosData[]>(`${API_URL}/api/produtos`);
  return response.data; // Retorna apenas os dados
};

export function useProdutosData() {
  const query = useQuery({
    queryFn: fetchData,
    queryKey: ["produtos-data"],
    retry: 2,
  });

  return query; // Retorna diretamente o objeto da query
}
