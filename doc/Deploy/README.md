# Visutterのデプロイ方法
いちいち下記の手順を実行するのは、めんどくさいしヒューマンエラーの要因にもなり得る。
自動化の手法は要検討。

## 手順
### 1. frontendをビルド
frontendディレクトリに移動
```bash
$ cd {frontend dir}
```

ビルドコマンド実行
```bash
$ yarn build
```

### 2. backendをビルド
backendディレクトリに移動
```bash
$ cd {backend dir}
```

パッケージ生成
```bash
$ mvn clean package
```

### 3. コンテナ実行
Docker Image作成
```bash
$  docker build -t visutter .
```

コンテナが存在している場合は削除
```bash
$ docker ps -a
$ docker stop visutter-container
$ docker rm visutter-container
```

Imageからコンテナ作成&実行
```bash
$ docker run -t -p 8080:8080 --name visutter-container visutter
```
