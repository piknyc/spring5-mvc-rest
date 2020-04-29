package guru.springfamework.service;

import guru.springfamework.api.v1.mapper.CategoryMapper;
import guru.springfamework.api.v1.model.CategoryDTO;
import guru.springfamework.api.v1.model.NotFoundException;
import guru.springfamework.domain.Category;
import guru.springfamework.repositories.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
	private final CategoryMapper categoryMapper;
	private final CategoryRepository categoryRepository;

	public CategoryServiceImpl(CategoryMapper categoryMapper, CategoryRepository categoryRepository) {
		this.categoryMapper = categoryMapper;
		this.categoryRepository = categoryRepository;
	}

	@Override
	public List<CategoryDTO> getAllCategories() {
		return categoryRepository.findAll().stream()
				  .map(categoryMapper::categoryToCategoryDTO)
				  .collect(Collectors.toList());
	}

	@Override
	public CategoryDTO getCategoryByName(String name) throws NotFoundException {

		Category cat = categoryRepository.findByName(name);

		if(cat != null) {
			return categoryMapper.categoryToCategoryDTO(cat);
		}
		else {
			throw new NotFoundException();
		}

	}
}
