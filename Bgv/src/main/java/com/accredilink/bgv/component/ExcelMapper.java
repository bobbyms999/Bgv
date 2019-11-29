package com.accredilink.bgv.component;

import java.io.IOException;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.accredilink.bgv.entity.Address;
import com.accredilink.bgv.entity.Alias;
import com.accredilink.bgv.entity.Discipline;
import com.accredilink.bgv.entity.Employee;
import com.accredilink.bgv.util.StringUtils;

@Component
public class ExcelMapper {

	@Autowired
	DisciplineDataLoading disciplineDataLoading;

	public List<?> mapToObject(MultipartFile file, Class<?> classType) {

		if (classType == Employee.class) {
			return loadEmployeeData(file);
		} else if (classType == Alias.class) {

		} else {
			return null;
		}
		return null;
	}

	private List<?> loadEmployeeData(MultipartFile file) {
		Map<String, Integer> dataMap = disciplineDataLoading.getData();

		try {
			XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
			XSSFSheet sheet = workbook.getSheetAt(0);

			List<Employee> employees = new ArrayList<Employee>();
			Address address = new Address();
			Alias alias = new Alias();
			Discipline discipline = new Discipline();
			Employee employee;
			int count = 0;

			
			for(Cell row : sheet.getRow(0)) {
				System.out.println(row+"--");
			}
			
			
			for (int i = 0; i < sheet.getLastRowNum(); i++) {
				Row row = sheet.getRow(i);
				employee = new Employee();
				
				if (row != null) {
					Iterator<Cell> cells = row.iterator();
					while (cells.hasNext()) {
						Cell cell = cells.next();
						switch (cell.getCellType()) {
						case Cell.CELL_TYPE_NUMERIC: {

							if (HSSFDateUtil.isCellDateFormatted(cell)) {
								employee.setDateOfBirth(cell.getDateCellValue().toInstant()
										.atZone(ZoneId.systemDefault()).toLocalDate());
								break;
							} else if (address.getZip() != 0) {
								address.setZip((int) cell.getNumericCellValue());
								break;
							}
						}
						case Cell.CELL_TYPE_STRING: {
							System.out.println(cell.getStringCellValue());
							if (StringUtils.isNullOrEmpty(employee.getFirstName())
									&& !StringUtils.isNullOrEmpty(cell.getStringCellValue())) {
								employee.setFirstName(cell.getStringCellValue());
								break;
							}
							if (StringUtils.isNullOrEmpty(employee.getMiddleName())
									&& !StringUtils.isNullOrEmpty(cell.getStringCellValue())) {
								employee.setMiddleName(cell.getStringCellValue());
								break;
							}
							if (StringUtils.isNullOrEmpty(employee.getLastName())
									&& !StringUtils.isNullOrEmpty(cell.getStringCellValue())) {
								employee.setLastName(cell.getStringCellValue());
								break;
							}

							if (StringUtils.isNullOrEmpty(alias.getAliasName())
									&& !StringUtils.isNullOrEmpty(cell.getStringCellValue())) {
								alias.setAliasName(cell.getStringCellValue());
								break;
							}

							if (StringUtils.isNullOrEmpty(address.getAddressLine1())
									&& !StringUtils.isNullOrEmpty(cell.getStringCellValue())) {
								address.setAddressLine1(cell.getStringCellValue());
								break;
							}

							if (StringUtils.isNullOrEmpty(address.getCity())
									&& !StringUtils.isNullOrEmpty(cell.getStringCellValue())) {
								address.setCity(cell.getStringCellValue());
								break;
							}

							if (StringUtils.isNullOrEmpty(address.getState())
									&& !StringUtils.isNullOrEmpty(cell.getStringCellValue())) {
								address.setState(cell.getStringCellValue());
								break;
							}

							if (StringUtils.isNullOrEmpty(employee.getSsnNumber())
									&& !StringUtils.isNullOrEmpty(cell.getStringCellValue())) {
								employee.setSsnNumber(cell.getStringCellValue());
								break;
							}

							if (StringUtils.isNullOrEmpty(discipline.getDisciplineValue())
									&& !StringUtils.isNullOrEmpty(cell.getStringCellValue())) {
								dataMap.forEach((k, v) -> {
									if (cell.getStringCellValue().trim().equals(k)) {
										discipline.setDisciplineValue(k);
									}
								});
								break;
							}
						}
						case Cell.CELL_TYPE_BLANK:{
							System.out.println("came");
							break;
						}
						default:{
							System.out.println("default");
						}
						}
					}
					employee.setAlias(alias);
					employee.setAddress(address);
					employee.setDiscipline(discipline);
					employee.setAgency(null);
					employees.add(employee);
				} else {
					if (count <= 2) {
						System.out.println(i);
						break;
					}
					count++;
				}
			}
			return employees;
		} catch (IOException ioe) {
			ioe.printStackTrace();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
