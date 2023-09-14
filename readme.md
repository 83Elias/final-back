# Primer Parcial Backend (Leer el contenido actualizado en la sesion Final Parcial Backend)

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


# Final Parcial Backend

para este entrega final se agrega el micro-servicio de ms-user que en este caso consumira los usuarios registrados,
ademas de lo anterior se agrega el endpoint de consulta por id de usuario y eliminacion por id de factura.

antes de empezar tener en cuenta que el reino (Reaml) actualizado se encuentra en la ruta

**realm-reino > parcial final > reino-parcial-final.json**

al igual que el reino, en el proyecto se encuentra una carpeta con las colecciones postman
para que usted pueda usar los endpoints creados esta ubicada en la carpeta

** postman coleccion > bills-parcial.postman_collection.json ***

nota: tener en cuenta que el reino tiene el siguente nombre **parcial-back-end** , si tiene un reino con el mismo nombre eliminarlo
y usar el actualizado.


# Usuarios de pruebas parcial final

en el reino se hay 3 usuarios creados

#1 el primer usuario contiene el rol de USER

#2 el segundo usuario esta asignado al grupo PROVIDERS

#3 el tercer usuario no tiene el rol ni esta asignado a un grupo simplemente es un usuario normal


para el **primer** usuario debe usar las siguentes credenciales

```
ID: c25f6863-d802-4c23-b6e0-99c68f289eb3
User: normaluserparcial
password: 12345
Privilegio: ROLE_USER
```

para el **segundo** usuario debe usar las siguentes credenciales

```
ID: 746c225d-63d6-4440-92d8-5657ea6b287e
User: usertest1
password: 1234
Privilegio: PROVIDER
```

para el **tercer** usuario debe usar las siguentes credenciales

```
ID: 1f841eea-d6ed-4e13-b9cc-65569cab0785
User: usertest2
password: 12345
Privilegio: NINGUNO
```

# Clientes 

para los clientes creados en existen 2 clientes disponibles actualmente los cuales son:

```
Client: ms-user
secret: hOIPchDdv7MiaQfx69I3xtAbRCNDIT5X
```
y para el gateway api es:
```
Client: api-gateway-client
secret: jAzDKi5wy9qYWfRarOGyypa4KbkGy2s0
```
al igual que en el primer parcial los proyectos fueron configurados para ejecutarse con java 11 y no con 17
si desea que se ejecute con java 17 realize el cambio de version de ejecucion del proyecto en la siguente propiedad del pom.xml

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


#Base de Datos

antes de empezar a probar la solucion, debe abrir su base de datos H2 y ejecutar los siguentes script de base de datos
en el campo CUSTOMER_BILL como puede ver se esta colocando el ID de keyloak del cliente 
en este caso el id **746c225d-63d6-4440-92d8-5657ea6b287e** corresponde al usuario usertest1
y el id **1f841eea-d6ed-4e13-b9cc-65569cab0785** corresponde al usuario  usertest2
```
INSERT INTO public.bill (ID_BILL, CUSTOMER_BILL, PRODUCT_BILL, TOTAL_PRICE)
VALUES (RANDOM_UUID(), '746c225d-63d6-4440-92d8-5657ea6b287e', 'courses/Java', 500);

INSERT INTO public.bill (ID_BILL, CUSTOMER_BILL, PRODUCT_BILL, TOTAL_PRICE)
VALUES (RANDOM_UUID(), '746c225d-63d6-4440-92d8-5657ea6b287e', 'courses/Java', 500);

INSERT INTO public.bill (ID_BILL, CUSTOMER_BILL, PRODUCT_BILL, TOTAL_PRICE)
VALUES (RANDOM_UUID(), '746c225d-63d6-4440-92d8-5657ea6b287e', 'courses/Java', 500);

INSERT INTO public.bill (ID_BILL, CUSTOMER_BILL, PRODUCT_BILL, TOTAL_PRICE)
VALUES (RANDOM_UUID(), '1f841eea-d6ed-4e13-b9cc-65569cab0785', 'courses/Java', 500);

INSERT INTO public.bill (ID_BILL, CUSTOMER_BILL, PRODUCT_BILL, TOTAL_PRICE)
VALUES (RANDOM_UUID(), '1f841eea-d6ed-4e13-b9cc-65569cab0785', 'courses/Java', 500);

INSERT INTO public.bill (ID_BILL, CUSTOMER_BILL, PRODUCT_BILL, TOTAL_PRICE)
VALUES (RANDOM_UUID(), '1f841eea-d6ed-4e13-b9cc-65569cab0785', 'courses/Java', 500);

```

en caso que usted tenga un request id diferente, reemplazar los valores anteriores de la columna
mencionada, en caso que desee modificar el registro usar el siguenet script ejecute una sentencia
UPDATE

```
UPDATE table_name
SET column1 = value1, column2 = value2, ...
WHERE condition;
```

nota: el script tambien lo encuentra en la carpeta resource del proyecto ms-bills