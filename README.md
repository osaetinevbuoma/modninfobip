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

### Get logs ###

```
JSONObject messageLog(String authorization)
```

### Get filtered logs ###

```
JSONObject messageLog(String authorization, Map filters)
```
* filters - A filter map. View [here](https://dev.infobip.com/docs/message-logs) for filter information.

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
    compile "com.modnsolutions:modninfobip:1.0"
}
```

## Usage ##
* Import the required service classes into you Grails Service or Controller class.

```
import com.modnsolutions.AuthorizationService
import com.modnsolutions.SendMessageService
```
* Inject AuthorizationService and SendMessageService classes into your Grails Service or Controller class.

```
AuthorizationService authorizationService
SendMessageService sendMessageService
```

* In your class method, generate your basic authorization code and send your messages

```
String basicAuthorization = authorizationService.basicAuthorization("INFOBIP_USERNAME", "INFOBIP_PASSWORD")
JSONObject singleMessageResponse = sendMessageService.sendSingleMessage(basicAuthorization, from, to, text)
println singleMessageResponse
```

### Example ###
```
import com.modnsolutions.AuthorizationService
import com.modnsolutions.SendMessageService
import org.grails.web.json.JSONArray

class SMSController {
    AuthorizationService authorizationService
    SendMessageService sendMessageService
    
    def sendSingleSMS(String from, String to, String text) {
        String basicAuthorization = authorizationService.basicAuthorization("INFOBIP_USERNAME", "INFORBIP_PASSWORD")
        JSONObject singleMessageResponse = sendMessageService.sendSingleMessage(basicAuthorization, from, to, text)
        println singleMessageResponse

        flash.message = singleMessageResponse
        render view: "/index.gsp"
    }
    
    ...
}
```