# RELATÓRIO DE PROMPTS DE ENTRADA E DE SAÍDA

**Projeto:** Aquarela Papelaria  
**Entidade:** Pedido  
**Atividade:** Parcela 1 — Os Quatro Estados da Tela  
**Tecnologias:** Kotlin, Jetpack Compose, Room, Gradle e Android Studio

---

## Interação 01 — Análise inicial da entidade Pedido

**Prompt de Entrada:**

> eu sou responsavel pela entidade Pedido do projeto Aquarela Papelaria e queria q vc analisace meu codigo e explicase cada linha pq ainda tenho dificuldade pra entender algumas partes do kotlin

**Prompt de Saída:**

O arquivo `Pedido.kt` define a estrutura dos dados armazenados na tabela de pedidos. A anotação `@Entity(tableName = "pedidos")` informa ao Room que a classe representa essa tabela. `data class Pedido` reúne as propriedades que formam cada registro.

`@PrimaryKey(autoGenerate = true)` identifica `id` como chave primária gerada pelo banco. `clienteId` guarda o identificador do cliente associado; `servico` descreve o trabalho solicitado; `quantidade` indica o número de unidades, com valor padrão de 1; `prazo` registra a data de entrega em milissegundos; `valor` guarda o preço em centavos; e `status` registra a etapa do pedido. `materialId` e `quantidadeMaterial` representam informações relacionadas ao consumo de material.

A função da entidade é **representar dados**. Regras de validação, consultas e apresentação visual pertencem a outras partes do aplicativo.

---

## Interação 02 — Entendendo a chave estrangeira

**Prompt de Entrada:**

> nessa parte do ForeignKey eu n entendi pq tem clienteId e nem pra q serve esse RESTRICT, vc pode explicar essas linha e o que acontece se eu apaga um cliente q tem pedido

**Prompt de Saída:**

A anotação `ForeignKey` cria um relacionamento entre a tabela de pedidos e a tabela de clientes. Em `entity = Cliente::class`, o Room identifica a entidade referenciada. `parentColumns = ["id"]` seleciona a coluna da tabela Cliente, e `childColumns = ["clienteId"]` seleciona a coluna correspondente em Pedido.

`onDelete = ForeignKey.RESTRICT` bloqueia a exclusão de um cliente que ainda possua pedidos vinculados. Essa regra ajuda a impedir registros de pedidos apontando para clientes inexistentes. O campo `clienteId` não guarda o objeto Cliente inteiro: ele armazena apenas seu identificador.

---

## Interação 03 — Compreendendo o PedidoDao

**Prompt de Entrada:**

> queria entender melhor o PedidoDao pq ele tem varios @Query e eu n sei como essas consulta funciona, vc pode explica principalmente o SELECT e o ORDER BY prazo ASC

**Prompt de Saída:**

`PedidoDao.kt` concentra as operações de acesso à tabela de pedidos. A anotação `@Dao` identifica uma interface de acesso ao banco; `@Insert` é usada para inserir registros e `@Update`, para atualizá-los. `@Query` permite escrever uma consulta SQL específica.

```kotlin
@Query("SELECT * FROM pedidos ORDER BY prazo ASC")
fun listarPorPrazo(): Flow<List<Pedido>>
```

`SELECT *` seleciona todas as colunas, `FROM pedidos` indica a tabela, `ORDER BY prazo` ordena os resultados pela data de entrega e `ASC` estabelece a ordem crescente. A função retorna `Flow<List<Pedido>>`, permitindo observar novas listas quando os dados do Room forem alterados.

---

## Interação 04 — Compreendendo a integração com o banco

**Prompt de Entrada:**

> eu ja fiz a integração do Pedido com o banco mais queria entender pra q serve o AppDatabase e pq o ViewModel precisa dele pra cadastra os pedidos, explica essa parte do codigo pra mim

**Prompt de Saída:**

`AppDatabase.kt` configura o banco Room, registra suas entidades e disponibiliza os DAOs. No ViewModel, a instrução abaixo obtém uma instância do banco:

```kotlin
private val banco = AppDatabase.obterBanco(application)
```

