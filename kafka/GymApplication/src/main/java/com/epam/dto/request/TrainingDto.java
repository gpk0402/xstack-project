package com.epam.dto.request;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TrainingDto {
	@NotBlank(message = "Trainee User Name is required")
	 private String traineeUsername;
	@NotBlank(message = "Trainer User Name is required")
	 private String trainerUsername;
	@NotBlank(message = "Training Name is required")
	 private String trainingName;
	@NotNull(message = "Training Date is required")
	 private LocalDate date;
	@NotBlank(message = "Training Type is required")
	 private String trainingType;
	@NotNull(message = "Training Duration is required")
	 private int duration;

}
