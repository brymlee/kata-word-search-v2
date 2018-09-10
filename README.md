<html>
    <h1>kata-word-search-v2</h1>
    <h4>Word search challenge puzzle created by Pillar Technology : https://github.com/PillarTechnology/kata-word-search</h4>
    <h2>Things to be familar with</h2>
    <ul>
        <li>Gradle</li>
        <li>General Java Syntax</li>
        <li>Java Lambda Expressions</li>
        <li>JUnit Testing Framework</li>
    </ul>
    <h2>Directions</h2>
    <ol>
        <li>Make sure that you are able to build the project using the Gradle wrapper supplied. 
            You may need to generate your own Gradle wrapper.
            Note that I have supplied a shell script that refers to the gradle wrapper stored at the root level of the project.
            To run this shell script simply run "bash build.sh". This will run the gradle build to generate a jar file as well as run the unit tests written.</li>
        <li>Familiarze yourself with the code base. 
            The tests, written in the "brymlee.wordpuzzle.WordSearchTest", documents important unit tests. 
            All of the functional code written was intended to be highly abstract and easy to understand at a high level.
            The reason for doing this is to allow the code to be self documenting.
            All puzzle resources are located in the "brymlee.wordpuzzle" package.
            Please note that this project utilizes resources that are added on the classpath.
            This means that you will need to add resources to the classpath respectively to add additional examples.</li>
        <li>To run the CLI application: 
            <ol>
                <li>Run "bash build.sh". This does the underlying gradle build and runs tests.</li>
                <li>Run the executable JAR file located in the generated build directory (build/libs). 
                    Example: "java -jar build/libs/kata-word-search-v2.jar word-search-0.txt". 
                    This is a fat jar. 
                    Which means that all dependencies are bundled to be self contained. 
            </ol>
        </li>
    </ol>
</html>
