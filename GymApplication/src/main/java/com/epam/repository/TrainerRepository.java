package com.epam.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.epam.entity.Trainee;
import com.epam.entity.Trainer;
import com.epam.entity.Training;

public interface TrainerRepository extends JpaRepository<Trainer,Integer>{
	
	Optional<Trainer> findByUserUsername(String username);

	List<Trainer> findByTraineeListNotContaining(Trainee trainee);
	
	List<Trainer> findByIdNotIn(List<Integer> userIdsOfTrainers);
	
	 @Query("SELECT t FROM Training t "+
             "JOIN t.trainee tr "+
             "JOIN t.trainer tnr "+
             "JOIN t.trainingType tt "
             + "WHERE tnr.user.username = :username "
             + "AND (:periodFrom IS NULL OR t.date >= :periodFrom) "
             + "AND (:periodTo IS NULL OR t.date <= :periodTo) "
             + "AND (:traineeName IS NULL OR tr.user.username LIKE %:traineeName%) ")
	 
	List<Training> findTrainingsForTrainer(String username, LocalDate periodFrom, LocalDate periodTo,
			String traineeName);
}
