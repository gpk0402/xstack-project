package com.epam.kafkaconsumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.epam.dto.TrainingReportDto;
import com.epam.service.TrainingReportService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ReportConsumer {
	
	@Autowired
	TrainingReportService trainingReportService;
	
	@KafkaListener(topics = "report-topic", groupId = "gym-report")
	public void saveTrainingReport(TrainingReportDto trainingReportDto) {
		log.info("Consumed Training Report: ", trainingReportDto);
		trainingReportService.saveTrainingReport(trainingReportDto);
	}

}
