# test-shokworks

### Documentacion
La documentación del proyecto con swagger esta en la dirección http://localhost:8080/swagger-ui.html 

Para agregar el JWT se debe escribir "Bearer {{token}}"

### Base de datos
Se utilizo la base de datos PostgreSQL, la base de datos esta alojada en heroku, en caso de correr local por favor agregar los siguientes roles:

```
INSERT INTO role(name) VALUES('ROLE_USER');
INSERT INTO role(name) VALUES('ROLE_MODERATOR');
INSERT INTO role(name) VALUES('ROLE_ADMIN');
```

### Backup

Para el backup se creo un script ubicado en la carpeta backup/backup.sh el cual genera un archivo .sql y un archivo .txt que indica la ruta del nuevo respaldo y en caso de borrarse algun respaldo por haber mas de 15 indica el nombre y el peso.

Ademas se crea un cronjob que ejecute el archivo .sh todas las noches a las 2 am

```
crontab -e

```
Y se agrega a la lista de tareas la siguiente linea

```
0 2 * * * {{ruta donde esta el .sh}}/backup.sh

```