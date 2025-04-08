///*
// * Copyright 2013-2018 the original author or authors.
// *
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// *
// *      https://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//
//package free.tools.mypermission.demos.oss;
//
//import com.aliyun.oss.OSS;
//import com.aliyun.oss.OSSClientBuilder;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
///**
// * @author lynn
// */
//@Configuration
//public class OssConfig {
//
//    @Value("${alibaba.cloud.access-key}")
//    private String accessKey;
//
//    @Value("${alibaba.cloud.secret-key}")
//    private String secretKey;
//
//    @Value("${alibaba.cloud.oss.endpoint}")
//    private String endpoint;
//
//    @Value("${alibaba.cloud.oss.bucket}")
//    private String bucketName;
//
//    @Bean
//    public OSS ossClient() {
//        return new OSSClientBuilder().build(endpoint, accessKey, secretKey);
//    }
//
//    @Bean
//    public String bucketName() {
//        return bucketName;
//    }
//}
