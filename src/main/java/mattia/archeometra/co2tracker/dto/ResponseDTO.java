package mattia.archeometra.co2tracker.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDTO {

    protected LocalDateTime timestamp;
    protected int statusCode;
    protected HttpStatus status;
    protected String reason;
    protected String message;
    protected String devMessage;
    protected Map<?,?> data;

    public enum CustomStatusCode {
        DISTRICT_NAME_NOT_FOUND("District name not found", HttpStatus.BAD_REQUEST),
        CITY_NAME_NOT_FOUND("City name not found", HttpStatus.BAD_REQUEST),
        TRACKER_CREATION_OK("Tracking creation successful!", HttpStatus.CREATED),
        TRACKER_INTERNAL_SERVER_ERROR("Unexpected internal error!", HttpStatus.INTERNAL_SERVER_ERROR),
        READING_RETRIEVE_OK("Reading retrieved successfully", HttpStatus.OK);

        private final String statusMessage;
        private final HttpStatus statusCode;

        CustomStatusCode(String statusMessage, HttpStatus statusCode) {
            this.statusMessage = statusMessage;
            this.statusCode = statusCode;
        }

        public String statusMessage() {
            return this.statusMessage;
        }

        public int statusCode() {
            return this.statusCode.value();
        }

        public HttpStatus httpValue() {
            return HttpStatus.valueOf(this.statusCode.value());
        }
    }
}
