package com.modnsolutions

import grails.transaction.Transactional

@Transactional
class AuthorizationService {

    /**
     * Generates an encoded string using the RFC2045-MIME variant of Base64
     *
     * @param username  The client's infobip username
     * @param password  The client's infobip password
     * @return  String Base64 encoded string
     */
    String basicAuthorization(String username, String password) {
        String userPass = "$username:$password"
        String base64Encoded = Base64.getEncoder().encodeToString(userPass.getBytes("utf-8"))
        return "Basic $base64Encoded"
    }
}
