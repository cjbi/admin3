  # 目录结构

下面是整个项目的目录结构

```
├── admin3-server                  # 后端服务模块
│   ├── src/main/java
│   │    └──tech.wetech.admin3
│   │       ├── common             # 公共目录
│   │       ├── controller         # 控制器
│   │       ├── infra              # 基础设施，存放一些中间件的配置，以及service层、repository层的一些接口的技术实现
│   │       └── sys                # 业务模块，sys业务的模块
│   ├── src/main/test              # 测试用例
│   └── pom.xml                    # 测试用例
├── admin3-ui                      # 前端模块
│   ├── public                 
│   ├── src                
│   │   ├── api                    # 请求接口定义
│   │   ├── assets                 # 本地静态资源
│   │   ├── components             # 业务通用组件
│   │   ├── router                 # 路由配置
│   │   ├── store                  # 全局存储，状态管理
│   │   ├── utils                  # 工具库
│   │   ├── views                  # 视图页面
│   │   ├── App.vue                # Vue 模板入口
│   │   └── main.ts                # Vue JS 入口
│   ├── .env                       # 环境变量
│   ├── index.html                 # 首页
│   ├── package.json               # Node.js 配置
│   ├── vite.config.ts             # Vite 配置
│   └── pom.xml
├── pom.xml                        
└── README.md                      
```

## 代码结构推荐

为了让项目代码组织更加规范，让开发能够更方便的定位到相关页面组件代码，这里定义了一套规范，该规范当前只作为推荐的指导，并非强制

### 后端

```
└──src/main/java
    └──tech.wetech.admin3
       ├── controller 
       │   └── XxxController.java           # 控制器，模块与模块间服务调用可放在控制层处理，为了提升工程的内聚力，不建议将控制器放在业务模块中
       ├── infra                            # 基础设施，存放一些中间件的配置，以及service层、repository层的一些接口的技术实现
       └── sys                              # sys业务的模块
       └── custombusiness                   # 自定义业务模块，按照业务划分新建包
           ├── event                        # 定义事件，用于业务间解耦合
           │   ├──  XxxCreated.java
           │   └──  XxxUpdated.java
           ├── exception                   
           │   ├──  XxxException.java       # 业务异常
           │   └──  XxxResultStatus.java    # 业务异常码
           ├── model
           │   └── Xxx.java                 # 业务模型
           ├── repository
           │   └── XxxRepository.java       # 仓储对象
           │
           └── service 
               ├── dto
               │   └── XxxDTO.java          # 数据传输对象
               └── XxxService.java          # 业务服务，非必要不定义接口，以避免代码膨胀
```

### 前端

TODO
