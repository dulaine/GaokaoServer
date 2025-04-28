protoc.exe -I=./ --cpp_out=./ ./yzpm.proto
protoc.exe -I=./ --descriptor_set_out=yzpm.desc ./yzpm.proto
protogen -p:detectMissing -i:yzpm.proto -o:SubscribeReq.cs

pause