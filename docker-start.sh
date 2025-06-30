#!/bin/bash

echo "🚀 Iniciando Devolve Fácil com Docker..."

# Verificar se o arquivo .env existe
if [ ! -f .env ]; then
    echo "📝 Criando arquivo .env a partir do exemplo..."
    cp env.example .env
    echo "✅ Arquivo .env criado! Edite se necessário."
fi

# Verificar se o Docker está rodando
if ! docker info > /dev/null 2>&1; then
    echo "❌ Docker não está rodando. Inicie o Docker Desktop primeiro."
    exit 1
fi

echo "🔨 Construindo e iniciando containers..."
docker-compose up -d --build

echo "⏳ Aguardando serviços inicializarem..."
sleep 10

echo "📊 Status dos containers:"
docker-compose ps

echo ""
echo "🌐 Acessos:"
echo "   Frontend: http://localhost:3000"
echo "   Backend API: http://localhost:8080/api"
echo "   MySQL: localhost:3306"
echo ""
echo "📋 Comandos úteis:"
echo "   Ver logs: docker-compose logs -f"
echo "   Parar: docker-compose down"
echo "   Rebuild: docker-compose up -d --build" 