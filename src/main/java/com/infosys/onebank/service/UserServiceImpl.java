package com.infosys.onebank.service;


import com.infosys.onebank.dto.UserDTO;
import com.infosys.onebank.resource.User;
import com.infosys.onebank.utils.JsonParserUtils;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static com.infosys.onebank.OneBankConstants.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private RestTemplate restTemplate;

    public UserDTO createUser(User user) {
        return postUser(JsonParserUtils.createJson(user));
    }

    private UserDTO postUser(String user) {
        String finalURI = BASE_URI + "obp/v3.1.0/users";
        HttpEntity<String> request = new HttpEntity<String>(user, createHeaders());

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(finalURI, request, String.class);
        JSONObject object = JsonParserUtils.parse(responseEntity.getBody());
        return new UserDTO(
                (String) object.get("user_id"),
                (String) object.get("email"),
                (String) object.get("username")
        );
        
    }

    private HttpHeaders createHeaders(){
        return new HttpHeaders() {{
            String contentType = "application/json";
            set( "Content-Type", contentType );
        }};
    }
}