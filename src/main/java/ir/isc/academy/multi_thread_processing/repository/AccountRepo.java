package ir.isc.academy.multi_thread_processing.repository;

import ir.isc.academy.multi_thread_processing.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepo extends JpaRepository<Account, Long> {
}
