# 📄 PRD — Documento de Requisitos do Produto

| | |
|---|---|
| **App** | Aquarela Papelaria |
| **Grupo** | _(nº do grupo — a preencher)_ |
| **Autores** | Eduardo, Arthur, Iraquitan e David — 3º ano A, Ensino Médio |
| **Versão do documento** | 1.0 |
| **Última atualização** | 06/10/2026 |
| **Status** | (x) Rascunho ( ) Em revisão ( ) Aprovado |

---

## 1. Visão do produto

**Pitch:** O **Aquarela Papelaria** ajuda **o dono de uma papelaria pequena** a **controlar pedidos de impressão e estoque de material** sem precisar de **caderno, WhatsApp e planilhas soltas**.

**Problema:** O dono da papelaria atende, produz, entrega e cobra sozinho. Os pedidos ficam anotados no caderno ou no WhatsApp e dependem da memória dele, então prazos são esquecidos. Ele também só descobre que o papel ou a tinta acabou na hora de imprimir, com o cliente esperando.

**Por que vale a pena fazer isso:** Com todos os pedidos numa fila ordenada por prazo e o estoque avisando quando o material está acabando, o dono deixa de depender da memória. Ele entrega no prazo, evita parar um serviço por falta de papel ou tinta e registra cada pedido em poucos segundos, até com o cliente na frente.

---

## 2. Público e cenário de uso

**Usuário-alvo:** dono de papelaria pequena que também é o único funcionário. Usa o celular no balcão, todos os dias, sem equipe, sem perfis de acesso e sem senha.

**História de uso (conte como uma cena real):**
> "São 14h, o dono da Aquarela acabou de atender uma cliente que quer 50 cópias coloridas para amanhã. Ele abre o app, toca em '+', escolhe o serviço, informa o nome, a quantidade e o prazo e confirma. Em menos de 30 segundos, o pedido aparece na fila, ordenado pelo prazo. Mais tarde ele toca em 'Iniciar produção' e o app desconta o papel do estoque, avisando que a resma de A4 está no nível mínimo."

---

## 3. Objetivos e não-objetivos

**Objetivos desta versão (v1.0):**

1. Registrar um pedido de impressão em menos de 30 segundos, com poucos campos obrigatórios.
2. Acompanhar a fila de pedidos por prazo e status (Orçamento → Em produção → Pronto → Entregue e pago).
3. Controlar o estoque de papéis, tintas e materiais, com baixa ao iniciar a produção e alerta de nível mínimo.

**Não-objetivos (fora do escopo):**

- ❌ Login, cadastro e perfis de acesso (o app abre direto, um único usuário).
- ❌ Módulo de Caixa (entradas, saídas, fiado, fechamento do dia) e tela de histórico de pedidos por cliente — ficam para uma versão futura.
- ❌ Notificações push, chat, pagamento, sincronização em nuvem e backup automático.

---

## 4. Requisitos funcionais

Prioridade: **Must** (sem isso não entrega), **Should** (importante), **Could** (se sobrar tempo).

