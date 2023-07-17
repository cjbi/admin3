# 常用命令

## 服务端命令

- 编译打包
```
mvn clean package
```

- 安装到本地仓库
```
mvn clean install
```
说明：如果服务端使用webjars管理前端资源，强烈建议将admin3-ui安装到本地仓库

- 运行部署
```shell
cd admin3-server/target
nohup java -jar -Dspring.datasource.url=xxx -Dspring.datasource.username=xxx -Dspring.datasource.password=xxx admin3-server-0.0.1-SNAPSHOT.jar >/dev/null 2>&1 &
```

- 打包JVM镜像
```shell
mvn spring-boot:build-image -Dmaven.test.skip=true -Ddocker.image-name=xxx -Ddocker.username=xxx -Ddocker.password=xxx
```

- 打包native镜像
```shell
 mvn -Pnative spring-boot:build-image -Dmaven.test.skip=true -Ddocker.image-name=xxx -Ddocker.username=xxx -Ddocker.password=xxx
```

- 本机测试native镜像

> 注意：本机需要安装 [graalvm](https://www.graalvm.org/latest/docs/getting-started/)

```shell
mvn clean -X package -Pnative,nativeTest
```


## 前端命令

- 安装依赖
```
yarn install
```

- 开发模式运行
```
yarn dev
```

- 编译项目
```
yarn build
```
