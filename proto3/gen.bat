@echo off
protoc.exe -I=./ --java_out=../src/main/java/ ./yzpm.proto  && java -jar ./JavaServerProtoScriptGenerator-1.0-SNAPSHOT.jar
@rem protoc.exe -I=./ --cpp_out=./ ./SubscribeReq.proto
@rem protoc.exe -I=./ --descriptor_set_out=SubscribeReq.desc ./SubscribeReq.proto
@rem protogen -p:detectMissing -i:SubscribeReq.proto -o:SubscribeReq.cs
echo done!
pause