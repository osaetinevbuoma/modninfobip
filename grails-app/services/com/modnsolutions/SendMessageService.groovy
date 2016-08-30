package com.modnsolutions

import grails.transaction.Transactional
import org.grails.web.json.JSONArray
import org.grails.web.json.JSONObject

@Transactional
class SendMessageService {

    UtilitiesService utilitiesService

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
        String address = utilitiesService.getInfobipHost() + "/text/single"
        JSONObject messageObj = new JSONObject([from: from, to: to, text: text])
        utilitiesService.post(address, authorization, messageObj.toString())
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
        String address = utilitiesService.getInfobipHost() + "/text/single"

        JSONArray toArr = new JSONArray()
        to.each {
            toArr.put(it)
        }

        JSONObject messageObj = new JSONObject([from: from, to: toArr, text: text])
        utilitiesService.post(address, authorization, messageObj.toString())
    }

    /**
     * Send multiple messages to one or more destination phone numbers
     *
     * @param authorization Authorization code
     * @param data          Array of information data
     * @return
     */
    JSONObject sendMultipleMessages(String authorization, JSONObject data) {
        String address = utilitiesService.getInfobipHost() + "/text/multi"
        utilitiesService.post(address, authorization, data.toString())
    }

    /**
     * Get delivery report for message sent
     *
     * @param authorization Authorization code
     * @return
     */
    JSONObject deliveryReport(String authorization) {
        String address = utilitiesService.getInfobipHost() + "/reports"
        utilitiesService.get(address, authorization)
    }

    /**
     * Get delivery report for message sent but filtered
     * Filter parameters are any of the following:
     * <ul>
     *     <li>bulkId - string - The ID that uniquely identifies the request. Bulk ID will be
     *     received only when you send a message to more than one destination address.</li>
     *     <li>messageId - string - The ID that uniquely identifies the message sent.</li>
     *     <li>limit - string - The maximum number of returned delivery reports. Default value is 50.</li>
     * </ul>
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
        String address = utilitiesService.getInfobipHost() + "/reports?$params"
        utilitiesService.get(address, authorization)
    }

    /**
     * Get message logs for the last 48 hours
     *
     * @param authorization Authorization code
     * @return
     */
    JSONObject messageLog(String authorization) {
        String address = utilitiesService.getInfobipHost() + "/logs"
        utilitiesService.get(address, authorization)
    }

    /**
     * Get message logs for the last 48 hours but filtered
     * Filter parameters are any of the following:
     * <ul>
     *     <li>from - string - Sender ID that can be alphanumeric or numeric.</li>
     *     <li>to - string - The message destination address.</li>
     *     <li>bulkId - string - The ID that uniquely identifies the request. Bulk ID will be
     *     received only when you send a message to more than one destination address.</li>
     *     <li>messageId - string - The ID that uniquely identifies the message sent.</li>
     *     <li>generalStatus - string - Sent SMS status group.Indicates whether the message is
     *     successfully sent, not sent, delivered, not delivered, waiting for delivery or any
     *     other possible status.</li>
     *     <li>sentSince - datetime - Lower limit on date and time of sending SMS.</li>
     *     <li>sentUntil - datetime - Upper limit on date and time of sending SMS.</li>
     *     <li>limit - integer - Maximal number of messages in returned logs. Default value is
     *     50.</li>
     *     <li>mcc - string - Mobile country code.</li>
     *     <li>mnc - string Mobile network code.</li>
     * </ul>
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
        String address = utilitiesService.getInfobipHost() + "/logs?$params"
        utilitiesService.get(address, authorization)
    }
}