`private` limita o acesso à propriedade à própria classe. `AppDatabase.obterBanco(application)` fornece o acesso ao banco configurado para o aplicativo. A chamada `banco.pedidoDao()` obtém a interface de operações sobre pedidos. Dessa forma, a tela Compose não precisa executar consultas diretamente: ela conversa com o ViewModel, que consulta o banco.

---

## Interação 05 — Preparação da branch da Parcela 1

**Prompt de Entrada:**

> terminei a integração dos pedidos e agr preciso começa a parcela 1, n sei se tenho q cria outra branch ou continua na mesma, queria q vc explicase os comando do git

**Prompt de Saída:**

A implementação dos quatro estados pode ser separada da integração anterior na branch `feature/estados-da-tela`. Antes de criar a branch, é importante executar `git status` para conferir arquivos modificados, preparados para commit e não rastreados.

```bash
git status
git switch -c feature/estados-da-tela
git branch --show-current
```

`git switch -c` cria e seleciona uma branch nova. `git branch --show-current` confirma seu nome. Arquivos não rastreados, especialmente uma eventual cópia duplicada do projeto, não devem ser incluídos indiscriminadamente com `git add .`. O objetivo é separar os commits da atividade atual dos commits da integração anterior.

---

## Interação 06 — Criando o UiState.kt

**Prompt de Entrada:**

> eu ja criei a branch e agr preciso fazer o UiState.kt, mais antes queria entender pra q serve essa sealed interface e pq o professor pede esses quatro estados

**Prompt de Saída:**

Uma `sealed interface` define um conjunto fechado de tipos relacionados dentro das regras de herança do Kotlin. Na atividade, `UiState` representa o resultado da consulta de pedidos.

`Carregando` significa que a leitura está em andamento; `Vazio` representa uma consulta concluída sem pedidos; `Erro` indica falha; e `Sucesso` indica que os dados foram obtidos. Com essa estrutura, a tela consegue apresentar um indicador, uma mensagem de lista vazia, um aviso de erro ou os dados da lista, conforme o estado observado.

O estado da interface não deve ser confundido com o **status comercial** de um pedido, como orçamento, produção ou entregue: são conceitos diferentes.

---

## Interação 07 — Diferença entre data object e data class

**Prompt de Entrada:**

> no UiState tem data object no Carregando e no Vazio mais no Erro e Sucesso tem data class, n entendi pq é diferente e se isso muda alguma coisa no app

**Prompt de Saída:**

`data object` é adequado quando o estado não precisa transportar informações adicionais. Um único objeto `Carregando` ou `Vazio` já é suficiente para representar a situação.

`data class` é útil quando o estado precisa guardar propriedades. Em `Erro`, é possível guardar a referência a uma mensagem do `strings.xml`. Em `Sucesso`, a lista de pedidos é transportada no atributo `itens`:

```kotlin
data class Sucesso(val itens: List<Pedido>) : UiState
```

A diferença não muda a finalidade dos quatro estados, mas permite que cada um carregue apenas os dados necessários.

---

## Interação 08 — Corrigindo um erro no UiState.kt

**Prompt de Entrada:**

> eu colei o codigo do UiState mais apareceu um monte de erro vermelho no android studio, vc consegue ver oq eu fiz de errado pq n entendi o problema

**Prompt de Saída:**

Um arquivo Kotlin deve ter apenas uma declaração `package`. Quando a mesma linha é colada duas vezes, o compilador pode apontar vários erros em sequência.

```kotlin
package br.edu.ifpe.aquarelapapelaria.ui

import androidx.annotation.StringRes
import br.edu.ifpe.aquarelapapelaria.model.Pedido
```

A declaração `package` indica a localização lógica do arquivo. Os `import`s tornam acessíveis tipos de outros pacotes: `StringRes` identifica referências a recursos de texto e `Pedido` é necessário para `List<Pedido>`. Depois de remover a declaração repetida, o arquivo deve ser salvo e conferido novamente.

---

## Interação 09 — Entendendo o strings.xml

**Prompt de Entrada:**

