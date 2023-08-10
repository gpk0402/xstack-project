package com.epam.kafkaproducer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.epam.dto.request.TrainingReportDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReportProducer {
	
	@Autowired
	private final KafkaTemplate<String, TrainingReportDto> kafkaTemplate;
	
	public void sendGymReport(TrainingReportDto dto)
	{
		kafkaTemplate.send("report-topic",dto);
	}

}
