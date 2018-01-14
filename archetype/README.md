**this is the maven archetype template for basic Paco Test project.**

to add the archetype to your local maven archetype catalog run the following from the page-content-tester/archetype directory
    
    mvn clean install
    
using the archetype to generate a new project run

    mvn archetype:generate -DarchetypeGroupId=io.github.christian-draeger -DarchetypeArtifactId=paco-archetype -DarchetypeVersion=1.0.1 -DgroupId=your.groupid -DartifactId=your-artifactId
    
GOOD TO KNOW:
The most simple and reliable way to create an new archetype is to create a simple maven project that describes the intended structure, dependencies and minimal java classes. 
Afterwards you can run `mvn archetype:create-from-project` from the root directory of your example project.

If the maven run was successful you will find your created maven template under `/target/generated-resources/archetype`.

Have a look at the pom files and adjust them if needed.

To add the generated archetype to your maven archetype catalog (so that you can actually use it) you can either do

    cd /target/generated-resources/archetype
    mvn clean install
    
or copy the `/target/generated-resources/archetype` folder into a new directory to have it permanently as an own project 
and execute `mvn clean install` from there.

To verify if everything worked properly you can check your `~/.m2/repository` folder. 
There should be an archetype-catalog.xml file including your archetype.