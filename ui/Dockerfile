FROM node:14-alpine

ENV PORT 3003

RUN mkdir -p /usr/src/app
WORKDIR /usr/src/app

COPY package.json /usr/src/app
# COPY package-lock.json /usr/src/app

COPY . /usr/src/app

RUN npm install

RUN npm run build

EXPOSE 3000

ENTRYPOINT ["npm", "run", "dev"]