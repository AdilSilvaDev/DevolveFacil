import React, { useContext } from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import { AuthProvider, AuthContext } from './context/AuthContext';
import LoginPage from './pages/LoginPage';
import DashboardPage from './pages/DashboardPage';
import ColetasPage from './pages/ColetasPage';
import ColetaDetalhePage from './pages/ColetaDetalhePage';
import NovaColetaPage from './pages/NovaColetaPage';
import AprovacoesPage from './pages/AprovacoesPage';
import UploadAnexoPage from './pages/UploadAnexoPage';
import Navbar from './components/Navbar';
import './App.css';

// Componente para rotas protegidas
const ProtectedRoute = ({ children }) => {
  const { user, loading } = useContext(AuthContext);
  
  if (loading) {
    return <div className="loading">Carregando...</div>;
  }
  
  if (!user) {
    return <Navigate to="/login" replace />;
  }
  
  return children;
};

// Componente principal da aplicação
const AppContent = () => {
  const { user } = useContext(AuthContext);

  return (
    <Router>
      <div className="App">
        {user && <Navbar />}
        <main className="main-content">
          <Routes>
            <Route path="/login" element={
              user ? <Navigate to="/dashboard" replace /> : <LoginPage />
            } />
            <Route path="/dashboard" element={
              <ProtectedRoute>
                <DashboardPage />
              </ProtectedRoute>
            } />
            <Route path="/coletas" element={
              <ProtectedRoute>
                <ColetasPage />
              </ProtectedRoute>
            } />
            <Route path="/coletas/nova" element={
              <ProtectedRoute>
                <NovaColetaPage />
              </ProtectedRoute>
            } />
            <Route path="/coletas/:id" element={
              <ProtectedRoute>
                <ColetaDetalhePage />
              </ProtectedRoute>
            } />
            <Route path="/aprovacoes" element={
              <ProtectedRoute>
                <AprovacoesPage />
              </ProtectedRoute>
            } />
            <Route path="/upload/:coletaId" element={
              <ProtectedRoute>
                <UploadAnexoPage />
              </ProtectedRoute>
            } />
            <Route path="/" element={<Navigate to="/dashboard" replace />} />
          </Routes>
        </main>
      </div>
    </Router>
  );
};

// App principal com providers
function App() {
  return (
    <AuthProvider>
      <AppContent />
    </AuthProvider>
  );
}

export default App;
