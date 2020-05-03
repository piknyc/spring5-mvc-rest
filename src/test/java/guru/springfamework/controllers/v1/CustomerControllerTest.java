package guru.springfamework.controllers.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.service.CustomerService;
import guru.springfamework.service.ResourceNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class CustomerControllerTest {
	@Mock
	CustomerService customerService;

	@InjectMocks
	CustomerController customerController;

	MockMvc mockMvc;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		mockMvc = MockMvcBuilders.standaloneSetup(customerController)
				  .setControllerAdvice(new RestResponseEntityExceptionHandler()).build();
	}


	@Test
	public void testUpdateCustomer() throws Exception {
		//given
		CustomerDTO customer = new CustomerDTO();
		customer.setFirstName("Fred");
		customer.setLastName("Flintstone");

		CustomerDTO returnDTO = new CustomerDTO();
		returnDTO.setFirstName(customer.getFirstName());
		returnDTO.setLastName(customer.getLastName());
		returnDTO.setCustomerUrl("/api/v1/customers/1");

		when(customerService.saveCustomerByDTO(anyLong(), any(CustomerDTO.class))).thenReturn(returnDTO);

		//when/then
		mockMvc.perform(put("/api/v1/customers/1")
				  .contentType(MediaType.APPLICATION_JSON)
				  .content(asJsonString(customer)))
				  .andExpect(status().isOk())
				  .andExpect(jsonPath("$.firstname", equalTo("Fred")))
				  .andExpect(jsonPath("$.lastname", equalTo("Flintstone")))
				  .andExpect(jsonPath("$.customer_url", equalTo("/api/v1/customers/1")));
	}

	@Test
	public void testPatchCustomer() throws Exception {

		//given
		CustomerDTO customer = new CustomerDTO();
		customer.setFirstName("Fred");

		CustomerDTO returnDTO = new CustomerDTO();
		returnDTO.setFirstName(customer.getFirstName());
		returnDTO.setLastName("Flintstone");
		returnDTO.setCustomerUrl("/api/v1/customers/1");

		when(customerService.patchCustomer(anyLong(), any(CustomerDTO.class))).thenReturn(returnDTO);

		mockMvc.perform(patch("/api/v1/customers/1")
				  .contentType(MediaType.APPLICATION_JSON)
				  .content(asJsonString(customer)))
				  .andExpect(status().isOk())
				  .andExpect(jsonPath("$.firstname", equalTo("Fred")))
				  .andExpect(jsonPath("$.lastname", equalTo("Flintstone")))
				  .andExpect(jsonPath("$.customer_url", equalTo("/api/v1/customers/1")));
	}


	@Test
	public void testDeleteCustomer() throws Exception {

		mockMvc.perform(delete("/api/v1/customers/1")
				  .contentType(MediaType.APPLICATION_JSON))
				  .andExpect(status().isOk());

		verify(customerService).deleteCustomerById(anyLong());
	}


	@Test
	public void testNotFoundException() throws Exception {

		when(customerService.getCustomerById(anyLong())).thenThrow(ResourceNotFoundException.class);

		mockMvc.perform(get(CustomerController.BASE_URL + "/222")
				  .contentType(MediaType.APPLICATION_JSON))
				  .andExpect(status().isNotFound());
	}

	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}