| ID | História de usuário | Critério de aceite | Prioridade |
|---|---|---|---|
| RF01 | Como dono da papelaria, quero ver a fila de pedidos ordenada por prazo para saber o que entregar primeiro. | Ao abrir o app, a lista mostra cliente, serviço, valor, prazo e status de cada pedido salvo, do prazo mais próximo ao mais distante; se não houver nenhum, aparece "Nenhum pedido ainda. Toque em + para cadastrar." | Alta |
| RF02 | Como dono da papelaria, quero cadastrar um pedido para não depender de caderno ou WhatsApp. | Ao tocar em "+", escolher um cliente já cadastrado ou digitar um novo (nome obrigatório, telefone opcional), preencher serviço e prazo (obrigatórios) e opcionalmente quantidade e valor, e confirmar, o pedido aparece na fila com status "Orçamento". | Alta |
| RF03 | Como dono da papelaria, quero mudar o status de um pedido com um toque para acompanhar a produção. | Ao tocar no botão de avanço do pedido, o status passa para o próximo (Orçamento → Em produção → Pronto → Entregue e pago) e a lista mostra o novo status na hora. | Alta |
| RF04 | Como dono da papelaria, quero cadastrar e consultar os materiais do estoque com alerta de nível mínimo para não descobrir a falta só na hora de imprimir. | A tela Estoque lista cada material com quantidade atual e nível mínimo; materiais com quantidade igual ou menor que o mínimo aparecem destacados com o aviso "Estoque baixo". | Alta |
| RF05 | Como dono da papelaria, quero que o estoque seja descontado quando o pedido entra em produção para manter as quantidades corretas sem trabalho extra. | Ao mudar um pedido para "Em produção", a quantidade usada é subtraída do material vinculado e a baixa fica registrada no histórico de movimentações; se a quantidade for maior que o saldo, o status não muda e aparece "Estoque insuficiente de [material]". | Alta |
| RF06 | Como dono da papelaria, quero avisar o cliente quando o pedido ficar pronto para ele vir buscar. | Ao tocar em "Avisar cliente" num pedido "Pronto", o WhatsApp abre com uma mensagem pronta para o telefone do cliente; se o cliente não tem telefone ou o app não está instalado, aparece aviso claro. | Media/Baixa |

---

## 5. Requisitos não funcionais

| ID | Requisito | Como será verificado |
|---|---|---|
| RNF01 | O app não pode fechar sozinho durante o uso normal | 5 minutos de uso contínuo sem crash, em 2 celulares diferentes |
| RNF02 | Toda operação que pode falhar está dentro de `try/catch` | Revisão do código: banco e entradas do usuário |
| RNF03 | Nenhuma falha mostra tela branca ou fecha o app — sempre há mensagem ao usuário | Testes de falha da seção 9 |
| RNF04 | O app roda a partir do Android 8.0 (API 26, minSdk) | Instalação em dispositivo real |
| RNF05 | Textos visíveis ficam em `strings.xml`, não escritos direto no código | Revisão do código |
| RNF06 | Todo arquivo do pacote do app tem comentário de fronteira escrito pelo grupo | Revisão do código |
| RNF07 | Qualquer integrante consegue localizar e alterar qualquer parte do app | Teste de mudança ao vivo (rubrica) |
| RNF08 | Cadastrar um pedido leva menos de 30 segundos e exige no máximo 3 campos obrigatórios | Teste cronometrado com o dono da papelaria |
| RNF09 | A fila de pedidos abre em menos de 2 segundos com até 200 pedidos salvos | Teste com dados de exemplo no celular |

---

## 6. Telas e navegação

| Tela | O que mostra | Ações disponíveis |
|---|---|---|
| Pedidos (principal) | Fila ordenada por prazo: cliente, serviço, valor, prazo e status | Cadastrar pedido, avançar status, abrir detalhe |
| Novo pedido / Detalhe | Campos do pedido (cliente, serviço, quantidade, prazo, valor, material usado) | Salvar, avançar status, avisar cliente, excluir |
| Estoque | Lista de materiais com quantidade atual, nível mínimo e destaque de "Estoque baixo" | Cadastrar material, abrir material |
| Novo material / Editar | Nome, quantidade atual, nível mínimo e unidade | Salvar, ajustar quantidade, excluir |

**Rascunhos das telas:** colocar as imagens em `docs/telas/`.

- `docs/telas/01-pedidos.png`
- `docs/telas/02-novo-pedido.png`
- `docs/telas/03-estoque.png`
- `docs/telas/04-novo-material.png`

---

## 7. Dados

### Opção A (Room)

O banco tem **4 entidades**: `Cliente`, `Pedido`, `Material` e `MovimentacaoEstoque`.

**Relações:** um `Cliente` tem vários `Pedido`s (`Pedido.clienteId`); um `Material` pode ser usado em vários `Pedido`s (`Pedido.materialId`); e um `Material` tem várias `MovimentacaoEstoque` (`MovimentacaoEstoque.materialId`).

**Entidade 1 — `Cliente`**

