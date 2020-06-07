package ch.nblotti.securities.firm.service;


import ch.nblotti.securities.firm.repository.*;
import ch.nblotti.securities.firm.repository.eod.FirmEODRepository;
import ch.nblotti.securities.firm.to.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;


@Service
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class FirmService {

  private static final Logger logger = Logger.getLogger("FirmService");

  public static final String FIRMS = "firms";
  public static final String FIRM_MAP = "firmsMap";


  @Autowired
  FirmQuoteRepository firmQuoteRepository;

  @Autowired
  private FirmEODRepository firmEODRepository;

  @Autowired
  FirmInfoRepository firmInfoRepository;

  @Autowired
  private FirmHighlightsRepository firmHighlightsRepository;

  @Autowired
  private FirmSharesStatsRepository firmSharesStatsRepository;

  @Autowired
  private FirmValuationRepository firmValuationRepository;


  public Optional<FirmEODValuationTO> getValuationByDateAndFirm(LocalDate runDate, FirmEODQuoteTO firmEODQuoteTO) {

    return firmEODRepository.getValuationByDateAndFirm(runDate, firmEODQuoteTO.getExchangeShortName(), firmEODQuoteTO.getCode());

  }

  public Optional<FirmEODHighlightsTO> getHighlightsByDateAndFirm(LocalDate runDate, FirmEODQuoteTO firmEODQuoteTO) {

    return firmEODRepository.getHighlightsByDateAndFirm(runDate, firmEODQuoteTO.getExchangeShortName(), firmEODQuoteTO.getCode());
  }

  public Optional<FirmEODShareStatsTO> getSharesStatByDateAndFirm(LocalDate runDate, FirmEODQuoteTO firmEODQuoteTO) {
    return firmEODRepository.getSharesStatByDateAndExchangeAndFirm(runDate, firmEODQuoteTO.getExchangeShortName(), firmEODQuoteTO.getCode());
  }

  public Optional<FirmEODInfoTO> getInfosByDateAndExchangeAndFirm(LocalDate runDate, FirmEODQuoteTO firmEODQuoteTO) {
    return firmEODRepository.getInfosByDateAndExchangeAndFirm(runDate, firmEODQuoteTO.getExchangeShortName(), firmEODQuoteTO.getCode());
  }


  public List<FirmEODQuoteTO> getExchangeDataForDate(LocalDate localDate, String exchange) {
    return firmEODRepository.getExchangeDataByDate(localDate, exchange);
  }


  public Iterable<FirmEODQuoteTO> saveAllEODMarketQuotes(List<FirmEODQuoteTO> firmsTOs) {
    return firmQuoteRepository.saveAll(firmsTOs);
  }

  public <S extends FirmEODHighlightsTO> S save(S entity) {
    return firmHighlightsRepository.save(entity);
  }

  public <S extends FirmEODShareStatsTO> S save(S entity) {
    return firmSharesStatsRepository.save(entity);
  }

  public <S extends FirmEODValuationTO> S save(S entity) {
    return firmValuationRepository.save(entity);
  }

  public <S extends FirmEODInfoTO> S save(S entity) {
    return firmInfoRepository.save(entity);
  }


}
