package com.hoangtien2k3.ecommerce.service;

import com.hoangtien2k3.ecommerce.constants.MessageCode;
import com.hoangtien2k3.ecommerce.exception.DuplicatedException;
import com.hoangtien2k3.ecommerce.exception.NotFoundException;
import com.hoangtien2k3.ecommerce.model.tax.TaxClass;
import com.hoangtien2k3.ecommerce.repository.tax.TaxClassRepository;
import com.hoangtien2k3.ecommerce.dto.TaxClassListGetVm;
import com.hoangtien2k3.ecommerce.dto.TaxClassPostVm;
import com.hoangtien2k3.ecommerce.dto.TaxClassVm;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(transactionManager = "postgresTransactionManager")
public class TaxClassService {

    private final TaxClassRepository taxClassRepository;

    public TaxClassService(TaxClassRepository taxClassRepository) {
        this.taxClassRepository = taxClassRepository;
    }

    @Transactional(transactionManager = "postgresTransactionManager", readOnly = true)
    public List<TaxClassVm> findAllTaxClasses() {
        return taxClassRepository
            .findAll(Sort.by(Sort.Direction.ASC, "name"))
            .stream()
            .map(TaxClassVm::fromModel)
            .toList();
    }

    @Transactional(transactionManager = "postgresTransactionManager", readOnly = true)
    public TaxClassVm findById(final Long id) {
        final TaxClass taxClass = taxClassRepository
            .findById(id)
            .orElseThrow(
                () -> new NotFoundException(MessageCode.TAX_CLASS_NOT_FOUND, id));
        return TaxClassVm.fromModel(taxClass);
    }

    @Transactional(transactionManager = "postgresTransactionManager")
    public TaxClass create(final TaxClassPostVm taxClassPostVm) {
        if (taxClassRepository.existsByName(taxClassPostVm.name())) {
            throw new DuplicatedException(MessageCode.NAME_ALREADY_EXITED, taxClassPostVm.name());
        }
        return taxClassRepository.save(taxClassPostVm.toModel());
    }

    @Transactional(transactionManager = "postgresTransactionManager")
    public void update(final TaxClassPostVm taxClassPostVm, final Long id) {
        final TaxClass taxClass = taxClassRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException(MessageCode.TAX_CLASS_NOT_FOUND, id));

        if (taxClassRepository.existsByNameNotUpdatingTaxClass(taxClassPostVm.name(), id)) {
            throw new DuplicatedException(MessageCode.NAME_ALREADY_EXITED, taxClassPostVm.name());
        }

        taxClass.setName(taxClassPostVm.name());
        taxClassRepository.save(taxClass);
    }

    @Transactional(transactionManager = "postgresTransactionManager")
    public void delete(final Long id) {
        final boolean isTaxClassExisted = taxClassRepository.existsById(id);
        if (!isTaxClassExisted) {
            throw new NotFoundException(MessageCode.TAX_CLASS_NOT_FOUND, id);
        }
        taxClassRepository.deleteById(id);
    }

    @Transactional(transactionManager = "postgresTransactionManager", readOnly = true)
    public TaxClassListGetVm getPageableTaxClasses(final int pageNo, final int pageSize) {
        final Pageable pageable = PageRequest.of(pageNo, pageSize);
        final Page<TaxClass> taxClassPage = taxClassRepository.findAll(pageable);
        final List<TaxClass> taxClassList = taxClassPage.getContent();

        final List<TaxClassVm> taxClassVms = taxClassList.stream()
            .map(TaxClassVm::fromModel)
            .toList();

        return new TaxClassListGetVm(
            taxClassVms,
            taxClassPage.getNumber(),
            taxClassPage.getSize(),
            (int) taxClassPage.getTotalElements(),
            taxClassPage.getTotalPages(),
            taxClassPage.isLast()
        );
    }
}
