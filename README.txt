При первом запуске в Докере необходимо создать сеть
docker network create book-shop

Для сборки пода с БД postgres выполнить
docker-compouse up -d

Приложение собирается через maven
docker образ собирается при сборке приложения
mvn clean install -Pbuild-docker

для запуска в Докере
docker run -d --name=book-shop-server --net=book-shop -p 6551:9876 -e "SPRING_PROFILES_ACTIVE=dev-local-docker" book-shop/book-shop-server