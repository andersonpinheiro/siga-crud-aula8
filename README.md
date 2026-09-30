# SIGA — Operações CRUD e Regras de Domínio

**Técnicas de Programação II (TP2) · Aula 8** — CST em Desenvolvimento de Software Multiplataforma · Fatec de Porto Ferreira

Este repositório contém a entrega da atividade prática da Aula 8. O sistema inicial possuía operações incompletas de banco de dados e graves falhas arquiteturais ("deslizes"). Todo o código foi corrigido, implementando um CRUD completo e seguro, com as regras de negócio devidamente protegidas em uma Camada de Serviço especializada.

## 💡 O que foi feito

1. **CRUD Completo**: A camada `AlunoDAOMemoria` agora cobre as 4 operações fundamentais (`inserir`, `buscarPorMatricula`, `listarTodos`, `atualizar` e `remover`), com checagens de existência de chave primária.
2. **Correção da Coleção Exposta (Deslize 1)**: O método de listar não devolve mais o ponteiro para o banco de dados interno. Agora ele aplica uma *cópia defensiva*, impedindo que camadas externas modifiquem o banco sem passar pelas regras de negócio.
3. **Fim da Exclusão Silenciosa (Deslize 2)**: O método `remover` agora verifica a existência do aluno e, em caso de ausência, dispara um `IllegalArgumentException`. A falha silenciosa é uma violação gravíssima de confiabilidade.
4. **Fim da Validação Divergente (Deslize 3)**: Antes, o `Main` e o Serviço definiam o limite de notas com valores diferentes. Agora a Camada de Apresentação é apenas "burra" no que tange às regras de negócio; o `ServicoAluno` tornou-se o guardião exclusivo da lógica (Centralização de Regras de Domínio).

## 📂 Estrutura do projeto refatorado

```text
siga-crud-aula8/
├── .gitignore                (Configurações de arquivos ignorados, ex: bin/, .DS_Store)
└── src/
    └── siga/
        ├── ServicoAluno.java              (Camada de Negócio com as regras do CRUD)
        ├── Aluno.java                     (Entidade de Domínio)
        ├── AlunoDAO.java                  (Interface de Persistência)
        ├── AlunoDAOMemoria.java           (Implementação de persistência corrigida)
        └── Main.java                      (Ponto de entrada tratando exceções de forma clara)
```

## 🚀 Como compilar e executar

Pré-requisito: JDK 17 ou superior (`java -version` para verificar).

```bash
# 1. Compilar (a saída vai para a pasta "bin", excluída do versionamento)
javac -d bin src/siga/*.java

# 2. Executar
java -cp bin siga.Main
```
