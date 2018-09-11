package com.infosys.onebank.utils;

import com.infosys.onebank.dto.Error;

public class APIExceptionUtils {

    public static Error createClientException(Exception ex) {
        Error error = createError("CLN-001");
        error.setErrorMsg(ex.getMessage());
        return error;
    }

    public static Error createServerException(Exception ex) {
        Error error = createError("SRV-001");
        error.setErrorMsg(ex.getMessage());
        return error;
    }

    private static Error createError(String errorCode) {
        Error error = new Error(errorCode);
        return error;
    }
}
