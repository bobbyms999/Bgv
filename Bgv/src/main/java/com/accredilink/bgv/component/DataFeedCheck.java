package com.accredilink.bgv.component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.rowset.serial.SerialBlob;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.accredilink.bgv.entity.Alias;
import com.accredilink.bgv.entity.BgDataBase;
import com.accredilink.bgv.entity.DataFeedEmployee;
import com.accredilink.bgv.entity.Employee;
import com.accredilink.bgv.entity.EmployeeAgency;
import com.accredilink.bgv.entity.EmployeeBgDetails;
import com.accredilink.bgv.exception.AccredLinkAppException;
import com.accredilink.bgv.key.EmployeeBgDetailsKey;
import com.accredilink.bgv.repository.BgDataBaseRepository;
import com.accredilink.bgv.repository.EmployeeBgDetailsRepository;
import com.accredilink.bgv.service.AliasService;
import com.accredilink.bgv.service.DataFeedEmployeeService;

@Component
public class DataFeedCheck {

	@Autowired
	private AliasService aliasService;

	@Autowired
	private DataFeedEmployeeService dataFeedEmployeeService;

	@Autowired
	private EmployeeBgDetailsRepository employeeBgDetailsRepository;

	@Autowired
	private BgDataBaseRepository bgDataBaseRepository;

	@Autowired
	private NationalOIGCheck nationalOIGCheck;

	@Autowired
	private TxOIGCheck txOIGCheck;

	@Autowired
	private DadsEMRCheck dadsEMRCheck;

	private Alias alias;
	private DataFeedEmployee dataFeedEmp;

	@Autowired
	private LobHelper lobHelper;

	public List<EmployeeBgDetails> verifyEmployeeData(EmployeeAgency employeeAgency) throws IOException {
		List<EmployeeBgDetails> saveEmplBgDetails = new ArrayList<EmployeeBgDetails>();
		// Verifying the data in datafeedTable with first name is avaiable or not

		dataFeedEmp = dataFeedEmployeeService.findAllByFirstName(employeeAgency.getEmployee().getFirstName(),
				employeeAgency.getEmployee().getLastName());

		// Loading the employee Success image for Bgv Verification.
		ClassLoader classLoader = ClassLoader.getSystemClassLoader();
		File file = new File(classLoader.getResource("EmployeeBgPass.png").getFile());

		if (dataFeedEmp == null) {

			// Verifying the data in aliasTable with alias name is avaiable or not

			alias = aliasService.findAllByAliasNamesGeneral(employeeAgency.getEmployee().getFirstName());
			if (alias == null) {
				saveEmplBgDetails.add(storeEmployeeBgDetails(employeeAgency, 1,
						"No Records Found From National OIG Data Sheet", "No Records Found In National OIG DataBase", Files.readAllBytes(file.toPath())));
				saveEmplBgDetails.add(storeEmployeeBgDetails(employeeAgency, 2,
						"No Records Found From Texas OIG Data Sheet", "No Records Found In Texas OIG DataBase", Files.readAllBytes(file.toPath())));

				// Call The Dad's EMR Call from selinum code and store the employee details //
				// to list

				saveEmplBgDetails.add(dadsEMRCheck.check(employeeAgency));

			} else { // need to integrate selinum code(NATIONAL/TEXAS OIG) for ssn number check and
						// // store the employee bg details
				saveEmplBgDetails.addAll(processOigCalls(employeeAgency));
			}
		} else {

			// need to integrate selinum code for ssn number check and store the employee bg
			// details
			saveEmplBgDetails.addAll(processOigCalls(employeeAgency));

		}
		return saveEmplBgDetails;

	}

