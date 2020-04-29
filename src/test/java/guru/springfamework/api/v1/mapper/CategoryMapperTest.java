package guru.springfamework.api.v1.mapper;

import guru.springfamework.api.v1.model.CategoryDTO;
import guru.springfamework.domain.Category;
import org.junit.Test;

import static org.junit.Assert.*;

public class CategoryMapperTest {
	CategoryMapper categoryMapper = CategoryMapper.INSTANCE;

	@Test
	public void categoryToCategoryDTO() {
		Category category = new Category();
		category.setName("Fred");
		category.setId(1L);

		CategoryDTO categoryDTO = categoryMapper.categoryToCategoryDTO(category);

		assertEquals("Fred", categoryDTO.getName());
	}
}