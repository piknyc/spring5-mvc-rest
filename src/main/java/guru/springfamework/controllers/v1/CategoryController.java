package guru.springfamework.controllers.v1;

import guru.springfamework.api.v1.model.CategoryDTO;
import guru.springfamework.api.v1.model.CategoryListDTO;
import guru.springfamework.api.v1.model.NotFoundException;
import guru.springfamework.service.CategoryService;
import guru.springfamework.service.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(CategoryController.BASE_URL)
public class CategoryController {
	public static final String BASE_URL = "/api/v1/categories";

	private final CategoryService categoryService;

	public CategoryController(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	@GetMapping
	public ResponseEntity<CategoryListDTO> getallCatetories() {

		return new ResponseEntity<CategoryListDTO>(
				  new CategoryListDTO(categoryService.getAllCategories()), HttpStatus.OK);
	}

	@GetMapping("{name}")
	public ResponseEntity<CategoryDTO> getCategoryByName( @PathVariable String name){
		try {
			return new ResponseEntity<CategoryDTO>(
					  categoryService.getCategoryByName(name), HttpStatus.OK
			);
		} catch (NotFoundException e) {
			throw new ResourceNotFoundException("Not found");
		}

	}
}