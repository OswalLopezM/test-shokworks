#!/bin/bash
# vars
backups_path="/Users/administrador/Oswaldo/test/backups"
database="testshokworks"
current_date_time="`date +%Y%m%d.%H%M%S`";
name_project="test"
# dump
pg_dump $database > $backups_path/$name_project$database-$current_date_time.sql;
# se obtiene el tamano del backup
size="`wc -c $backups_path/$name_project$database-$current_date_time.sql`";
# obtiene el backup mas viejo
first_backup="`ls $backups_path | sort -n | head -1`"
echo 'first backup '$first_backup
# obtiene el tamano del backup mas viejo
size_first="`wc -c $backups_path/$first_backup`"
echo 'size_first '$size_first
# cantidad de backups
backups_count="`find  $backups_path/*.sql -type f | wc -l`"
echo 'backups_count '$backups_count
# Imprime en un .txt informacion del backup
echo 'Subject:'$size >  $backups_path/$name_project$database-$current_date_time.txt
# En caso de ser mas de 15 backups se borrara el ultimo
if [ $backups_count -ge 16 ] ; then
 echo 'Greather than 16'
# borra el backup
 rm $backups_path/$first_backup
 first_text="`ls $backups_path | sort -n | head -1`"
# borra el texto del backup
 rm $backups_path/$first_text
# Imprime en el archivo .txt la informacion del backup eliminado
 printf "\nArchivo borrado: "$first_backup >>  $backups_path/$name_project$database-$current_date_time.txt
 printf "\nPeso: "$size_first >>  $backups_path/$name_project$database-$current_date_time.txt
fi