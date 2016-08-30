package com.modnsolutions

import grails.transaction.Transactional
import org.grails.web.json.JSONObject

@Transactional
class ReceiveMessageService {

    UtilitiesService utilitiesService

    /**
     * Pull all received messages sent to infobip number
     *
     * @param authorization : Basic authorization
     * @return JSONObject : Response object with messages
     */
    JSONObject pullReceivedMessages(String authorization) {
        String address = utilitiesService.getInfobipHost() + "/inbox/reports"
        utilitiesService.get(address, authorization)
    }

    /**
     * Pull received message sent to infobip number but limited to a certain number of results
     *
     * @param authorization : Basic authorization
     * @param limit : The limit amount of messages to return
     * @return JSONObject : Response object with messages
     */
    JSONObject pullReceivedMessages(String authorization, int limit) {
        String address = utilitiesService.getInfobipHost() + "/inbox/reports?limit=$limit"
        utilitiesService.get(address, authorization)
    }

    /**
     * Pull all message received logs unfiltered
     *
     * @param authorization : Basic authorization
     * @return JSONObject : Response object with messages
     */
    JSONObject messageLog(String authorization) {
        String address = utilitiesService.getInfobipHost() + "/inbox/logs"
        utilitiesService.get(address, authorization)
    }

    /**
     * Pull message received logs filter.
     * Filter parameters are
     * <ul>
     *     <li>limit - integer - Maximum number of messages in returned logs. Default value is 50.</li>
     *     <li>keyword - string - Keyword used in received messages</li>
     *     <li>to - string - The message destination address.</li>
     *     <li>receivedSince - datetime - Lower limit on date and time of sending SMS.</li>
     *     <li>receivedUntil - datetime - Upper limit on date and time of sending SMS.</li>
     * </ul>
     *
     * @param authorization : Basic authorization
     * @param filters : Map of filter parameters
     * @return JSONObject : Response object with messages
     */
    JSONObject messageLog(String authorization, Map filters) {
        String params
        filters.each { key, value ->
            params += "$key=$value&"
        }
        String address = utilitiesService.getInfobipHost() + "/inbox/logs?$params"
        utilitiesService.get(address, authorization)
    }
}
