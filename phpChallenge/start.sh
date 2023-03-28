#!/bin/bash
service mysql start
mysqladmin -u root password "root"

service apache2 start
tail -f /var/log/apache2/access.log