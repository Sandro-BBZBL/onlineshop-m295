package ch.sandro.bucher.onlineshop.customer;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CustomerService {
    private final CustomerRepository repository;

    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    public List<Customer> getAll() { return repository.findAll(); }
    public Customer save(Customer c) { return repository.save(c); }
    public Customer getByUsername(String u) { return repository.findByUsername(u).orElse(null); }
}