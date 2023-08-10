package com.epam.restapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.epam.dto.request.TrainingDto;
import com.epam.dto.request.TrainingReportDto;
import com.epam.dto.response.ReportDto;
import com.epam.proxy.TrainingReportProxy;
import com.epam.service.TrainingServiceImpl;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("training")
@Slf4j
public class TrainingController {
	
	@Autowired
	TrainingServiceImpl trainingServiceImpl;
	
	@Autowired
	TrainingReportProxy reportProxy;
	
	@PostMapping
	@ResponseStatus(code = HttpStatus.OK)
	public ResponseEntity<Void> addTraining(@Valid @RequestBody TrainingDto trainingDto)
	{
		log.info("Entered into add Training Method RestController :{}",trainingDto);
		TrainingReportDto reportDto= trainingServiceImpl.addTraining(trainingDto);
		return reportProxy.saveTrainingReport(reportDto);
	}
	
	@GetMapping("/trainerReport")
	public ResponseEntity<ReportDto> getTrainingReportByUsername(@RequestParam String username) throws Exception
	{
		return new ResponseEntity<>(reportProxy.getTrainingReportByUsername(username).getBody(),HttpStatus.OK);
	}
	
	

}
