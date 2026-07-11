package com.burgerjoint.customerservice.service;

import com.burgerjoint.customerservice.dto.CustomerDTO;
import com.burgerjoint.customerservice.model.Customer;
import com.burgerjoint.customerservice.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public List<CustomerDTO> listarTodos() {
        return customerRepository.findAll().stream()
                .map(this::convertirAEntityADto)
                .collect(Collectors.toList());
    }

    public CustomerDTO buscarPorId(Long id) {
        Customer customer = customerRepository.findById(id).orElse(null);
        return (customer != null) ? convertirAEntityADto(customer) : null;
    }

    public CustomerDTO guardar(CustomerDTO customerDTO) {
        Customer customer = convertirADtoAEntity(customerDTO);
        Customer guardado = customerRepository.save(customer);
        return convertirAEntityADto(guardado);
    }

    public void eliminar(Long id) {
        customerRepository.deleteById(id);
    }

    private CustomerDTO convertirAEntityADto(Customer customer) {
        CustomerDTO dto = new CustomerDTO();
        dto.setId(customer.getId());
        dto.setNombre(customer.getNombre());
        dto.setEmail(customer.getEmail());
        dto.setTelefono(customer.getTelefono());
        return dto;
    }

    private Customer convertirADtoAEntity(CustomerDTO dto) {
        Customer entity = new Customer();
        entity.setId(dto.getId());
        entity.setNombre(dto.getNombre());
        entity.setEmail(dto.getEmail());
        entity.setTelefono(dto.getTelefono());
        return entity;
    }

}
