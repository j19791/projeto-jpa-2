package br.com.caelum.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import br.com.caelum.model.Loja;
import br.com.caelum.model.Produto;

@Repository
public class ProdutoDao {

	@PersistenceContext
	private EntityManager em;//Por padrão, a EntityManager é criada sempre em cada transação que for executada e é encerrada ao final desta ação. 

	public List<Produto> getProdutos() {
		return em.createQuery("from Produto", Produto.class).getResultList();
	}

	public Produto getProduto(Integer id) {
		Produto produto = em.find(Produto.class, id);
		return produto;
	}

	public List<Produto> getProdutos(String nome, Integer categoriaId, Integer lojaId) {
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder(); //fabrica auxiliar responsável pela criação de queries com a Criteria API
		CriteriaQuery<Produto> query = criteriaBuilder.createQuery(Produto.class);//cria critério de consulta com retorno do tipo da classe 
		Root<Produto> root = query.from(Produto.class);//passar a classe que vai servir de base na consulta

		Path<String> nomePath = root.<String> get("nome");//A raiz é usada para definir os caminhos (Path) até os atributos do objeto
		Path<Integer> lojaPath = root.<Loja> get("loja").<Integer> get("id");
		Path<Integer> categoriaPath = root.join("categorias").<Integer> get("id");//o relacionamento de "categoria" é @ManyToMany. Precisaremos de um join a partir do produto

		List<Predicate> predicates = new ArrayList<>();

		if (!nome.isEmpty()) {//caso o nome não seja vazio
			Predicate nomeIgual = criteriaBuilder.like(nomePath, nome);//Predicate de parecido (like):  buscar o produto que tenha o nome parecido ao entrado pelo usuário.
			predicates.add(nomeIgual);  
		}
		if (categoriaId != null) {
			Predicate categoriaIgual = criteriaBuilder.equal(categoriaPath, categoriaId);
			predicates.add(categoriaIgual);
		}
		if (lojaId != null) {
			Predicate lojaIgual = criteriaBuilder.equal(lojaPath, lojaId);
			predicates.add(lojaIgual);
		}

		query.where((Predicate[]) predicates.toArray(new Predicate[0]));//array de predicados p/ buscar os produtos

		TypedQuery<Produto> typedQuery = em.createQuery(query);
		return typedQuery.getResultList();

	}

	public void insere(Produto produto) {
		if (produto.getId() == null)
			em.persist(produto);
		else
			em.merge(produto);
	}

}
