Write-Host "🚀 Devolve Fácil - Docker Setup" -ForegroundColor Green
Write-Host ""

# Verificar se o arquivo .env existe
if (-not (Test-Path ".env")) {
    Write-Host "📝 Criando arquivo .env a partir do exemplo..." -ForegroundColor Yellow
    Copy-Item "env.example" ".env"
    Write-Host "✅ Arquivo .env criado! Edite se necessário." -ForegroundColor Green
}

# Verificar se o Docker está rodando
try {
    docker info | Out-Null
} catch {
    Write-Host "❌ Docker não está rodando. Inicie o Docker Desktop primeiro." -ForegroundColor Red
    exit 1
}

Write-Host "Escolha o modo de execução:" -ForegroundColor Cyan
Write-Host "1. 🚀 Produção (Tomcat + WAR)" -ForegroundColor White
Write-Host "2. 🔧 Desenvolvimento (JAR standalone)" -ForegroundColor White
Write-Host "3. 📦 Apenas gerar WAR para deploy externo" -ForegroundColor White
Write-Host ""

$choice = Read-Host "Digite sua escolha (1, 2 ou 3)"

switch ($choice) {
    "1" {
        Write-Host "🔨 Construindo e iniciando containers em modo PRODUÇÃO..." -ForegroundColor Yellow
        docker-compose up -d --build
        
        Write-Host "⏳ Aguardando serviços inicializarem..." -ForegroundColor Yellow
        Start-Sleep -Seconds 15
        
        Write-Host "📊 Status dos containers:" -ForegroundColor Cyan
        docker-compose ps
        
        Write-Host ""
        Write-Host "🌐 Acessos:" -ForegroundColor Green
        Write-Host "   Frontend: http://localhost:3000" -ForegroundColor White
        Write-Host "   Backend API: http://localhost:8080/api" -ForegroundColor White
        Write-Host "   MySQL: localhost:3306" -ForegroundColor White
        Write-Host ""
        Write-Host "📋 Comandos úteis:" -ForegroundColor Cyan
        Write-Host "   Ver logs: docker-compose logs -f" -ForegroundColor White
        Write-Host "   Parar: docker-compose down" -ForegroundColor White
        Write-Host "   Rebuild: docker-compose up -d --build" -ForegroundColor White
    }
    "2" {
        Write-Host "🔨 Construindo e iniciando containers em modo DESENVOLVIMENTO..." -ForegroundColor Yellow
        docker-compose -f docker-compose.dev.yml up -d --build
        
        Write-Host "⏳ Aguardando serviços inicializarem..." -ForegroundColor Yellow
        Start-Sleep -Seconds 10
        
        Write-Host "📊 Status dos containers:" -ForegroundColor Cyan
        docker-compose -f docker-compose.dev.yml ps
        
        Write-Host ""
        Write-Host "🌐 Acessos:" -ForegroundColor Green
        Write-Host "   Frontend: http://localhost:3000" -ForegroundColor White
        Write-Host "   Backend API: http://localhost:8080/api" -ForegroundColor White
        Write-Host "   MySQL: localhost:3306" -ForegroundColor White
        Write-Host ""
        Write-Host "📋 Comandos úteis:" -ForegroundColor Cyan
        Write-Host "   Ver logs: docker-compose -f docker-compose.dev.yml logs -f" -ForegroundColor White
        Write-Host "   Parar: docker-compose -f docker-compose.dev.yml down" -ForegroundColor White
        Write-Host "   Rebuild: docker-compose -f docker-compose.dev.yml up -d --build" -ForegroundColor White
    }
    "3" {
        Write-Host "📦 Gerando WAR para deploy externo..." -ForegroundColor Yellow
        Set-Location "Back-end"
        mvn clean package -DskipTests
        
        if (Test-Path "target/devolvefacil.war") {
            Write-Host "✅ WAR gerado com sucesso!" -ForegroundColor Green
            Write-Host "📁 Arquivo: Back-end/target/devolvefacil.war" -ForegroundColor White
            Write-Host ""
            Write-Host "📋 Para deploy no Tomcat:" -ForegroundColor Cyan
            Write-Host "   1. Copie o WAR para webapps/" -ForegroundColor White
            Write-Host "   2. Ou renomeie para ROOT.war" -ForegroundColor White
            Write-Host "   3. Configure as variáveis de ambiente" -ForegroundColor White
        } else {
            Write-Host "❌ Erro ao gerar WAR" -ForegroundColor Red
        }
        Set-Location ".."
    }
    default {
        Write-Host "❌ Opção inválida!" -ForegroundColor Red
    }
} 