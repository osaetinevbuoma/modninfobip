package com.modnsolutions

import grails.core.GrailsApplication
import grails.transaction.Transactional
import org.grails.web.json.JSONObject

@Transactional
class UtilitiesService {

    GrailsApplication grailsApplication

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

    /**
     * Get infobip host url
     * @return String : host url
     */
    String getInfobipHost() {
        grailsApplication.config.infobip.host
    }

    /**
     * Send HTTP post request to URL
     * @param address
     * @param authorization
     * @param bodyParameters
     * @return
     */
    JSONObject post(String address, String authorization, String bodyParameters) {
        Map headers = ["Authorization": authorization]
        JsonHttpService.post(address, headers, bodyParameters, true)
    }

    /**
     * Send HTTP get request to URL
     * @param address
     * @param authorization
     * @return
     */
    JSONObject get(String address, String authorization) {
        Map headers = ["Authorization": authorization]
        JsonHttpService.get(address, headers, true)
    }
}