> pq o professor fala q n pode deixa os texto direto no codigo e tem q colocar tudo no strings.xml, sendo q quando eu escrevo no Text funciona normal

**Prompt de Saída:**

O Compose permite escrever textos diretamente em `Text()`, mas centralizá-los no `strings.xml` facilita manutenção e tradução e atende ao requisito da atividade.

```xml
<string name="pedidos_vazios">Nenhum pedido cadastrado.</string>
```

O texto é exibido na tela por meio de um recurso:

```kotlin
Text(text = stringResource(R.string.pedidos_vazios))
```

O nome `pedidos_vazios` funciona como uma chave que o Android utiliza para recuperar a mensagem. É importante não criar recursos com nomes duplicados dentro dos arquivos de valores.

---

## Interação 10 — Adaptando o PedidosViewModel

**Prompt de Entrada:**

> eu ja tenho um PedidosViewModel q cadastra os pedidos e n quero perde esse codigo, queria q vc me explicase como adicionar os quatro estados sem apagar oq ja fiz na integração

**Prompt de Saída:**

O ViewModel existente pode continuar responsável pelo cadastro e receber o novo controle de estados da leitura. Não é necessário apagar `cadastrar()` nem criar uma segunda classe para essa função.

```kotlin
private val _uiState = MutableStateFlow<UiState>(UiState.Carregando)
```

A expressão cria um fluxo mutável cujo valor inicial é `Carregando`. Depois da consulta ao Room, o ViewModel atualiza o valor para `Vazio` ou `Sucesso`; em caso de falha tratada, atualiza para `Erro`.

A integração existente permanece aproveitada, enquanto o novo fluxo passa a informar explicitamente a situação da listagem.

---

## Interação 11 — Entendendo MutableStateFlow e StateFlow

**Prompt de Entrada:**

> n entendi pq tem um _uiState e dps outro uiState sem o underline, tbm queria saber pq um usa MutableStateFlow e o outro StateFlow

**Prompt de Saída:**

`MutableStateFlow` permite ler **e modificar** o estado; `StateFlow` expõe uma visão somente de leitura. Uma prática comum é manter a versão mutável privada e disponibilizar a versão pública para a tela:

```kotlin
private val _uiState = MutableStateFlow<UiState>(UiState.Carregando)
val uiState: StateFlow<UiState> = _uiState.asStateFlow()
```

O sublinhado inicial em `_uiState` é uma convenção de nome para diferenciar a propriedade interna da pública. O método `asStateFlow()` impede que a tela altere diretamente o estado. Cabe ao ViewModel decidir qual valor emitir.

---

## Interação 12 — Entendendo init e carregar()

**Prompt de Entrada:**

> no ViewModel tem init chamando carregar e dps aparece fun carregar, eu n entendi pq ele executa sozinho quando abre o aplicativo e pra q serve cada parte

**Prompt de Saída:**

`init` é um bloco executado quando o objeto `PedidosViewModel` é criado:

```kotlin
init {
    carregar()
}
```

A função `carregar()` inicia a consulta ao banco e modifica `uiState` conforme o resultado. Ao chamá-la no início, o ViewModel procura os pedidos sem exigir uma ação inicial do usuário. A função continua disponível para outras chamadas, como a do botão **Tentar novamente**. O bloco `init` não significa que a leitura será repetida para cada recomposição do Compose: ele executa na criação daquela instância do ViewModel.

---

## Interação 13 — Entendendo viewModelScope, collect e isEmpty

**Prompt de Entrada:**

> queria q vc me explicase a parte do viewModelScope.launch e collect pq n entendi como ele pega a lista do banco e sabe quando tem q mostrar Vazio ou Sucesso

**Prompt de Saída:**

`viewModelScope.launch` cria uma coroutine associada ao ciclo de vida do ViewModel. Isso permite iniciar uma operação assíncrona sem bloquear a interface principal.

`collect` recebe cada lista emitida pelo `Flow` retornado pelo DAO. Dentro da coleta, `lista.isEmpty()` verifica se o conjunto recebido está vazio:

