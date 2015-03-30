# CSC440TopDollarDeals

## Simple Deployment
1. Install [Activator](https://typesafe.com/get-started).
2. Add `activator` to your path.
3. Open a terminal or console window.
4. Change the directory to the [tree/master/src](src) directory.
5. Run `activator run`.
6. Open a browser.
7. Navigate to `http://localhost:9000`.

## Advanced Deployment
Standalone Play applications
The simplest and the more robust way is to simply run your Play application without any container. You can use a frontal HTTP server like Lighttpd or Apache if you need more advanced HTTP features like virtual hosting.

The built-in HTTP server can serve thousands of HTTP requests per second so it will never be the performance bottleneck. Moreover it uses a more efficient threading model (where a Servlet container uses 1 thread per request). Different modules allow you to use different servers (Grizzly, Netty, etc...) as well.

Those servers support long polling and allow to manage very long requests (waiting for a long task to complete), and direct streaming of File objects (and any InputStream if you specify the Content-Length), without blocking the execution thread.

You will have less problems running your application this way, as you will use the same environment that you used during the development process. A lot of bugs can be discovered only when you deploy to a JEE application server (different home dir, classloader issues, library conflicts, etc...).


Java EE application servers

Your Play application can also run inside your favourite application server. Most application servers are supported out of the box.

Deploying
You need to package your application as a war file. This is easily done with the following command:

play war myapp -o myapp.war
Please note that your application server must support deployment of exploded WAR files.

You are now ready to deploy your application.

You are advised to ‘isolate’ your Play application from the other applications to avoid version mismatches between the application libraries. This step is not standardized by the JEE / Servlet Container specification, and is therefore vendor specific.

We recommend you refer to your application server manual in order to ‘isolate’ your WAR. As an example below is how you isolate a war file in JBoss Application server. Note that this is an optional step:

Insert the following content (or create the file) in your application war directory at myapp.war/WEB-INF/jboss-web.xml:

<jboss-web>
 <class-loading java2classloadingcompliance="false">
 <loader-repository>
 com.example:archive=unique-archive-name
 <loader-repository-config>java2ParentDelegation=false</loader-repository-config>
 </loader-repository>
</class-loading>
</jboss-web>

Replace com.example:archive=unique-archive-name with whatever you wish as long as it is unique.
