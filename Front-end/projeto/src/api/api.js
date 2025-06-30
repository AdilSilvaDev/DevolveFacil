import axios from 'axios';

const API_URL = 'http://localhost:8080/api';

// Configurar axios para incluir credenciais (cookies/sessão)
axios.defaults.withCredentials = true;

export const login = async (email, senha) => {
  const response = await axios.post(`${API_URL}/auth/login`, { email, senha });
  return response.data;
};

export const register = async (usuario) => {
  const response = await axios.post(`${API_URL}/auth/register`, usuario);
  return response.data;
};

export const getMe = async () => {
  const response = await axios.get(`${API_URL}/auth/me`);
  return response.data;
};

export const logout = async () => {
  const response = await axios.post(`${API_URL}/auth/logout`);
  return response.data;
};

export const getMinhasColetas = async () => {
  const response = await axios.get(`${API_URL}/coletas`);
  return response.data;
};

export const getColetaDetalhe = async (id) => {
  const response = await axios.get(`${API_URL}/coletas/${id}`);
  return response.data;
};

export const criarColeta = async (coletaData) => {
  const response = await axios.post(`${API_URL}/coletas`, coletaData);
  return response.data;
};

export const atualizarStatusColeta = async (id, status, comentario) => {
  const response = await axios.put(`${API_URL}/coletas/${id}/status`, null, {
    params: { status, comentario }
  });
  return response.data;
};

export const uploadAnexo = async (coletaId, file, descricao, tipoAnexo) => {
  const formData = new FormData();
  formData.append('file', file);
  if (descricao) formData.append('descricao', descricao);
  if (tipoAnexo) formData.append('tipoAnexo', tipoAnexo);
  const response = await axios.post(`${API_URL}/anexos/upload/${coletaId}`, formData, {
    headers: {
      'Content-Type': 'multipart/form-data',
    },
  });
  return response.data;
};

export const getAnexosColeta = async (coletaId) => {
  const response = await axios.get(`${API_URL}/anexos/coleta/${coletaId}`);
  return response.data;
};

export const downloadAnexo = async (anexoId) => {
  const response = await axios.get(`${API_URL}/anexos/download/${anexoId}`, {
    responseType: 'blob'
  });
  return response.data;
};

export const getOcorrenciasPendentes = async () => {
  const response = await axios.get(`${API_URL}/ocorrencias/pendentes`);
  return response.data;
};

export const aprovarOcorrencia = async (ocorrenciaId, comentario) => {
  const response = await axios.post(`${API_URL}/ocorrencias/${ocorrenciaId}/aprovar`, {
    comentario
  });
  return response.data;
}; 