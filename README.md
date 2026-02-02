# MyHome - Plataforma de Classificados Imobiliários

### 🎓 Informações Acadêmicas
* **Instituição:** Instituto Federal da Paraíba (IFPB) - Campus João Pessoa
* **Curso:** Tecnologia em Sistemas para Internet
* **Disciplina:** Padrões de Projeto de Software
* **Período:** 5º Período
* **Professor:** Alex Sandro da Cunha Rêgo
* **Estudante:** Felipe de Brito, Joana Elise

---

## 🏗️ Arquitetura do Sistema
Abaixo, o diagrama de classes detalha a estrutura do MyHome, destacando a implementação dos padrões criacionais, estruturais e comportamentais para garantir a fidelidade ao domínio solicitada:
<p align="center">
  <img src="diagrama.png" alt="Diagrama de Classes MyHome" width="800">
  <br>
  <em>Figura 1: Arquitetura Técnica e Padrões de Projeto aplicados ao MyHome.</em>
</p>

## 📂 Estrutura do Projeto

```text
MyHome/
├── .git/
├── .github/
│   └── appmod/
│       └── appcat/
├── bin/
│   ├── builder/
│   ├── config/
│   ├── fachada/
│   ├── integration/
│   ├── memento/
│   ├── model/
│   ├── observerAndstrategy/
│   ├── search/
│   ├── state/
│   ├── util/
│   ├── validation/
│   └── Main.class
├── config.properties
├── diagrama.png
├── imoveis.csv
├── Main.java
├── README.md
└── src/
    ├── builder/
    │   └── Anuncio.java
    ├── config/
    │   └── ConfigManager.java
    ├── fachada/
    │   └── MyHomeFachada.java
    ├── integration/
    │   ├── CriadorImovel.java
    │   ├── ImovelFactory.java
    │   ├── ImportadorArquivo.java
    │   └── ImportadorCSVImovel.java
    ├── memento/
    │   ├── AnuncioMemento.java
    │   └── HistoricoAnuncio.java
    ├── model/
    │   ├── Apartamento.java
    │   ├── Casa.java
    │   ├── Imovel.java
    │   ├── ImovelComercial.java
    │   └── Terreno.java
    ├── observerAndstrategy/
    │   ├── CanalNotificacao.java
    │   ├── NotificadorEmail.java
    │   └── NotificadorWhatsApp.java
    ├── search/
    │   ├── FiltroAnd.java
    │   ├── FiltroAnuncio.java
    │   ├── FiltroPrecoMaximo.java
    │   └── FiltroTituloContem.java
    ├── state/
    │   ├── EstadoAnuncio.java
    │   ├── EstadoAtivo.java
    │   ├── EstadoModerando.java
    │   ├── EstadoRascunho.java
    │   ├── EstadoSuspenso.java
    │   └── EstadoVendido.java
    ├── util/
    │   └── ConsoleLogger.java
    └── validation/
        ├── ValidadorAnuncio.java
        ├── ValidadorPreco.java
        └── ValidadorTermosProibidos.java
```

## 🚀 1. Descrição da Solução
O **MyHome** é um ecossistema robusto para classificados de imóveis, desenvolvido em **Java Puro (Java SE)**, sem a dependência de frameworks externos. A solução foca em extensibilidade e desacoplamento, permitindo que novos tipos de imóveis, métodos de validação e canais de comunicação sejam adicionados sem a necessidade de modificar o núcleo do sistema (Princípio Aberto/Fechado).

A arquitetura foi desenhada para suportar fluxos complexos de moderação, buscas dinâmicas com lógica booleana e persistência de estado (Undo/Redo), garantindo que a plataforma seja escalável e de fácil manutenção.

---

## 🛠️ 2. Padrões de Projeto Utilizados

