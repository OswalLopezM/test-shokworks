package com.test.test.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.test.test.model.UserAccount;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventResponse {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long id;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String address;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Date date;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private UserResponse user;
}
