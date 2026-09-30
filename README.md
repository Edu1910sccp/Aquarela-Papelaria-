Integrantes: Eduardo, Arthur, Iraquitan e David
Turma: 3o ano A - Ensino Medio
Repositorio: https://github.com/Edu1910sccp/Projeto-H-lio-
Entrega final: 10/12/2026


1. VISAO GERAL
---------------
Nome do app: Aquarela Papelaria

Pitch: A Aquarela ajuda as pessoas a transformarem ideias ou conceitos
em produtos fisicos impressos, sem precisar ir presencialmente ate a
grafica para cada pedido.


2. PROBLEMA
------------
Hoje, sem o app: quem quer um produto personalizado (uma caneca, um
botom) ou precisa imprimir algo (um trabalho escolar, uma planilha) tem
que ir ate a loja fisica, explicar o pedido pessoalmente e esperar sem
nenhum controle simples do que ja foi pedido.

Situacao concreta [DEFINIR EM GRUPO - validar/ajustar com exemplo real,
ex. de conversa com o Claudio]: um estudante decide na vespera que
precisa de um material personalizado para um trabalho e nao tem como
registrar o pedido, acompanhar o status ou saber o que ja pediu antes
sem ir a loja de novo.

Como e resolvido hoje: indo presencialmente ate a grafica.


3. PUBLICO-ALVO
-----------------
Perfil principal:
- Pessoas comuns que querem um produto personalizado (caneca, botom)
- Estudantes que precisam imprimir trabalhos escolares ou comprar
  materiais escolares
- Trabalhadores/professores que precisam imprimir planilhas ou
  documentos

Quando/onde usam [DEFINIR EM GRUPO]: a preencher - ex. "sempre que
surge a necessidade de um produto novo, geralmente de casa ou da
escola, antes de ir a loja retirar".

Pessoa real para testar o app: Claudio Lucas de Farias (dono da loja,
tio de um dos membros do grupo).


4. SOLUCAO EM UMA TELA
------------------------
[DEFINIR EM GRUPO - a descricao abaixo e uma proposta inicial, precisa
ser validada]

- A tela principal lista: os pedidos ja feitos (produto, cliente,
  status)
- A acao principal do usuario e: criar um novo pedido, escolhendo o
  tipo de produto e o cliente
- Depois de agir, o usuario ve: o novo pedido aparecendo na lista, com
  status "Em aberto"


5. FUNCIONALIDADES DO MVP
----------------------------
[DEFINIR EM GRUPO - nomes de funcionalidade e responsavel a confirmar;
proposta inicial abaixo]

F1 - Cadastrar/listar produtos (caneca, botom, impressao)
     Essencial: Sim | Responsavel: a definir

F2 - Criar pedido vinculado a um cliente e um produto
     Essencial: Sim | Responsavel: a definir

F3 - Listar pedidos com status (em aberto / pronto / entregue)
     Essencial: Sim | Responsavel: a definir

F4 - Buscar/filtrar pedidos por cliente ou status
     Essencial: Nao | Responsavel: a definir


6. FORA DO ESCOPO
--------------------
[DEFINIR EM GRUPO - proposta inicial, seguindo as sugestoes do canvas]

- Login/cadastro de usuario com senha
- Pagamento dentro do app
- Notificacoes push
- Chat com a loja
- Sincronizacao em nuvem / multiplos dispositivos


7. CAMINHO TECNICO
---------------------
Opcao escolhida: A - Room (dados salvos no proprio celular).

Entidades previstas (a implementar por cada integrante):
- Produto (nome, tipo, preco)
- Cliente (nome, contato)
- Pedido (produto, cliente, status, data)

Bibliotecas do grupo:
- Jetpack Compose + Material 3
- Navigation Compose
- Room + KSP
- Kotlin Coroutines + Flow

Obs.: as dependencias do Retrofit ja estao configuradas no boilerplate
por padrao do template da turma, mas nao serao usadas neste MVP, ja
que o caminho escolhido foi Room.

Onde entra o try/catch:
- Lista de pedidos vazia ao abrir o app
  -> usuario ve: mensagem "Nenhum pedido ainda" em vez de tela em branco
