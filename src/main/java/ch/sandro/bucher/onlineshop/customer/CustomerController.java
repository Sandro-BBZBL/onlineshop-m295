package ch.sandro.bucher.onlineshop.customer;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    private final CustomerService service;

    public CustomerController(CustomerService service) {
        this.service = service;
    }

    @GetMapping
    public List<Customer> getAll() { return service.getAll(); }

    @PostMapping
    public Customer create(@RequestBody Customer customer) { return service.save(customer); }

    @GetMapping("/{username}")
    public Customer getByUsername(@PathVariable String username) { return service.getByUsername(username); }
}