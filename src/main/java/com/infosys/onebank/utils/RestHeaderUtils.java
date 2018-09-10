package com.infosys.onebank.utils;

import org.springframework.http.HttpHeaders;

public class RestHeaderUtils {

    public static HttpHeaders createHeaders(final String token){
        return new HttpHeaders() {{
            String authHeader = "DirectLogin token=" + token;
            String contentType = "application/json";
            set( "Authorization", authHeader );
            set( "Content-Type", contentType);
        }};
    }

    public static HttpHeaders createLoginHeaders(final String username, final String password, final String consumerKey){
        return new HttpHeaders() {{
            String authHeader = "DirectLogin username=" + username + ", password="+ password + ", consumer_key="+consumerKey;
            set( "Authorization", authHeader );
        }};
    }
}
