### 介绍
基于微服务的在线学习平台，管理员可以发布课程相关信息，用户可以查看课程的详细信息并购买课程。

### 主要功能
1. 管理员可以添加新的讲师信息，发布新的课程。
2. 用户可以查看课程的详细信息，包括课程分类、授课讲师、章节信息、课时信息、销售数量等，可以根据选择进行购买。
3. 使用腾讯云进行对象存储，将课程具体的视频存放在云端。
4. 将服务拆分成了多个子模块，使用了Nacos作为注册中心，使用Open Feign实现服务间的调用。

### 技术栈
微服务:
- Spring Cloud和Spring Cloud Alibaba框架
- Nacos 注册中心
- Spring Cloud Open Feign远程调用
- Spring Cloud Gateway微服务网关
- Maven父子多模块

数据存储层:
- MySQL:存储数据
- MyBatis Plus:数据访问框架

工具库:
- Easy Excel:读写Excel文件
- fastjson: json 序列化
- httpclient:请求客户端
- Swagger + Knife4j接口文档
