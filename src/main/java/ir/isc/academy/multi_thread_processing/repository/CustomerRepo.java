package ir.isc.academy.multi_thread_processing.repository;

import ir.isc.academy.multi_thread_processing.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, Long> {
}
