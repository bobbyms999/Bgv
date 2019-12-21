package com.accredilink.bgv.component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.accredilink.bgv.entity.Address;
import com.accredilink.bgv.entity.Agency;
import com.accredilink.bgv.entity.Alias;
import com.accredilink.bgv.entity.BgDataBase;
import com.accredilink.bgv.entity.DataFeedEmployee;
import com.accredilink.bgv.entity.Discipline;
import com.accredilink.bgv.entity.Employee;
import com.accredilink.bgv.entity.EmployeeAgency;
import com.accredilink.bgv.exception.AccredLinkAppException;
import com.accredilink.bgv.repository.AgencyRepository;
import com.accredilink.bgv.repository.EmployeeRepository;
import com.accredilink.bgv.util.Validator;
import com.accredilink.bgv.util.StringUtil;

@Component
public class ExcelMapper {

	@Autowired
	DisciplineDataLoading disciplineDataLoading;

	@Autowired
	private AgencyRepository agencyRepository;

	@Autowired
	private EmployeeRepository employeeRepository;
	
	public int uniqueRecords;

	static Set<DataFeedEmployee> dataFeedEmployee = new HashSet<DataFeedEmployee>();

	public List<?> mapToObject(MultipartFile file,String loggedInUser,String agencyName, Class<?> classType) {

		if (classType == Employee.class) {
			return loadEmployeeData(file,loggedInUser,agencyName);
		} else if (classType == Alias.class) {
			return verifyAliasName(file);
		} else if (classType == DataFeedEmployee.class) {
			return dataFeedEmployee(file);
		}
		return null;
	}

