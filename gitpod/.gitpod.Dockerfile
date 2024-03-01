FROM gitpod/workspace-full

USER root

RUN bash -c ". /home/gitpod/.sdkman/bin/sdkman-init.sh && \
    sdk install java 21.0.1-tem && \
    sdk default java 21.0.1-tem"

# Install MySQL
RUN install-packages mysql-server \
 && mkdir -p /var/run/mysqld /var/log/mysql \
 && chown -R gitpod:gitpod /etc/mysql /var/run/mysqld /var/log/mysql /var/lib/mysql /var/lib/mysql-files /var/lib/mysql-keyring /var/lib/mysql-upgrade

# Install our own MySQL config
COPY gitpod/mysql/mysql.cnf /etc/mysql/mysql.conf.d/mysqld.cnf

# Install default-login for MySQL clients
COPY gitpod/mysql/client.cnf /etc/mysql/mysql.conf.d/client.cnf

COPY gitpod/mysql/mysql-bashrc-launch.sh /etc/mysql/mysql-bashrc-launch.sh

RUN install-packages mysql-server \
  && chown -R gitpod:gitpod /etc/mysql\
  && chmod +x /etc/mysql/mysql-bashrc-launch.sh

USER gitpod

RUN echo "/etc/mysql/mysql-bashrc-launch.sh" >> /home/gitpod/.bashrc.d/100-mysql-launch
