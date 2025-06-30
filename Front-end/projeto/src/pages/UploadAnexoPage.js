import React, { useState, useContext } from 'react';
import { AuthContext } from '../context/AuthContext';
import { uploadAnexo } from '../api/api';

const UploadAnexoPage = ({ coletaId }) => {
  const { user } = useContext(AuthContext);
  const [file, setFile] = useState(null);
  const [descricao, setDescricao] = useState('');
  const [tipoAnexo, setTipoAnexo] = useState('');
  const [msg, setMsg] = useState('');

  const handleSubmit = async (e) => {
    e.preventDefault();
    setMsg('');
    try {
      await uploadAnexo(coletaId, file, descricao, tipoAnexo);
      setMsg('Arquivo enviado com sucesso!');
    } catch {
      setMsg('Erro ao enviar arquivo.');
    }
  };

  return (
    <div>
      <h2>Upload de Anexo</h2>
      <form onSubmit={handleSubmit}>
        <input type="file" onChange={e => setFile(e.target.files[0])} required />
        <input type="text" placeholder="Descrição" value={descricao} onChange={e => setDescricao(e.target.value)} />
        <input type="text" placeholder="Tipo do Anexo" value={tipoAnexo} onChange={e => setTipoAnexo(e.target.value)} />
        <button type="submit">Enviar</button>
      </form>
      {msg && <p>{msg}</p>}
    </div>
  );
};

export default UploadAnexoPage; 