# Microsoft SQL Serverの環境構築方法
## Dockerを用いた手順
### Dockerのインストール
https://www.docker.com/products/docker-desktop

### イメージの取得
```bash
$ docker pull mcr.microsoft.com/mssql/server:2019-latest
```

### コンテナの作成
```bash
$ docker create --name mssql-visutter -e 'ACCEPT_EULA=Y' -e 'SA_PASSWORD=yourStrong(!)Password' -p 1433:1433 mcr.microsoft.com/mssql/server:2019-latest
```

### コンテナの開始
```bash
$ docker start mssql-visutter
```

### コンテナの起動
```bash
$ docker run --name mssql-visutter -e 'ACCEPT_EULA=Y' -e 'SA_PASSWORD=yourStrong(!)Password' -p 1433:1433 -d mcr.microsoft.com/mssql/server:2019-latest
```

### コンテナ内でのbashコマンド実行
```bash
$ docker exec -it mssql-visutter bash
```

### MSSQLサーバに接続
```bash
$ docker exec -it mssql-visutter /opt/mssql-tools/bin/sqlcmd -S localhost -U sa -P 'yourStrong(!)Password'
```

### コンテナ停止
```bash
$ docker stop mssql-visutter
```

### コンテナの削除
```bash
$ docker rm --force mssql-visutter
```

## 参考
https://hub.docker.com/_/microsoft-mssql-server