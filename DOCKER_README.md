# Devolve Fácil - Docker Setup

Este documento explica como executar o projeto Devolve Fácil usando Docker.

## Pré-requisitos

- Docker instalado
- Docker Compose instalado

## Configuração

1. **Copie o arquivo de variáveis de ambiente:**
   ```bash
   cp env.example .env
   ```

2. **Edite o arquivo .env se necessário:**
   ```bash
   # Configurações do MySQL
   MYSQL_DATABASE=devolvefacil
   MYSQL_USER=devolveuser
   MYSQL_PASSWORD=devolvesenha
   MYSQL_ROOT_PASSWORD=root123
   ```

## Executando o Projeto

### 🚀 Produção (com Tomcat):
```bash
docker-compose up -d --build
```

### 🔧 Desenvolvimento (com JAR):
```bash
docker-compose -f docker-compose.dev.yml up -d --build
```

### Ver logs:
```bash
# Todos os serviços
docker-compose logs -f

# Serviço específico
docker-compose logs -f backend
docker-compose logs -f frontend
docker-compose logs -f db
```

### Parar todos os serviços:
```bash
docker-compose down
```

### Parar e remover volumes (cuidado: apaga dados do banco):
```bash
docker-compose down -v
```

## Acessos

- **Frontend:** http://localhost:3000
- **Backend API:** http://localhost:8080/api
- **MySQL:** localhost:3306

## Estrutura dos Containers

### Backend (Spring Boot + Tomcat) - Produção
- **Porta:** 8080
- **Build:** Multi-stage com Maven (WAR)
- **Runtime:** Tomcat 10.1 + JDK 17
- **Banco:** Conecta ao MySQL
- **Arquivo:** `devolvefacil.war` deployado como ROOT

### Backend (Spring Boot) - Desenvolvimento
- **Porta:** 8080
- **Build:** Multi-stage com Maven (JAR)
- **Runtime:** Java 17 JRE
- **Banco:** Conecta ao MySQL

### Frontend (React)
- **Porta:** 3000
- **Build:** Multi-stage com Node.js
- **Runtime:** Nginx
- **API:** Consome backend em localhost:8080

### Database (MySQL)
- **Porta:** 3306
- **Versão:** MySQL 8.0
- **Persistência:** Volume `db_data`

## Networks

- **backend:** Comunicação entre backend e database
- **frontend:** Comunicação entre frontend e backend

## Volumes

- **db_data:** Persistência dos dados do MySQL

## Deploy em Tomcat Externo

Para deploy em um servidor Tomcat externo:

1. **Gerar o WAR:**
   ```bash
   cd Back-end
   mvn clean package -DskipTests
   ```

2. **O arquivo WAR estará em:** `target/devolvefacil.war`

3. **Deploy no Tomcat:**
   - Copie o WAR para `webapps/`
   - Ou renomeie para `ROOT.war` para ser a aplicação padrão

4. **Configurar variáveis de ambiente no Tomcat:**
   ```bash
   # No setenv.sh do Tomcat
   export JAVA_OPTS="$JAVA_OPTS -Dspring.profiles.active=prod"
   export JAVA_OPTS="$JAVA_OPTS -Dspring.datasource.url=jdbc:mysql://localhost:3306/devolvefacil"
   export JAVA_OPTS="$JAVA_OPTS -Dspring.datasource.username=devolveuser"
   export JAVA_OPTS="$JAVA_OPTS -Dspring.datasource.password=devolvesenha"
   ```

## Desenvolvimento

Para desenvolvimento local sem Docker:
1. Backend: `mvn spring-boot:run` (porta 8080)
2. Frontend: `npm start` (porta 3000)
3. MySQL: Instalar localmente ou usar Docker apenas para o banco

## Troubleshooting

### Problemas comuns:

1. **Porta já em uso:**
   ```bash
   # Verificar portas em uso
   netstat -ano | findstr :8080
   netstat -ano | findstr :3000
   ```

2. **Erro de conexão com banco:**
   ```bash
   # Aguardar banco inicializar
   docker-compose logs db
   ```

3. **Rebuild após mudanças:**
   ```bash
   docker-compose build --no-cache
   docker-compose up -d
   ```

4. **Problemas com Tomcat:**
   ```bash
   # Verificar logs do Tomcat
   docker-compose logs backend
   
   # Entrar no container
   docker exec -it devolvefacil-backend-1 bash
   ```

## Comandos Úteis

```bash
# Ver containers rodando
docker ps

# Entrar em um container
docker exec -it devolvefacil-backend-1 bash
docker exec -it devolvefacil-frontend-1 sh

# Ver logs em tempo real
docker-compose logs -f backend

# Parar e remover tudo
docker-compose down -v --remove-orphans

# Gerar WAR para deploy externo
cd Back-end && mvn clean package -DskipTests
```

## Diferenças entre Produção e Desenvolvimento

| Aspecto | Produção | Desenvolvimento |
|---------|----------|-----------------|
| **Backend** | Tomcat + WAR | JAR standalone |
| **Imagem** | ~500MB | ~200MB |
| **Deploy** | WAR no Tomcat | JAR executável |
| **Hot Reload** | Não | Sim (com volume) |
| **Uso** | Servidor de produção | Desenvolvimento local | 