#!/bin/sh

# 运行环境
env=@spring.application.proflies@

# jar包名称
jar_name=@build.finalName@.jar

# 根据启动的jar包名称关闭旧的进程实例
pid=`ps -ef | grep ${jar_name} | grep -v grep | awk '{print $2}'`
echo "Wetech-Admin服务旧应用进程id：$pid"
if [ -n "$pid" ]
then
  echo "关闭Wetech-Admin服务旧进程：$pid"
  kill -9 $pid
fi

echo "启动Wetech-Admin服务..."
nohup java -jar -Dspring.profiles.active=${env} ${jar_name} >/dev/null 2>&1 &

