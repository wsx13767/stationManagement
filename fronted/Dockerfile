FROM node:14.5.0-alpine
COPY ./ ./app
WORKDIR /app
RUN npm i
ENTRYPOINT ["npm", "run", "start"]