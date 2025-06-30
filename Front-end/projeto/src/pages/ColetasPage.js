import React, { useEffect, useState, useContext } from 'react';
import { Link } from 'react-router-dom';
import { AuthContext } from '../context/AuthContext';
import { getMinhasColetas } from '../api/api';
import './ColetasPage.css';

const ColetasPage = () => {
  const { user } = useContext(AuthContext);
  const [coletas, setColetas] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const [filtroStatus, setFiltroStatus] = useState('');

  useEffect(() => {
    const fetchColetas = async () => {
      try {
        const data = await getMinhasColetas();
        setColetas(data);
      } catch (err) {
        setError('Erro ao carregar coletas');
      } finally {
        setLoading(false);
      }
    };
    fetchColetas();
  }, []);

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

  const coletasFiltradas = filtroStatus 
    ? coletas.filter(coleta => coleta.status === filtroStatus)
    : coletas;

  if (loading) return <div className="loading">Carregando...</div>;

  return (
    <div className="coletas-page fade-in">
      <div className="card">
        <div className="card-header">
          <h2 className="card-title">📦 Minhas Coletas</h2>
          <Link to="/coletas/nova" className="btn btn-primary">
            ➕ Nova Coleta
          </Link>
        </div>

        {error && (
          <div className="error-message">
            ❌ {error}
          </div>
        )}

        <div className="filtros-section">
          <label className="form-label">Filtrar por Status:</label>
          <select
            value={filtroStatus}
            onChange={(e) => setFiltroStatus(e.target.value)}
            className="form-input"
            style={{ maxWidth: '200px' }}
          >
            <option value="">Todos os status</option>
            <option value="SOLICITADA">Solicitada</option>
            <option value="COLETADA">Coletada</option>
            <option value="ENTREGUE">Entregue</option>
            <option value="CANCELADA">Cancelada</option>
            <option value="SINISTRO">Sinistro</option>
          </select>
        </div>

        {coletasFiltradas.length === 0 ? (
          <div className="empty-state">
            <p>📭 Nenhuma coleta encontrada</p>
            <Link to="/coletas/nova" className="btn btn-primary">
              Criar primeira coleta
            </Link>
          </div>
        ) : (
          <div className="coletas-grid">
            {coletasFiltradas.map(coleta => (
              <div key={coleta.id} className="coleta-card">
                <div className="coleta-header">
                  <h3 className="coleta-codigo">#{coleta.codigo}</h3>
                  <span className={`status-badge ${getStatusClass(coleta.status)}`}>
                    {coleta.status}
                  </span>
                </div>

                <div className="coleta-content">
                  <p className="coleta-descricao">{coleta.descricao}</p>
                  
                  <div className="coleta-info">
                    <div className="info-item">
                      <strong>📅 Criada:</strong> {new Date(coleta.dataCriacao).toLocaleDateString('pt-BR')}
                    </div>
                    {coleta.dataPrevistaColeta && (
                      <div className="info-item">
                        <strong>⏰ Prevista:</strong> {new Date(coleta.dataPrevistaColeta).toLocaleDateString('pt-BR')}
                      </div>
                    )}
                    {coleta.solicitante && (
                      <div className="info-item">
                        <strong>👤 Solicitante:</strong> {coleta.solicitante.nome}
                      </div>
                    )}
                    {coleta.transportador && (
                      <div className="info-item">
                        <strong>🚚 Transportador:</strong> {coleta.transportador.nome}
                      </div>
                    )}
                  </div>
                </div>

                <div className="coleta-actions">
                  <Link to={`/coletas/${coleta.id}`} className="btn btn-primary">
                    👁️ Ver Detalhes
                  </Link>
                  <Link to={`/upload/${coleta.id}`} className="btn btn-success">
                    📎 Anexar
                  </Link>
                </div>
              </div>
            ))}
          </div>
        )}
      </div>
    </div>
  );
};

export default ColetasPage; 