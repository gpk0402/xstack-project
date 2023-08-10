package com.epam.model;

import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
@Document(collection = "TrainingReport")
public class TrainingReport {
	@Id
	private String username;
	private String firstName;
	private String lastName;
	private boolean status;
	private String email;
	private Map<Long,Map<Long,Map<Long,Map<String,Long>>>> trainingSummary;
				
}
