FROM postgres:latest
ADD simmapdatabase.sql /docker-entrypoint-initdb.d/
ENV POSTGRES_DB simmapdatabase
ENV POSTGRES_USER <username>
ENV POSTGRES_PASSWORD <password>