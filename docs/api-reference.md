# API参考

Admin3 HTTP API 基于 Resource & Action 设计，是 REST API 的超集，Action不局限于增删改查。

## URI规范

URI 结构 https://[host]:[port]/{service name}]/v{version number}/{resource}

**要求**

* 不使用大写

* 用中杠-不用下杠_

* 参数列表要encode

* URI中的名词表示资源集合，使用复数形式

## 资源

REST API 是可单独寻址的“资源”（API 中的“名词”）的“集合”。资源通过资源名称被引用，并通过一组“方法”（也称为“动词”或“操作”）进行控制。

API 的标准方法（也称为“REST 方法”）包括 List、Get、Create、Update 和 Delete。API
设计者还可以使用“自定义方法”（也称为“自定义动词”或“自定义操作”）来实现无法轻易映射到标准方法的功能。

## 方法

面向资源的 API 的关键特性是，强调资源（数据模型）甚于资源上执行的方法（功能）。典型的面向资源的 API
使用少量方法公开大量资源。方法可以是标准方法或自定义方法。对于本指南，标准方法有：List、Get、Create、Update和 Delete。

如果 API 功能能够自然映射到标准方法，则应该在 API 设计中使用该方法。对于不会自然映射到某一标准方法的功能，可以使用自定义方法。

### 标准方法

| 标准方法   | HTTP映射                                        | HTTP请求正文 | hTTP响应正文 |
|--------|-----------------------------------------------|----------|----------|
| List   | `GET <collection URL>`                         | 无        | 资源*列表    |
| Get    | `GET <collection URL>/<collection_id>`          | 无        | 资源*      |
| Create | `POST <collection URL>`                         | 资源       | 资源*      |
| Update | `PUT or PATCH <resource URL>/<collection_id>` | 资源       | 资源*      |
| Delete | `DELETE <resource URL>/<collection_id>`         | 不适用      |          |

### 自定义方法

自定义方法是指 5 个标准方法之外的 API 方法。这些方法应该仅用于标准方法不易表达的功能。通常情况下，API
设计者应该尽可能优先考虑使用标准方法，而不是自定义方法。标准方法具有大多数开发者熟悉的更简单且定义明确的语义，因此更易于使用且不易出错

对于自定义方法，应该使用以下通用HTTP映射：

```
https://service.name/v1/some/resource/name:customVerb
```

以下为自定义方法示例

| 方法名称     | 自定义动词     | HTTP动词 | 备注                            |
|----------|-----------|--------|-------------------------------|
| Cancel   | :cancel   | POST   | 取消一个未完成的操作                    |
| BatchGET | :batchGet | GET    | 批量获取多个资源                      |
| Move     | :move     | POST   | 将资源从一个父级移动到另一个父级              |
| Search   | :search   | GET    | List 的替代方法，用于获取不符合 List 语义的数据 |
| Undelete | :undelete | POST   | 恢复之前删除的资源 |

注意：上面的情况指的是API名称；HTTP/JSON URI 后缀使用 :lowerCamelCase。

## 请求

### 身份认证

**请求登录接口获得Token**

```
curl -X 'POST' \
'http://localhost:8080/login' \
-H 'accept: */*' \
-H 'Content-Type: application/json' \
-d '{
"username": "admin",
"password": "123456"
}'
```

**在请求头加入Token**

Token规则 Authorization: Bearer + {Token}

```
curl -X 'GET' \
  'http://localhost:8080/admin3/users?size=20&page=1&state=NORMAL' \
  -H 'accept: */*' \
  -H 'Authorization: Bearer c023825e63104d079766b66b7e950da9'
```

### 请求参数

请求的参数可以放在 Request 的 headers、parameters（query string）、body（GET 请求没有 body） 里

几个特殊的 Parameters 请求参数

* `page`  要检索的分页，默认值为1
* `size`  要检索的页数，默认值为20
* `sort`  对结果进行排序的一个或多个属性，使用以下格式：property1，property2 (,asc|desc) –例如，?sort=name&sort=email,asc

## 响应

### HTTP状态码

| 状态码 | 说明                                    |
|-----|---------------------------------------|
| 200 | Ok, 服务器成功返回的数据                        |
| 201 | Created, 用户新建或修改数据成功                  |
| 400 | Bad Request, 用户发出的请求有错误，例如参数校验失败等     |
| 401 | Unauthorized, 用户未取得授权 （未登录、密码错误、账户停用） |
| 403 | Forbidden, 用户被禁止访问资源                  |
| 404 | Not Found, 请求的记录不存在                   |
| 500 | Internal Server Error, 系统级别错误，不可恢复的异常 |

### 响应体

正常响应的 body 直接就是数据，不要做多余的包装。只有当出错时，才需要进行包装，错误示例：

```json
{
  "code": 1002,
  "message": "参数非法",
  "data": {},
  "errors": [{"username":"不能为空"}]
}
```

**说明**

| 属性      | 类型     | 是否必须 | 说明   |
|---------|--------|------|------|
| code    | number | 是    | 业务状态码 |
| message | string | 是    | 业务消息 |
| data    | any    | 否    | 异常数据 |
| errors  | array  | 否    | 错误列表 |

 **业务状态码**

| 状态码  | 说明      |
|------|---------|
| 1001 | 失败      |
| 1002 | 参数非法    |
| 1003 | 记录不存在   |
| 1004 | 未授权     |
| 1005 | 禁止访问    |
| -1   | 服务器内部错误 |

对于分页接口,可能要返回总条数, 结构如下：
```json lines
{
  "list": [
    {
      "id": 1
    },
    {
      "id": 2
    }
  ],
  "total": 2
}
```

## 示例

以角色、用户为例：

* 获取单个角色 `GET /roles/{roleId}`

* 获取角色列表 `GET /roles`

* 新增角色 `POST /roles`

* 更新角色 `PUT /roles/{roleId}`

* 删除角色 `DELETE /roles/{roleId}`

* 获取角色下的所有用户 `GET /roles/{roleId}/users`

* 禁用用户 POST `/users/{userId}:disable`

* 启用用户 POST `/users/{userId}:enable`
