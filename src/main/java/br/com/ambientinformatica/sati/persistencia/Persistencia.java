package br.com.ambientinformatica.sati.persistencia;

import javax.persistence.EntityManager;

public class Persistencia<E> {

	private Class<E> classe;
	protected EntityManager em;

	public Persistencia(Class<E> classe) {
		this.classe = classe;
		em = ConexaoJpa.criarEntityManager();
	}

	public void abrirTransacao() {
		em.getTransaction().begin();
	}

	public void commitTransacao() {
		em.getTransaction().commit();
	}

	public void fechar() {
		em.close();
	}

	public void incluir(E v) throws Exception {
		try {
			abrirTransacao();
			em.persist(v);
			commitTransacao();
		} catch (Exception e) {
			throw new Exception(" Erro ao realizar a inclusao ");
		}
	}

	public void excluir(Object id) throws Exception {
		try {
			abrirTransacao();
			E obj = em.find(classe, id);
			em.remove(obj);
		} catch (Exception e) {
			throw new Exception(" Erro ao realizar a exclusao ");
		}
	}

	public E alterar(E v) throws Exception {
		try {
			abrirTransacao();
			E obj = em.merge(v);
			commitTransacao();
			return obj;
		} catch (Exception e) {
			throw new Exception(" Erro ao realizar a alteracao ");
		}
	}

	public E consultar(Object id) throws Exception {
		try {
			return em.find(classe, id);
		} catch (Exception e) {
			throw new Exception(" Erro ao realizar a consulta ");
		}
	}
}
