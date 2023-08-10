package com.epam.proxy;

import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.epam.dto.request.TrainingReportDto;
import com.epam.dto.response.ReportDto;

import jakarta.validation.Valid;

@FeignClient(name = "training-report")
@LoadBalancerClient(name="training-report")
public interface TrainingReportProxy {
	
	@PostMapping("/trainingReport")
	public ResponseEntity<Void> saveTrainingReport(@RequestBody @Valid TrainingReportDto trainingReportDto);
	
	@GetMapping("/trainingReport")
	public ResponseEntity<ReportDto> getTrainingReportByUsername(@RequestParam String username);
	

}
