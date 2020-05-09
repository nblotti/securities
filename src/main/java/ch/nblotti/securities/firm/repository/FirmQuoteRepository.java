package ch.nblotti.securities.firm.repository;

import ch.nblotti.securities.firm.to.FirmEODQuoteTO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FirmQuoteRepository extends CrudRepository<FirmEODQuoteTO, Long> {
}