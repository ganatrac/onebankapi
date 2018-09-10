package com.infosys.onebank.utils;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;

/**
 * Created by chirag.ganatra on 7/17/2018.
 */
public class HashUtils {

    private static HashUtils hashUtils;

    static {
        hashUtils = new HashUtils();
    }

    public static HashUtils getInstance() {
        return hashUtils;
    }

    private HashUtils(){}

    public String getValue(String hash) {
        byte[] byteValue = Base64.decodeBase64(hash);
        return StringUtils.newStringUtf8(byteValue);
    }

    public String getHash(String value) {
        return Base64.encodeBase64String(value.getBytes());
    }

    public static void main(String... args) {
        System.out.println(HashUtils.getInstance().getHash("Infosys@123"));
        System.out.println(HashUtils.getInstance().getValue(HashUtils.getInstance().getHash("Infosys@123")));
    }
}
