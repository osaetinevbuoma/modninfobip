# Infobip plugin #
Grails plugin for Infobip SMS API

## Prerequisite to using **modninfobip** plugin ##
* Sign up for an [infobip](www.infobip.com) account
* Confirm your account

## Method Declarations ##
### Generate basic authorization ###

```
String basicAuthorization(String username, String password)
```
* username - infobip username
* password - infobip password

**NB**: Basic authorization is mandatory for all infobip REST API requests.

### Send an SMS to a single phone number ###

```
JSONObject sendSingleMessage(String authorization, String from, String to, String text)
```
* from - Sender phone number registered on infobip (Phone numbers are usually prefixed with *+ (plus sign)*, followed by a *country code*, *network code* and the *subscriber number*)
* to - Recipient phone number
* text - SMS message to send

### Send an SMS to multiple phone numbers ###

```
JSONObject sendSingleMessage(String authorization, String from, List to, String text)
```
* to - A list of string types of recipients' phone numbers

### Send multiple SMS's to multiple or same recipient phone number ###

```
JSONObject sendMultipleMessages(String authorization, JSONObject data)
```
* data - A json object of data. An example is show below.

```
{  
   "messages":[  
      {  
         "from":"InfoSMS",
         "to":[  
            "41793026727",
            "41793026731"
         ],
         "text":"May the Force be with you!"
      },
      {  
         "from":"41793026700",
         "to":"41793026785",
         "text":"A long time ago, in a galaxy far, far away... It is a period of civil war. Rebel spaceships, striking from a hidden base, have won their first victory against the evil Galactic Empire."
      }
   ]
}
```

### Get delivery reports ###

```
JSONObject deliveryReport(String authorization)
```

### Get filtered delivery reports ###

```
JSONObject deliveryReport(String authorization, Map filters)
```
* filters - A filter map. View [here](https://dev.infobip.com/docs/delivery-reports) for more filter information.

**NB**: Delivery reports can only be gotten once. A second call returns an empty result.

### Pull received messages to phone number from Infobip server ###

```
JSONObject pullReceivedMessages(String authorization)
JSONObject pullReceivedMessages(String authorization, int limit)
```
* limit - The number of messages to pull from server. Default is 50 and maximum is 10000

### Get logs ###

```
// Call the messageLog method corresponding service 
// class (SendMessageService or ReceiveMessageService)
JSONObject messageLog(String authorization)
```

### Get filtered logs ###

```
JSONObject messageLog(String authorization, Map filters)
```
* filters - A filter map. View [here](https://dev.infobip.com/docs/message-logs) for filter information for sent messages and
    [here](https://dev.infobip.com/docs/received-messages-logs) for filter information for received messages.

## Installation ##
Edit `application.groovy` (or `application.yml` if you prefer) and `build.gradle`

* application.groovy
```
infobip.host = "https://api.infobip.com/sms/1"
```

**OR**

* application.yml
```
infobip:
    host: https://api.infobip.com/sms/1
```

* build.gradle
```
repositories {
    maven {
        url  "http://dl.bintray.com/modnsolutions/plugins-libraries" 
    }
}

dependencies {
    compile "com.modnsolutions:modninfobip:1.0.2"
}
```

## Usage ##
* Import the required service classes into you Grails Service or Controller class.

```
import com.modnsolutions.ReceiveMessageService //service to pull received messages and message log from infobip server
import com.modnsolutions.SendMessageService // service to send messages via infobip, get delivery report and message logs
import com.modnsolutions.UtilitiesService
```
* Inject AuthorizationService and SendMessageService classes into your Grails Service or Controller class.

```
ReceiveMessageService receiveMessageService
SendMessageService sendMessageService
UtilitiesService utilitiesService
```

* In your class method, generate your basic authorization code and send your messages

```
String basicAuthorization = utilitiesService.basicAuthorization("INFOBIP_USERNAME", "INFOBIP_PASSWORD")
JSONObject singleMessageResponse = sendMessageService.sendSingleMessage(basicAuthorization, from, to, text)
println singleMessageResponse
```

### Example ###
```
import com.modnsolutions.ReceiveMessageService
import com.modnsolutions.SendMessageService
import com.modnsolutions.UtilitiesService

// In Grails you can handle json objects and arrays by importing and using
// import org.grails.web.json.JSONArray
// import org.grails.web.json.JSONObject

class SMSController {
    ReceiveMessageService receiveMessageService
    SendMessageService sendMessageService
    UtilitiesService utilitiesService
    
    def sendSingleSMS(String from, String to, String text) {
        String basicAuthorization = utilitiesService.basicAuthorization("INFOBIP_USERNAME", "INFORBIP_PASSWORD")
        render sendMessageService.sendSingleMessage(basicAuthorization, from, to, text)
    }
    
    def pullMessages() {
        String basicAuthorization = utilitiesService.basicAuthorization("INFOBIP_USERNAME", "INFORBIP_PASSWORD")
        render receiveMessageService.pullReceivedMessages(basicAuthorization)
    }
    
    ...
}
```