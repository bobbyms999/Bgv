package com.accredilink.bgv.component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.accredilink.bgv.entity.Discipline;
import com.accredilink.bgv.repository.DisciplineRepository;

@Component
public class DisciplineDataLoading {

	@Autowired
	private DisciplineRepository disciplineRepository;

	private final Map<String, Integer> dataMap = new HashMap<String, Integer>();
	private List<Discipline> disciplines;

	@PostConstruct
	public void init() {
		disciplines = disciplineRepository.findAll();
		disciplines.forEach(discipline -> {
			dataMap.put(discipline.getDisciplineValue(), discipline.getDisciplineId());
		});
		System.out.println(dataMap);
	}

	public Map<String, Integer> getData() {
		return dataMap;
	}
}
