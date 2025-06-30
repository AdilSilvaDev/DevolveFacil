import React, { useContext } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { AuthContext } from '../context/AuthContext';
import './Navbar.css';

const Navbar = () => {
  const { user, logout } = useContext(AuthContext);
  const navigate = useNavigate();

  const handleLogout = async () => {
    await logout();
    navigate('/login');
  };

  return (
    <nav className="navbar">
      <div className="navbar-container">
        <div className="navbar-brand">
          <Link to="/dashboard">
            <h2>🔄 Devolve Fácil</h2>
          </Link>
        </div>
        
        <div className="navbar-menu">
          <Link to="/dashboard" className="nav-link">
            📊 Dashboard
          </Link>
          <Link to="/coletas" className="nav-link">
            📦 Coletas
          </Link>
          <Link to="/coletas/nova" className="nav-link">
            ➕ Nova Coleta
          </Link>
          <Link to="/aprovacoes" className="nav-link">
            ✅ Aprovações
          </Link>
        </div>
        
        <div className="navbar-user">
          <span className="user-info">
            👤 {user?.nome} ({user?.tipo})
          </span>
          <button onClick={handleLogout} className="logout-btn">
            🚪 Sair
          </button>
        </div>
      </div>
    </nav>
  );
};

export default Navbar; 