import axios, { AxiosPromise } from "axios";
import { produtosData } from "../interface/produtosData";
import { useMutation, useQueryClient } from "@tanstack/react-query";

const API_URL = "http://localhost:8080";

const postData = async (data: produtosData): AxiosPromise<any> => {
  const response = await axios.post(`${API_URL}/api/produtos`, data);
  return response.data;
};

export function useProdutosDataM() {
  const queryClient = useQueryClient();
  const mutation = useMutation({
    mutationFn: postData,
    retry: 2,
    onSuccess: () => {
        queryClient.invalidateQueries({ queryKey: ['produtos-data'] });

    }
  })

  return mutation;
}
