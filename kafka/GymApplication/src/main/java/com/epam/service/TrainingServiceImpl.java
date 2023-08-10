package com.epam.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.dto.request.TrainingDto;
import com.epam.dto.request.TrainingReportDto;
import com.epam.dto.response.NotificationDto;
import com.epam.entity.Trainee;
import com.epam.entity.Trainer;
import com.epam.entity.Training;
import com.epam.exception.UserException;
import com.epam.kafkaproducer.NotificationProducer;
import com.epam.repository.TraineeRepository;
import com.epam.repository.TrainerRepository;
import com.epam.repository.TrainingRepository;
import com.epam.repository.TrainingTypeRepository;
import com.epam.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TrainingServiceImpl implements TrainingService {

	@Autowired
	TraineeRepository traineeRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	TrainerRepository trainerRepository;

	@Autowired
	TrainingRepository trainingRepository;

	@Autowired
	TrainingTypeRepository trainingTypeRepository;
	
	@Autowired
	NotificationProducer notificationProducer;
	

	static final String TRAINEE_EXCEPTION = "Trainee with username not found";

	static final String TRAINER_EXCEPTION = "Trainer with username not found";

	static final String TRAINING_EXCEPTION = "Training cannot be created as trainee or trainer is not associated with each other";

	@Override
	public TrainingReportDto addTraining(TrainingDto trainingDto) {
		log.info("Entered into add Training method {}", trainingDto);
		Trainee trainee = traineeRepository.findByUserUsername(trainingDto.getTraineeUsername())
				.orElseThrow(() -> new UserException(TRAINEE_EXCEPTION));
		Trainer trainer = trainerRepository.findByUserUsername(trainingDto.getTrainerUsername())
				.orElseThrow(() -> new UserException(TRAINER_EXCEPTION));
		if (!trainee.getTrainerList().contains(trainer)
				|| !trainingDto.getTrainingType().equals(trainer.getTrainingType().getTrainingTypeName())) {
			throw new UserException(TRAINING_EXCEPTION);
		}
		log.info("creating training profile");
		Training training = Training.builder().date(trainingDto.getDate()).duration(trainingDto.getDuration())
				.trainingName(trainingDto.getTrainingName()).trainee(trainee).trainer(trainer)
				.trainingType(trainer.getTrainingType()).build();

		TrainingReportDto reportDto = TrainingReportDto.builder().date(trainingDto.getDate())
				.duration(trainingDto.getDuration()).trainerFirstName(trainer.getUser().getFirstName())
				.trainerLastName(trainer.getUser().getLastName()).trainerUsername(trainer.getUser().getUsername())
				.trainerStatus(trainer.getUser().isActive()).trainerEmail(trainer.getUser().getEmail()).build();
		
		NotificationDto dto=NotificationDto.builder().subject("Training added Successfully").toEmails(List.of(trainee.getUser().getEmail(),trainer.getUser().getEmail())).ccEmails(List.of())
				.body("Training Details are :\n"+
		        		"Trainee Name :"+trainingDto.getTraineeUsername()+"\n"
		        		+"Trainer Name :"+trainingDto.getTrainerUsername()+"\n"
		        		+"Training Name :"+trainingDto.getTrainingName()+"\n"
		        		+"Training Type :"+trainingDto.getTrainingName()+"\n"
		        		+"Training Date :"+trainingDto.getDate()+"\n"
		        		+"Training Duration :"+trainingDto.getDuration()).build();
		notificationProducer.sendNotification(dto);
		trainingRepository.save(training);
		return reportDto;
		

	}

}
