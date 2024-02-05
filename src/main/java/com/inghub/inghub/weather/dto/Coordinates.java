package com.inghub.inghub.weather.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Coordinates {
  private double lat;
  private double lon;
}