```kotlin
.collect { lista ->
    _uiState.value = if (lista.isEmpty()) {
        UiState.Vazio
    } else {
        UiState.Sucesso(lista)
    }
}
```

Quando não há pedidos, emite-se Vazio. Quando existem registros, emite-se Sucesso junto com os itens. Novas emissões do Flow podem atualizar automaticamente a listagem.

---

## Interação 14 — Entendendo try/catch

**Prompt de Entrada:**

> pq precisa desse try catch dentro do carregar e se tiver algum erro no banco o app vai fecha sozinho ou ele vai mostra uma mensagem, queria entender isso melhor

**Prompt de Saída:**

`try/catch` permite tratar exceções lançadas durante a consulta. O bloco `try` envolve o trabalho com o DAO e o `catch` altera a interface para o estado Erro, usando um texto definido em `strings.xml`:

```kotlin
_uiState.value = UiState.Erro(R.string.erro_carregar_pedidos)
```

`CancellationException` deve ser tratada separadamente e relançada, para não transformar um cancelamento normal de coroutine em mensagem de falha. O tratamento não garante que todo erro possível do aplicativo seja capturado, mas torna a consulta mais robusta diante das exceções desse fluxo.

---

## Interação 15 — Adaptando a tela PedidosScreen.kt

**Prompt de Entrada:**

> eu ja tenho um PedidosScreen.kt com os campos pra cadastra pedido, vc acha melhor fazer outra tela ou adaptar essa msm pq n queria perde o formulario q ja fiz

**Prompt de Saída:**

A melhor opção é adaptar o arquivo existente, preservando nome do cliente, serviço, quantidade, prazo, botão de cadastro e mensagem de validação. Criar uma segunda tela duplicaria trabalho e poderia separar a listagem do formulário já integrado.

A alteração central é observar `uiState` no lugar de utilizar apenas a lista diretamente:

```kotlin
val estado by viewModel.uiState.collectAsStateWithLifecycle()
```

`collectAsStateWithLifecycle()` transforma o fluxo em um estado observado pelo Compose, respeitando o ciclo de vida da interface. A parte da listagem pode utilizar um `when` com os quatro estados, sem `else`.

---

## Interação 16 — Entendendo rememberSaveable

**Prompt de Entrada:**

> no formulario tem rememberSaveable e mutableStateOf em todos os campos e eu n sei pq precisa disso, vc pode me explicar como ele guarda oq eu digito

**Prompt de Saída:**

`mutableStateOf` cria uma propriedade observável pelo Compose. `rememberSaveable` permite preservar valores compatíveis em determinadas recriações da interface, como uma mudança de orientação do celular.

```kotlin
var servico by rememberSaveable { mutableStateOf("") }
```

A variável `servico` começa vazia. O `OutlinedTextField` recebe esse valor e utiliza `onValueChange = { servico = it }` para atualizá-lo conforme a digitação. O mesmo padrão é aplicado ao nome, à quantidade e ao prazo. Esses valores ainda não são pedidos gravados: são informações temporárias do formulário.

---

## Interação 17 — Entendendo o when sem else

**Prompt de Entrada:**

> eu queria entender pq o professor n deixa colocar else no final do when e como ele sabe qual dos quatro estados tem q mostra, vc pode explicar essa parte linha por linha

**Prompt de Saída:**

O `when` escolhe qual trecho da interface deve ser exibido conforme o valor observado de `UiState`. Uma opção mostra `CircularProgressIndicator()` para Carregando; outra mostra a mensagem de lista vazia; Erro apresenta a mensagem e o botão de nova tentativa; Sucesso apresenta os pedidos.

A `sealed interface` permite ao compilador conhecer os tipos possíveis. Ao tratar todos eles explicitamente, o `when` torna-se exaustivo sem `else`. Isso é útil porque, caso um novo estado seja adicionado depois, o compilador poderá apontar que a tela precisa lidar com ele.

A exigência da aula é especificamente não usar `else` no `when` dos estados; um `else` de uma expressão `if` comum não é a mesma coisa.

---

## Interação 18 — Entendendo o botão Tentar novamente

