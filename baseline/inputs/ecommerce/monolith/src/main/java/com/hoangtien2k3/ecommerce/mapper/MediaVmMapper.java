package com.hoangtien2k3.ecommerce.mapper;

import com.hoangtien2k3.ecommerce.model.media.Media;
import com.hoangtien2k3.ecommerce.dto.MediaVm;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MediaVmMapper extends BaseMapper<Media, MediaVm> {
}
