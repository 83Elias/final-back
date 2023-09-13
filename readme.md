# Primer Parcial Backend

en el .zip estan las pruebas funcionales de como se realizaron las pruebas de la solucion, adicional a eso los proyectos se ejecutan con java 11, si requieres que los proyectos se ejecuten con java 17 
entra al archivo pom.xml y modifica  el siguente atributo:

```
<properties>
        <java.version>11</java.version>
</properties>
```

cambie el 11 por la version 17, le deberia quedar como en el siguente ejemplo

```
<properties>
        <java.version>17</java.version>
</properties>
```

actualice el proyecto maven para que tome los cambios y listo.


tener en cuenta que el usuario para hacer las pruebas creado en el reino es el siguente

Usuario Normal
usuario: normaluserparcial

password: 12345

Usuario Admin

usuario2: adminuser
password: 123

para el clienteID configurado en el gateway, tiene el siguente nombre para que lo pueda detectar facilmente y obtener el secret access

nombre del Cliente: api-gateway-client


NOTA: en el realm va a encontrar 2 roles USER y ADMIN respectivamente


# Gateway Api y Bills Service
en el gateway api en el .properties  encontrara la siguente configuracion:

```
# Configuraciones del servidor
server:
  port: 8090
# Configuraciones de eureka
eureka:
  instance:
    hostname: localhost
    prefer-ip-address: true
  client:
    register-with-eureka: true
    fetch-registry: true
    serviceUrl:
      defaultZone: http://localhost:8761/eureka
# Configuraciones de spring cloud
spring:
  application:
    name: ms-gateway
  cloud:
    gateway:
      default-filters:
        - TokenRelay
      routes:
        - id: ms-bill
          uri: lb://ms-bill
          predicates:
            - Path=/api/v1/**
  security:
    oauth2:
      client:
        provider:
          api-gateway-service:
            issuer-uri: http://localhost:8080/realms/parcial-back-end
        registration:
          api-gateway-service:
            client-id: api-gateway-client
            scope: openid
            client-secret: jAzDKi5wy9qYWfRarOGyypa4KbkGy2s0
            authorization-grant-type: authorization_code
            redirect-uri: http://localhost:8090/login/oauth2/code/keycloak

```
donde dice client-secret debe colocar el secre del cliente api-gateway-client del realms, para el micro-servicio de Bills, si usted esta usando un puerto diferente al 8080 de keycloak, debera configurar la etiqueta issuer-uri con el puerto de keyloak de su docker

```
spring:
  application:
    name: ms-bill
  security:
    oauth2:
      resourceserver:
        jwt:
          issuer-uri: http://localhost:8080/realms/parcial-back-end
          jwk-set-uri: ${spring.security.oauth2.resourceserver.jwt.issuer-uri}/protocol/openid-connect/certsspring:
  application:
    name: ms-bill
  security:
    oauth2:
      resourceserver:
        jwt:
          issuer-uri: http://localhost:8080/realms/parcial-back-end
          jwk-set-uri: ${spring.security.oauth2.resourceserver.jwt.issuer-uri}/protocol/openid-connect/certs

```


# Base de datos

para la base de dato se uso H2, el cual se puede descargar en el siguente link:  https://www.h2database.com/html/download.html  ,  guia de uso de h2 https://www.h2database.com/html/quickstart.html.
si al correr el servicio bills, no se ejecutan los script de base de datos schema.sql y data.sql, debera ejecutarlo manualmente en la consola de h2.

los script se encuentran en la ruta: "\ms-bills\src\main\resources\schema.sql" y  "\ms-bills\src\main\resources\data.sql"



nota: para consumir el endpoint bills desde el gateway pegue la siguente url en su navegador: http://localhost:8090/api/v1/bills/all
 

sss