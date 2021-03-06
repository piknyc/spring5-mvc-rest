package guru.springfamework.service;

import guru.springfamework.api.v1.model.NotFoundException;
import guru.springfamework.api.v1.model.VendorDTO;

import java.util.List;

public interface VendorService {
	List<VendorDTO> findAllVendors();

	VendorDTO findVendorByLastName(String lastName) throws NotFoundException;

	VendorDTO getVendorById(Long id);

	VendorDTO createNewVendor(VendorDTO vendorDTO);

	VendorDTO saveVendorByDTO(Long id, VendorDTO vendorDTO);

	VendorDTO patchVendor(Long id, VendorDTO vendorDTO);

	void deleteVendorById(Long id);
}
