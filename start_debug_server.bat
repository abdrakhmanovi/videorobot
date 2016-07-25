set JAVA_OPTS=%JAVA_OPTS% -Xmx1024M -XX:MaxPermSize=256M
set MAVEN_OPTS=%MAVEN_OPTS% %JAVA_OPTS% -javaagent:C:\Users\abdrakhmanovi\.p2\pool\plugins\org.zeroturnaround.eclipse.embedder_6.4.6.RELEASE\jrebel\jrebel.jar -Xdebug -Xnoagent -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,address=8000,server=y,suspend=n
mvn jetty:run