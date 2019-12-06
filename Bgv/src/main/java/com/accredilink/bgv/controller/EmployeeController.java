package com.accredilink.bgv.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.accredilink.bgv.component.DisciplineDataLoading;
import com.accredilink.bgv.entity.Agency;
import com.accredilink.bgv.entity.Discipline;
import com.accredilink.bgv.entity.Employee;
import com.accredilink.bgv.entity.EmployeeAgency;
import com.accredilink.bgv.repository.AgencyRepository;
import com.accredilink.bgv.service.EmployeeService;
import com.accredilink.bgv.util.ResponseObject;

@RestController
@RequestMapping("/employee")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private AgencyRepository agencyRepository;

	@Autowired
	private DisciplineDataLoading disciplineDataLoading;

	@PostMapping("/create")
	public ResponseObject createEmployee(@RequestBody EmployeeAgency employeeAgency) {
		return employeeService.create(employeeAgency);
	}

	@GetMapping("/delete/{employeeId}")
	public ResponseObject deleteEmployee(@PathVariable int employeeId) {
		return employeeService.delete(employeeId);
	}

	@GetMapping("/fetchallemployees")
	public List<Employee> fetchAllEmployees() {
		return employeeService.getAllEmployees();
	}

	@GetMapping("/fetchallagencies")
	public List<Agency> fetchAllAgencies() {
		return agencyRepository.findAll();
	}

	@GetMapping("/fetchalldisciplines")
	public List<Discipline> fetchAllDisciplines() {
		return disciplineDataLoading.fetchAllDisciplines();
	}
}
