package com.hoangtien2k3.ecommerce.service;

import com.hoangtien2k3.ecommerce.model.media.Media;
import com.hoangtien2k3.ecommerce.dto.MediaDto;
import com.hoangtien2k3.ecommerce.dto.MediaPostVm;
import com.hoangtien2k3.ecommerce.dto.MediaVm;
import java.util.List;

public interface MediaService {
    Media saveMedia(MediaPostVm mediaPostVm);

    MediaVm getMediaById(Long id);

    void removeMedia(Long id);

    MediaDto getFile(Long id, String fileName);

    List<MediaVm> getMediaByIds(List<Long> ids);
}
