docker-compose exec db  /bin/sh -i

mysql -u root -p

mysql> GRANT SELECT, LOCK TABLES, SHOW VIEW, TRIGGER ON wordpress.* TO 'smvp_wp_admin';

mysql> GRANT PROCESS ON *.* TO smvp_wp_admin;

mysql> FLUSH PRIVILEGES;

mysqldump -u smvp_wp_admin -p wordpress > wordpress-backup.sql

docker cp smvp-wordpress-db:/wordpress-backup.sql wordpress-backup.sql

scp -i ./aws-wordpress.pem ./wordpress-backup.sql ubuntu@3.145.102.244:/tmp/wordpress-backup.sql
wordpress-backup.sql


# restore
mysql -u smvp_wp_admin -p wordpress < wordpress-backup.sql
