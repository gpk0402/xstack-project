package com.epam.dto.request;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
@Data
public class TraineeTrainingsListUpdate {
	@NotBlank(message = "Trainee UserName is required")
	private String traineeUsername;
	@Size(min = 1,message = "Trainers list shouldnt be empty")
	private List<String> trainersUsernames;
}
