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


# Run Locally

 -Dservice.port=8080 
 
 -Dappname=parkabled
 
 -Dauth.provider=stormpath
 
 -Dbackendless-application-id=F7D421A4-8FA0-AD33-FFA3-97D476001300
 
 -Dbackendless-secret-id=571B9BE1-FF2E-0B41-FF4A-F232B0FADB00
 
 -Dstormpath-application-id=2WOVYCQWFLTRUG8AX4DJXHBC3
 
 -Dstormpath-secret-id=JKNKllXsovG2ElgcqO99l55VeiYdlG08yCzSom7cD40
 
 -DSENDGRID_PASSWORD=vcpcgyco7416
 
 -DSENDGRID_USERNAME=app39948759@heroku.com
 


Done :)

    