| Padrão | Pacote / Localização | Finalidade no Projeto |
| :--- | :--- | :--- |
| **Singleton** | `config/ConfigManager.java` | Garante uma única instância para o carregamento e acesso global ao arquivo `config.properties`. |
| **Builder** | `builder/Anuncio.java` | Facilita a criação guiada de anúncios complexos, garantindo que o objeto só seja instanciado se possuir os dados obrigatórios. |
| **Prototype** | `model/Imovel.java` | Permite a clonagem eficiente de modelos de imóveis carregados da base CSV para a criação de novos anúncios. |
| **Factory Method** | `integration/ImovelFactory.java` | Centraliza a criação de diferentes tipos de imóveis (Casa, Apto, Terreno, Comercial) utilizando um **Registry Map**, eliminando `if/else` excessivos. |
| **Facade** | `fachada/MyHomeFachada.java` | Oferece uma interface simplificada que orquestra os subsistemas complexos (Builder, State, Observer) para a classe `Main`. |
| **Template Method** | `integration/ImportadorArquivo.java` | Define a estrutura fixa do algoritmo de importação de arquivos, delegando o processamento específico de cada linha para as subclasses. |
| **State** | `state/` | Gerencia o ciclo de vida do anúncio (Rascunho, Moderando, Ativo, Suspenso, Vendido), isolando a lógica de transição em classes próprias. |
| **Chain of Responsibility** | `validation/` | Implementa uma esteira de validação automática. O anúncio percorre uma corrente de validadores (Preço, Termos Proibidos) antes de ser publicado. |
| **Observer** | `observerAndstrategy/` | O Anúncio (Subject) notifica automaticamente os canais de comunicação sobre mudanças de estado ou eventos relevantes. |
| **Strategy** | `observerAndstrategy/` | Permite alternar dinamicamente o meio de envio das notificações (Telegram, E-mail, WhatsApp) através de uma interface comum. |
| **Composite** | `search/` | Provê um mecanismo de busca avançada onde filtros (Preço, Título) são tratados como objetos e podem ser combinados (AND). |
| **Memento** | `memento/` | Captura e restaura o estado interno do anúncio, habilitando a funcionalidade de "Desfazer" (Undo) durante a edição. |
---

## 📋 3. Especificação dos Requisitos Resolvidos

### Requisitos Funcionais (RF)
* **RF01 (Criação Guiada):** Uso do **Builder** para garantir que anúncios tenham título, preço e imóvel válidos.
* **RF02 (Modelos Padrão):** Implementado via interface `Cloneable` no pacote `model`, permitindo a clonagem de protótipos de imóveis.
* **RF03 (Moderação Automática):** Implementado via **Chain of Responsibility**, consultando termos proibidos definidos externamente.
* **RF04 (Estados do Anúncio):** Máquina de estados completa que gerencia desde o rascunho até a venda ou suspensão do anúncio.
* **RF05 (Notificações):** Estrutura de **Observer + Strategy** para múltiplos canais de comunicação.
* **RF06 (Busca Avançada):** Padrão **Specification** para compor filtros dinâmicos sem poluir as classes de serviço.
* **RF07 (Configuração Centralizada):** O `ConfigManager` carrega o arquivo `config.properties` da raiz do projeto.
* **RF08 (Histórico/Undo):** Implementação de **Memento** para salvar e restaurar o estado do anúncio.

### Requisitos Estruturais (E)
* **E1 (Carga de Dados):** Importação extensível de arquivos CSV através de **Template Method**.
* **E2 (Domínio Silencioso e Desacoplado):** Para cumprir a exigência de evitar `System.out.println` nas classes de lógica, o sistema utiliza o retorno de listas de mensagens (`List<String>`). Toda a comunicação de estado e eventos é gerada no domínio, orquestrada pela `MyHomeFachada` e exibida exclusivamente pela classe `Main`, garantindo que o núcleo do sistema seja independente de interface.

---

## 💡 4. Originalidade e Diferenciais Técnicos

1. **Registry-Based Factory (Diferencial de Código):** Ao contrário de implementações tradicionais com longas estruturas de if/else, o ImovelFactory utiliza um mapeamento funcional (Map). Isso torna o sistema "Plug & Play", permitindo adicionar novos tipos de imóveis (como 'Galpão' ou 'Chácara') com apenas uma linha de configuração, sem alterar a lógica de importação.


2. **Mecanismo de Tolerância a Falhas (RF08 - Memento):** Implementamos um sistema de Undo/Redo (Desfazer/Refazer) para a edição de anúncios no estado de Rascunho. Isso permite que o anunciante recupere versões anteriores de um anúncio após edições acidentais, garantindo a integridade dos dados antes da submissão para moderação.

---

## 🏃 5. Como Executar o Projeto

### Pré-requisitos
* Java JDK 17 ou superior.
* Arquivos `config.properties` e `imoveis.csv` localizados na raiz do projeto.

### Instruções
1.  Abra o terminal na pasta raiz do projeto.
2.  Compile todas as classes:
    ```bash
    javac -d bin (Get-ChildItem -Recurse *.java).FullName
    ```
3.  Execute a aplicação:
    ```bash
    java -cp bin Main
    ```

---
