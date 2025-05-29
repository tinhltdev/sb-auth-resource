#Enable Security log
logging.level.org.springframework.security: DEBUG

1. Class CredentialsContainer used for security: remove password as soon as the authentication process finish.
=> The following image show the way how Spring security are processing an authentication request
![alt text](https://docs.spring.io/spring-security/reference/_images/servlet/authentication/unpwd/daoauthenticationprovider.png)
2. The AuthorizationFilter is last in the Spring Security filter chain by default. 
3. The AuthorizationFilter runs not just on every request, but on every dispatch. This means that the REQUEST dispatch needs authorization, but also FORWARDs, ERRORs, and INCLUDEs.
For that reason, you may want to permit all FORWARD dispatches.
4.  Matching Requests
- match any request
- URI pattern-matching: Ant (as seen above) and Regular Expressions.
5. you can apply an security at request level or method level(have to enable @EnableMethodSecurity)

# request level
![alt text](src\main\resources\1.png)
# method level
![alt text](src\main\resources\2.png)