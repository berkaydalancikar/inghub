package com.inghub.inghub.weather;

import java.util.Arrays;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.inghub.inghub.dto.ApiException;
import com.inghub.inghub.weather.dto.Coordinates;
import com.inghub.inghub.weather.dto.GetCoordinatesByCityResponse;
import com.inghub.inghub.weather.dto.GetWeatherInfoByCoordinatesResponse;
import com.inghub.inghub.weather.dto.WeatherInfo;

import io.github.cdimascio.dotenv.Dotenv;

@Service
public class WeatherService {
  private final WebClient webClient;
  private final Dotenv dotenv = Dotenv.load();

  @SuppressWarnings("null")
  public WeatherService(WebClient.Builder webClientBuilder) {
    this.webClient = webClientBuilder
    .baseUrl(dotenv.get("OPENWEATHERMAP_BASE_URL"))
    .build();
  }

  public Coordinates getCoordinatesByCityName(String cityName) {
    try {
      GetCoordinatesByCityResponse response = this.webClient
      .get()
      .uri("/2.5/weather?q={cityName}&appid={apiKey}", cityName, dotenv.get("OPENWEATHERMAP_API_KEY")
      )
      .retrieve()
      .bodyToMono(GetCoordinatesByCityResponse.class)
      .block();

      return response.getCoord();
    } catch(WebClientResponseException exception) {
      throw new ApiException(
        HttpStatus.OK,
        exception.getResponseBodyAsString()
      );
    } catch(Exception exception) {
      throw new ApiException(HttpStatus.OK, "Error");
    }
  }

  public WeatherInfo[] getWeatherInfoByCoordinates(Coordinates coordinates, int hours) {
    try {
      GetWeatherInfoByCoordinatesResponse response = this.webClient
        .get()
        .uri("/3.0/onecall?lat={lat}&lon={lon}&appid={apiKey}",
          coordinates.getLat(),
          coordinates.getLon(),
          dotenv.get("OPENWEATHERMAP_API_KEY")
        )
        .retrieve()
        .bodyToMono(GetWeatherInfoByCoordinatesResponse.class)
        .block();

      WeatherInfo[] array = response.getHourly().toArray(new WeatherInfo[0]);
      return Arrays.copyOfRange(array, 0, Math.min(hours, array.length));

    } catch(WebClientResponseException exception) {
      throw new ApiException(
        HttpStatus.OK,
        exception.getResponseBodyAsString()
      );
    } catch(Exception exception) {
      throw new ApiException(HttpStatus.OK, "Error");
    }
  }
}
