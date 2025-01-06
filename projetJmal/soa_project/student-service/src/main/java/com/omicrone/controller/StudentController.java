package com.omicrone.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.omicrone.entity.Student;
import com.omicrone.exceptions.AdressBadRequestException;
import com.omicrone.request.CreateStudentRequest;
import com.omicrone.request.UpdateStudentRequest;
import com.omicrone.response.StudentResponse;
import com.omicrone.service.StudentService;

@RestController
@RequestMapping("/api/student")
@CrossOrigin(origins = "http://localhost:4200")  // Allow access from your frontend
public class StudentController {
	
	@Autowired
	StudentService studentService;
	
	@PostMapping("/create")
	public StudentResponse createStudent (@RequestBody CreateStudentRequest createStudentRequest) {
		return studentService.createStudent(createStudentRequest);
	}
	
	@GetMapping("getById/{id}")
	public StudentResponse getById (@PathVariable long id) {
		return studentService.getById(id);
	}
	

	@GetMapping("/getAllStudents")
	public List<StudentResponse> getAll() {
		return studentService.getAllStudents();
	}
	
    @PutMapping("/{id}")
    public StudentResponse updateStudent(
            @PathVariable("id") Long id, 
            @RequestBody UpdateStudentRequest updateStudentRequest) {
        return studentService.updateStudent(id, updateStudentRequest);
    }

    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable("id") Long id) {
        studentService.deleteStudent(id);
    }
}
