[English](README_en.md)
## 无纺布溯源管理系统
本项目为实现无纺布生产过程中进行溯源的条码管理系统。在本地部署Java后端之后，使用Android app即可开始使用
## 开始使用
### 前置条件
本项目需要使用Docker。安装docker之后，配置一个名为 "product"的数据库，设置端口为5432。随后需要设置Java服务器，将后端项目导入至Java的IDE中并开始运行。
由于在前端项目的res/xml/network_security_config路径下并没有添加动态的ip地址，因此需要把Android设备与Java后端设备处于同一局域网下，并在这个目录下添加后端的IP地址。
### 安装Android App
通过Android Studio®把此App直接推送至Android设备上，确保后端服务器处于开启状态，之后可以打开App并使用其所有功能
## Android App使用说明
### 扫描条形码
在 APP 的启动界面选择“扫描条形码”，将手机摄像头对准条形码后，即可访问响应信息。
<div style="display: flex; justify-content: space-around;">
 <img src="https://github.com/yangyang03-dev/nonwoven-production-traceability-management-system/blob/main/assets/camera.png" alt="扫码界面" width="400" >
</div>

### 查看溯源信息并根据原材料ID管理原材料
由于在溯源信息界面已经与原材料相关信息进行了关联，因此可以点击“原材料管理”栏下面的纤维原材料ID或粘合剂原材料ID直接跳转至原材料管理界面。原材料的管理一共包括查看信息、删除原材料以及新增原材料共 3 个方面。以上显示了溯源信息以及原材料信息，并有一个红色的“删除此原材料”的按钮，执行此按钮的操作需要管理员的权限。 
在原材料的缩略列表界面，有一个“添加新的原材料”的按钮，此按钮的操作也需要在登录管理员身份之后进行。下图是登录时的提示窗口以及登录后的界面
<div style="display: flex; justify-content: space-around;">
<img src="https://github.com/yangyang03-dev/nonwoven-production-traceability-management-system/blob/main/assets/tracability.jpg" alt="管理界面" width="400" >
</div>

### 添加新的原材料
此APP还配置了向后端服务器添加新的原材料的功能，在如下界面中添加新的原材料，
需要在每个项目中填写内容，填写完毕后即可看到原材料被成功添加 
<div style="display: flex; justify-content: space-around;">
<img src="https://github.com/yangyang03-dev/nonwoven-production-traceability-management-system/blob/main/assets/create.jpg" alt="添加界面" width="400">
</div>
