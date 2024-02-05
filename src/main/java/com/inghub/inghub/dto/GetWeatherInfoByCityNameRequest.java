package com.inghub.inghub.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class GetWeatherInfoByCityNameRequest {
  @NotBlank()
  private String cityName;

  @NotNull()
  @Positive()
  private Integer hours;
}
