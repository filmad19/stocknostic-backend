# stocknostic

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## Running the application

### Database

if you want to connect to the database with IntelliJ use:
```shell script
POSTGRES_USER: postgres
POSTGRES_PASSWORD: postgres
PORT: 27017
```

go into the console in the backend folder and start the docker container

```shell script
\projects\stocknostic\stocknostic-backend> docker-compose up -d
```

### Backend

start the backend using the GUI or go into the console in the backend folder and start the quarkus backend
```shell script
\projects\stocknostic\stocknostic-backend> ./mvnw compile quarkus:dev
```

### Frontend

start the frontend using the GUI or go into the console in the frontend folder and start the frontend
```shell script
\projects\stocknostic\stocknostic-frontend> ionic serve
```

the browser should be opened automatically. If to search for http://localhost:8100/ to see the webApp.
Press F12, make the view responsive and select a phone to see the app

Now everything should work!
--
American stock markets are opened at 15:30 our time. So you migth not see the live update of the stocks.
Therefore we recommend searching crypto currencies (e.g. BTC-USD) to see this feature.