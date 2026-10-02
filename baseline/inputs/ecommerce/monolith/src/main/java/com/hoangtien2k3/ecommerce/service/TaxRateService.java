package com.hoangtien2k3.ecommerce.service;

import com.hoangtien2k3.ecommerce.constants.MessageCode;
import com.hoangtien2k3.ecommerce.exception.NotFoundException;
import com.hoangtien2k3.ecommerce.model.tax.TaxRate;
import com.hoangtien2k3.ecommerce.repository.tax.TaxClassRepository;
import com.hoangtien2k3.ecommerce.repository.tax.TaxRateRepository;
import com.hoangtien2k3.ecommerce.dto.TaxRateGetDetailVm;
import com.hoangtien2k3.ecommerce.dto.TaxRateListGetVm;
import com.hoangtien2k3.ecommerce.dto.TaxRatePostVm;
import com.hoangtien2k3.ecommerce.dto.TaxRateVm;
import java.util.HashSet;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TaxRateService {

    private final TaxRateRepository taxRateRepository;
    private final TaxClassRepository taxClassRepository;

    public TaxRateService(TaxRateRepository taxRateRepository,
                          TaxClassRepository taxClassRepository) {
        this.taxRateRepository = taxRateRepository;
        this.taxClassRepository = taxClassRepository;
    }

    @Transactional(transactionManager = "postgresTransactionManager")
    public TaxRate createTaxRate(final TaxRatePostVm taxRatePostVm) {

        final Long taxClassId = taxRatePostVm.taxClassId();
        final boolean isTaxClassExisted = taxClassRepository.existsById(taxClassId);
        if (!isTaxClassExisted) {
            throw new NotFoundException(MessageCode.TAX_CLASS_NOT_FOUND, taxClassId);
        }

        final TaxRate taxRate = TaxRate.builder()
            .rate(taxRatePostVm.rate())
            .zipCode(taxRatePostVm.zipCode())
            .taxClass(taxClassRepository.getReferenceById(taxClassId))
            .stateOrProvinceId(taxRatePostVm.stateOrProvinceId())
            .countryId(taxRatePostVm.countryId())
            .build();

        return taxRateRepository.save(taxRate);
    }

    @Transactional(transactionManager = "postgresTransactionManager")
    public void updateTaxRate(final TaxRatePostVm taxRatePostVm,
                              final Long id) {
        final TaxRate taxRate = taxRateRepository
            .findById(id)
            .orElseThrow(
                () -> new NotFoundException(MessageCode.TAX_RATE_NOT_FOUND, id));

        final Long taxClassId = taxRatePostVm.taxClassId();
        final boolean isTaxClassExisted = taxClassRepository.existsById(taxClassId);
        if (!isTaxClassExisted) {
            throw new NotFoundException(MessageCode.TAX_CLASS_NOT_FOUND, taxClassId);
        }
        taxRate.setRate(taxRatePostVm.rate());
        taxRate.setZipCode(taxRatePostVm.zipCode());
        taxRate.setTaxClass(taxClassRepository.getReferenceById(taxClassId));
        taxRate.setStateOrProvinceId(taxRatePostVm.stateOrProvinceId());
        taxRate.setCountryId(taxRatePostVm.countryId());

        taxRateRepository.save(taxRate);
    }

    @Transactional(transactionManager = "postgresTransactionManager")
    public void delete(final Long id) {
        final boolean isTaxRateExisted = taxRateRepository.existsById(id);
        if (!isTaxRateExisted) {
            throw new NotFoundException(MessageCode.TAX_RATE_NOT_FOUND, id);
        }
        taxRateRepository.deleteById(id);
    }

    @Transactional(transactionManager = "postgresTransactionManager", readOnly = true)
    public TaxRateVm findById(final Long id) {
        final TaxRate taxRate = taxRateRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException(MessageCode.TAX_RATE_NOT_FOUND, id));
        return TaxRateVm.fromModel(taxRate);
    }

    @Transactional(transactionManager = "postgresTransactionManager", readOnly = true)
    public List<TaxRateVm> findAll() {
        return taxRateRepository
            .findAll()
            .stream()
            .map(TaxRateVm::fromModel)
            .toList();
    }

    @Transactional(transactionManager = "postgresTransactionManager", readOnly = true)
    public TaxRateListGetVm getPageableTaxRates(int pageNo, int pageSize) {
        final Pageable pageable = PageRequest.of(pageNo, pageSize);
        final Page<TaxRate> taxRatePage = taxRateRepository.findAll(pageable);
        final List<TaxRate> taxRates = taxRatePage.getContent();

        final List<TaxRateGetDetailVm> taxRateGetDetailVms = taxRates.stream()
            .map(taxRate -> new TaxRateGetDetailVm(
                taxRate.getId(),
                taxRate.getRate(),
                taxRate.getZipCode(),
                taxRate.getTaxClass().getName(),
                "State-" + taxRate.getStateOrProvinceId(),
                "Country-" + taxRate.getCountryId()))
            .toList();

        return new TaxRateListGetVm(
            taxRateGetDetailVms,
            taxRatePage.getNumber(),
            taxRatePage.getSize(),
            (int) taxRatePage.getTotalElements(),
            taxRatePage.getTotalPages(),
            taxRatePage.isLast()
        );
    }

    public double getTaxPercent(Long taxClassId, Long countryId, Long stateOrProvinceId, String zipCode) {
        Double taxPercent = taxRateRepository.getTaxPercent(countryId, stateOrProvinceId, zipCode, taxClassId);
        if (taxPercent != null) {
            return taxPercent;
        }

        return 0;
    }

    public List<TaxRateVm> getBulkTaxRate(List<Long> taxClassIds,
                                          Long countryId,
                                          Long stateOrProvinceId,
                                          String zipCode) {
        return taxRateRepository.getBatchTaxRates(countryId,
            stateOrProvinceId,
            zipCode,
                new HashSet<>(taxClassIds))
            .stream().map(TaxRateVm::fromModel).toList();
    }
}
