FROM    node:18-alpine

WORKDIR /app

COPY    package.json package-lock.json
COPY    . .

RUN     npm install --legacy-peer-deps
RUN     npm install -g @angular/cli

CMD     ["ng", "serve", "--host", "0.0.0.0"]
