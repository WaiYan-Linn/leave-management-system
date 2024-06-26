package yu.cs.spring.model.master.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

import yu.cs.spring.model.master.entity.Account;

public interface AccountRepo extends JpaRepositoryImplementation<Account, Integer>{

	Optional<Account> findByUsername(String username);
	
	  @Query("SELECT a FROM Account a WHERE a.role = 1")
	  List<Account> findAllMembers();

	  boolean existsByUsername(String email);
	  
	  @Query("SELECT a.name FROM Account a WHERE a.username = :username")
	  String getNameByUsername(String username);
}
