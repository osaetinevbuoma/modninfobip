package com.modnsolutions

import grails.transaction.Transactional
import org.grails.web.json.JSONException
import org.grails.web.json.JSONObject

import javax.net.ssl.HttpsURLConnection

@Transactional
class HttpService {
    private final String USER_AGENT = "Mozilla/5.0"
    private final String CHARSET = "UTF-8"
    private final String CONTENTTYPE = "application/json"

    JSONObject post(String address, String authorization, String bodyParameters) {
        StringBuffer returnedBuffer
        JSONObject returnedObj = null

        try {
            URL url = new URL(address)
            HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection()
            httpsURLConnection.requestMethod = "POST"
            httpsURLConnection.setRequestProperty("User-Agent", USER_AGENT)
            httpsURLConnection.setRequestProperty("Accept-Charset", CHARSET)
            httpsURLConnection.setRequestProperty("Accept-Language", "en-US,en;q=0.5")
            httpsURLConnection.setRequestProperty("Accept", CONTENTTYPE)
            httpsURLConnection.setRequestProperty("Content-Type", CONTENTTYPE)
            httpsURLConnection.setRequestProperty("Authorization", authorization)
            httpsURLConnection.doOutput = true
            httpsURLConnection.connect()

            // Send post request
            DataOutputStream dataOutputStream = new DataOutputStream(httpsURLConnection.outputStream)
            dataOutputStream.writeBytes(bodyParameters)
            dataOutputStream.flush()
            dataOutputStream.close()

            int responseCode = httpsURLConnection.responseCode
            if (responseCode == 200) {
                BufferedReader bufferedReader = new BufferedReader(
                        new InputStreamReader(httpsURLConnection.inputStream))
                String inputLine
                returnedBuffer = new StringBuffer()

                while ((inputLine = bufferedReader.readLine()) != null) {
                    returnedBuffer.append(inputLine)
                }

                bufferedReader.close()
                httpsURLConnection.disconnect()
                returnedObj = new JSONObject(returnedBuffer.toString())
            }
        } catch (MalformedURLException ex) {
            println ex.message
        } catch (IOException ex) {
            println ex.message
        } catch (JSONException ex) {
            println ex.message
        } catch (IllegalStateException ex) {
            println ex.message
        }

        return returnedObj
    }

    JSONObject get(String address, String authorization) {
        StringBuffer returnedBuffer
        JSONObject returnedObj = null

        try {
            URL url = new URL(address)
            HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection()
            httpsURLConnection.setRequestProperty("User-Agent", USER_AGENT)
            httpsURLConnection.setRequestProperty("Accept-Charset", CHARSET)
            httpsURLConnection.setRequestProperty("Accept", CONTENTTYPE)
            httpsURLConnection.setRequestProperty("Authorization", authorization)
            httpsURLConnection.connect()

            int responseCode = httpsURLConnection.responseCode
            if (responseCode == 200) {
                BufferedReader bufferedReader = new BufferedReader(
                        new InputStreamReader(httpsURLConnection.inputStream))
                returnedBuffer = new StringBuffer()

                String inputLine
                while ((inputLine = bufferedReader.readLine()) != null) {
                    returnedBuffer.append(inputLine)
                }

                bufferedReader.close()
                httpsURLConnection.disconnect()

                returnedObj = new JSONObject(returnedBuffer.toString())
            }
        } catch (MalformedURLException ex) {
            println ex.message
        } catch (IOException ex) {
            println ex.message
        } catch (JSONException ex) {
            println ex.message
        }

        return returnedObj
    }
}
