FROM golang:1.17

WORKDIR /usr/src/app

ENV GOPROXY https://goproxy.cn,direct

COPY go.mod .
COPY go.sum .
RUN go mod download && go mod verify

COPY main/main.go .
RUN go build -v -o /usr/local/bin/app ./

ENV FLAG flag{4n_34sy_s1gn_1n_1s_n0t_1t?m4yb3}

CMD ["app"]