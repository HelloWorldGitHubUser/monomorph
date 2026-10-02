package com.hoangtien2k3.ecommerce.service;

import com.hoangtien2k3.ecommerce.constants.MessageCode;
import com.hoangtien2k3.ecommerce.dto.OrderExistsByProductAndUserGetVm;
import com.hoangtien2k3.ecommerce.dto.RatingListVm;
import com.hoangtien2k3.ecommerce.dto.RatingPostVm;
import com.hoangtien2k3.ecommerce.dto.RatingVm;
import com.hoangtien2k3.ecommerce.dto.ResponeStatusVm;
import com.hoangtien2k3.ecommerce.exception.AccessDeniedException;
import com.hoangtien2k3.ecommerce.exception.NotFoundException;
import com.hoangtien2k3.ecommerce.exception.ResourceExistedException;
import com.hoangtien2k3.ecommerce.model.rating.Rating;
import com.hoangtien2k3.ecommerce.repository.rating.RatingRepository;
import com.hoangtien2k3.ecommerce.utils.AuthenticationUtils;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

@Slf4j
@Service
@Transactional(transactionManager = "postgresTransactionManager")
@RequiredArgsConstructor
public class RatingService {

    private final RatingRepository ratingRepository;

    public RatingListVm getRatingListByProductId(Long id, int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("createdOn").descending());
        Page<Rating> ratings = ratingRepository.findByProductId(id, pageable);

        List<RatingVm> ratingVmList = new ArrayList<>();
        for (Rating rating : ratings.getContent()) {
            ratingVmList.add(RatingVm.fromModel(rating));
        }

        return new RatingListVm(ratingVmList, ratings.getTotalElements(), ratings.getTotalPages());
    }

    public RatingListVm getRatingListWithFilter(String proName, String cusName,
            String message, ZonedDateTime createdFrom,
            ZonedDateTime createdTo, int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("createdOn").descending());
        Page<Rating> ratings = ratingRepository.getRatingListWithFilter(
                proName.toLowerCase(),
                cusName.toLowerCase(), message.toLowerCase(),
                createdFrom, createdTo, pageable);

        List<RatingVm> ratingVmList = new ArrayList<>();
        for (Rating rating : ratings.getContent()) {
            ratingVmList.add(RatingVm.fromModel(rating));
        }

        return new RatingListVm(ratingVmList, ratings.getTotalElements(), ratings.getTotalPages());
    }

    public RatingVm createRating(RatingPostVm ratingPostVm) {
        String username = AuthenticationUtils.extractUserId();

        // StorefrontOrderController.checkCompletedOrder currently hardcodes true
        OrderExistsByProductAndUserGetVm orderCheck = new OrderExistsByProductAndUserGetVm(true);
        if (!orderCheck.isPresent()) {
            throw new AccessDeniedException(MessageCode.ACCESS_DENIED);
        }

        if (ratingRepository.existsByCreatedByAndProductId(username, ratingPostVm.productId())) {
            throw new ResourceExistedException(MessageCode.RESOURCE_ALREADY_EXISTED);
        }

        Rating rating = new Rating();
        rating.setRatingStar(ratingPostVm.star());
        rating.setContent(ratingPostVm.content());
        rating.setProductId(ratingPostVm.productId());
        rating.setProductName(ratingPostVm.productName());
        rating.setFirstName(username);
        rating.setLastName("");

        Rating savedRating = ratingRepository.save(rating);
        return RatingVm.fromModel(savedRating);
    }

    public ResponeStatusVm deleteRating(long ratingId) {
        Rating rating = ratingRepository.findById(ratingId)
                .orElseThrow(() -> new NotFoundException(MessageCode.RATING_NOT_FOUND, ratingId));

        ratingRepository.delete(rating);
        return new ResponeStatusVm("Delete Rating", MessageCode.SUCCESS_MESSAGE, HttpStatus.OK.toString());
    }

    public Double calculateAverageStar(Long productId) {
        List<Object[]> totalStarsAndRatings = ratingRepository.getTotalStarsAndTotalRatings(productId);
        if (ObjectUtils.isEmpty(totalStarsAndRatings.get(0)[0])) {
            return 0.0;
        }
        int totalStars = (Integer.parseInt(totalStarsAndRatings.get(0)[0].toString()));
        int totalRatings = (Integer.parseInt(totalStarsAndRatings.get(0)[1].toString()));
        return (totalStars * 1.0) / totalRatings;
    }
}
