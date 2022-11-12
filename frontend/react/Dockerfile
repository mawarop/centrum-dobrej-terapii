# pull official base image
FROM node:13.12.0-alpine

# set working directory
WORKDIR /app

# add `/app/node_modules/.bin` to $PATH
ENV PATH /app/node_modules/.bin:$PATH

# install app dependencies
COPY package.json ./
COPY package-lock.json ./
RUN npm install --silent
RUN npm install react-scripts@3.4.1 -g --silent

# add app
COPY src ./src
COPY public ./public
EXPOSE 3000
# start app
RUN #npm config set proxy http://server:8080
CMD ["npm", "start"]
#CMD ["echo", "xd"]
#CMD ["nc", "-l", "5000"]