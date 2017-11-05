#!/bin/bash -e

if [ -n "$(git status --porcelain)" ]; then
	git status --porcelain
  	echo "there are changes - please commit everything before uploading a release";
  	exit 1;
fi

mvn clean deploy -Prelease
cd src/main/java/paco/parent
mvn clean deploy -Prelease