**Prompt de Entrada:**

> pq no botão Tentar novamente tem onClick chamando viewModel.carregar e como isso faz os pedidos aparece dnv depois q da erro

**Prompt de Saída:**

`onClick` define o comportamento executado ao tocar em um botão do Compose:

```kotlin
Button(onClick = { viewModel.carregar() }) {
    Text(stringResource(R.string.tentar_novamente))
}
```

Ao tocar, o usuário aciona `carregar()`, que emite Carregando e tenta fazer uma nova leitura. Se o DAO retornar pedidos, o estado muda para Sucesso; se retornar lista vazia, muda para Vazio; se lançar uma exceção tratada, muda para Erro novamente. Portanto, não é necessário fechar o aplicativo para repetir a consulta.

---

## Interação 19 — Problema na compilação do Gradle

**Prompt de Entrada:**

> tentei compilar o projeto mais deu build failed e apareceu um erro de metadata.bin no gradle, queria saber se foi o codigo q coloquei ou se é problema do android studio

**Prompt de Saída:**

Um erro de leitura de `metadata.bin` em `.gradle/caches` pode indicar um arquivo de cache ausente, inválido ou inacessível. Isso não comprova um erro na lógica Kotlin, pois a falha pode ocorrer antes da compilação das classes.

Uma abordagem é sincronizar novamente o projeto. Se o erro persistir, pode-se fechar o Android Studio, parar os processos Gradle e renomear apenas a pasta de cache problemática para permitir a reconstrução. É importante não apagar os arquivos-fonte nem usar comandos destrutivos no repositório.

O resultado `BUILD SUCCESSFUL`, após executar a tarefa de compilação, indica que aquela compilação terminou sem erros.

---

## Interação 20 — Executando o aplicativo no celular físico

**Prompt de Entrada:**

> o emulador Pixel 6 fica fechando sozinho e n consigo abrir o app nele, queria usar meu celular de verdade pra fazer os testes vc pode me explicar como conectar ele no android studio

**Prompt de Saída:**

Um celular Android físico pode executar o projeto por USB. Primeiro, ativa-se o menu de Opções do desenvolvedor e a Depuração USB nas Configurações do aparelho. Em seguida, conecta-se o celular ao computador com cabo de dados e autoriza-se a depuração quando a mensagem aparecer.

No Android Studio, seleciona-se o dispositivo físico na barra superior e executa-se a configuração `app` com **Run**. O Android Studio instala o aplicativo no aparelho. Essa alternativa evita depender do emulador que estava encerrando inesperadamente.

---

## Interação 21 — Testando o estado Sucesso

**Prompt de Entrada:**

> consegui abrir o app no celular e cadastrei um pedido de impressão A4 com quantidade 2 e prazo 3 dias, ele apareceu na lista isso quer dizer q o estado Sucesso funcionou msm

**Prompt de Saída:**

Sim. O cadastro válido passou pelas verificações do formulário e do ViewModel, foi gravado no Room e apareceu na listagem. O Flow do DAO forneceu a lista atualizada; como ela continha registros, o ViewModel emitiu `UiState.Sucesso(lista)`.

A tela apresentou o identificador do pedido, o serviço, a quantidade, o prazo e o status. Esse comportamento comprova o fluxo básico entre formulário, ViewModel, banco e interface. É uma verificação complementar importante aos cinco testes de sabotagem.

---

## Interação 22 — Testando campo obrigatório vazio

**Prompt de Entrada:**

> eu deixei o nome do cliente vazio e tentei cadastra o pedido, apareceu a msg de preencher os campos corretamente e n cadastrou, isso significa q a validação ta funcionando

**Prompt de Saída:**

Sim. A validação usa condições como `nomeCliente.isBlank()` para identificar campos obrigatórios vazios. Ao encontrar dados inválidos, a função informa a mensagem e interrompe o cadastro antes de acessar o banco.

No teste realizado, a mensagem **Preencha os campos corretamente.** foi exibida e nenhum pedido inválido foi cadastrado. Esse resultado pode ser documentado como teste aprovado de campo obrigatório vazio. A evidência visual corresponde ao campo **Nome do cliente** vazio.

