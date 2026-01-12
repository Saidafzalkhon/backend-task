package uz.java.backendtask.service.impl;

import io.minio.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.java.backendtask.config.MinioProperties;
import uz.java.backendtask.exception.FileException;
import uz.java.backendtask.service.StorageService;

@Service
@RequiredArgsConstructor
public class MinioStorageService implements StorageService {

    private final MinioClient minioClient;
    private final MinioProperties props;

    @Override
    public String upload(
            MultipartFile file,
            String objectKey,
            String contentType
    ) {
        try {
            ensureBucketExists();
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(props.getBucket())
                            .object(objectKey)
                            .stream(
                                    file.getInputStream(),
                                    file.getSize(),
                                    -1
                            )
                            .contentType(contentType)
                            .build()
            );

            return props.getUrl()
                    + "/" + props.getBucket()
                    + "/" + objectKey;

        } catch (Exception e) {
            throw new FileException("MinIO upload failed");
        }
    }

    @Override
    public void delete(String objectKey) {

    }

    private void ensureBucketExists() {
        String bucketName = props.getBucket();
        try {
            boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (!found) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            }
            String policyJson = getPublicGetPolicy(bucketName);
            minioClient.setBucketPolicy(SetBucketPolicyArgs.builder()
                    .bucket(bucketName)
                    .config(policyJson)
                    .build());
        } catch (Exception e) {
            throw new FileException(e.getMessage());
        }
    }

    private String getPublicGetPolicy(String bucketName) {
        return "{\n" +
                "    \"Version\": \"2012-10-17\",\n" +
                "    \"Statement\": [\n" +
                "        {\n" +
                "            \"Effect\": \"Allow\",\n" +
                "            \"Principal\": \"*\",\n" +
                "            \"Action\": \"s3:GetObject\",\n" +
                "            \"Resource\": \"arn:aws:s3:::" + bucketName + "/*\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";
    }
}
