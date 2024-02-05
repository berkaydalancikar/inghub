package com.inghub.inghub;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import com.inghub.inghub.dto.ApiException;
import com.inghub.inghub.dto.GetWeatherInfoByCityNameRequest;
import com.inghub.inghub.weather.dto.WeatherInfo;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;


@RestController
@RequestMapping("/api")
@Validated
public class ApplicationController {
  @Autowired
  ApplicationService applicationService;

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException ex) {
    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult().getAllErrors().forEach((error) -> {
        String fieldName = ((FieldError) error).getField();
        String errorMessage = error.getDefaultMessage();
        errors.put(fieldName, errorMessage);
    });
    return ResponseEntity.badRequest().body(errors);
}

    @Operation(summary = "Get Weather Info By City Name", description = "Retrieve weather information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = WeatherInfo.class),
                    examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                            name = "example",
                            value = "[{\"feels_like\": 283.2, \"humidity\": 77.0, \"temp\": 292.01 }]"
                    )
            )),
    })
  @PostMapping("/get-weather-info-by-city-name")
  public ResponseEntity<?> getWeatherInfoByCityName(
    @Valid
    @RequestBody
    GetWeatherInfoByCityNameRequest request) {
    try {
      return ResponseEntity.ok(
        applicationService.getWeatherInfoByCityName(request.getCityName(), request.getHours())
      );
    } catch(ApiException exception) {
      return ResponseEntity.status(HttpStatus.OK).body(exception.getMessage());
    }
  }
}
