package mattia.archeometra.co2tracker.exception.handler;


import lombok.extern.slf4j.Slf4j;
import mattia.archeometra.co2tracker.dto.ResponseDTO;
import mattia.archeometra.co2tracker.exception.CityNotFoundException;
import mattia.archeometra.co2tracker.exception.DistrictNameNotFoundException;
import mattia.archeometra.co2tracker.exception.TrackerInternalServerError;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.time.LocalDateTime;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class ExceptionHandler {

    public ExceptionHandler(){
        super();
    }

    /*
        Tutte le eccezioni custom vengono gestite dall'exception handler che si occupa di valorizzare
        adeguatamente il DTO di risposta e restituirlo in una ResponseEntity
     */


    @org.springframework.web.bind.annotation.ExceptionHandler({DistrictNameNotFoundException.class})
    public ResponseEntity<ResponseDTO> handleDistrictNameNotFoundException(final DistrictNameNotFoundException ex){

        return new ResponseEntity<>(
                ResponseDTO.builder()
                        .timestamp(LocalDateTime.now())
                        .data(Map.of("DistrictNameNotFoundException", ex))
                        .message(ResponseDTO.CustomStatusCode.DISTRICT_NAME_NOT_FOUND.statusMessage())
                        .status(ResponseDTO.CustomStatusCode.DISTRICT_NAME_NOT_FOUND.httpValue())
                        .statusCode(ResponseDTO.CustomStatusCode.DISTRICT_NAME_NOT_FOUND.statusCode())
                        .build(),
                ResponseDTO.CustomStatusCode.DISTRICT_NAME_NOT_FOUND.httpValue()
        );

    }

    @org.springframework.web.bind.annotation.ExceptionHandler({CityNotFoundException.class})
    public ResponseEntity<ResponseDTO> handleCityNotFoundException(final CityNotFoundException ex){

        return new ResponseEntity<>(
                ResponseDTO.builder()
                        .timestamp(LocalDateTime.now())
                        .data(Map.of("CityNotFoundException", ex))
                        .message(ResponseDTO.CustomStatusCode.CITY_NAME_NOT_FOUND.statusMessage())
                        .status(ResponseDTO.CustomStatusCode.CITY_NAME_NOT_FOUND.httpValue())
                        .statusCode(ResponseDTO.CustomStatusCode.CITY_NAME_NOT_FOUND.statusCode())
                        .build(),
                ResponseDTO.CustomStatusCode.CITY_NAME_NOT_FOUND.httpValue()
        );

    }

    @org.springframework.web.bind.annotation.ExceptionHandler({TrackerInternalServerError.class})
    public ResponseEntity<ResponseDTO> handleTrackerInternalServerError(final TrackerInternalServerError ex){

        return new ResponseEntity<>(
                ResponseDTO.builder()
                        .timestamp(LocalDateTime.now())
                        .data(Map.of("Internal Server Error", ex))
                        .message(ResponseDTO.CustomStatusCode.TRACKER_INTERNAL_SERVER_ERROR.statusMessage())
                        .status(ResponseDTO.CustomStatusCode.TRACKER_INTERNAL_SERVER_ERROR.httpValue())
                        .statusCode(ResponseDTO.CustomStatusCode.TRACKER_INTERNAL_SERVER_ERROR.statusCode())
                        .build(),
                ResponseDTO.CustomStatusCode.TRACKER_INTERNAL_SERVER_ERROR.httpValue()
        );

    }


}
