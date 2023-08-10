package com.epam.utils;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.dto.request.TraineeDto;
import com.epam.dto.request.TrainerDto;
import com.epam.dto.response.TraineeDetailsDto;
import com.epam.dto.response.TraineeProfileDto;
import com.epam.dto.response.TrainerDetailsDto;
import com.epam.dto.response.TrainerProfileDto;
import com.epam.dto.response.TrainingDetailsDto;
import com.epam.entity.Trainee;
import com.epam.entity.Trainer;
import com.epam.entity.Training;
import com.epam.entity.User;

@Service
public class ServiceMapper {

	@Autowired
	UsernameGenerator usernameGenerator;

	@Autowired
	PasswordGenerator passwordGenerator;

	public User createUserTraineeProfile(TraineeDto traineeDto) {
		String username = usernameGenerator.generateUsername(traineeDto.getFirstName(), traineeDto.getLastName());
		String password = passwordGenerator.generatePassword();
		return User.builder().firstName(traineeDto.getFirstName()).lastName(traineeDto.getLastName()).username(username)
				.password(password).email(traineeDto.getEmail()).createdDate(LocalDate.now()).isActive(true).build();
	}
	
	public User createUserTrainerProfile(TrainerDto trainerDto) {
		String username = usernameGenerator.generateUsername(trainerDto.getFirstName(), trainerDto.getLastName());
		String password = passwordGenerator.generatePassword();
		return User.builder().firstName(trainerDto.getFirstName()).lastName(trainerDto.getLastName()).username(username)
				.password(password).email(trainerDto.getEmail()).createdDate(LocalDate.now()).isActive(true).build();
	}

	public TraineeProfileDto getTraineeProfileDto(Trainee trainee) {
		return TraineeProfileDto.builder().userName(trainee.getUser().getUsername())
				.firstName(trainee.getUser().getFirstName()).lastName(trainee.getUser().getLastName())
				.address(trainee.getAddress()).dateOfBirth(trainee.getDateOfBirth()).isActive(true)
				.trainersList(getTrainerDetailsList(trainee.getTrainerList())).build();
	}
	
	public TrainerProfileDto getTrainerProfile(Trainer trainer) {
		User user=trainer.getUser();
		return TrainerProfileDto.builder().userName(user.getUsername()).firstName(user.getFirstName())
				.lastName(user.getLastName()).specialization(trainer.getTrainingType().getTrainingTypeName())
				.isActive(true).traineesList(getTraineeList(trainer.getTraineeList())).build();
	}

	public List<TrainerDetailsDto> getTrainerDetailsList(List<Trainer> trainerList) {
		return trainerList.stream().map(t ->
			TrainerDetailsDto.builder().userName(t.getUser().getUsername()).firstName(t.getUser().getFirstName())
					.lastName(t.getUser().getLastName()).specialization(t.getTrainingType().getTrainingTypeName())
					.build()
		).toList();
	}
	public List<TraineeDetailsDto> getTraineeList(List<Trainee> traineeList) {
		return traineeList.stream().map(t ->
			 TraineeDetailsDto.builder().userName(t.getUser().getUsername())
					.firstName(t.getUser().getFirstName()).lastName(t.getUser().getLastName()).build()
		).toList();
	}

	public List<TrainingDetailsDto> getTrainingDetailsList(List<Training> trainingsList) {
		return trainingsList.stream().map(t ->
			TrainingDetailsDto.builder().date(t.getDate()).duration(t.getDuration())
					.trainerName(t.getTrainer().getUser().getUsername()).trainingName(t.getTrainingName())
					.traineeName(t.getTrainee().getUser().getUsername())
					.trainingType(t.getTrainingType().getTrainingTypeName()).build()
		).toList();
	}

}
