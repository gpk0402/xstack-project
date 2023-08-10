package com.epam.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.dto.request.TrainerDto;
import com.epam.dto.request.TrainerTrainingsList;
import com.epam.dto.request.TrainerUpdateDto;
import com.epam.dto.response.CredentialsDto;
import com.epam.dto.response.NotificationDto;
import com.epam.dto.response.TrainerProfileDto;
import com.epam.dto.response.TrainingDetailsDto;
import com.epam.entity.Trainer;
import com.epam.entity.Training;
import com.epam.entity.TrainingType;
import com.epam.entity.User;
import com.epam.exception.UserException;
import com.epam.proxy.NotificationProxy;
import com.epam.repository.TrainerRepository;
import com.epam.repository.TrainingTypeRepository;
import com.epam.repository.UserRepository;
import com.epam.utils.ServiceMapper;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TrainerServiceImpl implements TrainerService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	TrainerRepository trainerRepository;

	@Autowired
	TrainingTypeRepository trainingTypeRepository;

	@Autowired
	ServiceMapper serviceMapper;
	
	@Autowired
	NotificationProxy notificationProxy;
	
	static final String TRAINER_EXCEPTION = "Trainer with username not found";

	@Override
	public CredentialsDto addTrainer(TrainerDto trainerDto) {
		log.info("Entered into Create Trainer Method :{}",trainerDto);
		log.info("Creating User profile");
		User user = serviceMapper.createUserTrainerProfile(trainerDto);
		userRepository.save(user);
		TrainingType trainingType=trainingTypeRepository.findByTrainingTypeName(trainerDto.getSpecialization()).orElseGet(()->
			TrainingType.builder().trainingTypeName(trainerDto.getSpecialization()).build());
		log.info("Creating Trainer profile");
		Trainer trainer = Trainer.builder().trainingType(trainingType).user(user).build();
		trainerRepository.save(trainer);
		log.info("Retriving Trainee username and password");
		CredentialsDto credentialsDto=CredentialsDto.builder().username(user.getUsername()).password(user.getPassword()).build();
		NotificationDto dto=NotificationDto.builder().subject("Registration Successfull").toEmails(List.of(user.getEmail())).ccEmails(List.of())
				.body("Dear User your login credentials are :\n"+"Username :"+
        credentialsDto.getUsername()+"\n"+"Password :"+credentialsDto.getPassword()).build();
		notificationProxy.addNewNotificationLog(dto);
		return credentialsDto;
	}

	@Override
	public TrainerProfileDto getTrainerProfile(String username) {
		log.info("Entered into get Trainer Profile of {}",username);
		log.info("Checking whether Trainer with username present or not");
		Trainer trainer = trainerRepository.findByUserUsername(username)
				.orElseThrow(() -> new UserException(TRAINER_EXCEPTION));
		log.info("Retriving trainer profile of username :{}",username);
		return serviceMapper.getTrainerProfile(trainer);
	}

	@Override
	@Transactional
	public TrainerProfileDto updateTrainerProfile(TrainerUpdateDto updateDto) {
		log.info("Entered into update Trainer Profile of {}",updateDto);
		log.info("Checking whether Trainer with userName present or not");
		Trainer trainer = trainerRepository.findByUserUsername(updateDto.getUsername())
				.orElseThrow(() -> new UserException(TRAINER_EXCEPTION));
		User user = trainer.getUser();
		log.info("Updating user profile");
		user.setActive(true);
		user.setEmail(updateDto.getEmail());
		user.setFirstName(updateDto.getFirstName());
		user.setLastName(updateDto.getLastName());
		log.info("Updating trainer profile");
		updateDto.setSpecialization(trainer.getTrainingType().getTrainingTypeName());
		trainer.setUser(user);
		log.info("Retriving Updated trainee Profile");
		NotificationDto dto=NotificationDto.builder().subject("Trainer details Updated Successfully").toEmails(List.of(user.getEmail())).ccEmails(List.of())
				.body("First Name :"+updateDto.getFirstName()+"\n"
		        		+"Last Name : "+updateDto.getLastName()+"\n"
		        		+"User Name :"+updateDto.getUsername()+"\n"
		        		+"Specialization :"+updateDto.getSpecialization()+"\n"
		        		+"Email :"+updateDto.getEmail()+"\n"
		        		+"Status:"+updateDto.isActive()).build();
		notificationProxy.addNewNotificationLog(dto);
		return serviceMapper.getTrainerProfile(trainer);
	}

	@Override
	public List<TrainingDetailsDto> getTrainerTrainingsList(TrainerTrainingsList trainerTrainingsList) {
		log.info("Entered into getTrainerTrainingsList");
		log.info("Checking whether trainer with username present or not");
//		Trainer trainer=trainerRepository.findByUserUsername(trainerTrainingsList.getUsername()).orElseThrow(()->new UserException(TRAINER_EXCEPTION));
		List<Training> trainingsList = trainerRepository.findTrainingsForTrainer(trainerTrainingsList.getUsername(),
				trainerTrainingsList.getPeriodFrom(), trainerTrainingsList.getPeriodTo(),
				trainerTrainingsList.getTraineeName());
		log.info("Retriving training details of trainee");
		return serviceMapper.getTrainingDetailsList(trainingsList);

	}

}
