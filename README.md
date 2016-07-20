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
7. Create `jmms-X.Y.zip` package and attach it to GitHub release
8. Create new version number with `-SNAPSHOT` postfix in pom.xml
