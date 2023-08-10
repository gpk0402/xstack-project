package com.epam.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.dto.request.TraineeDto;
import com.epam.dto.request.TraineeTrainingsList;
import com.epam.dto.request.TraineeUpdateDto;
import com.epam.dto.response.CredentialsDto;
import com.epam.dto.response.NotificationDto;
import com.epam.dto.response.TraineeProfileDto;
import com.epam.dto.response.TrainerDetailsDto;
import com.epam.dto.response.TrainingDetailsDto;
import com.epam.entity.Trainee;
import com.epam.entity.Trainer;
import com.epam.entity.Training;
import com.epam.entity.User;
import com.epam.exception.UserException;
import com.epam.proxy.NotificationProxy;
import com.epam.repository.TraineeRepository;
import com.epam.repository.TrainerRepository;
import com.epam.repository.TrainingRepository;
import com.epam.repository.UserRepository;
import com.epam.utils.ServiceMapper;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TraineeServiceImpl implements TraineeService {

	@Autowired
	TraineeRepository traineeRepository;

	@Autowired
	TrainingRepository trainingRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	TrainerRepository trainerRepository;

	@Autowired
	ServiceMapper serviceMapper;
	
	@Autowired
	NotificationProxy notificationProxy;
	

	static final String TRAINEE_EXCEPTION = "Trainee with username not found";

	@Override
	public CredentialsDto addTrainee(TraineeDto traineeDto) {
		log.info("Entered into Create Trainee Method :{}", traineeDto);
		log.info("Creating User profile");
		User user = serviceMapper.createUserTraineeProfile(traineeDto);
		userRepository.save(user);
		log.info("Creating Trainee profile");
		Trainee trainee = Trainee.builder().address(traineeDto.getAddress()).dateOfBirth(traineeDto.getDateOfBirth())
				.user(user).build();
		traineeRepository.save(trainee);
		log.info("Retriving Trainee username and password");
		CredentialsDto credentialsDto=CredentialsDto.builder().username(user.getUsername()).password(user.getPassword()).build();
		NotificationDto dto=NotificationDto.builder().subject("Registration Successfull").toEmails(List.of(user.getEmail())).ccEmails(List.of())
				.body("Dear User your login credentials are :\n"+"Username :"+
        credentialsDto.getUsername()+"\n"+"Password :"+credentialsDto.getPassword()).build();
		notificationProxy.addNewNotificationLog(dto);
		return credentialsDto;

	}

	@Override
	public TraineeProfileDto getTraineeProfile(String username) {
		log.info("Entered into get Trainee Profile of {}", username);
		Trainee trainee = traineeRepository.findByUserUsername(username)
				.orElseThrow(() -> new UserException(TRAINEE_EXCEPTION));
		log.info("Retriving trainee profile of username :{}", username);
		return serviceMapper.getTraineeProfileDto(trainee);
	}

	@Override
	@Transactional
	public TraineeProfileDto updateTraineeProfile(TraineeUpdateDto traineeUpdateDto) {
		log.info("Entered into update Trainee Profile of {}", traineeUpdateDto);
		Trainee trainee = traineeRepository.findByUserUsername(traineeUpdateDto.getUsername())
				.orElseThrow(() -> new UserException(TRAINEE_EXCEPTION));
		User user = trainee.getUser();
		user.setEmail(traineeUpdateDto.getEmail());
		user.setFirstName(traineeUpdateDto.getFirstName());
		user.setLastName(traineeUpdateDto.getLastName());
		user.setActive(traineeUpdateDto.isActive());
		trainee.setAddress(traineeUpdateDto.getAddress());
		trainee.setDateOfBirth(traineeUpdateDto.getDateOfBirth());
		log.info("Retriving Updated trainee Profile");
		NotificationDto dto=NotificationDto.builder().subject("Trainee details updated Successfully").toEmails(List.of(user.getEmail())).ccEmails(List.of())
				.body("First Name :"+traineeUpdateDto.getFirstName()+"\n"
		        		+"Last Name : "+traineeUpdateDto.getLastName()+"\n"
		        		+"User Name :"+traineeUpdateDto.getUsername()+"\n"
		        		+"Address :"+traineeUpdateDto.getAddress()+"\n"
		        		+"Email :"+traineeUpdateDto.getEmail()+"\n"
		        		+"Status:"+traineeUpdateDto.isActive()).build();
		notificationProxy.addNewNotificationLog(dto);
		return serviceMapper.getTraineeProfileDto(trainee);
	}

	@Override
	@Transactional
	public void deleteTrainee(String userName) {
		log.info("Entered into delete trainee profile of {}", userName);
		log.info("Checking whether trainee with Username present or Not");
		Trainee trainee = traineeRepository.findByUserUsername(userName)
				.orElseThrow(() -> new UserException(TRAINEE_EXCEPTION));
		for (Trainer t : trainee.getTrainerList()) {
			t.getTraineeList().remove(trainee);
		}
		log.info("Deleting the trainee profile");
		traineeRepository.deleteById(trainee.getId());
	}

	@Override
	@Transactional
	public List<TrainerDetailsDto> updateTraineeTrainersList(String username, List<String> trainerUsernames) {
		log.info("Entered into update Trainee TrainersList of username {}", username);
		log.info("Checking whether Trainee with userName present or Not");
		Trainee trainee = traineeRepository.findByUserUsername(username)
				.orElseThrow(() -> new UserException(TRAINEE_EXCEPTION));
		List<Trainer> trainersToAdd = new ArrayList<>();
		List<Trainer> trainersToRemove = new ArrayList<>();

		for (String s : trainerUsernames) {
			Trainer trainer = trainerRepository.findByUserUsername(s)
					.orElseThrow(() -> new UserException(TRAINEE_EXCEPTION));
			if (!trainee.getTrainerList().contains(trainer)) {
				trainersToAdd.add(trainer);
			}
		}

		for (Trainer t : trainee.getTrainerList()) {
			if (!trainerUsernames.contains(t.getUser().getUsername())) {
				trainersToRemove.add(t);
			}
		}

		trainee.getTrainerList().addAll(trainersToAdd);
		trainee.getTrainerList().removeAll(trainersToRemove);

		List<Training> trainingToRemove = trainingRepository.findByTraineeAndTrainerNotIn(trainee,
				trainee.getTrainerList());
		trainingRepository.deleteAll(trainingToRemove);
		log.info("Retriving trainer profile details after updating");
		return serviceMapper.getTrainerDetailsList(trainee.getTrainerList());

	}

	@Override
	public List<TrainerDetailsDto> getNotAssignedActiveTrainers(String username) {
		log.info("Entered into getNotAssignedActiveTrainers of {}", username);
		log.info("Checking whether trainee with username present or not");
		Trainee trainee = traineeRepository.findByUserUsername(username)
				.orElseThrow(() -> new UserException(TRAINEE_EXCEPTION));
		List<Trainer> notAssignedTrainers = trainerRepository.findByTraineeListNotContaining(trainee);
		log.info("Retriving Not assigned trainers List");
		return serviceMapper.getTrainerDetailsList(notAssignedTrainers);
	}

	@Override
	public List<TrainingDetailsDto> getTraineeTrainingsList(TraineeTrainingsList traineeTrainingsList) {
		log.info("Entered into getTraineeTrainingsList");
		log.info("Checking whether trainee with username present or not");
//		Trainee trainee = traineeRepository.findByUserUsername(traineeTrainingsList.getUsername())
//				.orElseThrow(() -> new UserException(TRAINEE_EXCEPTION));
		List<Training> trainingsList = traineeRepository.findTrainingsForTrainee(traineeTrainingsList.getUsername(),
				traineeTrainingsList.getPeriodFrom(), traineeTrainingsList.getPeriodTo(),
				traineeTrainingsList.getTrainerName(), traineeTrainingsList.getTrainingType());
		log.info("Retriving training details of trainee");
		return serviceMapper.getTrainingDetailsList(trainingsList);

	}

}
