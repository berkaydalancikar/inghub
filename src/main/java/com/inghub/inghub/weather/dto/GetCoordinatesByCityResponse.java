package com.inghub.inghub.weather.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown=true)
@Getter
@Setter
public class GetCoordinatesByCityResponse {
  private int cod;
  private Coordinates coord;
}