---

## Interação 23 — Testando o modo avião

**Prompt de Entrada:**

> ativei o modo avião e o app continuou mostrando os pedidos normal, isso ta certo msm pq eu achei q deveria aparecer erro quando fica sem internet

**Prompt de Saída:**

O comportamento está correto para uma consulta a dados armazenados localmente no Room. O modo avião interfere nas conexões de rede, mas não impede a leitura do banco local do aplicativo.

Como a tela continuou mostrando os pedidos sem internet, o teste comprova que a listagem já armazenada funciona offline. Não se deve registrar artificialmente um erro de conexão onde ele não ocorreu.

---

## Interação 24 — Testando o estado Carregando

**Prompt de Entrada:**

> coloquei delay 2000 no carregar e quando abri o app apareceu aquela bolinha girando por dois segundo e dps os pedidos, pq precisou colocar esse delay pra testar

**Prompt de Saída:**

A consulta ao banco local costuma ser rápida demais para observar o estado Carregando. O `delay(2000)` adicionou temporariamente cerca de dois segundos de espera à coroutine, tornando o indicador visível.

Durante esse intervalo, `_uiState` permanecia em `UiState.Carregando`, e a tela mostrou o progresso e o texto correspondente. Depois, a leitura forneceu os pedidos e o estado passou a Sucesso. Após o teste, o atraso artificial e seu import, quando não utilizado, devem ser removidos — como foi feito.

---

## Interação 25 — Testando o estado Erro

**Prompt de Entrada:**

> eu coloquei aquela falha simulada no carregar e apareceu a msg de erro com o botão Tentar novamente, queria entender pq o try catch conseguiu mostrar o erro em vez do app fecha

**Prompt de Saída:**

Foi inserida temporariamente uma exceção controlada no início da leitura do ViewModel. Como a exceção foi lançada dentro de `try`, o `catch` a tratou e definiu o estado como `UiState.Erro`.

O Compose recebeu esse estado e exibiu **Não foi possível carregar os pedidos.** e o botão **Tentar novamente**. Esse teste comprova que uma falha tratada na consulta pode ser comunicada ao usuário sem interromper o fluxo da tela.

---

## Interação 26 — Testando a recuperação após o erro

**Prompt de Entrada:**

> cliquei em Tentar novamente e os pedidos apareceu dnv sem precisar reiniciar o app, pq ele conseguiu recupera e como eu sei q esse botão ta funcionando certo

**Prompt de Saída:**

O botão chamou `viewModel.carregar()` novamente. A falha temporária havia sido configurada para ocorrer apenas na primeira tentativa. Por isso, a segunda chamada seguiu até a consulta ao DAO e recebeu os pedidos.

A tela deixou o estado Erro, passou pela nova consulta e voltou a mostrar a lista em Sucesso. Isso demonstra que a ação do botão está conectada à função `carregar()`. Depois da comprovação, a variável de falha única e o código que lançava a exceção foram removidos.

---

## Interação 27 — Testando o estado Vazio

**Prompt de Entrada:**

> eu limpei os dados do aplicativo no celular e quando abri apareceu Nenhum pedido cadastrado, queria entender como ele sabe q o banco ta vazio e muda o estado sozinho

**Prompt de Saída:**

Limpar os dados locais do aplicativo removeu os registros daquele ambiente de teste. Ao abrir novamente, o ViewModel executou `carregar()`, o DAO forneceu uma lista vazia e `lista.isEmpty()` retornou verdadeiro.

Então `_uiState` foi atualizado para `UiState.Vazio`, fazendo o Compose apresentar **Nenhum pedido cadastrado.**. O comportamento confirma que a aplicação distingue uma lista vazia de uma falha na consulta.

---

## Interação 28 — Criando o relatório de sabotagem

**Prompt de Entrada:**

> agora q terminei os cinco teste preciso fazer o sabotagem.md mais n sei direito oq escrever nele, vc pode me explicar como organizar os resultado e o q colocar em cada parte

**Prompt de Saída:**

