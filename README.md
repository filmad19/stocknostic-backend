# stocknostic

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## Setup projects

1. Go to: file > new > project from version control

2. Then copy the frontend repository link and create a new folder to save the project.
(should be in the same folder as the 'stocknostic-backend')

3. In the stocknostic-backend project
go to: file > new > module from existing source

- If you cannot see the frontend project and the backend project restart Intellij

## Running the application

### Database

You need to have docker installed 

Go into the console in the backend folder and start the docker container

```shell script
\projects\stocknostic\stocknostic-backend> docker-compose up -d
```

If you want to connect to the database with IntelliJ use the postgres Database with these configurations:
```shell script
POSTGRES_USER: postgres
POSTGRES_PASSWORD: postgres
PORT: 27017
```

### Backend

Start the backend using the GUI or go into the console in the backend folder and start the quarkus backend
```shell script
\projects\stocknostic\stocknostic-backend> ./mvnw compile quarkus:dev
```

### Frontend

You will need npm and node.js to run the angular project

First you need to install all the dependencies to run the frontend
```shell script
\projects\stocknostic\stocknostic-frontend> npm install
```

Then you have to install ionic, which is the framework we use
```shell script
\projects\stocknostic\stocknostic-frontend> npm install -g @ionic/cli
```

start the frontend using the GUI or go into the console in the frontend folder and start the frontend
```shell script
\projects\stocknostic\stocknostic-frontend> ionic serve
```

the browser should be opened automatically. If to search for http://localhost:8100/ to see the webApp.
Press F12, make the view responsive and select a phone to see the app

Now everything should work!
--
American stock markets are opened at 15:30 (our time). So you might not see the live update of the stocks.
Therefor we recommend searching crypto currencies (e.g. BTC-USD) to see this feature.