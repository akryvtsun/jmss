# jmss

[![Build Status](https://travis-ci.org/englishman/jmss.svg)](https://travis-ci.org/englishman/jmss)
[![Coverage Status](https://coveralls.io/repos/englishman/jmss/badge.svg?branch=master)](https://coveralls.io/r/englishman/jmss?branch=master)

Shooting Match Scoring System

#### How to create release ####

1. Use this command in cloned via 'https' repository

   ```
   mvn -Dusername=englishman -Dpassword=******** release:prepare
   ```
   See [here](http://stackoverflow.com/a/28343179/2313177) for details.
2. Do steps from [GitHub creating releases](https://help.github.com/articles/creating-releases) manual.

#### How to create release *manually* ####

1. Remove `-SNAPSHOT` from application version in pom.xml
2. Make release build via `mvn clean package`
3. update `start.bat` with release version
4. Test manually created release artifact from `targer` folder
5. Commit and push changes and make sure Travis CI build is green
6. Create release in GitHub with new label `vX.Y`
7. Create `jmss-X.Y.zip` package and attach it to GitHub release
8. Create new version number with `-SNAPSHOT` postfix in pom.xml

#### Logback config ####

##### How to see what logback config file is used  #####

   ```
    -Dlogback.statusListenerClass=ch.qos.logback.core.status.OnConsoleStatusListener
   ```
   [StackOverflow source](http://stackoverflow.com/a/35072342/2313177) 

##### How to see what logback config file is used #####
   ```
    -Dlogback.configurationFile=<full_path_to_logback[-test].xml_file>
   ```
   [Manual source](http://logback.qos.ch/manual/configuration.html)
   
##### Log levels #####  
- OFF - The OFF is used to turn off logging.
- ERROR - The ERROR level designates error events which may or not be fatal to the application.
- WARN - The WARN level designates potentially harmful situations.
- INFO - The INFO level designates informational messages highlighting overall progress of the application.
- DEBUG - The DEBUG level designates informational events of lower importance.
- TRACE - The TRACE level designates informational events of very low importance.
- ALL - The ALL is used to turn on all logging.
    
