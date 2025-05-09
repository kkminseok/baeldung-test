package com.my.springrabbitmqstomp1on1sample;

import java.security.Principal;

public class StompPrincipal implements Principal {
    private String name;

    public StompPrincipal(String name) {
        this.name = name;
    }
    @Override
    public String getName() {
        return name;
    }
}
