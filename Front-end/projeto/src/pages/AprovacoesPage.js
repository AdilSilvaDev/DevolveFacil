import React, { useState, useEffect, useContext } from 'react';
import { Link } from 'react-router-dom';
import { AuthContext } from '../context/AuthContext';
import { getOcorrenciasPendentes, aprovarOcorrencia } from '../api/api';
import './AprovacoesPage.css';

const AprovacoesPage = () => {
  const { user } = useContext(AuthContext);
  const [ocorrencias, setOcorrencias] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const [aprovando, setAprovando] = useState(null);
  const [comentarioAprovacao, setComentarioAprovacao] = useState('');

  useEffect(() => {
    fetchOcorrencias();
  }, []);

  const fetchOcorrencias = async () => {
    try {
      const data = await getOcorrenciasPendentes();
      setOcorrencias(data);
    } catch (err) {
      setError('Erro ao carregar ocorrências pendentes');
    } finally {
      setLoading(false);
    }
  };

  const handleAprovar = async (ocorrenciaId) => {
    if (!comentarioAprovacao.trim()) {
      setError('Digite um comentário para a aprovação');
      return;
    }

    setAprovando(ocorrenciaId);
    setError('');

    try {
      await aprovarOcorrencia(ocorrenciaId, comentarioAprovacao);
      setComentarioAprovacao('');
      fetchOcorrencias(); // Recarregar lista
    } catch (err) {
      setError('Erro ao aprovar ocorrência');
    } finally {
      setAprovando(null);
    }
  };

  const getStatusClass = (tipo) => {
    const statusMap = {
      'SOLICITADA': 'status-solicitada',
      'CANCELADA': 'status-cancelada',
      'SINISTRO': 'status-sinistro'
    };
    return statusMap[tipo] || 'status-solicitada';
  };

  if (loading) return <div className="loading">Carregando...</div>;

  return (
    <div className="aprovacoes-page fade-in">
      <div className="card">
        <div className="card-header">
          <h2 className="card-title">✅ Aprovações Pendentes</h2>
          <span className="badge-count">{ocorrencias.length} pendentes</span>
        </div>

        {error && (
          <div className="error-message">
            ❌ {error}
          </div>
        )}

        {ocorrencias.length === 0 ? (
          <div className="empty-state">
            <p>🎉 Não há ocorrências pendentes de aprovação!</p>
          </div>
        ) : (
          <div className="ocorrencias-grid">
            {ocorrencias.map((ocorrencia) => (
              <div key={ocorrencia.id} className="ocorrencia-card">
                <div className="ocorrencia-header">
                  <span className={`status-badge ${getStatusClass(ocorrencia.tipo)}`}>
                    {ocorrencia.tipo}
                  </span>
                  <span className="ocorrencia-data">
                    {new Date(ocorrencia.dataOcorrencia).toLocaleDateString('pt-BR')}
                  </span>
                </div>

                <div className="ocorrencia-info">
                  <h4>Coleta #{ocorrencia.coleta?.codigo}</h4>
                  <p><strong>Descrição:</strong> {ocorrencia.coleta?.descricao}</p>
                  <p><strong>Comentário:</strong> {ocorrencia.comentario}</p>
                  <p><strong>Solicitado por:</strong> {ocorrencia.usuario?.nome}</p>
                </div>

                <div className="ocorrencia-actions">
                  <Link 
                    to={`/coletas/${ocorrencia.coleta?.id}`} 
                    className="btn btn-secondary"
                  >
                    👁️ Ver Detalhes
                  </Link>
                  
                  <div className="aprovacao-form">
                    <textarea
                      placeholder="Comentário da aprovação..."
                      value={comentarioAprovacao}
                      onChange={(e) => setComentarioAprovacao(e.target.value)}
                      className="form-input form-textarea"
                      rows="3"
                    />
                    <button
                      onClick={() => handleAprovar(ocorrencia.id)}
                      className="btn btn-success"
                      disabled={aprovando === ocorrencia.id}
                    >
                      {aprovando === ocorrencia.id ? '🔄 Aprovando...' : '✅ Aprovar'}
                    </button>
                  </div>
                </div>
              </div>
            ))}
          </div>
        )}
      </div>
    </div>
  );
};

export default AprovacoesPage; 