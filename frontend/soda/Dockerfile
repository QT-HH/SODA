FROM node:lts-alpine as build-stage
WORKDIR /hoempage
COPY . .
RUN npm install
RUN npm run build


FROM nginx:stable-alpine as production-stage
RUN rm /etc/nginx/conf.d/default.conf
COPY ./nginx/hoempage.conf /etc/nginx/conf.d/default.conf
COPY --from=build-stage ./hoempage/dist /usr/share/nginx/html/hoempage
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
