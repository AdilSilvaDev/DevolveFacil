import React, { useState, useEffect, useContext } from 'react';
import { Link } from 'react-router-dom';
import { AuthContext } from '../context/AuthContext';
import { getMinhasColetas } from '../api/api';
import './DashboardPage.css';

const DashboardPage = () => {
  const { user } = useContext(AuthContext);
  const [coletas, setColetas] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');

  useEffect(() => {
    const fetchColetas = async () => {
      try {
        const data = await getMinhasColetas();
        setColetas(data);
      } catch (err) {
        setError('Erro ao carregar dados do dashboard');
      } finally {
        setLoading(false);
      }
    };
    fetchColetas();
  }, []);

  const getStatusCount = (status) => {
    return coletas.filter(coleta => coleta.status === status).length;
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

  const coletasRecentes = coletas.slice(0, 5);

  if (loading) return <div className="loading">Carregando...</div>;

  return (
    <div className="dashboard-page fade-in">
      <div className="welcome-section">
        <h1>👋 Bem-vindo, {user?.nome}!</h1>
        <p>Gerencie suas coletas e acompanhe o status das devoluções</p>
      </div>

      <div className="stats-grid">
        <div className="stat-card">
          <div className="stat-icon">📦</div>
          <div className="stat-content">
            <h3>{coletas.length}</h3>
            <p>Total de Coletas</p>
          </div>
        </div>

        <div className="stat-card">
          <div className="stat-icon">⏳</div>
          <div className="stat-content">
            <h3>{getStatusCount('SOLICITADA')}</h3>
            <p>Solicitadas</p>
          </div>
        </div>

        <div className="stat-card">
          <div className="stat-icon">🚚</div>
          <div className="stat-content">
            <h3>{getStatusCount('COLETADA')}</h3>
            <p>Coletadas</p>
          </div>
        </div>

        <div className="stat-card">
          <div className="stat-icon">✅</div>
          <div className="stat-content">
            <h3>{getStatusCount('ENTREGUE')}</h3>
            <p>Entregues</p>
          </div>
        </div>
      </div>

      <div className="dashboard-content">
        <div className="quick-actions">
          <div className="card">
            <div className="card-header">
              <h3 className="card-title">⚡ Ações Rápidas</h3>
            </div>
            <div className="actions-grid">
              <Link to="/coletas/nova" className="action-card">
                <div className="action-icon">➕</div>
                <h4>Nova Coleta</h4>
                <p>Criar uma nova solicitação de coleta</p>
              </Link>
              
              <Link to="/coletas" className="action-card">
                <div className="action-icon">📋</div>
                <h4>Ver Coletas</h4>
                <p>Visualizar todas as suas coletas</p>
              </Link>
              
              <Link to="/aprovacoes" className="action-card">
                <div className="action-icon">✅</div>
                <h4>Aprovações</h4>
                <p>Gerenciar aprovações pendentes</p>
              </Link>
              
              <div className="action-card">
                <div className="action-icon">📊</div>
                <h4>Relatórios</h4>
                <p>Visualizar relatórios e estatísticas</p>
              </div>
            </div>
          </div>
        </div>

        <div className="recent-coletas">
          <div className="card">
            <div className="card-header">
              <h3 className="card-title">🕒 Coletas Recentes</h3>
              <Link to="/coletas" className="btn btn-secondary">
                Ver Todas
              </Link>
            </div>
            
            {coletasRecentes.length === 0 ? (
              <div className="empty-state">
                <p>Nenhuma coleta encontrada</p>
                <Link to="/coletas/nova" className="btn btn-primary">
                  Criar Primeira Coleta
                </Link>
              </div>
            ) : (
              <div className="coletas-list">
                {coletasRecentes.map(coleta => (
                  <div key={coleta.id} className="coleta-item">
                    <div className="coleta-info">
                      <h4>#{coleta.codigo}</h4>
                      <p>{coleta.descricao}</p>
                      <span className="coleta-date">
                        {new Date(coleta.dataCriacao).toLocaleDateString('pt-BR')}
                      </span>
                    </div>
                    <div className="coleta-status">
                      <span className={`status-badge ${getStatusClass(coleta.status)}`}>
                        {coleta.status}
                      </span>
                      <Link to={`/coletas/${coleta.id}`} className="btn btn-primary btn-sm">
                        Ver
                      </Link>
                    </div>
                  </div>
                ))}
              </div>
            )}
          </div>
        </div>
      </div>

      {error && (
        <div className="error-message">
          ❌ {error}
        </div>
      )}
    </div>
  );
};

export default DashboardPage; 