package mattia.archeometra.co2tracker.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import mattia.archeometra.co2tracker.dto.Co2ReadingDTO;
import mattia.archeometra.co2tracker.dto.ResponseDTO;
import mattia.archeometra.co2tracker.exception.CityNotFoundException;
import mattia.archeometra.co2tracker.exception.DistrictNameNotFoundException;
import mattia.archeometra.co2tracker.exception.TrackerInternalServerError;
import mattia.archeometra.co2tracker.service.Co2TrackerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("rest/api/v0/sensor-readings")
public class Co2TrackerController {

    @Autowired
    private Co2TrackerService trackerService;

    @PostMapping(value = "/newReading", produces = "application/json")
    @Operation(summary = "Create new CO2 reading")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Tracking creation successful"),
            @ApiResponse(responseCode = "400", description = "District or City not found"),
            @ApiResponse(responseCode = "500", description = "Unexpected internal error!")
    })
    public ResponseEntity<ResponseDTO> createReading( @RequestBody Co2ReadingDTO trackerDTO) throws DistrictNameNotFoundException, CityNotFoundException, TrackerInternalServerError {

        log.info("Starting new reading creation");
        StopWatch stopWatch = new StopWatch("createReading");
        stopWatch.start();


        String cityName = getCityNameFromRole();

        Co2ReadingDTO savedReading = trackerService.createReading(trackerDTO, cityName);

        stopWatch.stop();
        log.info("End of new reading creation in: {} ms", stopWatch.getTotalTimeMillis());


        return new ResponseEntity<>(
                ResponseDTO.builder()
                        .timestamp(LocalDateTime.now())
                        .data(Map.of("New Saved Reading", savedReading))
                        .message(ResponseDTO.CustomStatusCode.TRACKER_CREATION_OK.statusMessage())
                        .status(ResponseDTO.CustomStatusCode.TRACKER_CREATION_OK.httpValue())
                        .statusCode(ResponseDTO.CustomStatusCode.TRACKER_CREATION_OK.statusCode())
                        .build(),
                ResponseDTO.CustomStatusCode.TRACKER_CREATION_OK.httpValue()
        );
    }

    @GetMapping(value = "/district/{districtName}", produces = "application/json")
    @Operation(summary = "Get CO2 readings by district name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reading retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "District or City not found"),
            @ApiResponse(responseCode = "500", description = "Unexpected internal error!")
    })
    public ResponseEntity<ResponseDTO> getReadings(
            @Parameter(description = "Name of the district", example = "Eixample")
            @PathVariable String districtName
    ) throws DistrictNameNotFoundException, CityNotFoundException {

        log.info("Starting reading retrieval ");
        StopWatch stopWatch = new StopWatch("getReadings");
        stopWatch.start();

        String cityName = getCityNameFromRole();

        List<Co2ReadingDTO> co2ReadingList = trackerService.getReadings(districtName, cityName);

        stopWatch.stop();
        log.info("End of readings retrieval in: {} ms", stopWatch.getTotalTimeMillis());

        return new ResponseEntity<>(
                ResponseDTO.builder()
                        .timestamp(LocalDateTime.now())
                        .data(Map.of("District Reading list", co2ReadingList))
                        .message(ResponseDTO.CustomStatusCode.READING_RETRIEVE_OK.statusMessage())
                        .status(ResponseDTO.CustomStatusCode.READING_RETRIEVE_OK.httpValue())
                        .statusCode(ResponseDTO.CustomStatusCode.READING_RETRIEVE_OK.statusCode())
                        .build(),
                ResponseDTO.CustomStatusCode.READING_RETRIEVE_OK.httpValue()
        );
    }

    private String getCityNameFromRole() {

        //Estraggo il ruolo dal SecurityContext per conoscere la città
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String role = authentication.getAuthorities().iterator().next().getAuthority();

        //Rimuovo il prefisso ROLE_ e mi assicuro che sia tutto in maiuscolo per combaciare con i nomi su DB
        return role.substring(5).toUpperCase();

    }

}
