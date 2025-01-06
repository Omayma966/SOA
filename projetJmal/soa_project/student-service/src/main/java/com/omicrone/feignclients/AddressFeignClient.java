package com.omicrone.feignclients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.omicrone.request.CreateAddressRequest;
import com.omicrone.request.UpdateAddressRequest;
import com.omicrone.response.AddressResponse;
import com.omicrone.response.StudentResponse;

@FeignClient(name = "address-service")  // This is the service name registered in Eureka
public interface AddressFeignClient {

    @GetMapping("/api/address/getById/{id}")
    public AddressResponse getById(@PathVariable long id);

    @PostMapping("/api/address/create")
    public AddressResponse createAddress(@RequestBody CreateAddressRequest createAddressRequest);
    
    @GetMapping("api/addresses/{id}")
    AddressResponse getAddressById(@PathVariable("id") Long id);
    
    @DeleteMapping("/{addressId}")
    void deleteAddress(@PathVariable Long addressId);
    
    @PutMapping("/{addressId}")
    AddressResponse updateAddress(@PathVariable Long addressId, @RequestBody UpdateAddressRequest updateAddressRequest);


}
