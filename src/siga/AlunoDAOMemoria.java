package siga;

import java.util.ArrayList;
import java.util.List;

/**
 * Código INICIAL da atividade — CRUD INCOMPLETO e com deslizes PROPOSITAIS.
 *
 * DESLIZE 1 — coleção interna exposta (etapa 3):
 * listarTodos() devolve a PRÓPRIA lista interna. Quem chamar o método pode
 * adicionar ou remover alunos diretamente, por fora do DAO, quebrando o
 * encapsulamento estudado na Aula 1. A correção é a cópia defensiva:
 * devolver uma nova lista com os mesmos elementos.
 *
 * DESLIZE 2 — exclusão silenciosa (etapa 3):
 * remover() não verifica se o aluno existia. Quando a matrícula não é
 * encontrada, nada acontece — mas o sistema informa sucesso ao usuário.
 * Falhar em silêncio é pior que falhar com erro.
 *
 * PENDENTE (etapa 1): o método atualizar() ainda não foi implementado, e
 * inserir() não impede matrícula duplicada.
 *
 * Tarefa:
 *   - Etapa 1: completar as quatro operações do CRUD;
 *   - Etapa 3: corrigir a coleção exposta e a exclusão silenciosa.
 */
public class AlunoDAOMemoria implements AlunoDAO {

    private final List<Aluno> armazem = new ArrayList<>();

    @Override
    public void inserir(Aluno aluno) {
        if (buscarPorMatricula(aluno.getMatricula()) != null) {
            throw new IllegalStateException("Matrícula já cadastrada no sistema.");
        }
        armazem.add(aluno);
    }

    @Override
    public Aluno buscarPorMatricula(String matricula) {
        for (Aluno aluno : armazem) {
            if (aluno.getMatricula().equals(matricula)) {
                return aluno;
            }
        }
        return null;   // não encontrado
    }

    @Override
    public List<Aluno> listarTodos() {
        // CORREÇÃO (Deslize 1): cópia defensiva da lista para evitar mutação externa.
        return new ArrayList<>(armazem);
    }

    @Override
    public void atualizar(Aluno aluno) {
        Aluno encontrado = buscarPorMatricula(aluno.getMatricula());
        if (encontrado == null) {
            throw new IllegalArgumentException("Aluno não encontrado para atualização.");
        }
        int index = armazem.indexOf(encontrado);
        armazem.set(index, aluno);
    }

    @Override
    public void remover(String matricula) {
        // CORREÇÃO (Deslize 2): verificar a existência antes de tentar excluir.
        Aluno encontrado = buscarPorMatricula(matricula);
        if (encontrado == null) {
            throw new IllegalArgumentException("Não é possível remover: matrícula não encontrada.");
        }
        armazem.remove(encontrado);
    }
}
