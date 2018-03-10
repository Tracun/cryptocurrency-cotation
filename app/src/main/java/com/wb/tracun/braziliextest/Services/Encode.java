package com.wb.tracun.braziliextest.Services;

import org.apache.commons.codec.binary.Hex;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by u4239 on 08/02/2018.
 */

public class Encode {

    /**
     * You have to use: compile 'commons-codec:commons-codec:1.9'
     * as dependency
     */
    public static String toHMACSHA512(String value, String securityHmac) throws UnsupportedEncodingException,
            NoSuchAlgorithmException, InvalidKeyException {

        SecretKeySpec key = new SecretKeySpec((securityHmac).getBytes("UTF-8"), "HmacSHA512");

        Mac mac = Mac.getInstance("HmacSHA512");

        mac.init(key);

        byte[] bytes = mac.doFinal(value.getBytes());

        return new String(Hex.encodeHex(bytes));
    }
}
