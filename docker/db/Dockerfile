FROM mysql
ENV MYSQL_ROOT_PASSWORD=my-secret-pw
WORKDIR /usr/local/bin
ADD init.sql /usr/local/bin
EXPOSE 3306/tcp