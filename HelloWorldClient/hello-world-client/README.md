1. Install node: 
sudo apt install nodejs

node -v

2. Install npm (usr/bin/node): 
sudo apt install npm

3. Install create-react-app: 
npm i -g create-react-app

4. Get all components: 
cd <root project folder - hello-world-client>

npm install

5. Run project: 
npm start

or Run via docker:

5. Publish new local docker container: 
cd <root project folder - hello-world-client

sudo docker build . -t react-docker

6. Run local docker container: 
docker run -p 3000:3000 react-docker
