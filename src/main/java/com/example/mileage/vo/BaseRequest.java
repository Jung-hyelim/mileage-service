package com.example.mileage.vo;

import com.example.mileage.enums.EventAction;
import com.example.mileage.enums.EventType;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Getter
@Setter
@ToString
public abstract class BaseRequest {
    @NotNull(message = "type은 필수값 입니다.")
    private EventType type;

    @NotNull(message = "action은 필수값 입니다.")
    private EventAction action;

    @NotNull(message = "userId는 필수값 입니다.")
    @Length(max = 36, message = "userId는 36자까지 입력 가능합니다.")
    private String userId;

    private String key; // event 의 구분 key 값을 상속받은 Request 에서 지정하여 넣어준다.

}
