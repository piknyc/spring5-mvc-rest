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
				  .map(customer -> {
					  CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);
					  customerDTO.setCustomerUrl("/api/v1/customers/" + customer.getId());
					  return customerDTO;
				  })
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

	@Override
	public CustomerDTO getCustomerById(Long id) {

		return customerRepository.findById(id)
				  .map(customer -> {
					  CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);
					  customerDTO.setCustomerUrl("/api/v1/customers/" + customer.getId());
					  return customerDTO;
				  })
				  .orElseThrow(RuntimeException::new); //todo implement better exception handling
	}

	@Override
	public CustomerDTO createNewCustomer(CustomerDTO customerDTO) {

		Customer customer = customerMapper.customerDtoToCustomer(customerDTO);

		Customer savedCustomer = customerRepository.save(customer);

		CustomerDTO returnDto = customerMapper.customerToCustomerDTO(savedCustomer);

		returnDto.setCustomerUrl("/api/v1/customer/" + savedCustomer.getId());

		return returnDto;
	}

	@Override
	public CustomerDTO saveCustomerByDTO(Long id, CustomerDTO customerDTO) {
		Customer customer = customerMapper.customerDtoToCustomer(customerDTO);
		customer.setId(id);

		Customer savedCustomer = customerRepository.save(customer);

		CustomerDTO returnDto = customerMapper.customerToCustomerDTO(savedCustomer);

		returnDto.setCustomerUrl("/api/v1/customer/" + savedCustomer.getId());

		return returnDto;

	}

	@Override
	public CustomerDTO patchCustomer(Long id, CustomerDTO customerDTO) {
		return customerRepository.findById(id).map(customer -> {
			if (customerDTO.getFirstName() != null) {
				customer.setFirstName(customerDTO.getFirstName());

			}
			if (customerDTO.getLastName() != null) {
				customer.setLastName(customerDTO.getLastName());
			}


			CustomerDTO returnCustomerDTO = customerMapper.customerToCustomerDTO(customerRepository.save(customer));
			returnCustomerDTO.setCustomerUrl("/api/v1/customers/" + customer.getId());
			return returnCustomerDTO;

		}).orElseThrow(RuntimeException::new);
	}

	@Override
	public void deleteCustomerById(Long id) {
		customerRepository.deleteById(id);
	}
}
