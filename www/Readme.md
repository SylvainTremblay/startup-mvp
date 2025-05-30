Management of WebServer (NGINX)

Open a terminal connection: docker-compose exec webserver  /bin/sh -i

Restarting the server: nginx -s reload

Testing config changes: nginx -t 

Configs are located under /etc/nginx/nginx.conf

Following config was added:
http {
        ...
        client_max_body_size 4m;
        ...
}

Management of Wordpress

Open a terminal connection: docker-compose exec wordpress  /bin/sh -i    

Check php.ini config: php --ini

php.ini file was added under /usr/local/etc/php with:

upload_max_filesize = 4M

