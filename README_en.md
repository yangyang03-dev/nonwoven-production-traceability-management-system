[中文](README.md)
## Nonwoven Production Traceability Management System
This is a barcode-management-system project for traceability of nonwoven production. You can start to use an Android app after deploying Java backend on your local machine.
## Start
### Prerequisites
You will need Docker for this project. First, configure a database with naming "product", then set the port to 5432. After that, configure your Java server, run your Java backend project.
In the frontend Android Studio project, under the directory res/xml/network_security_config we used a fixed address. Thus, you will have to make sure Android device and Java backend server is in the same local network, and then add the address to this directory.
### Install Android App
Push the app to your Android device via Android Studio®, you have to confirm the backend server is running. After that, you can use all the functionalities of app.
## How to use Android App
### Scan barcode
At the start screen, choose “扫描条形码” (scan barcode), enable your phone camera and look up for specific information.
<div style="display: flex; justify-content: space-around;">
 <img src="https://github.com/yangyang03-dev/nonwoven-production-traceability-management-system/blob/main/assets/camera.png" alt="扫码界面" width="400" >
</div>

### Look up traceability information and manage materials with ID
Info of materials have been linked in the traceability screen, you can click the 纤维原材料ID(Materials of Fibre ID) or 粘合剂原材料ID(Materials of Adhesive ID) below the “原材料管理”(Material Management) column and jump to the management screen. Management includes look up, delete and create. The above shows traceability information and material information, equipped with a "删除此原材料" (Delete this material) button, you need admin permission to operate this. 
 At the abbreviated list screen, you can see “添加新的原材料”(Add new materials) button, you need admin permission to operate this. Image below shows the prompt when logging in and the status of logged.
<div style="display: flex; justify-content: space-around;">
<img src="https://github.com/yangyang03-dev/nonwoven-production-traceability-management-system/blob/main/assets/tracability.jpg" alt="管理界面" width="400" >
</div>

### Add new materials
You can also try to add new materials to backend server, as shown below. Fill in each entry, and new materials would be successfully added.
<div style="display: flex; justify-content: space-around;">
<img src="https://github.com/yangyang03-dev/nonwoven-production-traceability-management-system/blob/main/assets/create.jpg" alt="添加界面" width="400">
</div>
