set outPath=../spring-netty-protobuf-demo/src/main/java
set fileArray=(Student)

# 将.proto文件生成java类
for %%i in %fileArray% do (
    echo generate cli protocol java code: %%i.proto
    protoc --java_out=%outPath% ./%%i.proto
)

pause
