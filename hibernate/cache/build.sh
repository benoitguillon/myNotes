#!/bin/sh

mvn clean package -DskipTests=true -Pcluster

cp target/cache.war src/main/docker/hibernate_cache_demo

docker build -t hibcachedemo src/main/docker/hibernate_cache_demo

rm src/main/docker/hibernate_cache_demo/cache.war
