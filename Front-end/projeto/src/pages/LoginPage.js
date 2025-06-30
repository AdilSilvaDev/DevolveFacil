import React, { useState, useContext } from 'react';
import { AuthContext } from '../context/AuthContext';
import './LoginPage.css';

const LoginPage = () => {
  const { login } = useContext(AuthContext);
  const [email, setEmail] = useState('');
  const [senha, setSenha] = useState('');
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    setLoading(true);
    
    try {
      await login(email, senha);
    } catch (err) {
      setError('E-mail ou senha inválidos!');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="login-page">
      <div className="login-container">
        <div className="login-card">
          <div className="login-header">
            <h1>🔄 Devolve Fácil</h1>
            <p>Sistema de Gestão de Devoluções</p>
          </div>
          
          <form onSubmit={handleSubmit} className="login-form">
            <div className="form-group">
              <label htmlFor="email" className="form-label">
                📧 E-mail
              </label>
              <input
                id="email"
                type="email"
                className="form-input"
                placeholder="Digite seu e-mail"
                value={email}
                onChange={e => setEmail(e.target.value)}
                required
                disabled={loading}
              />
            </div>
            
            <div className="form-group">
              <label htmlFor="senha" className="form-label">
                🔒 Senha
              </label>
              <input
                id="senha"
                type="password"
                className="form-input"
                placeholder="Digite sua senha"
                value={senha}
                onChange={e => setSenha(e.target.value)}
                required
                disabled={loading}
              />
            </div>
            
            {error && (
              <div className="error-message">
                ❌ {error}
              </div>
            )}
            
            <button 
              type="submit" 
              className="btn btn-primary login-btn"
              disabled={loading}
            >
              {loading ? '🔄 Entrando...' : '🚀 Entrar'}
            </button>
          </form>
          
          <div className="login-footer">
            <p>© 2024 Devolve Fácil - Todos os direitos reservados</p>
          </div>
        </div>
      </div>
    </div>
  );
};

export default LoginPage; 