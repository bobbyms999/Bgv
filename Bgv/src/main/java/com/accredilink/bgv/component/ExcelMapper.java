package com.accredilink.bgv.component;

import java.io.IOException;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.accredilink.bgv.entity.Address;
import com.accredilink.bgv.entity.Alias;
import com.accredilink.bgv.entity.Discipline;
import com.accredilink.bgv.entity.Employee;
import com.accredilink.bgv.exception.AccredLinkAppException;

@Component
public class ExcelMapper {

	@Autowired
	DisciplineDataLoading disciplineDataLoading;

	public List<?> mapToObject(MultipartFile file, Class<?> classType) {

		if (classType == Employee.class) {
			return loadEmployeeData(file);
		} else if (classType == Alias.class) {
			return verifyAliasName(file);
		}
		return null;
	}

	/*
	 * Loading excel sheet data to employee object
	 */
	private List<?> loadEmployeeData(MultipartFile file) {
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
					} else {
						break;
					}
					if (row.getCell(1) != null) {
						employee.setMiddleName(row.getCell(1).getStringCellValue());
					}
					
					employee.setLastName(row.getCell(2).getStringCellValue());
					if (row.getCell(3) != null) {
						employee.setMaidenName(row.getCell(3).getStringCellValue());
					}
					employee.setSsnNumber(row.getCell(9).getStringCellValue());
					employee.setDateOfBirth((row.getCell(10).getDateCellValue().toInstant()
							.atZone(ZoneId.systemDefault()).toLocalDate()));
					employee.setActive("S");
					employee.setCreatedBy(row.getCell(0).getStringCellValue());
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
				}
				employee.setEmployeeAgency(null);
				employees.add(employee);
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
				Alias alias = new Alias();
				alias.setAliasNameFor(name);
				alias.setAliasNamesGeneral(data.get(name));
				disciplineDataLoading.saveAliasData(alias);
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

	private static String removeLastChar(String str) {
		return str.substring(0, str.length() - 1);
	}
}
