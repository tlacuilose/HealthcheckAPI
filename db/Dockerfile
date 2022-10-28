# MariabDB 10.3 with SSH
FROM mariadb:latest

# List packages
ENV PACKAGES openssh-server openssh-client

# Install packages
RUN apt-get update && apt-get install -y $PACKAGES

# Allow SSH Root Login
RUN sed -i 's|^#PermitRootLogin.*|PermitRootLogin yes|g' /etc/ssh/ssh_config

# Configure root password
RUN echo "root:root1234" | chpasswd
