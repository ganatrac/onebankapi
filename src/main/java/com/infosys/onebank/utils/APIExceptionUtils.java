package com.infosys.onebank.utils;

import com.infosys.onebank.exception.InvalidAPICallException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.http.ResponseEntity;

import static com.infosys.onebank.OneBankConstants.HTTP_STATUS_CODE;

/**
 * Created by chirag.ganatra on 9/8/2018.
 */
public class APIExceptionUtils {

    private static final Logger logger = LogManager.getLogger(APIExceptionUtils.class);

    public static InvalidAPICallException createInvalidAPIException(ResponseEntity<String> responseEntity) {
        JSONObject error = JsonParserUtils.parse(responseEntity.getBody());
        error.put(HTTP_STATUS_CODE, responseEntity.getStatusCode());
        logger.error(error.toJSONString());
        return new InvalidAPICallException(error.toJSONString());
    }
}