| Campo | Tipo | Obrigatório | Observação |
|---|---|---|---|
| `id` | Long | sim | chave primária, autogerada |
| `nome` | String | sim | nome do cliente |
| `telefone` | String | não | usado para avisar pelo WhatsApp |

**Entidade 2 — `Pedido`**

| Campo | Tipo | Obrigatório | Observação |
|---|---|---|---|
| `id` | Long | sim | chave primária, autogerada |
| `clienteId` | Long | sim | chave estrangeira para `Cliente` |
| `servico` | String | sim | ex.: "Cópia colorida A4" |
| `quantidade` | Int | não | padrão 1 |
| `prazo` | Long | sim | data de entrega em milissegundos |
| `valor` | Long | não | em centavos, para evitar erro de arredondamento |
| `status` | String | sim | ORCAMENTO, EM_PRODUCAO, PRONTO ou ENTREGUE_PAGO |
| `materialId` | Long? | não | chave estrangeira para `Material` (o que será descontado) |
| `quantidadeMaterial` | Int | não | quanto do material o pedido usa |

**Entidade 3 — `Material`**

| Campo | Tipo | Obrigatório | Observação |
|---|---|---|---|
| `id` | Long | sim | chave primária, autogerada |
| `nome` | String | sim | ex.: "Papel A4 75g" |
| `quantidadeAtual` | Int | sim | saldo em estoque |
| `nivelMinimo` | Int | sim | igual ou abaixo disso, mostra alerta |
| `unidade` | String | sim | folhas, resmas, cartuchos |

**Entidade 4 — `MovimentacaoEstoque`** (histórico de entradas e baixas)

| Campo | Tipo | Obrigatório | Observação |
|---|---|---|---|
| `id` | Long | sim | chave primária, autogerada |
| `materialId` | Long | sim | chave estrangeira para `Material` |
| `pedidoId` | Long? | não | preenchido quando a baixa vem de um pedido |
| `tipo` | String | sim | ENTRADA (reposição), SAIDA (pedido) ou AJUSTE |
| `quantidade` | Int | sim | quantidade movimentada |
| `data` | Long | sim | momento do registro, em milissegundos |

**Operações necessárias:** (x) inserir (x) listar (x) atualizar (x) excluir — para `Cliente`, `Pedido` e `Material`; `MovimentacaoEstoque` só insere e lista (o histórico não se edita).

### Retrofit

Não se aplica. O app usa apenas Room, sem API e sem internet.

---

## 8. Arquitetura e tecnologias

| Item | Escolha |
|---|---|
| Linguagem | Kotlin |
| Interface | (x) Jetpack Compose ( ) XML/Views |
| Persistência | (x) Room |
| Rede | — (não usa) |
| Outras bibliotecas | Material 3, ViewModel + StateFlow, Navigation Compose, KSP (para o Room) |
| `minSdk` / `targetSdk` | 26 / 35 |

**Organização de pastas do projeto:**

```
app/src/main/java/br/edu/ifpe/aquarelapapelaria/
├── ui/        # telas (Pedidos, Estoque, formulários) e tema
├── data/      # Room: Pedido, Material, DAOs e AppDatabase
└── MainActivity.kt
```

---

## 9. Tratamento de erros

| Situação de falha | O que o app faz | Mensagem para o usuário |
|---|---|---|
| Lista de pedidos vazia | Mostra o estado vazio com a dica do botão "+" | "Nenhum pedido ainda. Toque em + para cadastrar." |
| Estoque vazio | Mostra o estado vazio com a dica do botão "+" | "Nenhum material cadastrado. Toque em + para começar." |
| Campo obrigatório em branco (cliente, serviço ou prazo) | Não salva, mantém o formulário e destaca o campo | "Preencha o cliente, o serviço e o prazo." |
| Valor ou quantidade inválidos (texto, negativo) | Não salva e mantém o que foi digitado | "Digite um número válido." |
| Estoque insuficiente ao iniciar a produção | Não muda o status do pedido nem altera o estoque | "Estoque insuficiente de [material]. Atualize o estoque e tente de novo." |
| Erro ao salvar ou ler no banco | Mantém a tela, não perde o que foi digitado | "Não foi possível salvar. Tente de novo." |
| WhatsApp não instalado (RF06) | Mantém a tela do pedido | "WhatsApp não encontrado neste celular." |

