package com.epam.dto.response;

import java.util.List;

import com.epam.dto.request.TrainingDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrainingResponse {
    private List<String> emails;
    private TrainingDto trainingDto;

}
