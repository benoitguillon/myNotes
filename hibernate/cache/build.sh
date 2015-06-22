#!/bin/sh

mvn clean package -DskipTests=true

cp target/cache.war src/main/docker/hibernate_cache_demo

sudo docker build -t hibcachedemo src/main/docker/hibernate_cache_demo

rm src/main/docker/hibernate_cache_demo/cache.war
