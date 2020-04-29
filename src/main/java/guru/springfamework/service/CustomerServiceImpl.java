package guru.springfamework.service;

import guru.springfamework.api.v1.mapper.CustomerMapper;
import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.api.v1.model.NotFoundException;
import guru.springfamework.domain.Customer;
import guru.springfamework.repositories.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class CustomerServiceImpl implements CustomerService {
	private final CustomerRepository customerRepository;
	private final CustomerMapper customerMapper;

	public CustomerServiceImpl(CustomerRepository customerRepository, CustomerMapper customerMapper) {
		this.customerRepository = customerRepository;
		this.customerMapper = customerMapper;
	}

	@Override
	public List<CustomerDTO> findAllCustomers() {
		return customerRepository.findAll()
				  .stream()
				  .map(customerMapper::customerToCustomerDTO)
				  .collect(Collectors.toList());
	}

	@Override
	public CustomerDTO findCustomerByLastName(String lastName) throws NotFoundException {
		Customer customer = customerRepository.findByLastName(lastName);

		if(customer != null) {
			return customerMapper.customerToCustomerDTO(customer);
		}
		else {
			throw new NotFoundException();
		}
	}
}
