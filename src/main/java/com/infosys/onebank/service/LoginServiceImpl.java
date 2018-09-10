package com.infosys.onebank.service;


import com.infosys.onebank.utils.HashUtils;
import com.infosys.onebank.utils.JsonParserUtils;
import com.infosys.onebank.utils.PropertyLoader;
import com.infosys.onebank.utils.RestHeaderUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static com.infosys.onebank.OneBankConstants.BASE_URI;

@Service
public class LoginServiceImpl implements LoginService {

    static final Logger logger = LogManager.getLogger(LoginServiceImpl.class);

    @Autowired
    private RestTemplate restTemplate;

    private String consumerKey = "dlqlcg3c3szlcwoezsqp4fd2kwfarqukve1vn2iq";
    private String username = "chiragmganatra@gmail.com";
    private String password = "RGV2ZWxvcDEyM0A=";

    public LoginServiceImpl() {
        consumerKey = PropertyLoader.getInstance().getPropertyValue("consumerKey");
        username = PropertyLoader.getInstance().getPropertyValue("username");
        password = HashUtils.getInstance().getValue(PropertyLoader.getInstance().getPropertyValue("password"));
    }

    public String getLoginToken() {
        String loginURI = BASE_URI + "my/logins/direct";
        ResponseEntity<String> response = restTemplate.exchange(loginURI,
                HttpMethod.POST,
                new HttpEntity<String>(RestHeaderUtils.createLoginHeaders(username, password, consumerKey)),
                String.class);

        JSONObject token = JsonParserUtils.parse(response.getBody());
        return token.get("token").toString();

    }

}
