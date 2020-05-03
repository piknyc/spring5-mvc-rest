package guru.springfamework;

import guru.springfamework.domain.Category;
import guru.springfamework.domain.Customer;
import guru.springfamework.domain.Vendor;
import guru.springfamework.repositories.CategoryRepository;
import guru.springfamework.repositories.CustomerRepository;
import guru.springfamework.repositories.VendorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
public class Bootstrap implements CommandLineRunner {
	private CategoryRepository categoryRepository;
	private CustomerRepository customerRepository;
	private VendorRepository vendorRepository;

	public Bootstrap(CategoryRepository categoryRepository, CustomerRepository customerRepository, VendorRepository vendorRepository) {
		this.categoryRepository = categoryRepository;
		this.customerRepository = customerRepository;
		this.vendorRepository = vendorRepository;
	}


	@Override
	public void run(String... args) throws Exception {
		Category fruits = new Category();
		fruits.setName("Fruits");

		Category dried = new Category();
		dried.setName("Dried");

		Category fresh = new Category();
		fresh.setName("Fresh");

		Category exotic = new Category();
		exotic.setName("Exotic");

		Category nuts = new Category();
		nuts.setName("Nuts");

		categoryRepository.save(fruits);
		categoryRepository.save(dried);
		categoryRepository.save(fresh);
		categoryRepository.save(exotic);
		categoryRepository.save(nuts);


		System.out.println("Category Data Loaded = " + categoryRepository.count());

		customerRepository.save(new Customer("Dylan", "Larken"));
		customerRepository.save(new Customer("Bob", "Probert"));
		customerRepository.save(new Customer("Steve", "Yzerman"));

		System.out.println("Customer Data Loaded = " + customerRepository.count());

		vendorRepository.save(new Vendor(1L, "googl", "google.com"));
		vendorRepository.save(new Vendor(2L, "walmart", "walmart.com"));

		System.out.println("Vendor Data Loaded = " + vendorRepository.count());
	}
}
