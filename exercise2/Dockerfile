FROM node:18-alpine3.16 as prepare
WORKDIR /workspace
COPY . .
RUN npm ci

FROM prepare AS test
RUN CI=true npx nx test

FROM test AS build
RUN CI=true npx nx build

FROM nginx:1.22.0
COPY --from=build /workspace/dist/apps/demo /usr/share/nginx/html
