package guru.springfamework.service;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.api.v1.model.NotFoundException;

import java.util.List;

public interface CustomerService {
	List<CustomerDTO> findAllCustomers();
	CustomerDTO findCustomerByLastName(String lastName) throws NotFoundException;

}
