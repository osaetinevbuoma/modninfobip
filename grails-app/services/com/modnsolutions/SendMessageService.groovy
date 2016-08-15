package com.modnsolutions

import grails.core.GrailsApplication
import grails.transaction.Transactional
import org.grails.web.json.JSONArray
import org.grails.web.json.JSONObject

@Transactional
class SendMessageService {

    GrailsApplication grailsApplication
    HttpService httpService

    /**
     * Send a single message to a number from a verified Infobip number
     *
     * @param authorization Authorization code
     * @param from          Verified Infobip number
     * @param to            Destination phone number
     * @param text          Text message to send
     * @return
     */
    JSONObject sendSingleMessage(String authorization, String from, String to, String text) {
        String address = grailsApplication.config.infobip.host + "/sms/1/text/single"
        JSONObject messageObj = new JSONObject([from: from, to: to, text: text])
        httpService.post(address, authorization, messageObj.toString())
    }

    /**
     * Send a single message to a set of numbers from a verified Infobip number
     *
     * @param authorization Authorization code
     * @param from          Verified Infobip number
     * @param to            List of destination phone number
     * @param text          Text message to send
     * @return
     */
    JSONObject sendSingleMessage(String authorization, String from, List to, String text) {
        String address = grailsApplication.config.infobip.host + "/sms/1/text/single"

        JSONArray toArr = new JSONArray()
        to.each {
            toArr.put(it)
        }

        JSONObject messageObj = new JSONObject([from: from, to: toArr, text: text])
        httpService.post(address, authorization, messageObj.toString())
    }

    /**
     * Send multiple messages to one or more destination phone numbers
     *
     * @param authorization Authorization code
     * @param data          Array of information data
     * @return
     */
    JSONObject sendMultipleMessages(String authorization, JSONObject data) {
        String address = grailsApplication.config.infobip.host + "/sms/1/text/multi"
        httpService.post(address, authorization, data.toString())
    }

    /**
     * Get delivery report for message sent
     *
     * @param authorization Authorization code
     * @return
     */
    JSONObject deliveryReport(String authorization) {
        String address = grailsApplication.config.infobip.host + "/sms/1/reports"
        httpService.get(address, authorization)
    }

    /**
     * Get delivery report for message sent but filtered
     *
     * @param authorization Authorization code
     * @param filters       Map of filters to filter results
     * @return
     */
    JSONObject deliveryReport(String authorization, Map filters) {
        String params = ""
        filters.each { key, value ->
            params += "$key=$value&"
        }
        String address = grailsApplication.config.infobip.host + "/sms/1/reports?$params"
        httpService.get(address, authorization)
    }

    /**
     * Get message logs for the last 48 hours
     *
     * @param authorization Authorization code
     * @return
     */
    JSONObject messageLog(String authorization) {
        String address = grailsApplication.config.infobip.host + "/sms/1/logs"
        httpService.get(address, authorization)
    }

    /**
     * Get message logs for the last 48 hours but filtered
     *
     * @param authorization Authorization code
     * @param filters       Map of filters to filter results
     * @return
     */
    JSONObject messageLog(String authorization, Map filters) {
        String params
        filters.each { key, value ->
            params += "$key=$value&"
        }
        String address = grailsApplication.config.infobip.host + "/sms/1/logs?$params"
        httpService.get(address, authorization)
    }
}
