package com.hoangtien2k3.ecommerce.repository.media;

import com.hoangtien2k3.ecommerce.model.media.Media;
import com.hoangtien2k3.ecommerce.dto.NoFileMediaVm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MediaRepository extends JpaRepository<Media, Long> {
    @Query(value = "select new com.hoangtien2k3.ecommerce.dto.NoFileMediaVm(m.id, m.caption, m.fileName, m.mediaType) "
        + "from Media m where m.id = ?1")
    NoFileMediaVm findByIdWithoutFileInReturn(Long id);
}
