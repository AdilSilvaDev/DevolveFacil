import React, { useState, useContext } from 'react';
import { useNavigate } from 'react-router-dom';
import { AuthContext } from '../context/AuthContext';
import { criarColeta } from '../api/api';
import './NovaColetaPage.css';

const buscarEnderecoPorCep = async (cep) => {
  try {
    const res = await fetch(`https://viacep.com.br/ws/${cep}/json/`);
    const data = await res.json();
    if (data.erro) return null;
    return data;
  } catch {
    return null;
  }
};

const NovaColetaPage = () => {
  const navigate = useNavigate();
  const { user } = useContext(AuthContext);
  const [formData, setFormData] = useState({
    descricao: '',
    enderecoColeta: '',
    enderecoEntrega: '',
    dataPrevistaColeta: '',
    arquivoAnexo: ''
  });
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');

  const handleChange = async (e) => {
    const { name, value } = e.target;
     let newValue = value;
    if (name === "enderecoColeta") {
      newValue = value.replace(/\D/g, '').slice(0, 8); 
    }
    setFormData(prev => ({
      ...prev,
      [name]: newValue
    }));
 if (name === "enderecoColeta" && newValue.length === 8) {
      const data = await buscarEnderecoPorCep(newValue);
      if (data) {
        setFormData(prev => ({
          ...prev,
          enderecoColeta: `${data.logradouro}, ${data.bairro}, ${data.localidade}`
        }));
      } else {
        alert('CEP não encontrado!');
      }
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError('');

    try {
      const coletaCriada = await criarColeta(formData);
      navigate(`/coletas/${coletaCriada.id}`);
    } catch (err) {
      setError('Erro ao criar coleta. Tente novamente.');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="nova-coleta-page fade-in">
      <div className="card">
        <div className="card-header">
          <h2 className="card-title">➕ Nova Coleta</h2>
        </div>

        <form onSubmit={handleSubmit} className="coleta-form">
          <div className="form-group">
            <label htmlFor="descricao" className="form-label">
              📝 Descrição da Coleta *
            </label>
            <textarea
              id="descricao"
              name="descricao"
              value={formData.descricao}
              onChange={handleChange}
              className="form-input form-textarea"
              placeholder="Descreva detalhadamente a coleta..."
              required
            />
          </div>

          <div className="grid grid-2">
            <div className="form-group">
              <label htmlFor="enderecoColeta" className="form-label">
                📍 Endereço de Coleta
              </label>
              <input
                id="enderecoColeta"
                name="enderecoColeta"
                type="text"
                value={formData.enderecoColeta}
                onChange={handleChange}
                className="form-input"
                placeholder="Digite o CEP (apenas números)"
                maxLength={8}
                pattern="\d{8}"
                inputMode="numeric" 
              />
            </div>

            <div className="form-group">
              <label htmlFor="enderecoEntrega" className="form-label">
                🏭 Endereço de Entrega
              </label>
              <input
                id="enderecoEntrega"
                name="enderecoEntrega"
                type="text"
                value={formData.enderecoEntrega}
                onChange={handleChange}
                className="form-input"
                placeholder="Endereço da fábrica/entrega"
              />
            </div>
          </div>

          <div className="form-group">
            <label htmlFor="dataPrevistaColeta" className="form-label">
              📅 Data Prevista para Coleta
            </label>
            <input
              id="dataPrevistaColeta"
              name="dataPrevistaColeta"
              type="datetime-local"
              value={formData.dataPrevistaColeta}
              onChange={handleChange}
              className="form-input"
            />
          </div>

          <div className="form-group">
            <label htmlFor="arquivoAnexo" className="form-label">
              📎 Arquivo Anexo (URL)
            </label>
            <input
              id="arquivoAnexo"
              name="arquivoAnexo"
              type="text"
              value={formData.arquivoAnexo}
              onChange={handleChange}
              className="form-input"
              placeholder="URL do arquivo ou caminho"
            />
          </div>

          {error && (
            <div className="error-message">
              ❌ {error}
            </div>
          )}

          <div className="form-actions">
            <button
              type="button"
              onClick={() => navigate('/coletas')}
              className="btn btn-secondary"
              disabled={loading}
            >
              ❌ Cancelar
            </button>
            <button
              type="submit"
              className="btn btn-primary"
              disabled={loading}
            >
              {loading ? '🔄 Criando...' : '✅ Criar Coleta'}
            </button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default NovaColetaPage; 
