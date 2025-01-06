package com.omicrone.service;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.List;


import feign.FeignException;


import java.util.List;
import java.util.ArrayList;
import com.omicrone.entity.Student;
import com.omicrone.response.StudentResponse;
import com.omicrone.response.AddressResponse;
import com.omicrone.exceptions.AdressBadRequestException;
import com.omicrone.exceptions.StudentNotFoundException;
import com.omicrone.feignclients.AddressFeignClient;
import com.omicrone.repository.StudentRepository;
import com.omicrone.request.CreateAddressRequest;
import com.omicrone.request.CreateStudentRequest;
import com.omicrone.request.UpdateAddressRequest;
import com.omicrone.request.UpdateStudentRequest;

@Service
public class StudentService {

	@Autowired
	StudentRepository studentRepository;

	@Autowired
	WebClient webClient;

	@Autowired
	AddressFeignClient addressFeignClient;
	

	public StudentResponse createStudent(CreateStudentRequest createStudentRequest) {

	    Student student = new Student();
	    student.setFirstName(createStudentRequest.getFirstName());
	    student.setLastName(createStudentRequest.getLastName());
	    student.setEmail(createStudentRequest.getEmail());

	    CreateAddressRequest addressRequest = new CreateAddressRequest();
	    addressRequest.setCity(createStudentRequest.getCity());
	    addressRequest.setStreet(createStudentRequest.getStreet());

	    AddressResponse addressResponse = addressFeignClient.createAddress(addressRequest);

	    student.setAddressId(addressResponse.getAddressId());
	    student = studentRepository.save(student);

	    StudentResponse studentResponse = new StudentResponse(student);

	    // Populate street and city in StudentResponse
	    studentResponse.setStreet(addressResponse.getStreet());
	    studentResponse.setCity(addressResponse.getCity());
	    studentResponse.setAddressResponse(addressResponse);

	    return studentResponse;
	}

	public StudentResponse getById(long id) {
		Optional<Student> studentOpt = studentRepository.findById(id);
		if (studentOpt.isPresent()) {
			Student student = studentRepository.findById(id).get();
			StudentResponse studentResponse = new StudentResponse(student);

			// studentResponse.setAddressResponse(getAddressById(student.getAddressId()));

			studentResponse.setAddressResponse(addressFeignClient.getById(student.getAddressId()));

			return studentResponse;
		} else
			throw new StudentNotFoundException("Etudiant non existant !!!");
	}

	private AddressResponse createAddressWithWebClient(CreateAddressRequest request) {

		return webClient.post().uri("/create").contentType(MediaType.APPLICATION_JSON).bodyValue(request).retrieve()
				.bodyToMono(AddressResponse.class).block();
	}

	public AddressResponse getAddressById(long addressId) {

		return addressFeignClient.getById(addressId);
	}
	

	public List<StudentResponse> getAllStudents() {
		List<Student> students = studentRepository.findAll();

		return students.stream().map(student -> {
			AddressResponse addressResponse = addressFeignClient.getById(student.getAddressId());
			return new StudentResponse(student, addressResponse);
		}).toList();
	}
	 // Supprimer un étudiant
    public void deleteStudent(Long studentId) {
        // Vérifier si l'étudiant existe
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Étudiant non trouvé avec l'ID : " + studentId));

        // Supprimer l'adresse via le service d'adresses
        addressFeignClient.deleteAddress(student.getAddressId());

        // Supprimer l'étudiant
        studentRepository.delete(student);
    }

    // Modifier un étudiant
    public StudentResponse updateStudent(Long studentId, UpdateStudentRequest updateStudentRequest) {
        // Vérifier si l'étudiant existe
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Étudiant non trouvé avec l'ID : " + studentId));

        // Mettre à jour les données de l'étudiant
        student.setFirstName(updateStudentRequest.getFirstName());
        student.setLastName(updateStudentRequest.getLastName());
        student.setEmail(updateStudentRequest.getEmail());

        // Mettre à jour l'adresse via le service d'adresses
        UpdateAddressRequest addressRequest = new UpdateAddressRequest();
        addressRequest.setCity(updateStudentRequest.getCity());
        addressRequest.setStreet(updateStudentRequest.getStreet());
        AddressResponse addressResponse = addressFeignClient.updateAddress(student.getAddressId(), addressRequest);

        // Sauvegarder l'étudiant
        studentRepository.save(student);

        // Préparer la réponse
        StudentResponse studentResponse = new StudentResponse(student);
        studentResponse.setStreet(addressResponse.getStreet());
        studentResponse.setCity(addressResponse.getCity());
        studentResponse.setAddressResponse(addressResponse);

        return studentResponse;
    }

	
}