- Campo obrigatorio em branco ao criar pedido/cliente/produto
  -> usuario ve: mensagem indicando o campo que falta preencher
- Erro inesperado ao salvar no banco
  -> usuario ve: mensagem generica de erro, sem o app fechar


8. IDENTIDADE VISUAL
-----------------------
[DEFINIR EM GRUPO - cor e icone ainda nao escolhidos; valores abaixo
sao placeholder do boilerplate]

- Nome exibido (strings.xml): Aquarela Papelaria
- Cor principal (hex): #6650A4 (placeholder - trocar pela cor definida
  pelo grupo)
- Ideia do icone (512x512): a definir
- applicationId: br.edu.ifpe.aquarelapapelaria
- Versao inicial: 1.0 (versionCode 1)


9. EQUIPE, PAPEIS E RISCOS
------------------------------
[DEFINIR EM GRUPO - papeis ainda nao atribuidos entre Eduardo, Arthur,
Iraquitan e David]

- Dev / telas: David
- Dev / dados (Room): Eduardo 
- Design e identidade visual: Iraquitan 
- Documentacao, build e entrega: Arthur 

Lembrete do canvas: todos programam - o papel define quem responde por
aquela parte, nao quem trabalha sozinho nela.

Riscos:
- Risco: alguns membros do grupo não possuem computador em casa| Plano B: arrumar alguns dias pra alugar algum laboratório e fazer.


10. ACORDO DE TRABALHO COM IA
---------------------------------
Regras combinadas pelo grupo (do canvas, para o AGENTS.md):
1. Ninguem clica "Accept" no Agent Mode sem ler a mudanca inteira.
2. Quem aceitou o codigo escreve o comentario de fronteira do arquivo.
3. Antes de cada marco, o grupo revisa junto: alguem nao entendeu
   alguma parte?
4. Nenhuma chave de API ou senha vai para o prompt.

Outro combinado do grupo [DEFINIR EM GRUPO]: a preencher.

Como garantir que todos entendem tudo [DEFINIR EM GRUPO]: a preencher
- ex. quem implementa apresenta o arquivo aos demais, revezar partes,
revisar PR do colega.


11. MARCOS ATE 10/12
------------------------
M1 - Canvas preenchido + repositorio criado
     Prazo: 16/09 | Comprovacao: CANVAS.md no main

M2 - PRD aprovado + telas rascunhadas
     Prazo: 30/09 | Comprovacao: PRD.md + imagens em docs/

M3 - Funcionalidade base rodando
     Prazo: 21/10 | Comprovacao: tela principal lista dados + 1 acao + try/catch

M4 - Dados completos (Room) e erros tratados
     Prazo: 11/11 | Comprovacao: commits da camada de dados

M5 - Identidade visual + .apk de release testado
     Prazo: 25/11 | Comprovacao: icone, cores, .apk testado por 2 pessoas de fora

M6 - .aab + material de loja + README.md
     Prazo: 02/12 | Comprovacao: pasta loja/ + README.md completo

Entrega e apresentacao
     Prazo: 10/12 | Comprovacao: tag v1.0 no repositorio


12. DEFINICAO DE PRONTO
---------------------------
O grupo so considera o app pronto quando todas estas frases forem
verdadeiras:

[ ] O app abre e nao fecha sozinho depois de 5 minutos de uso.
[ ] A tela principal mostra dados reais (nao texto de exemplo fixo no codigo).
[ ] A acao principal funciona e o resultado aparece na tela.
[ ] Quando algo falha, aparece uma mensagem clara - o app nao quebra.
[ ] O app tem nome, icone e cor proprios (nada de icone padrao do Android).
[ ] Duas pessoas de fora do grupo instalaram o .apk e conseguiram usar sem explicacao.
[ ] O README.md explica o que o app faz, com o que foi feito e como gerar o build.
[ ] O docs/USO_DE_IA.md e o AGENTS.md estao preenchidos.
[ ] Cada integrante consegue abrir o projeto e fazer uma mudanca pequena sozinho.
[ ] Todo arquivo tem o comentario de fronteira escrito pelo grupo.