	/*
	 * Loading excel sheet data to employee object
	 */
	private List<?> loadEmployeeData(MultipartFile file,String loggedInUser,String agencyName) {
		Map<String, Integer> dataMap = disciplineDataLoading.getData();
		try {
			XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
			XSSFSheet sheet = workbook.getSheetAt(0);
			List<Employee> employees = new ArrayList<Employee>();
			Address address;
			Alias alias;
			Discipline discipline;
			Employee employee;

			for (int i = 1; i < sheet.getLastRowNum(); i++) {
				Row row = sheet.getRow(i);
				employee = new Employee();
				alias = new Alias();
				discipline = new Discipline();
				address = new Address();

				if (row != null) {
					if (row.getCell(0) != null) {
						employee.setFirstName(row.getCell(0).getStringCellValue());

						if (!(StringUtil.isNullOrEmpty(row.getCell(0).getStringCellValue()))) {

							String ssnNumber = Validator.validateSSN(row.getCell(9).getStringCellValue());
							Employee existingEmployee = employeeRepository.findByFirstNameAndLastNameAndSsnNumber(
									row.getCell(0).getStringCellValue(), row.getCell(2).getStringCellValue(),
									ssnNumber);
							if (existingEmployee == null) {
								if (row.getCell(1) != null) {
									employee.setMiddleName(row.getCell(1).getStringCellValue());
								}

								employee.setLastName(row.getCell(2).getStringCellValue());
								if (row.getCell(3) != null) {
									employee.setMaidenName(row.getCell(3).getStringCellValue());
								}
								employee.setSsnNumber(Validator.validateSSN(row.getCell(9).getStringCellValue()));
								employee.setDateOfBirth((row.getCell(10).getDateCellValue().toInstant()
										.atZone(ZoneId.systemDefault()).toLocalDate()));
								employee.setActive("S");
								employee.setCreatedBy(loggedInUser);
								if (row.getCell(4) != null) {
									employee.setAliasSpecific(row.getCell(4).getStringCellValue());
								}
								address.setAddressLine1(row.getCell(5).getStringCellValue());
								address.setCity(row.getCell(6).getStringCellValue());
								address.setState(row.getCell(7).getStringCellValue());
								address.setZip((int) row.getCell(8).getNumericCellValue());
								employee.setAddress(address);
								String value = row.getCell(11).getStringCellValue();
								for (String data : dataMap.keySet()) {
									if (data.equals(value)) {
										discipline.setDisciplineId(dataMap.get(data));
										discipline.setDisciplineValue(value);
									}
								}
								employee.setDiscipline(discipline);
								if (discipline.getDisciplineId() == 0) {
									employee.setDiscipline(null);
								}
								EmployeeAgency newEmployeeAgency = new EmployeeAgency();
								Optional<Agency> existingAgency = agencyRepository
										.findByAgencyName(agencyName);
								if (existingAgency.isPresent()) {
									newEmployeeAgency.setAgency(existingAgency.get());
								} else {
									throw new AccredLinkAppException("Agency Information Not Found");
								}
								newEmployeeAgency.setEmployee(employee);
								newEmployeeAgency.setBgStatus("NOT_STARTED");
								employee.addEmployeeAgency(newEmployeeAgency);
								employeeRepository.save(employee);
								employees.add(employee);
							} else {
								uniqueRecords=1;
								System.out.println("Duplicate Removed");
							}
						}
					} else {
						break;
					}
				}
			}
			return employees;
		} catch (NullPointerException e) {
			e.printStackTrace();
			throw new AccredLinkAppException("Sheet data not matching");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * Loading alias names sheet to Alias Object
	 */
	private List<String> verifyAliasName(MultipartFile file) {
		try {
			StringBuilder builder = new StringBuilder();
			Map<String, String> data = new HashMap<String, String>();
			XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
			XSSFSheet sheet = workbook.getSheetAt(0);
			ArrayList<String> response = new ArrayList<String>();
			// List<String> firstNamlst = disciplineDataLoading.fetchFirstNamelist();
			for (int i = 0; i < sheet.getLastRowNum(); i++) {
				builder = new StringBuilder();
				Row row = sheet.getRow(i);
				String mapkey = null;
				if (row != null) {
					if (row.getCell(0) == null) {
						break;
					}
					Iterator<Cell> cellIterator = row.cellIterator();
					while (cellIterator.hasNext()) {
						Cell cell = cellIterator.next();
						switch (cell.getCellType()) {
						case Cell.CELL_TYPE_NUMERIC:
							break;
						case Cell.CELL_TYPE_STRING:
							if (mapkey == null) {
								mapkey = cell.getStringCellValue();
							} else {
								builder.append(cell.getStringCellValue()).append(",");
							}
							break;
						}
					}
					if (mapkey != null)
						data.put(mapkey, removeLastChar(builder.toString()));

				}
			}
			for (String name : data.keySet()) {
				Alias aliasVerfiy = disciplineDataLoading.findAllByAliasNameForAndAliasNamesGeneral(name,
						data.get(name));

				if (aliasVerfiy == null) {
					Alias alias = new Alias();
					alias.setAliasNameFor(name);
					alias.setAliasNamesGeneral(data.get(name));
					disciplineDataLoading.saveAliasData(alias);
				}

			}
			response.add("Success");
			return response;
		} catch (NullPointerException e) {
			e.printStackTrace();
			throw new AccredLinkAppException("First Name Cell Shouldn't be Empty");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * Loading dataFeed Employee sheet to DataFeedEmployee Object
	 */
	private List<?> dataFeedEmployee(MultipartFile file) {
		try {
			System.out.println("File Name:::" + file.getOriginalFilename());
			String extension = file.getOriginalFilename().split("\\.")[1];
			System.out.println("extension:::" + extension);

			if (extension.equals("xls")) {
				dataFeedXlsEmployee(file, 1);
			} else {
				readCsvFileReader(file, 2);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList<>();
	}

	@SuppressWarnings("resource")
	public void readCsvFileReader(MultipartFile file, int bgdbid) throws IOException, ParseException {
		dataFeedEmployee = new HashSet<DataFeedEmployee>();
		InputStreamReader isr = new InputStreamReader(file.getInputStream());
		// System.out.println(path.toString());
		BufferedReader reader = new BufferedReader(isr);

		BgDataBase bgDataBase = disciplineDataLoading.getBgData(1);
		CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
				.withHeader("LASTNAME", "FIRSTNAME", "MIDNAME", "DOB").withIgnoreHeaderCase().withTrim());

		for (CSVRecord csvRecord : csvParser) {
			// Accessing Values by Column Index
			String firstName = csvRecord.get(1);
			String lastName = csvRecord.get(0);
			String dob = csvRecord.get(8);
			String midName = csvRecord.get(2);
			DataFeedEmployee emp = new DataFeedEmployee();
			if (!lastName.equals("LASTNAME") || !dob.equals("DOB") || !firstName.equals("FIRSTNAME")
					|| !midName.equals("MIDNAME")) {

				if (!firstName.isEmpty()) {
					if (!dob.isEmpty()) {
						Date date1 = new SimpleDateFormat("yyyymmdd").parse(dob);
						emp.setDob(convertToLocalDateViaSqlDate(date1));
					}
					emp.setFirstName(firstName);
					emp.setLastName(lastName);
					emp.setMiddleName(midName);

					emp.setBgDateBase(bgDataBase);
					dataFeedEmployee.add(emp);
				}

			}
		}

		try {
			// Saving all feed data in to saveall method
			disciplineDataLoading.saveAllDataFeedEmployeeData(dataFeedEmployee);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void dataFeedXlsEmployee(MultipartFile file, int bgdbid) {
		dataFeedEmployee = new HashSet<DataFeedEmployee>();
		try {
			HSSFWorkbook workbook = new HSSFWorkbook(file.getInputStream());
			HSSFSheet sheet = workbook.getSheetAt(0);
			List<String> titles = new ArrayList<>();
			BgDataBase bgDataBase = disciplineDataLoading.getBgData(2);
			for (int i = 0; i < sheet.getLastRowNum(); i++) {
				Row row = sheet.getRow(i);
				DataFeedEmployee b = new DataFeedEmployee();
				for (int j = 0; j < 8; j++) {
					Cell cell = row.getCell(j);
					if (i == 0) {
						titles.add(cell.getStringCellValue());
					}
					if (cell != null) {
						switch (cell.getCellType()) {
						case Cell.CELL_TYPE_NUMERIC:
							if (HSSFDateUtil.isCellDateFormatted(cell)) {
								setResponseData(titles.get(j), null, 0,
										convertToLocalDateViaSqlDate(cell.getDateCellValue()), b, bgDataBase);
							} else {
								setResponseData(titles.get(j), null, (int) cell.getNumericCellValue(), null, b,
										bgDataBase);
							}
							break;
						case Cell.CELL_TYPE_STRING:
							if (i > 0)
								setResponseData(titles.get(j), cell.getStringCellValue(), 0, null, b, bgDataBase);
							break;
						}
					} else {
						setResponseData(titles.get(j), null, 0, null, b, bgDataBase);
					}
				}
			}
			if (dataFeedEmployee != null) {
				disciplineDataLoading.saveAllDataFeedEmployeeData(dataFeedEmployee);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static LocalDate convertToLocalDateViaSqlDate(Date dateToConvert) {
		return new java.sql.Date(dateToConvert.getTime()).toLocalDate();
	}

	private static String removeLastChar(String str) {
		return str.substring(0, str.length() - 1);
	}

	public void setResponseData(String key, String response, int numeric, LocalDate date, DataFeedEmployee b,
			BgDataBase bgDataBase) {
		try {
			key = key.toUpperCase();
			if (key.equals("LASTNAME")) {
				b.setLastName(response.replace("'", ""));
			} else if (key.equals("FIRSTNAME")) {
				b.setFirstName(response.replace("'", ""));
			} else if (key.equals("MIDINITIAL")) {
				b.setMiddleName(response.replace("'", ""));
			}
			b.setBgDateBase(bgDataBase);
			if (b.getFirstName() == null)
				dataFeedEmployee.add(b);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) throws IOException, ParseException {
		ExcelMapper m = new ExcelMapper();
		// m.readCsvFileReader("C:\\Users\\sundeep\\Desktop\\acclink\\UPDATED.csv");
	}
}
