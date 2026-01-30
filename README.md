# MyHome - Plataforma de Classificados Imobiliários

### 🎓 Informações Acadêmicas
* **Instituição:** Instituto Federal da Paraíba (IFPB) - Campus João Pessoa
* **Curso:** Tecnologia em Sistemas para Internet
* **Disciplina:** Padrões de Projeto de Software
* **Período:** 5º Período
* **Professor:** Alex Sandro da Cunha Rêgo
* **Estudante:** Felipe

---

## 🚀 1. Descrição da Solução
O **MyHome** é um ecossistema robusto para classificados de imóveis, desenvolvido em **Java Puro (Java SE)**, sem a dependência de frameworks externos. A solução foca em extensibilidade e desacoplamento, permitindo que novos tipos de imóveis, métodos de validação e canais de comunicação sejam adicionados sem a necessidade de modificar o núcleo do sistema (Princípio Aberto/Fechado).

A arquitetura foi desenhada para suportar fluxos complexos de moderação, buscas dinâmicas com lógica booleana e persistência de estado (Undo/Redo), garantindo que a plataforma seja escalável e de fácil manutenção.

---

## 🛠️ 2. Padrões de Projeto Utilizados

| Padrão | Pacote / Localização | Finalidade no Projeto |
| :--- | :--- | :--- |
| **Singleton** | `config/ConfigManager.java` | Garante uma única instância para o carregamento e acesso global ao arquivo `config.properties`. |
| **Builder** | `builder/Anuncio.java` | Facilita a criação guiada de anúncios complexos, garantindo que o objeto só seja instanciado se possuir os dados obrigatórios. |
| **Factory Method** | `integration/ImovelFactory.java` | Centraliza a criação de diferentes tipos de imóveis (Casa, Apto, Terreno, Comercial) utilizando um **Registry Map**, eliminando `if/else` excessivos. |
| **Template Method** | `integration/ImportadorArquivo.java` | Define a estrutura fixa do algoritmo de importação de arquivos, delegando o processamento específico de cada linha para as subclasses. |
| **State** | `state/` | Gerencia o ciclo de vida do anúncio (Rascunho, Moderando, Ativo, Suspenso, Vendido), isolando a lógica de transição em classes próprias. |
| **Chain of Responsibility** | `validation/` | Implementa uma esteira de validação automática. O anúncio percorre uma corrente de validadores (Preço, Termos Proibidos) antes de ser publicado. |
| **Observer** | `observerAndstrategy/` | O Anúncio (Subject) notifica automaticamente os canais de comunicação sobre mudanças de estado ou eventos relevantes. |
| **Strategy** | `observerAndstrategy/` | Permite alternar dinamicamente o meio de envio das notificações (E-mail, WhatsApp) através de uma interface comum. |
| **Specification** | `search/` | Provê um mecanismo de busca avançada onde filtros (Preço, Título) são tratados como objetos e podem ser combinados (AND). |
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
* **E2 (Encapsulamento de Mensagens):** Para cumprir a exigência de evitar `System.out.println` no domínio, foi criada a classe `util.ConsoleLogger`, centralizando toda a comunicação de saída do sistema.

---

## 💡 4. Originalidade e Diferenciais Técnicos
1.  **Registry-Based Factory:** O uso de um `Map` funcional no `ImovelFactory` torna o sistema "Plug & Play" para novos tipos de imóveis.
2.  **Clean Logging:** A separação total da lógica de negócio da lógica de exibição (E2) garante que o sistema possa ser portado para uma interface Web ou Desktop sem alterações no Core.
3.  **Fidelidade ao Domínio:** Atributos específicos como Zoneamento (Terrenos) e Salas/Recepção (Comercial) demonstram o entendimento real do problema imobiliário.

---

## 🏃 5. Como Executar o Projeto

### Pré-requisitos
* Java JDK 17 ou superior.
* Arquivos `config.properties` e `imoveis.csv` localizados na raiz do projeto.

### Instruções
1.  Abra o terminal na pasta raiz do projeto.
2.  Compile todas as classes:
    ```bash
    javac -d bin src/**/*.java Main.java
    ```
3.  Execute a aplicação:
    ```bash
    java -cp bin Main
    ```

---
