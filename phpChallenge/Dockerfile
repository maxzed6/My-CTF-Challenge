FROM ubuntu:20.04

ENV DEBIAN_FRONTEND=noninteractive

COPY ./source.list /etc/apt/sources.list

RUN     echo 'nameserver 8.8.8.8' >> /etc/resolv.conf &&\
        apt-get update -y && \
        apt-get install -y php apache2 mysql-server vim curl gcc && \
        apt-get install -yqq php-mysql php-gd php-xml

COPY ./apache2.conf /etc/apache2/apache2.conf
COPY ./000-default.conf /etc/apache2/sites-enabled/000-default.conf
COPY ./readflag.c /
COPY ./flag /
COPY ./start.sh /

RUN rm /var/www/html/*
COPY ./tp /var/www/html/

RUN \
        chown -R root:root /var/www/html/ && \
        chmod -R 777 /var/www/html
RUN \
        chown root:root /flag && \
        chmod 600 /flag && \
        gcc /readflag.c -o /readflag && \
        chmod +s /readflag && \
        rm /readflag.c

CMD ["bash","/start.sh"]
EXPOSE 80