	public EmployeeBgDetails storeEmployeeBgDetails(EmployeeAgency employeeAgency, int bgBdId, String bgResult,
			String bgResultDesc, byte[] bgResultProof) {
		EmployeeBgDetails saveEmplBgDetails = new EmployeeBgDetails();
		EmployeeBgDetailsKey employeeBgDetailsKey = new EmployeeBgDetailsKey();
		try {
			// Adding composite key combination with emp,agency,bgdatabase
			employeeBgDetailsKey.setEmployee(employeeAgency.getEmployee());
			employeeBgDetailsKey.setAgency(employeeAgency.getAgency());
			BgDataBase bgdatabase = bgDataBaseRepository.findAllByBgDbId(bgBdId);
			employeeBgDetailsKey.setBgDateBase(bgdatabase);
			employeeBgDetailsKey.setBg_date_time(new Date());

			// Adding employeeBgDetails object
			saveEmplBgDetails.setActive(true);
			saveEmplBgDetails.setBgresult(bgResult);
			saveEmplBgDetails.setBgresultdesc(bgResultDesc);
			saveEmplBgDetails.setEmployeeBgDetailsPk(employeeBgDetailsKey);
			saveEmplBgDetails.setBgresultproof(new SerialBlob(bgResultProof));
			employeeBgDetailsRepository.save(saveEmplBgDetails);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return saveEmplBgDetails;
	}

	public List<EmployeeBgDetails> processOigCalls(EmployeeAgency employeeAgency) {
		List<EmployeeBgDetails> employeeBgDetails = new ArrayList<EmployeeBgDetails>();

		if (conditionalChecks(employeeAgency, employeeBgDetails, nationalOIGCheck) == false) {
			if (conditionalChecks(employeeAgency, employeeBgDetails, txOIGCheck) == false) {
				if (dadsEMRCheck(employeeAgency, employeeBgDetails) == false) {

				}
			}
		}
		return employeeBgDetails;
	}

	public boolean conditionalChecks(EmployeeAgency employeeAgency, List<EmployeeBgDetails> employeeBgDetails,
			OigCalls oigCalls) {
		boolean status = false;
		EmployeeBgDetails bgDetails = null;
		String SSNSuccess = "SSN Number Matched";
		try {
			// FirstName and LastName Check
			bgDetails = oigCalls.check(employeeAgency, employeeAgency.getEmployee().getLastName(),
					employeeAgency.getEmployee().getFirstName());
			if (!(bgDetails.getBgresult().trim().equals(SSNSuccess))) {
				employeeBgDetails.add(bgDetails);
			} else {
				employeeBgDetails.add(bgDetails);
				return true;
			}

			// aliasSpecific check if is true
			if (aliasSpecificCheck(employeeAgency.getEmployee())) {
				bgDetails = oigCalls.check(employeeAgency, employeeAgency.getEmployee().getLastName(),
						employeeAgency.getEmployee().getAliasSpecific());
				if (!(bgDetails.getBgresult().trim().equals(SSNSuccess))) {
					employeeBgDetails.add(bgDetails);
				} else {
					employeeBgDetails.add(bgDetails);
					return true;
				}
			}

			// alias names check if names are found
			String aliasNames = aliasNamesCheck(employeeAgency.getEmployee());
			if (aliasNames != null) {
				String names[] = aliasNames.split(",");
				// one alias name found
				if (names.length == 1) {
					bgDetails = oigCalls.check(employeeAgency, employeeAgency.getEmployee().getLastName(), names[0]);
					if (!(bgDetails.getBgresult().trim().equals(SSNSuccess))) {
						employeeBgDetails.add(bgDetails);
					} else {
						employeeBgDetails.add(bgDetails);
						return true;
					}
					// multiple alias names found
				} else {
					for (int i = 0; i < names.length; i++) {
						bgDetails = oigCalls.check(employeeAgency, employeeAgency.getEmployee().getLastName(),
								names[i]);
						if (!(bgDetails.getBgresult().trim().equals(SSNSuccess))) {
							employeeBgDetails.add(bgDetails);
						} else {
							employeeBgDetails.add(bgDetails);
							return true;
						}
					}
				}
			}

			// maiden name check
			String maidenName = maidenNameCheck(employeeAgency.getEmployee());
			if (maidenName != null) {

				// first name and maiden name check
				bgDetails = oigCalls.check(employeeAgency, maidenName, employeeAgency.getEmployee().getFirstName());
				if (!(bgDetails.getBgresult().trim().equals(SSNSuccess))) {
					employeeBgDetails.add(bgDetails);
				} else {
					employeeBgDetails.add(bgDetails);
					return true;
				}

				// aliasSpecific check if it is true
				if (aliasSpecificCheck(employeeAgency.getEmployee())) {
					bgDetails = oigCalls.check(employeeAgency, maidenName,
							employeeAgency.getEmployee().getAliasSpecific());
					if (!(bgDetails.getBgresult().trim().equals(SSNSuccess))) {
						employeeBgDetails.add(bgDetails);
					} else {
						employeeBgDetails.add(bgDetails);
						return true;
					}
				}

				// alias names check if names are found
				String aliasNames1 = aliasNamesCheck(employeeAgency.getEmployee());
				if (aliasNames1 != null) {
					String names[] = aliasNames1.split(",");
					// one alias name found
					if (names.length == 1) {
						bgDetails = oigCalls.check(employeeAgency, maidenName, names[0]);
						if (!(bgDetails.getBgresult().trim().equals(SSNSuccess))) {
							employeeBgDetails.add(bgDetails);
						} else {
							employeeBgDetails.add(bgDetails);
							return true;
						}
						// multiple alias names found
					} else {
						for (int i = 0; i < names.length; i++) {
							bgDetails = oigCalls.check(employeeAgency, maidenName, names[i]);
							if (!(bgDetails.getBgresult().trim().equals(SSNSuccess))) {
								employeeBgDetails.add(bgDetails);
							} else {
								employeeBgDetails.add(bgDetails);
								return true;
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new AccredLinkAppException("Unable to process your request now");
		}

		return status;
	}

	public boolean dadsEMRCheck(EmployeeAgency employeeAgency, List<EmployeeBgDetails> employeeBgDetails) {
		boolean status = false;
		String SSNSuccess = "SSN Number Matched";
		EmployeeBgDetails bgDetails = null;
		try {
			// SSN Check
			bgDetails = dadsEMRCheck.check(employeeAgency);
			if (!(bgDetails.getBgresult().trim().equals(SSNSuccess))) {
				employeeBgDetails.add(bgDetails);
			} else {
				employeeBgDetails.add(bgDetails);
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new AccredLinkAppException("Unable to process your request now");
		}
		return status;
	}

	public boolean aliasSpecificCheck(Employee employee) {
		if (employee.getAliasSpecific() != null) {
			return true;
		}
		return false;
	}

	public String maidenNameCheck(Employee employee) {
		if (employee.getMaidenName() != null) {
			return employee.getMaidenName();
		}
		return employee.getMaidenName();

	}

	public String aliasNamesCheck(Employee employee) {
		String aliasName = null;
		Alias alias = aliasService.findAllByAliasNameFor(employee.getFirstName());
		if (alias != null) {
			aliasName = alias.getAliasNamesGeneral();
		}
		return aliasName;
	}

}
