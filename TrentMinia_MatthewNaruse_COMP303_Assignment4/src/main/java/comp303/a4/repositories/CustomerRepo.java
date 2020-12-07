/**
 * COMP303-001 Assignment 4
 * Due Date: Dec 06, 2020
 * Submitted: ??? ##, 2020
 * 301 041 132 : Trent Minia
 * 300 549 638 : Matthew Naruse
 */
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
