package com.example.mileage.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.validation.FieldError;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class ErrorResponse {

    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();

    private int code;

    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("errors")
    private List<CustomFieldError> fieldErrors;

    public void setFieldErrors(List<FieldError> fieldErrorList) {
        this.fieldErrors = new ArrayList<>();
        this.fieldErrors.addAll(fieldErrorList.stream().map(CustomFieldError::new).collect(Collectors.toList()));
    }

    @Getter
    public static class CustomFieldError {
        private String field;
        private Object value;
        private String reason;

        public CustomFieldError(FieldError fieldError) {
            this.field = fieldError.getField();
            this.value = fieldError.getRejectedValue();
            this.reason = fieldError.getDefaultMessage();
        }
    }

}
