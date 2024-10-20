package ir.isc.academy.multi_thread_processing.repository;

import ir.isc.academy.multi_thread_processing.model.ReportDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ReportRepo {

    @PersistenceContext
    private EntityManager entityManager;

    public List<ReportDto> fetchReports() {
        String sql = "SELECT new ir.isc.academy.multi_thread_processing.model.ReportDto(" +
                "c.customerId, c.customerName, c.customerSurname, c.customerNationalId, " +
                "a.accountNumber, a.accountOpenDate, a.accountBalance) " +
                "FROM Customer c JOIN Account a ON a.accountCustomerId = c.customerId " +
                "WHERE a.accountBalance > 1000";

        TypedQuery<ReportDto> query = entityManager.createQuery(sql, ReportDto.class);
        return query.getResultList();
    }
}