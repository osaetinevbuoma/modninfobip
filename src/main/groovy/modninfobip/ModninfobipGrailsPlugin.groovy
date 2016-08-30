package modninfobip

import grails.plugins.*

class ModninfobipGrailsPlugin extends Plugin {

    // the version or versions of Grails the plugin is designed for
    def grailsVersion = "3.1.10 > *"
    // resources that are excluded from plugin packaging
    def pluginExcludes = [
        "grails-app/views/error.gsp"
    ]

    def title = "Grails Infobip" // Headline display name of the plugin
    def author = "Osaetin Evbuoma"
    def authorEmail = "osaetinevbuoma@gmail.com"
    def description = '''\
    Grails plugin for Infobip.
    '''
    def profiles = ['web']

    // URL to the plugin's documentation
    def documentation = "http://grails.org/plugin/modninfobip"

    // Extra (optional) plugin metadata

    // License: one of 'APACHE', 'GPL2', 'GPL3'
    def license = "APACHE"

    // Details of company behind the plugin (if there is one)
    def organization = [ name: "MOD'N Solutions", url: "http://www.modnsolutions.com/" ]

    // Location of the plugin's issue tracker.
    def issueManagement = [ system: "JIRA", url: "https://github.com/osaetinevbuoma/modninfobip/issues" ]

    // Online location of the plugin's browseable source code.
    def scm = [ url: "https://github.com/osaetinevbuoma/modninfobip" ]
}
