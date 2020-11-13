# AEM Unsplash Integration 

This project demonstrates the AEM integration with Unsplash (It is a website dedicated to sharing stock photography).
Unsplash, is a website with a vast collection of stock photography under the Unsplash license. 
Instead of having to visit the site time and again, a convenient way to lookup images saving both time and effort is to 
have the search availability within the content management system itself. 

We can integrate Unsplash with AEM using Unsplash API 

## PreRequisites

Obtaining credentials for Unsplash API : 

To use Unsplash developer API, you need to Join Unsplash and generate an API key. For more details about how to create a developer account and API key, read Unsplash Documentation

## How to build

If you have a running AEM instance you can build and package the whole project and deploy into AEM with

    mvn clean install -PautoInstallPackage -Padobe-public

## Maven settings

The project comes with the auto-public repository configured. To setup the repository in your Maven settings, refer to:

    http://helpx.adobe.com/experience-manager/kb/SetUpTheAdobeMavenRepository.html

###
