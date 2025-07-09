#!/bin/bash

###################################
## Shell script to generate SHA1 ##
###################################

echo -e "\033[01;32m#######################\033[01;32m"
echo -e "\033[01;32m### SHA1 generated! ###\033[01;32m"
echo -e "\033[01;32m#######################\033[01;32m"
echo -e "\n\n";

keytool -list -v -keystore ~/.android/debug.keystore -alias androiddebugkey -storepass android -keypass android 
