package yu.cs.spring.model;

import java.util.List;
import java.util.function.Function;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;

public class BaseRepositoryImpl<T,ID> extends SimpleJpaRepository<T, ID> implements BaseRepository<T, ID>  {

	private EntityManager entityManager;

	public BaseRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
		super(entityInformation, entityManager);
		this.entityManager= entityManager;
		
	}

	@Override
	public <R> List<R> search(Function<CriteriaBuilder, CriteriaQuery<R>> queryFunc) {
		var cq = queryFunc.apply(entityManager.getCriteriaBuilder());
		return entityManager.createQuery(cq).getResultList();
	}
	

}
