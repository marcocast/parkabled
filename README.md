# geoservices
Base RESTful project for building geoservices applications 

# Overview

# Requirements

Clone this project locally.

Create a Java Heroku application :
_https://devcenter.heroku.com/articles/deploying-java#deploy-your-application-to-heroku choose an app name {heroku-app-name}_

Create an Backendless application :
_singup here https://backendless.com_

Create an Stormpath application :
_singup here https://stormpath.com and use the same Heroku app name {heroku-app-name}_

# Get Started

Set the following properties using the Heroku CLI

1. heroku config:set appname={YOUR APP NAME}
1. heroku config:set backendless-application-id={BACKENDLESS APP ID}
1. heroku config:set backendless-secret-id={BACKENDLESS ANDROID SECRET ID}}
1. heroku config:set stormpath-application-id={STORMPATH APP ID}
1. heroku config:set stormpath-secret-id={STORMPATH SECRET ID}

Push to heroku.

Done :)

    
