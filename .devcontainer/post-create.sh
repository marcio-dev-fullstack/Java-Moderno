#!/bin/bash
echo "=== Configurando o ambiente do GitHub Codespaces ==="
echo "Baixando modelos do Ollama (llama3.2 e nomic-embed-text)..."

# Espera o Ollama estar pronto
until curl -s http://ollama:11434/api/tags > /dev/null; do
  echo "Aguardando container do Ollama iniciar..."
  sleep 2
done

# Baixa os modelos para o RAG e chat local
docker exec devhub-ollama ollama pull llama3.2
docker exec devhub-ollama ollama pull nomic-embed-text

echo "=== Ambiente configurado com sucesso no GitHub Codespaces! ==="
