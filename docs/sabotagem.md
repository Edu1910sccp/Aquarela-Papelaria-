# Testes de Sabotagem - Aquarela Papelaria

Este documento registra os testes de sabotagem realizados para validar os estados da interface (Carregando, Vazio, Erro e Sucesso) na entidade Cliente.

## 1. Sabotagem no DAO (Erro de SQL)
*   **Ação**: Alteração da query em `ClienteDao` para uma tabela inexistente (`SELECT * FROM clientes_erro`).
*   **O que apareceu na tela**: O ViewModel capturou a exceção do Room e mudou o estado para `Erro`. A tela exibiu a mensagem "Erro ao carregar clientes." e o botão "Tentar novamente".

## 2. Sabotagem no ViewModel (Exceção Forçada)
*   **Ação**: Inserção de `throw RuntimeException("Bug proposital")` no início do método `carregar()` do `ClientesViewModel`.
*   **O que apareceu na tela**: A tela entrou imediatamente no estado de `Erro`, validando que o bloco `try/catch` no ViewModel está protegendo o aplicativo de fechar e informando o usuário.

## 3. Sabotagem nos Dados (Estado Vazio)
*   **Ação**: Exclusão de todos os registros da tabela `clientes` diretamente no banco de dados.
*   **O que apareceu na tela**: O `Flow` emitiu uma lista vazia, o ViewModel processou a lógica `if (lista.isEmpty())` e a tela exibiu centralizado o texto "Nenhum cliente cadastrado.".

## 4. Sabotagem na UI (Remoção de Estado)
*   **Ação**: Comentado o caso `ClienteUiState.Carregando` no `when` da `ClientesScreen`.
*   **O que apareceu na tela**: Durante o tempo de resposta do banco, a tela ficou totalmente branca e sem feedback visual, provando a importância do estado de carregamento para a experiência do usuário.

## 5. Sabotagem de Recursos (String Nula)
*   **Ação**: Alteração do ID da string no estado de Erro para um valor inválido ou remoção do texto no `strings.xml`.
*   **O que apareceu na tela**: A tela de erro foi desenhada, mas sem a mensagem descritiva (ou com o ID do recurso exposto se não tratado), evidenciando que a UI depende da integridade do `strings.xml` para a fronteira de comunicação.
