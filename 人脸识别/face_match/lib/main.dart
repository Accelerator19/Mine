import 'dart:convert';

import 'package:flutter/material.dart';
import 'dart:io';
import 'dart:async';
import 'package:image_picker/image_picker.dart';
import 'package:dio/dio.dart';


void main() => runApp(MyApp());

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new MaterialApp(
      title: 'Startup Name Generator',
      theme: new ThemeData(
        primaryColor: Colors.lightBlue,
      ),
      home: new ImagePickerPage(),
    );
  }
}

class ImagePickerPage extends StatefulWidget {
  ImagePickerPage({Key key}) : super(key: key);

  _ImagePickerPageState createState() => _ImagePickerPageState();
}

class _ImagePickerPageState extends State<ImagePickerPage> {
  //记录选择的照片
  File _image;

  File _imageTwo;


  String eyesType;

  String mouthType;

  String noseType;

  String jawType;

  String faceupResult;

  String facemidResult;

  String facedownResult;

  String eyebrowType;

  String faceType;

  String eyeinResult;



  //拍照
  Future _getImageFromCamera() async {
    var image = await ImagePicker.pickImage(source: ImageSource.camera, maxWidth: 400);

    setState(() {
      if(_image == null){
        _image = image;
      }else if(_imageTwo == null){
        _imageTwo = image;
      }

    });
  }

  //相册选择
  Future _getImageFromGallery() async {
    var image = await ImagePicker.pickImage(source: ImageSource.gallery);

    setState(() {
      if(_image == null){
        _image = image;
      }else if(_imageTwo == null){
        _imageTwo = image;
      }
    });
  }

  //清空
  void _removeFile() {
    setState(() {
      _image = null;
      _imageTwo = null;
      eyesType = null;
      mouthType = null;
      noseType = null;
      jawType = null;
      facedownResult = null;
      faceupResult = null;
      facemidResult = null;
      eyebrowType = null;
      faceType = null;
      eyeinResult = null;
    });
  }

  static String currentTimeMillis() {
    return new DateTime.now().millisecondsSinceEpoch.toString();
  }

  //上传图片到服务器
  _uploadImage() async {

    FormData formData = FormData.from({
      "multipartFiles": [
        UploadFileInfo(_image, "imageOne.png"),
        UploadFileInfo(_imageTwo, "imageTwo.png")
      ]
    });
    var response =
        await Dio().post("http://59.110.227.134:8080/upload", data: formData);
    print(response);
    if (response.statusCode == 200) {
      Map responseMap = json.decode(response.data);
      print("${responseMap["eyesType"]}");
      setState(() {
        if(responseMap["eyesType"] != null){
          eyesType = "${responseMap["eyesType"]}";
        }
        if(responseMap["mouthType"] != null){
          mouthType = "${responseMap["mouthType"]}";
        }
        if(responseMap["noseType"] != null){
          noseType = "${responseMap["noseType"]}";
        }
        if(responseMap["jawType"] != null){
          jawType = "${responseMap["jawType"]}";
        }
        if(responseMap["faceupResult"] != null){
          faceupResult = "${responseMap["faceupResult"]}";
        }
        if(responseMap["facemidResult"] != null){
          facemidResult = "${responseMap["facemidResult"]}";
        }
        if(responseMap["facedownResult"] != null){
          facedownResult = "${responseMap["facedownResult"]}";
        }
        if(responseMap["eyebrowType"] != null){
          eyebrowType = "${responseMap["eyebrowType"]}";
        }
        if(responseMap["faceType"] != null){
          faceType = "${responseMap["faceType"]}";
        }
        if(responseMap["eyeinResult"] != null){
          eyeinResult = "${responseMap["eyeinResult"]}";
        }

      });
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: Text("选择图片并上传，图片格式jpg或jpeg")),
      body: Container(
        child: ListView(
          children: <Widget>[
            SizedBox(height: 20),
            RaisedButton(
              onPressed: () {
                _getImageFromCamera();
              },
              child: Text("照相机",style: TextStyle(fontSize: 20)),
            ),
            SizedBox(height: 20),
            RaisedButton(
              onPressed: () {
                _getImageFromGallery();
              },
              child: Text("相册",style: TextStyle(fontSize: 20)),
            ),
            SizedBox(height: 20),
            RaisedButton(
              onPressed: () {
                _removeFile();
              },
              child: Text("清空",style: TextStyle(fontSize: 20)),
            ),
            SizedBox(height: 20),
            /**
             * 展示选择的图片
             */
            _image == null
                ? Text("未选择图片",style: TextStyle(fontSize: 20))
                : Image.file(
                    _image,
                    fit: BoxFit.cover,
                  ),
            SizedBox(height: 20),
            _imageTwo == null
                ? Text("未选择图片",style: TextStyle(fontSize: 20))
                : Image.file(
              _imageTwo,
              fit: BoxFit.cover,
            ),
            SizedBox(height: 20),

            RaisedButton(
              onPressed: () {
                _uploadImage();
              },
              child: Text("上传图片到服务器",style: TextStyle(fontSize: 20)),
            ),
            SizedBox(height: 20),
            eyesType != null
                ? Text(eyesType,style: TextStyle(fontSize: 20),textAlign: TextAlign.center)
                : SizedBox(height: 20),
            SizedBox(height: 20),
            mouthType != null
                ? Text(mouthType,style: TextStyle(fontSize: 20),textAlign: TextAlign.center)
                : SizedBox(height: 20),
            SizedBox(height: 20),
            noseType != null
                ? Text(noseType,style: TextStyle(fontSize: 20),textAlign: TextAlign.center)
                : SizedBox(height: 20),
            SizedBox(height: 20),
            jawType != null
                ? Text(jawType,style: TextStyle(fontSize: 20),textAlign: TextAlign.center)
                : SizedBox(height: 20),
            SizedBox(height: 20),
            faceupResult != null
                ? Text(faceupResult,style: TextStyle(fontSize: 20),textAlign: TextAlign.center)
                : SizedBox(height: 20),
            SizedBox(height: 20),
            facemidResult != null
                ? Text(facemidResult,style: TextStyle(fontSize: 20),textAlign: TextAlign.center)
                : SizedBox(height: 20),
            SizedBox(height: 20),
            facedownResult != null
                ? Text(facedownResult,style: TextStyle(fontSize: 20),textAlign: TextAlign.center)
                : SizedBox(height: 20),
            SizedBox(height: 20),
            eyebrowType != null
                ? Text(eyebrowType,style: TextStyle(fontSize: 20),textAlign: TextAlign.center)
                : SizedBox(height: 20),
            SizedBox(height: 20),
            faceType != null
                ? Text(faceType,style: TextStyle(fontSize: 20),textAlign: TextAlign.center)
                : SizedBox(height: 20),
            SizedBox(height: 20),
            eyeinResult != null
                ? Text(eyeinResult,style: TextStyle(fontSize: 20),textAlign: TextAlign.center)
                : SizedBox(height: 20),
            SizedBox(height: 20),
          ],
        ),
      ),
    );
  }
}
