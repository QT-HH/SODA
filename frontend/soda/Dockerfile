FROM node:lts-alpine as build-stage
WORKDIR /soda
COPY . .
RUN npm install
RUN npm run build


FROM nginx:stable-alpine as production-stage
RUN rm /etc/nginx/conf.d/default.conf
COPY ./nginx/soda.conf /etc/nginx/conf.d/default.conf
COPY --from=build-stage ./soda/dist /usr/share/nginx/html/soda
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
