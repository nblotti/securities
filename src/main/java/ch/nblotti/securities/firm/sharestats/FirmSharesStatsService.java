package ch.nblotti.securities.firm.sharestats;


import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.Optional;
import java.util.logging.Logger;


@Service
public class FirmSharesStatsService {

  private static final Logger logger = Logger.getLogger("FirmService");

  public static final String FIRMS = "firms";
  public static final String FIRM_MAP = "firmsMap";

  @Autowired
  protected ModelMapper modelMapper;


  @Autowired
  private FirmSharesStatsRepository firmSharesStatsRepository;

  private EODFirmSharesStatsRepository EODFirmSharesStatsRepository;


  public Optional<FirmShareStatsDTO> getSharesStatByDateAndFirm(LocalDate runDate, String exchange, String symbol) {
    Optional<EODSharesStatsDTO> eODSharesStatsDTO = EODFirmSharesStatsRepository.getSharesStatByDateAndExchangeAndFirm(runDate, exchange, symbol);

    if (!eODSharesStatsDTO.isPresent())
      return Optional.empty();

    FirmShareStatsDTO fSpost = modelMapper.map(eODSharesStatsDTO.get(), FirmShareStatsDTO.class);
    fSpost.setExchange(exchange);
    fSpost.setDate(runDate);
    fSpost.setCode(symbol);

    return Optional.of(fSpost);


  }

  public FirmShareStatsDTO save(FirmShareStatsDTO entity) {

    FirmShareStatsTO firmEODInfoTO = modelMapper.map(entity, FirmShareStatsTO.class);

    FirmShareStatsTO saved = firmSharesStatsRepository.save(firmEODInfoTO);

    return modelMapper.map(saved, FirmShareStatsDTO.class);

  }


  @PostConstruct
  public void initFirmEODShareStatsTOMapper() {

    Converter<FirmShareStatsDTO, FirmShareStatsTO> toUppercase = new AbstractConverter<FirmShareStatsDTO, FirmShareStatsTO>() {

      @Override
      protected FirmShareStatsTO convert(FirmShareStatsDTO firmShareStatsDTO) {
        FirmShareStatsTO firmShareStatsTO = new FirmShareStatsTO();
        firmShareStatsTO.setSharesOutstanding(firmShareStatsDTO.getSharesOutstanding());
        firmShareStatsTO.setSharesFloat(firmShareStatsDTO.getSharesFloat());
        firmShareStatsTO.setPercentInsiders(firmShareStatsDTO.getPercentInsiders());
        firmShareStatsTO.setPercentInstitutions(firmShareStatsDTO.getPercentInstitutions());
        firmShareStatsTO.setSharesShort(firmShareStatsDTO.getSharesShort());
        firmShareStatsTO.setSharesShortPriorMonth(firmShareStatsDTO.getSharesShortPriorMonth());
        firmShareStatsTO.setShortRatio(firmShareStatsDTO.getShortRatio());
        firmShareStatsTO.setShortPercentOutstanding(firmShareStatsDTO.getShortPercentOutstanding());
        firmShareStatsTO.setShortPercentFloat(firmShareStatsDTO.getShortPercentFloat());
        return firmShareStatsTO;
      }
    };

    modelMapper.addConverter(toUppercase);

  }


  @PostConstruct
  public void initFirmShareStatsDTOMapper() {

    Converter<FirmShareStatsTO, FirmShareStatsDTO> toUppercase = new AbstractConverter<FirmShareStatsTO, FirmShareStatsDTO>() {

      @Override
      protected FirmShareStatsDTO convert(FirmShareStatsTO firmEODShareStatsTO) {
        FirmShareStatsDTO firmShareStatsDTO = new FirmShareStatsDTO();
        firmShareStatsDTO.setSharesOutstanding(firmEODShareStatsTO.getSharesOutstanding());
        firmShareStatsDTO.setSharesFloat(firmEODShareStatsTO.getSharesFloat());
        firmShareStatsDTO.setPercentInsiders(firmEODShareStatsTO.getPercentInsiders());
        firmShareStatsDTO.setPercentInstitutions(firmEODShareStatsTO.getPercentInstitutions());
        firmShareStatsDTO.setSharesShort(firmEODShareStatsTO.getSharesShort());
        firmShareStatsDTO.setSharesShortPriorMonth(firmEODShareStatsTO.getSharesShortPriorMonth());
        firmShareStatsDTO.setShortRatio(firmEODShareStatsTO.getShortRatio());
        firmShareStatsDTO.setShortPercentOutstanding(firmEODShareStatsTO.getShortPercentOutstanding());
        firmShareStatsDTO.setShortPercentFloat(firmEODShareStatsTO.getShortPercentFloat());
        return firmShareStatsDTO;
      }
    };

    modelMapper.addConverter(toUppercase);

  }


  @PostConstruct
  public void initShareStatsMapper() {

    Converter<EODSharesStatsDTO, FirmShareStatsDTO> toUppercase = new AbstractConverter<EODSharesStatsDTO, FirmShareStatsDTO>() {

      @Override
      protected FirmShareStatsDTO convert(EODSharesStatsDTO sharesStatsDTO) {
        FirmShareStatsDTO firmEODShareStatsTO = new FirmShareStatsDTO();
        firmEODShareStatsTO.setSharesOutstanding(sharesStatsDTO.getSharesOutstanding());
        firmEODShareStatsTO.setSharesFloat(sharesStatsDTO.getSharesFloat());
        firmEODShareStatsTO.setPercentInsiders(sharesStatsDTO.getPercentInsiders());
        firmEODShareStatsTO.setPercentInstitutions(sharesStatsDTO.getPercentInstitutions());
        firmEODShareStatsTO.setSharesShort(sharesStatsDTO.getSharesShort());
        firmEODShareStatsTO.setSharesShortPriorMonth(sharesStatsDTO.getSharesShortPriorMonth());
        firmEODShareStatsTO.setShortRatio(sharesStatsDTO.getShortRatio());
        firmEODShareStatsTO.setShortPercentOutstanding(sharesStatsDTO.getShortPercentOutstanding());
        firmEODShareStatsTO.setShortPercentFloat(sharesStatsDTO.getShortPercentFloat());
        return firmEODShareStatsTO;
      }
    };

    modelMapper.addConverter(toUppercase);

  }

}
