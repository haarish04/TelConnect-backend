# Security Configuration

This Project makes use of Spring Security along with JWT token for authorization and authentication. The following is the breakdown of the config files and security files. All the headings point to the respective filename in the project

## CorsConfig
Cross-Oriign Resource Sharing (CORS) is an HTTP-header based mechanism that allows an application or server indicate any origin which could be in the form of domain or port other than it's own address from which a browser should permit loading resources.

This essential means that we can specify the exact address from where our server will accept requests (usually our frontend) any other server/application trying to contact our server will be rejected.

In the class CorsConfig we implement WebMvcConfigurer and customize the the CorsMappings Registry. 

Add the origin domain in the form of -> ```java allowedOrigins("domain URL").allowCredentials(true) ```
Include the allowed request headers -> ```java allowedHeaders("*")``` to allow all headers, can be customized
Include the allowed methods -> ```java allowedMethods("GET","POST","PUT","DELETE","PATCH")``` to allow these specified http methods