# 🚀 Instruções para Executar o Devolve Fácil

## ✅ Checklist de Pré-requisitos

Antes de começar, verifique se você tem instalado:

- [ ] **Java 17 ou superior** (`java -version`)
- [ ] **Maven 3.6+** (`mvn -version`)
- [ ] **Node.js 16+** (`node -version`)
- [ ] **npm 8+** (`npm -version`)
- [ ] **MySQL 8.0+** rodando

## 🔧 Passo a Passo

### 1. Configurar o Banco de Dados

```sql
-- Conecte no MySQL e execute:
CREATE DATABASE devolvefacil;
CREATE USER 'devolvefacil'@'localhost' IDENTIFIED BY 'senha123';
GRANT ALL PRIVILEGES ON devolvefacil.* TO 'devolvefacil'@'localhost';
FLUSH PRIVILEGES;
```

### 2. Configurar o Back-end

Edite o arquivo `Back-end/src/main/resources/application.properties`:

```properties
# Configurações do banco
spring.datasource.url=jdbc:mysql://localhost:3306/devolvefacil
spring.datasource.username=devolvefacil
spring.datasource.password=senha123

# Configurações JPA
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect

# Configurações do servidor
server.port=8080
```

### 3. Executar o Back-end

```bash
cd Back-end
mvn clean install
mvn spring-boot:run
```

**Verificação**: Acesse `http://localhost:8080` - deve mostrar uma página de erro (normal, pois não há endpoint raiz)

### 4. Executar o Front-end

```bash
cd Front-end/projeto
npm install
npm start
```

**Verificação**: Acesse `http://localhost:3000` - deve abrir a tela de login

## 🧪 Testando o Sistema

### Criar Usuários de Teste

O sistema não tem usuários pré-cadastrados. Você precisará:

1. **Criar um endpoint temporário** para cadastro de usuários
2. **Ou inserir diretamente no banco**:

```sql
INSERT INTO usuarios (nome, email, senha, tipo, data_criacao, ativo) VALUES
('João Solicitante', 'joao@teste.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDa', 'SOLICITANTE', NOW(), 1),
('Maria Transportadora', 'maria@teste.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDa', 'TRANSPORTADOR', NOW(), 1);
```

**Senha para ambos**: `123456`

### Fluxo de Teste

1. **Login** com `joao@teste.com` / `123456`
2. **Criar uma coleta** na página "Nova Coleta"
3. **Fazer logout** e logar com `maria@teste.com`
4. **Visualizar coletas** disponíveis
5. **Atualizar status** de uma coleta

## 🐛 Problemas Comuns

### Erro: "Java version not supported"
```bash
# Verifique sua versão do Java
java -version

# Se for menor que 17, ajuste no pom.xml:
<java.version>11</java.version>  # ou sua versão
```

### Erro: "Cannot connect to database"
- Verifique se MySQL está rodando
- Confirme credenciais no `application.properties`
- Teste conexão: `mysql -u devolvefacil -p`

### Erro: "npm not recognized"
- Instale Node.js: https://nodejs.org/
- Reinicie o terminal após instalação

### Erro: "Port already in use"
```bash
# Windows
netstat -ano | findstr :8080
taskkill /PID [PID] /F

# Linux/Mac
lsof -i :8080
kill -9 [PID]
```

## 📱 Funcionalidades Disponíveis

### Para Solicitantes:
- ✅ Login/Logout
- ✅ Dashboard
- ✅ Criar coletas
- ✅ Visualizar coletas
- ✅ Upload de anexos
- ✅ Acompanhar status

### Para Transportadores:
- ✅ Login/Logout
- ✅ Dashboard
- ✅ Visualizar coletas atribuídas
- ✅ Atualizar status
- ✅ Registrar ocorrências
- ✅ Upload de comprovantes

## 🔍 Endpoints da API

- `POST /api/auth/login` - Login
- `POST /api/auth/logout` - Logout
- `GET /api/usuarios/perfil` - Perfil do usuário
- `GET /api/coletas` - Listar coletas
- `POST /api/coletas` - Criar coleta
- `PUT /api/coletas/{id}/status` - Atualizar status
- `POST /api/anexos/upload` - Upload de arquivo

## 📞 Suporte

Se encontrar problemas:

1. **Verifique os logs** no console
2. **Confirme versões** das tecnologias
3. **Teste endpoints** individualmente
4. **Verifique banco** de dados

---

# Windows PowerShell
.\docker-start.ps1

