package comp303.a4.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import comp303.a4.entities.Customer;

public interface CustomerRepo extends JpaRepository<Customer, Integer>{

	@Query(value="SELECT c FROM Customer c WHERE c.Username LIKE (:user) AND c.Password LIKE (:pass)")
	Customer loginValidation(String user, String pass);
	
	@Query(value="SELECT c FROM Customer c WHERE c.Username LIKE (:user)")
	List<Customer> usernameExistCheck(String user);
}
