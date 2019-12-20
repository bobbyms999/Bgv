package com.accredilink.bgv.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.accredilink.bgv.entity.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

	public Employee findByFirstNameAndLastNameAndSsnNumber(String firstName, String lastName, String ssnNumber);
	/*
	 * @Query(name =
	 * "select * from employee where first_name=:firstName and last_name=:lastName and ssn_number=:ssnNumber"
	 * ,nativeQuery = true) public Employee
	 * findFirstNameandLastNameandSsnNumber(@Param("firstName") String
	 * firstName, @Param("lastName") String lastName, @Param("ssnNumber") String
	 * ssnNumber);
	 */
}
