package com.timyang.protobufdemo;

import static com.timyang.protobufdemo.model.StudentModel.*;

import com.timyang.protobufdemo.conf.CreateNewWorldConfig;
import com.timyang.protobufdemo.conf.FtpConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Arrays;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringNettyProtobufDemoApplicationTests {

  @Resource
  FtpConfig ftpConfig;

  @Resource(name = "createNewWorldConfig")
  CreateNewWorldConfig cnwConfig;

  @Test
  public void contextLoads() {
    assert ftpConfig != null;

    ftpConfig.getMap().entrySet().stream().forEach(e -> {
      System.out.println(e.getKey());
      System.out.println(e.getValue());
    });


    System.out.println(cnwConfig);
  }

  @Test
  public void buffParse() {
     Student glad = Student.newBuilder()
      .setId(1)
      .setName("Glad")
      .addAllHobbies(Arrays.asList("play basketball", "jump", "dance", "rap music"))
      .build();

    System.out.println("a student profile: " + glad);


    byte[] gladByte = glad.toByteArray();
    System.out.println("transfer to byte, length: " + gladByte.length);
  }

}
