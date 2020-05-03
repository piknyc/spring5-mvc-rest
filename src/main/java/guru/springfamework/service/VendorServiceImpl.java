package guru.springfamework.service;

import guru.springfamework.api.v1.mapper.VendorMapper;
import guru.springfamework.api.v1.model.NotFoundException;
import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.domain.Vendor;
import guru.springfamework.repositories.VendorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class VendorServiceImpl implements VendorService {
	private final VendorRepository vendorRepository;
	private final VendorMapper vendorMapper;

	public VendorServiceImpl(VendorRepository vendorRepository, VendorMapper vendorMapper) {
		this.vendorRepository = vendorRepository;
		this.vendorMapper = vendorMapper;
	}

	@Override
	public List<VendorDTO> findAllVendors() {
		return vendorRepository.findAll()
				  .stream()
				  .map(vendor -> {
					  VendorDTO vendorDTO = vendorMapper.vendorToVendorDTO(vendor);
					  vendorDTO.setVendorUrl("/api/v1/vendors/" + vendor.getId());
					  return vendorDTO;
				  })
				  .collect(Collectors.toList());
	}

	@Override
	public VendorDTO findVendorByLastName(String name) throws NotFoundException {
		Vendor vendor = vendorRepository.findByName(name);

		if (vendor != null) {
			return vendorMapper.vendorToVendorDTO(vendor);
		} else {
			throw new NotFoundException();
		}
	}

	@Override
	public VendorDTO getVendorById(Long id) {

		return vendorRepository.findById(id)
				  .map(vendor -> {
					  VendorDTO vendorDTO = vendorMapper.vendorToVendorDTO(vendor);
					  vendorDTO.setVendorUrl("/api/v1/vendors/" + vendor.getId());
					  return vendorDTO;
				  })
				  .orElseThrow(ResourceNotFoundException::new);
	}

	@Override
	public VendorDTO createNewVendor(VendorDTO vendorDTO) {

		Vendor vendor = vendorMapper.vendorDtoToVendor(vendorDTO);

		Vendor savedVendor = vendorRepository.save(vendor);

		VendorDTO returnDto = vendorMapper.vendorToVendorDTO(savedVendor);

		returnDto.setVendorUrl("/api/v1/vendor/" + savedVendor.getId());

		return returnDto;
	}

	@Override
	public VendorDTO saveVendorByDTO(Long id, VendorDTO vendorDTO) {
		Vendor vendor = vendorMapper.vendorDtoToVendor(vendorDTO);
		vendor.setId(id);

		Vendor savedVendor = vendorRepository.save(vendor);

		VendorDTO returnDto = vendorMapper.vendorToVendorDTO(savedVendor);

		returnDto.setVendorUrl("/api/v1/vendor/" + savedVendor.getId());

		return returnDto;

	}

	@Override
	public VendorDTO patchVendor(Long id, VendorDTO vendorDTO) {
		return vendorRepository.findById(id).map(vendor -> {
			if (vendorDTO.getName() != null) {
				vendor.setName(vendorDTO.getName());

			}
			if (vendorDTO.getVendorUrl() != null) {
				vendor.setVendorUrl(vendorDTO.getVendorUrl());
			}


			VendorDTO returnVendorDTO = vendorMapper.vendorToVendorDTO(vendorRepository.save(vendor));
			returnVendorDTO.setVendorUrl("/api/v1/vendors/" + vendor.getId());
			return returnVendorDTO;

		}).orElseThrow(RuntimeException::new);
	}

	@Override
	public void deleteVendorById(Long id) {
		vendorRepository.deleteById(id);
	}
}
