# Twitter-Bot
This is a simple twitter bot, which tweets about a provided keyword that we provided. 

# Running the application

To test the application we need run a couple of gradle commands.

- Clean the previously generated build files
  <pre><code>./gradlew clean</code></pre> 
- Generate a fat jar which packages all dependencies into a single jar file.
  <pre><code>./gradlew fatJar</code></pre>
- Run the application
  
  Method 1:
  <pre><code>java -jar TwitterClient-1.0-SNAPSHOT.jar [args]</code></pre>
  Method 2:
  <pre><code>java -cp TwitterClient-1.0-SNAPSHOT.jar com.kpaudel.twitter.Application [args]</code></pre>