---

## 10. Identidade visual e publicação

| Item | Definição | Onde fica |
|---|---|---|
| Nome do app | Aquarela Papelaria | `strings.xml` |
| Cor principal | `#2A7FA8` (azul-aquarela) | `Color.kt` |
| Cor secundária | `#F2A65A` (laranja suave, usada nos alertas de estoque baixo e prazos próximos) | `Color.kt` |
| Ícone 512×512 | Pincel com uma mancha de aquarela azul sobre uma folha de papel, fundo claro | `loja/icone-512.png` |
| `applicationId` | `br.edu.ifpe.aquarelapapelaria` | `build.gradle.kts` |
| `versionName` / `versionCode` | `1.0` / `1` | `build.gradle.kts` |

**Material da loja:**

| Artefato | Limite | Conteúdo |
|---|---|---|
| Título | 30 caracteres | Aquarela Papelaria |
| Descrição curta | 80 caracteres | Pedidos e estoque da sua papelaria no celular, sem caderno nem planilhas. |
| Descrição completa | — | `loja/descricao.md` |
| Imagem de destaque | 1024×500 | `loja/destaque-1024x500.png` |
| Screenshots | mín. 2 | `loja/screenshots/` |
| Esboço de privacidade | — | `loja/privacidade.md` — o app não coleta dados pessoais para fora; tudo fica salvo no celular do dono e nada é enviado pela internet |
| Arquivo `.aab` | — | `loja/app-release.aab` |

---

## 11. Riscos

| Risco | Impacto | Plano B |
|---|---|---|
| O dono não ter tempo e parar de registrar os pedidos | Alto | Poucas telas e no máximo 3 campos obrigatórios; meta de registrar em menos de 30 segundos |
| A baixa automática de estoque ficar complexa demais para o prazo | Médio | Começar com baixa manual (botão "usei X") e automatizar só se sobrar tempo |
| Integrante fica sem computador | Médio | Todo o código no GitHub; outro integrante assume a parte e usa o computador da escola |
| Dono da papelaria indisponível para testar o `.apk` | Médio | Testar com 2 outras pessoas de fora do grupo, usando dados de exemplo |

---

## 12. Como vamos orientar a implementação com IA

A implementação usa o **Gemini no Android Studio**. Este PRD é o documento que diz à IA o que construir. Regras completas em [`docs/USO_DE_IA.md`](docs/USO_DE_IA.md).

**Recursos que vamos usar:** (x) Chat (x) Agent Mode (x) Explain Code (x) Ask Gemini no Logcat ( ) Generate Unit Tests ( ) Transform UI

**Regras que colocamos no `AGENTS.md`:**

- Fazer uma funcionalidade por vez, usando só Kotlin, Compose e Room, sem adicionar bibliotecas novas sem avisar o grupo.
- Explicar em linguagem simples o que cada arquivo faz e manter o código curto, com nomes em português fáceis de entender.
- Nunca pedir nem usar chaves, senhas ou dados reais de clientes; todo erro possível deve ter `try/catch` e mensagem clara ao usuário.

**Divisão do perímetro explicável:**

| Parte do código | Responsável |
|---|---|
| Telas (`ui/`) | _David_ |
| Dados (`data/`) | _Eduardo_ |
| Identidade visual e recursos | _Iraquitan_ |
| Build e artefatos de loja | _Arthur_ |

**Decisões que o grupo tomou contra a sugestão da IA** _(preencher ao longo do projeto)_:

-

---

## 13. Histórico de versões deste documento

| Versão | Data | Autor | O que mudou |
|---|---|---|---|
| 1.0 | 06/10/2026 | Eduardo, Arthur, Iraquitan e David | Versão inicial |

