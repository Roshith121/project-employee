package com.employee.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.employee.exception.ResourceNotFoundException;
import com.employee.model.EmployeeModel;
import com.employee.repo.EmployeeRepo;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
	
	@Autowired
	private EmployeeRepo employeeRepo;
	
	// get all employee
	
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/")
	public List<EmployeeModel> getAllEmployee(){
		return employeeRepo.findAll();
	}
	
	// create employee
	
	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping("/")
	public EmployeeModel createEmployee(@RequestBody EmployeeModel employee) {
		return employeeRepo.save(employee);
		
	}
	
	// get employee by id 
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/{id}")
	public ResponseEntity<EmployeeModel> getEmployeeById(@PathVariable Long id){

		EmployeeModel employee = employeeRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee id not found for "+id));
		return ResponseEntity.ok(employee);
	}
	
	
	//update employee
	@CrossOrigin(origins = "http://localhost:4200")
	@PutMapping("/{id}")
	public ResponseEntity<EmployeeModel> updateEmployee(@PathVariable Long id , @RequestBody EmployeeModel employeeDetails)
	{
		EmployeeModel employee = employeeRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee id not found for "+id));
		
		employee.setFirstName(employeeDetails.getFirstName());
		employee.setLastName(employeeDetails.getLastName());
		employee.setEmail(employeeDetails.getEmail());
		
		EmployeeModel updatedEmployee = employeeRepo.save(employee);
		return ResponseEntity.ok(updatedEmployee);
	}
	
	
	@CrossOrigin(origins = "http://localhost:4200")
	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteEmployee(@PathVariable Long id){
		
		EmployeeModel employee = employeeRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Employee not exist with id :"+id));
		employeeRepo.delete(employee);
		Map<String,Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
		
	}
	

}
