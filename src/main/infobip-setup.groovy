/**
 * Created by Osaetin Evbuoma on 8/10/16.
 */
file("grails-app/conf/application.groovy").withWriteAppen { BufferedWriter bufferedWriter ->
    bufferedWriter.newLine()
    bufferedWriter.writeLine("// Infobip configuration settings")
    bufferedWriter.writeLine("infobip.host = https://api.infobip.com")
}