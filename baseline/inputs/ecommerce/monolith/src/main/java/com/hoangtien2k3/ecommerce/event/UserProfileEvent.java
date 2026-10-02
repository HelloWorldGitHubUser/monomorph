package com.hoangtien2k3.ecommerce.event;

import com.hoangtien2k3.ecommerce.dto.EmailDetails;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class UserProfileEvent extends ApplicationEvent {
    private final EmailDetails emailDetails;

    public UserProfileEvent(Object source, EmailDetails emailDetails) {
        super(source);
        this.emailDetails = emailDetails;
    }
}
