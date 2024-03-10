package com.bsuir.service.impl;

import com.bsuir.dto.ImageLocation;
import com.bsuir.exception.ImageNotUploadedException;
import com.bsuir.service.GoogleDriverService;
import com.bsuir.service.PropertyImageService;
import com.bsuir.service.UserService;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.InputStreamContent;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class GoogleDriverServiceImpl implements GoogleDriverService {

    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final List<String> SCOPES = List.of(DriveScopes.DRIVE);
    private final UserService userService;
    private final PropertyImageService propertyImageService;
    private final HttpServletRequest request;

    @Value("${google.service.application.name}")
    private String applicationName;

    @Value("${google.credentials.folder.path}")
    private Resource tokensDirectoryPath;

    @Value("${google.service.account.key}")
    private Resource credentialsFilePath;

    @Value("${google.service.user.id}")
    private String userId;

    @Value("${google.service.folder.user.icon.id}")
    private String userIconFolderId;

    @Value("${google.service.folder.property.images.id}")
    private String propertyImageFolderId;

    @Value("${google.service.folder.article.images.id}")
    private String articleImageFolderId;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Override
    public void uploadUserIcon(MultipartFile file, String username) {
        String fileId = uploadFile(file, userIconFolderId);
        userService.updateIconUrl(fileId, username);
    }

    @Override
    public void uploadPropertyImage(MultipartFile file, Long propertyId) {
        String fileId = uploadFile(file, propertyImageFolderId);
        propertyImageService.createImage(fileId, propertyId);
    }

    @Override
    public ImageLocation uploadArticleImage(MultipartFile file) {
        String fileId = uploadFile(file, articleImageFolderId);

        return new ImageLocation("http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/image/" + fileId);
    }

    private Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        InputStream in = credentialsFilePath.getInputStream();
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(tokensDirectoryPath.getFile()))
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize(userId);
    }

    public Drive getInstance() throws GeneralSecurityException, IOException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        return new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(applicationName)
                .build();
    }

    private String uploadFile(MultipartFile file, String folderId) {
        try {
            File fileMetadata = new File();
            fileMetadata.setParents(Collections.singletonList(folderId));
            fileMetadata.setName(generateFileName(Objects.requireNonNull(file.getOriginalFilename())));
            File uploadFile = getInstance()
                    .files()
                    .create(fileMetadata, new InputStreamContent(
                            file.getContentType(),
                            new ByteArrayInputStream(file.getBytes()))
                    )
                    .setFields("id").execute();

            return uploadFile.getId();
        } catch (Exception e) {
            throw new ImageNotUploadedException("An error occured while uploading!");
        }
    }

    private String generateFileName(String originalName) {
        String newName = originalName.substring(0, originalName.lastIndexOf('.'));
        newName += "-" + RandomStringUtils.random(10, true, true);
        newName += checkFileExtension(originalName);
        return newName;
    }

    private String checkFileExtension(String originalName) {
        String[] extensionList = {".png", ".jpg", ".jpeg", ".gif"};
        for (String extension : extensionList) {
            if (originalName.endsWith(extension)) {
                return extension;
            }
        }
        throw new RuntimeException();
    }
}