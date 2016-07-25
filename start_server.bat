set JAVA_OPTS=%JAVA_OPTS% -Xmx1024M -XX:MaxPermSize=256M
set MAVEN_OPTS=%MAVEN_OPTS% %JAVA_OPTS% -javaagent:C:\Users\abdrakhmanovi\.p2\pool\plugins\org.zeroturnaround.eclipse.embedder_6.4.6.RELEASE\jrebel\jrebel.jar
mvn jetty:run