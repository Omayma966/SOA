package com.omicrone.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.omicrone.entity.Address;
import com.omicrone.exceptions.AdressNotFoundException;
import com.omicrone.repository.AddressRepository;
import com.omicrone.request.CreateAddressRequest;
import com.omicrone.request.UpdateAddressRequest;
import com.omicrone.response.AddressResponse;

@Service
public class AddressService {

	Logger logger = LoggerFactory.getLogger(AddressService.class);
	
	@Autowired
	AddressRepository addressRepository;
	
	@Value("${server.port}")
	private int serverPort;

	public AddressResponse createAddress(CreateAddressRequest createAddressRequest) {
		
		Address address = new Address();
		address.setStreet(createAddressRequest.getStreet());
		address.setCity(createAddressRequest.getCity());
		
		addressRepository.save(address);
		
		return new AddressResponse(address);
		
	}
	
	public AddressResponse getById (long id) {
		
		logger.info("Inside getById " + id);
		logger.info("Server Port : "+ serverPort);
		Optional<Address> addressOpt = addressRepository.findById(id);
		if(!addressOpt.isPresent())
			throw new AdressNotFoundException("Adresse inexistante ::: ");
		return new AddressResponse(addressOpt.get());
	}
	
    public AddressResponse updateAddress(Long id, UpdateAddressRequest request) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Adresse non trouvée"));
        address.setCity(request.getCity());
        address.setStreet(request.getStreet());
        address = addressRepository.save(address);
        return new AddressResponse(address);
    }

    public void deleteAddress(Long id) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Adresse non trouvée"));
        addressRepository.delete(address);
    }
	
}
