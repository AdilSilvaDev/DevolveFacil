# Devolve Fácil - Sistema de Gestão de Devoluções

Sistema completo para gerenciamento de entregas reversas com dois tipos de usuários: solicitante e transportador.

## 🚀 Tecnologias Utilizadas

### Back-end
- **Spring Boot 3.5.3**
- **Spring Security** (Autenticação com sessão)
- **Spring Data JPA**
- **MySQL**
- **Maven**

### Front-end
- **React 18**
- **React Router DOM**
- **CSS3** (Design responsivo)

## 📋 Pré-requisitos

### Para o Back-end:
- **Java 17 ou superior**
- **Maven 3.6+**
- **MySQL 8.0+**

### Para o Front-end:
- **Node.js 16+**
- **npm 8+**

## ⚙️ Configuração

### 1. Clone o repositório
```bash
git clone [URL_DO_REPOSITORIO]
cd DevolveFacil
```

### 2. Configuração do Banco de Dados

Crie um banco MySQL chamado `devolvefacil` e configure no arquivo:
`Back-end/src/main/resources/application.properties`

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/devolvefacil
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
```

### 3. Executar o Back-end

```bash
cd Back-end
mvn spring-boot:run
```

O servidor estará disponível em: `http://localhost:8080`

### 4. Executar o Front-end

```bash
cd Front-end/projeto
npm install
npm start
```

O aplicativo estará disponível em: `http://localhost:3000`

## 👥 Tipos de Usuário

### Solicitante
- Criar solicitações de coleta
- Acompanhar status das coletas
- Fazer upload de anexos
- Visualizar ocorrências

### Transportador
- Visualizar coletas atribuídas
- Atualizar status das coletas
- Registrar ocorrências
- Fazer upload de comprovantes

## 🔧 Funcionalidades Principais

- ✅ **Autenticação e Autorização**
- ✅ **Gestão de Usuários**
- ✅ **Criação e Acompanhamento de Coletas**
- ✅ **Upload de Anexos**
- ✅ **Registro de Ocorrências**
- ✅ **Sistema de Aprovações**
- ✅ **Interface Responsiva**

## 📁 Estrutura do Projeto

```
DevolveFacil/
├── Back-end/                 # API Spring Boot
│   ├── src/main/java/com/projeto/
│   │   ├── Config/          # Configurações de segurança
│   │   ├── Controller/      # Controllers REST
│   │   ├── DTO/            # Data Transfer Objects
│   │   ├── Entity/         # Entidades JPA
│   │   ├── Repository/     # Repositórios
│   │   └── Service/        # Serviços de negócio
│   └── src/main/resources/
│       └── application.properties
└── Front-end/               # Aplicação React
    └── projeto/
        ├── src/
        │   ├── components/  # Componentes React
        │   ├── pages/       # Páginas da aplicação
        │   ├── context/     # Contexto de autenticação
        │   └── api/         # Configuração da API
        └── public/
```

## 🐛 Solução de Problemas

### Erro de Java Version
Se aparecer erro de versão do Java, ajuste no `pom.xml`:
```xml
<properties>
    <java.version>17</java.version>  <!-- ou a versão disponível -->
</properties>
```

### Erro de Política de Execução (PowerShell)
Execute no PowerShell como administrador:
```powershell
Set-ExecutionPolicy -ExecutionPolicy RemoteSigned -Scope CurrentUser
```

### Erro de Conexão com Banco
Verifique se:
- MySQL está rodando
- Credenciais estão corretas
- Banco `devolvefacil` foi criado

## 📞 Suporte

Para dúvidas ou problemas, verifique:
1. Versões das tecnologias instaladas
2. Configuração do banco de dados
3. Logs de erro no console

---

**Desenvolvido com ❤️ para facilitar o gerenciamento de devoluções**