O arquivo `docs/sabotagem.md` deve informar o objetivo, o procedimento e o resultado observado de cada teste. Os cinco testes executados foram: lista vazia, modo avião, carregamento artificial, campo obrigatório vazio e falha simulada com recuperação.

Como complemento, também foi comprovado o estado Sucesso com um pedido válido. Para cada caso, é útil informar se o aplicativo permaneceu aberto, qual mensagem apareceu e se alterações temporárias foram retiradas depois. O relatório deve descrever o que foi efetivamente observado, sem declarar testes não realizados.

---

## Interação 29 — Adicionando prints ao sabotagem.md

**Prompt de Entrada:**

> eu tirei print de todos os teste no celular e queria colocar no sabotagem.md pra provar q funcionou, vc pode me ensinar como colocar as imagem e fazer elas aparecer no github

**Prompt de Saída:**

As seis imagens podem ser colocadas na pasta `docs/evidencias`, ao lado do relatório:

```text
docs/
├── sabotagem.md
└── evidencias/
    ├── 01-sucesso.jpg
    ├── 02-validacao.jpg
    ├── 03-modo-aviao.jpg
    ├── 04-carregando.jpg
    ├── 05-erro.jpg
    └── 06-vazio.jpg
```

A sintaxe `![Estado Sucesso](evidencias/01-sucesso.jpg)` insere uma imagem relativa à localização do arquivo Markdown. O texto entre colchetes é uma descrição alternativa. Para renderizar no GitHub, o repositório precisa conter tanto o `.md` quanto as imagens nos caminhos indicados.

---

## Interação 30 — Problema no Preview do Markdown

**Prompt de Entrada:**

> eu coloquei todas as seis imagem na pasta evidencias mais quando abro o sabotagem.md no preview elas n aparecem, só aparece o texto do relatorio, oq pode ta errado

**Prompt de Saída:**

A primeira verificação é conferir se `docs/evidencias` contém os arquivos e se os nomes usados no Markdown correspondem exatamente aos arquivos, incluindo a extensão `.jpg`.

Se as referências relativas estiverem corretas e o Preview do Android Studio continuar em branco, o problema pode estar no renderizador ou nas configurações do editor, e não necessariamente no Markdown. Uma conferência adicional pode ser feita ao abrir `docs/sabotagem.md` na branch publicada no GitHub. Não é necessário reescrever todo o relatório apenas porque as imagens não foram exibidas no editor local.

---

## Interação 31 — Conferindo os commits e a entrega

**Prompt de Entrada:**

> agora q ja fiz os quatro estado e os teste queria saber oq ainda falta pra entregar a parcela 1, vc pode me explicar os comando do git e como fazer os commit separado sem mexer na integração antiga

**Prompt de Saída:**

Antes de enviar, é necessário confirmar `UiState.kt` com quatro estados, ViewModel com `MutableStateFlow` e tratamento de exceções, `PedidosScreen.kt` com `when` exaustivo sem `else`, recursos de texto no `strings.xml`, botão Tentar novamente e documentação dos testes.

`git status --short` mostra o que mudou. `git add -- caminho/do/arquivo` prepara um arquivo específico, evitando incluir a pasta duplicada do projeto. `git diff --cached --name-only` permite conferir o conteúdo preparado antes de usar `git commit -m "mensagem"`.

Os commits devem separar as mudanças por responsabilidade (estados, ViewModel, interface e documentação). Por fim, `git push -u origin feature/estados-da-tela` publica a branch para abrir um Pull Request na base combinada com o grupo. O PR deve ser revisado na aba **Files changed** antes de ser criado.

---

## Conclusão

As interações acompanharam, em sequência, o entendimento da entidade Pedido, a integração com o Room, a preparação da branch, a implementação dos quatro estados da interface, a correção de problemas de compilação, os testes em celular físico e a organização das evidências para a entrega.

Os testes confirmados foram: Sucesso com pedido cadastrado, validação de campo obrigatório, funcionamento offline, Carregando, Erro com Tentar novamente e Vazio. As simulações temporárias foram retiradas após os testes.
