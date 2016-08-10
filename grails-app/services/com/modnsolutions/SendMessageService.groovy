package com.modnsolutions

import grails.core.GrailsApplication
import grails.transaction.Transactional
import org.grails.web.json.JSONArray
import org.grails.web.json.JSONObject

@Transactional
class SendMessageService {

    GrailsApplication grailsApplication
    HttpService httpService

    JSONObject sendSingleMessage(String authorization, String from, String to, String text) {
        String address = grailsApplication.config.infobip.host + "/sms/1/text/single"
        JSONObject messageObj = new JSONObject([from: from, to: to, text: text])
        httpService.post(address, authorization, messageObj.toString())
    }

    JSONObject sendSingleMessage(String authorization, String from, List<String> to, String text) {
        String address = grailsApplication.config.infobip.host + "/sms/1/text/single"

        JSONArray toArr = new JSONArray()
        to.each {
            toArr.put(it)
        }

        JSONObject messageObj = new JSONObject([from: from, to: toArr, text: text])
        httpService.post(address, authorization, messageObj.toString())
    }

    JSONObject sendMultipleMessages(String authorization, JSONObject data) {
        String address = grailsApplication.config.infobip.host + "/sms/1/text/multi"
        httpService.post(address, authorization, data.toString())
    }
}
