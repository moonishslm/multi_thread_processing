package ir.isc.academy.multi_thread_processing.service;

import ir.isc.academy.multi_thread_processing.model.Account;
import ir.isc.academy.multi_thread_processing.model.Customer;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.Arrays;

@Service
public class ValidationService {

    public boolean validateAccount(Account account) {
        return account.getAccountBalance().compareTo(account.getAccountLimit()) <= 0 &&
                Arrays.asList(1, 2, 3).contains(Integer.parseInt(account.getAccountType())) &&
                account.getAccountNumber().matches("^\\d{22}$") &&
                isNotNull(account);
    }

    public boolean validateCustomer(Customer customer) {
        return customer.getCustomerBirthDate().isAfter(LocalDate.of(1995, 1, 1)) &&
                customer.getCustomerNationalId().matches("^\\d{10}$") &&
                isNotNull(customer);
    }

    private boolean isNotNull(Object obj) {
        return Arrays.stream(obj.getClass().getDeclaredFields())
                .filter(field -> !field.getName().equals("id"))
                .noneMatch(field -> {
                    field.setAccessible(true);
                    try {
                        return field.get(obj) == null;
                    } catch (IllegalAccessException e) {
                        return true;
                    }
                });
    }
}