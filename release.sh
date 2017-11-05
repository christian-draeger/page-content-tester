#!/bin/bash -e

if [ -n "$(git status --porcelain)" ]; then
	git status --porcelain
  	echo "there are changes";
  	echo "please commit everything so we have a reproducible git commit ID as LambdaCD version";
  	exit 1;
fi

mvn clean deploy -Prelease
cd src/main/java/paco/parent
mvn clean deploy -Prelease