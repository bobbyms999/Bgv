package com.accredilink.bgv.component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.accredilink.bgv.entity.Alias;
import com.accredilink.bgv.entity.DataFeedEmployee;
import com.accredilink.bgv.entity.Discipline;
import com.accredilink.bgv.repository.AliasRepository;
import com.accredilink.bgv.repository.DataFeedEmployeeRepo;
import com.accredilink.bgv.repository.DisciplineRepository;
import com.accredilink.bgv.repository.EmployeeRepository;

@Component
public class DisciplineDataLoading {

	@Autowired
	private DisciplineRepository disciplineRepository;

	@Autowired
	EmployeeRepository employeeRepository;

	@Autowired
	AliasRepository aliasRepository;
	
	@Autowired
	DataFeedEmployeeRepo dataFeedEmployeeRepo;

	private final Map<String, Integer> dataMap = new HashMap<String, Integer>();
	private List<Discipline> disciplines;

	/*
	 * While startup of the application loading the data
	 */
	@PostConstruct
	public void init() {
		// loading discipline table data
		disciplines = disciplineRepository.findAll();
		disciplines.forEach(discipline -> {
			dataMap.put(discipline.getDisciplineValue(), discipline.getDisciplineId());
		});
	}

	public Map<String, Integer> getData() {
		return dataMap;
	}

	public List<Discipline> fetchAllDisciplines() {
		return disciplines;
	}

	public void saveAliasData(Alias alias) {
		aliasRepository.save(alias);
	}
	
	public void saveDataFeedEmployeeData(DataFeedEmployee dataFeedEmployee) {
		dataFeedEmployeeRepo.save(dataFeedEmployee);
	}
}
