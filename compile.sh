cd tg-admin-page/
npm i
npm run-script build
cd ..
cp tg-admin-page/dist/tg-admin-page/* src/main/resources/static/admin/
./gradlew bootJar
mkdir builded-application
cp build/libs/* builded-application/
cp --recursive resources/ builded-application/
