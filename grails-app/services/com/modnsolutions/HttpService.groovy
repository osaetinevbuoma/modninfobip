package com.modnsolutions

import grails.transaction.Transactional
import org.grails.web.json.JSONObject

import com.modnsolutions.JsonHttpService

@Transactional
class HttpService {
    private final String USER_AGENT = "Mozilla/5.0"
    private final String CHARSET = "UTF-8"
    private final String CONTENTTYPE = "application/json"

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
