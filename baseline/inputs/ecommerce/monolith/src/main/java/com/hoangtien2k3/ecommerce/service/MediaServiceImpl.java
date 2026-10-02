package com.hoangtien2k3.ecommerce.service;

import com.hoangtien2k3.ecommerce.mapper.MediaVmMapper;
import com.hoangtien2k3.ecommerce.model.media.Media;
import com.hoangtien2k3.ecommerce.dto.MediaDto;
import com.hoangtien2k3.ecommerce.dto.MediaDto.MediaDtoBuilder;
import com.hoangtien2k3.ecommerce.repository.media.FileSystemRepository;
import com.hoangtien2k3.ecommerce.repository.media.MediaRepository;
import com.hoangtien2k3.ecommerce.utils.StringUtils;
import com.hoangtien2k3.ecommerce.dto.MediaPostVm;
import com.hoangtien2k3.ecommerce.dto.MediaVm;
import com.hoangtien2k3.ecommerce.dto.NoFileMediaVm;
import com.hoangtien2k3.ecommerce.config.YasConfig;
import java.io.InputStream;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

@RequiredArgsConstructor
@Service
public class MediaServiceImpl implements MediaService {

    private final MediaVmMapper mediaVmMapper;
    private final MediaRepository mediaRepository;
    private final FileSystemRepository fileSystemRepository;
    private final YasConfig hoangtien2k3Config;

    @Override
    @SneakyThrows
    public Media saveMedia(MediaPostVm mediaPostVm) {
        Media media = new Media();
        media.setCaption(mediaPostVm.caption());
        media.setMediaType(mediaPostVm.multipartFile().getContentType());

        if (StringUtils.hasText(mediaPostVm.fileNameOverride())) {
            media.setFileName(mediaPostVm.fileNameOverride().trim());
        } else {
            media.setFileName(mediaPostVm.multipartFile().getOriginalFilename());
        }
        String filePath = fileSystemRepository.persistFile(media.getFileName(),
            mediaPostVm.multipartFile().getBytes());
        media.setFilePath(filePath);

        return mediaRepository.save(media);
    }

    @Override
    public void removeMedia(Long id) {
        NoFileMediaVm noFileMediaVm = mediaRepository.findByIdWithoutFileInReturn(id);
        try {
            mediaRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public MediaVm getMediaById(Long id) {
        NoFileMediaVm noFileMediaVm = mediaRepository.findByIdWithoutFileInReturn(id);
        if (noFileMediaVm == null) {
            return null;
        }
        String url = getMediaUrl(noFileMediaVm.id(), noFileMediaVm.fileName());

        return new MediaVm(
            noFileMediaVm.id(),
            noFileMediaVm.caption(),
            noFileMediaVm.fileName(),
            noFileMediaVm.mediaType(),
            url
        );
    }

    @Override
    public MediaDto getFile(Long id, String fileName) {

        MediaDtoBuilder builder = MediaDto.builder();

        Media media = mediaRepository.findById(id).orElse(null);
        if (media == null || !fileName.equalsIgnoreCase(media.getFileName())) {
            return builder.build();
        }
        MediaType mediaType = MediaType.valueOf(media.getMediaType());
        InputStream fileContent = fileSystemRepository.getFile(media.getFilePath());

        return builder
            .content(fileContent)
            .mediaType(mediaType)
            .build();
    }

    @Override
    public List<MediaVm> getMediaByIds(List<Long> ids) {
        return mediaRepository.findAllById(ids).stream()
                .map(mediaVmMapper::toVm)
                .map(media -> {
                    String url = getMediaUrl(media.getId(), media.getFileName());
                    media.setUrl(url);
                    return media;
                }).toList();
    }

    private String getMediaUrl(Long mediaId, String fileName) {
        return UriComponentsBuilder.fromUriString(hoangtien2k3Config.getPublicUrl())
                .path(String.format("/medias/%1$s/file/%2$s", mediaId, fileName))
                .build().toUriString();
    }
}
