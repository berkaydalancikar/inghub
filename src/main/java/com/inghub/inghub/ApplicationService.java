package com.inghub.inghub;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.inghub.inghub.dto.ApiException;
import com.inghub.inghub.weather.WeatherService;
import com.inghub.inghub.weather.dto.Coordinates;
import com.inghub.inghub.weather.dto.WeatherInfo;

@Service
public class ApplicationService {
  @Autowired
  WeatherService webClientService;

  public WeatherInfo[] getWeatherInfoByCityName(String cityName, int hours) {
    try {
      Coordinates cityCoordinates = webClientService.getCoordinatesByCityName(cityName);

      return webClientService.getWeatherInfoByCoordinates(cityCoordinates, hours);
    } catch(ApiException exception) {
      throw exception;
    } catch(Exception exception) {
      throw new ApiException(HttpStatus.OK, "Error");
    }
  }
}
