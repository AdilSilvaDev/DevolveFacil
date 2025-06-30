import React, { useState, useEffect, useContext } from 'react';
import { useParams, Link } from 'react-router-dom';
import { AuthContext } from '../context/AuthContext';
import { getColetaDetalhe, atualizarStatusColeta } from '../api/api';
import './ColetaDetalhePage.css';

const ColetaDetalhePage = () => {
  const { id } = useParams();
  const { user } = useContext(AuthContext);
  const [coleta, setColeta] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const [novoStatus, setNovoStatus] = useState('');
  const [comentario, setComentario] = useState('');

  useEffect(() => {
    const fetchColeta = async () => {
      try {
        const data = await getColetaDetalhe(id);
        setColeta(data);
      } catch (err) {
        setError('Erro ao carregar detalhes da coleta');
      } finally {
        setLoading(false);
      }
    };
    fetchColeta();
  }, [id]);

  const handleAtualizarStatus = async (e) => {
    e.preventDefault();
    if (!novoStatus || !comentario) {
      setError('Preencha o status e comentário');
      return;
    }

    try {
      await atualizarStatusColeta(id, novoStatus, comentario);
      // Recarregar dados da coleta
      const data = await getColetaDetalhe(id);
      setColeta(data);
      setNovoStatus('');
      setComentario('');
      setError('');
    } catch (err) {
      setError('Erro ao atualizar status');
    }
  };

  const getStatusClass = (status) => {
    const statusMap = {
      'SOLICITADA': 'status-solicitada',
      'COLETADA': 'status-coletada',
      'ENTREGUE': 'status-entregue',
      'CANCELADA': 'status-cancelada',
      'SINISTRO': 'status-sinistro'
    };
    return statusMap[status] || 'status-solicitada';
  };

  if (loading) return <div className="loading">Carregando...</div>;
  if (error) return <div className="error-message">{error}</div>;
  if (!coleta) return <div className="error-message">Coleta não encontrada</div>;

  return (
    <div className="coleta-detalhe-page fade-in">
      <div className="card">
        <div className="card-header">
          <h2 className="card-title">📦 Coleta #{coleta.codigo}</h2>
          <span className={`status-badge ${getStatusClass(coleta.status)}`}>
            {coleta.status}
          </span>
        </div>

        <div className="grid grid-2">
          <div className="info-section">
            <h3>📋 Informações Gerais</h3>
            <div className="info-item">
              <strong>Código:</strong> {coleta.codigo}
            </div>
            <div className="info-item">
              <strong>Descrição:</strong> {coleta.descricao}
            </div>
            <div className="info-item">
              <strong>Status:</strong> {coleta.status}
            </div>
            <div className="info-item">
              <strong>Data de Criação:</strong> {new Date(coleta.dataCriacao).toLocaleDateString('pt-BR')}
            </div>
            {coleta.dataPrevistaColeta && (
              <div className="info-item">
                <strong>Data Prevista:</strong> {new Date(coleta.dataPrevistaColeta).toLocaleDateString('pt-BR')}
              </div>
            )}
          </div>

          <div className="info-section">
            <h3>📍 Endereços</h3>
            {coleta.enderecoColeta && (
              <div className="info-item">
                <strong>Coleta:</strong> {coleta.enderecoColeta}
              </div>
            )}
            {coleta.enderecoEntrega && (
              <div className="info-item">
                <strong>Entrega:</strong> {coleta.enderecoEntrega}
              </div>
            )}
          </div>
        </div>

        <div className="participantes-section">
          <h3>👥 Participantes</h3>
          <div className="grid grid-2">
            <div className="info-item">
              <strong>Solicitante:</strong> {coleta.solicitante?.nome}
            </div>
            {coleta.transportador && (
              <div className="info-item">
                <strong>Transportador:</strong> {coleta.transportador?.nome}
              </div>
            )}
          </div>
        </div>

        {coleta.ocorrencias && coleta.ocorrencias.length > 0 && (
          <div className="ocorrencias-section">
            <h3>📝 Histórico de Ocorrências</h3>
            <div className="ocorrencias-list">
              {coleta.ocorrencias.map((ocorrencia, index) => (
                <div key={index} className="ocorrencia-item">
                  <div className="ocorrencia-header">
                    <span className={`status-badge ${getStatusClass(ocorrencia.tipo)}`}>
                      {ocorrencia.tipo}
                    </span>
                    <span className="ocorrencia-data">
                      {new Date(ocorrencia.dataOcorrencia).toLocaleString('pt-BR')}
                    </span>
                  </div>
                  <p className="ocorrencia-comentario">{ocorrencia.comentario}</p>
                  <div className="ocorrencia-usuario">
                    Por: {ocorrencia.usuario?.nome}
                  </div>
                </div>
              ))}
            </div>
          </div>
        )}

        <div className="acoes-section">
          <h3>⚡ Atualizar Status</h3>
          <form onSubmit={handleAtualizarStatus} className="status-form">
            <div className="form-group">
              <label className="form-label">Novo Status:</label>
              <select 
                value={novoStatus} 
                onChange={(e) => setNovoStatus(e.target.value)}
                className="form-input"
                required
              >
                <option value="">Selecione um status</option>
                <option value="SOLICITADA">Solicitada</option>
                <option value="COLETADA">Coletada</option>
                <option value="ENTREGUE">Entregue</option>
                <option value="CANCELADA">Cancelada</option>
                <option value="SINISTRO">Sinistro</option>
              </select>
            </div>
            
            <div className="form-group">
              <label className="form-label">Comentário:</label>
              <textarea
                value={comentario}
                onChange={(e) => setComentario(e.target.value)}
                className="form-input form-textarea"
                placeholder="Descreva a atualização..."
                required
              />
            </div>
            
            <button type="submit" className="btn btn-primary">
              📝 Atualizar Status
            </button>
          </form>
        </div>

        <div className="acoes-buttons">
          <Link to="/coletas" className="btn btn-secondary">
            ← Voltar para Coletas
          </Link>
          <Link to={`/upload/${id}`} className="btn btn-success">
            📎 Anexar Arquivo
          </Link>
        </div>
      </div>
    </div>
  );
};

export default ColetaDetalhePage; 