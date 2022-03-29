Proxy to support RelFinder.

#Steps to Build and Run the Docker image:

	Step 1: Open command prompt and navigate to project folder
	Step 2: Execute "docker build -t <image_name>:<tag> ." command
	Step 3: After successfull build, execute "docker run -d -p 8080:8080 <image_name>:<tag>" command to run the docker image


#Local Eclipse environment Setup: 

	Step 1: Download Eclipse IDE and Clone project from the repository
	Step 2: Open Eclipse IDE and open File -> Import... -> Maven -> Exsiting Maven Project
	Step 3: After importing do maven build. Right click on project -> Run As -> Maven Build
	Step 4: In commad prombt type "clean install"
	Step 5: To run in local, right click on ProxyServerApplication.java -> Run As -> Run As Java Application
	Step 6: By default the application will be accessed in <domain>.<port>/proxyserver endpoint. To check this change "contextpath" property in application.properties file

	Note: Application log file can be found in /home/relfinder/relfinder_proxy/logs/application.log path in docker container.
#Local environment Deployment:
	Step 1: Copy generated jar from target folder and execute "java -jar <jar_name>.jar" in command prombt to execute
	
#War File Generation:
	To generate war file Open pom.xml and chenge <packaging>jar</packaging> to <packaging>war</packaging>	