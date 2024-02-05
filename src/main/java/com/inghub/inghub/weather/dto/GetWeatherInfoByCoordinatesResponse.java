package com.inghub.inghub.weather.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown=true)
@Getter
@Setter
public class GetWeatherInfoByCoordinatesResponse {
  private List<WeatherInfo> hourly;
}
