package com.epam.dto;

import java.time.LocalDate;

import com.epam.dto.TrainingReportDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrainingReportDto {
	private String trainerUsername;
	private String trainerFirstName;
	private String trainerLastName;
	private boolean trainerStatus;
	private String trainerEmail;
	private int duration;
	private LocalDate date;
}
