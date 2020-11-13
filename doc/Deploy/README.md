# Visutterのデプロイ方法
理想はGitHubへPushを検知してDeploy

## 手順
### 1. frontendとbackendをビルド
backendディレクトリに移動
```bash
$ cd {backend dir}
```
シェルスクリプト実行
```bash
$ chmod 744 build.sh (権限付与のため初回のみ実行)
$ ./build.sh
```

### 2. ACRへImageをPush
```bash
$ az acr build --registry visutterregistry --image visutterimage .
```
Azure App ServiceでACRのvisutterimageのWebHookを作成しているため、ACRへのPushのみでDeployされる。

### その他. ローカルでコンテナ実行して確認する場合
Docker Image作成
```bash
$  docker build -t visutter .
```

Imageからコンテナ作成&実行
```bash
$ docker run -t -p 80:80 --name visutter-container visutter
```
