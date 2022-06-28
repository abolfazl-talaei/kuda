package com.kuda.app.security.impl;

import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

public class DefaultSuccessfulSignInHandler extends SimpleUrlAuthenticationSuccessHandler {

    public DefaultSuccessfulSignInHandler() {
        super();
        setUseReferer(true);
    }
}
