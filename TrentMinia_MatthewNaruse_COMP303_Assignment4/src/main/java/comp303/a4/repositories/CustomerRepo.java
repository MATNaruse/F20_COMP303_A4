package comp303.a4.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import comp303.a4.entities.Customer;

public interface CustomerRepo extends JpaRepository<Customer, Integer>{

}
