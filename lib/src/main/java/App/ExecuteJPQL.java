package App;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import classes.Aluno;
import classes.Estado;

public class ExecuteJPQL {

	public static void main(String[] args) {

		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("part2-DIO");
		EntityManager entityManager = entityManagerFactory.createEntityManager();

		entityManager.getTransaction().begin();

		Estado estadoParaAdicionar = new Estado("Bahia", "BA");
		entityManager.persist(estadoParaAdicionar);
		entityManager.persist(new Estado("Sao Paulo", "SP"));
		entityManager.persist(new Aluno("Daniel", 29, estadoParaAdicionar));
		entityManager.persist(new Aluno("Joao", 20, estadoParaAdicionar));
		entityManager.persist(new Aluno("Pedro", 30, estadoParaAdicionar));

		entityManager.getTransaction().commit();

		System.out.println("Ok!");

		// Buscando aluno com EntityManager7
		// Com o entityManager não possivel trazer todos os alunos do bd
		// Outra limitação é que só há busca por meio do id
		Aluno alunoEntityManager = entityManager.find(Aluno.class, 1);

		System.out.println("O aluno encontrado foi: " + alunoEntityManager);

		// 2.1 O parametro de busca que será utilizado nas proximas consultas
		String nome = "Joao";

		// JPQL
		String jpql = "select a from Aluno a where a.nome = :nome";

		Aluno alunoJPQL = entityManager.createQuery(jpql, Aluno.class).setParameter("nome", nome).getSingleResult();

		// Trazendo uma lista como resultado
		String jpqlList = "select a from Aluno a where a.estado = :estado";
		List<Aluno> alunoJPQLList = entityManager.createQuery(jpqlList, Aluno.class)
				.setParameter("estado", estadoParaAdicionar).getResultList();

     // Resultados das consultas acima
		System.out.println("Consulta alunoJPQL: " + alunoJPQL);
		alunoJPQLList.forEach(Aluno -> System.out.println("Consulta alunoJPQLList: " + Aluno));
		
		System.out.println("Fim!!!");
	}

}
