package ch.nblotti.securities.firm.highlights;


import ch.nblotti.securities.firm.quote.FirmQuoteDTO;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@Service
public class FirmHighlightsService {

  private static final Logger logger = Logger.getLogger("FirmService");

  public static final String FIRMS = "firms";
  public static final String FIRM_MAP = "firmsMap";


  @Autowired
  private EODFirmHighlightsRepository EODFirmHighlightsRepository;

  @Autowired
  private FirmHighlightsRepository firmHighlightsRepository;


  @Autowired
  protected ModelMapper modelMapper;



  public Optional<FirmHighlightsDTO> getHighlightsByDateAndFirm(LocalDate runDate, FirmQuoteDTO firmEODQuoteTO) {


    String exchange = firmEODQuoteTO.getExchangeShortName();
    String symbol = firmEODQuoteTO.getCode();

    Optional<EODFirmHighlightsDTO> EODFirmHighlightsDTO = EODFirmHighlightsRepository.getHighlightsByDateAndFirm(runDate, exchange, symbol);

    if (!EODFirmHighlightsDTO.isPresent())
      return Optional.empty();

    FirmHighlightsDTO fHpost = modelMapper.map(EODFirmHighlightsDTO.get(), FirmHighlightsDTO.class);
    fHpost.setExchange(exchange);
    fHpost.setDate(runDate);
    fHpost.setCode(symbol);
    return Optional.of(fHpost);
  }


  public FirmHighlightsDTO findTopByCodeOrderByDate(String code) {
    FirmHighlightsTO firmHighlightsDTO = firmHighlightsRepository.findTopByCodeOrderByDate(code);
    return modelMapper.map(firmHighlightsDTO, FirmHighlightsDTO.class);
  }

  public Iterable<FirmHighlightsDTO> findAll(Sort sort) {
    Iterable<FirmHighlightsTO> firmHighlightsDTOS = firmHighlightsRepository.findAll(sort);

    return StreamSupport.stream(firmHighlightsDTOS.spliterator(), false)
      .map(n -> modelMapper.map(n, FirmHighlightsDTO.class))
      .collect(Collectors.toList());
  }

  public FirmHighlightsDTO save(FirmHighlightsDTO firmHighlightsDTO) {
    FirmHighlightsTO firmHighlightsTO = modelMapper.map(firmHighlightsDTO, FirmHighlightsTO.class);

    FirmHighlightsTO saved = firmHighlightsRepository.save(firmHighlightsTO);

    return modelMapper.map(saved, FirmHighlightsDTO.class);
  }


  @PostConstruct
  public void initFirmEODHighlightsMapper() {

    Converter<FirmHighlightsTO, FirmHighlightsDTO> toUppercase = new AbstractConverter<FirmHighlightsTO, FirmHighlightsDTO>() {

      @Override
      protected FirmHighlightsDTO convert(FirmHighlightsTO firmEODHighlightsTO) {
        FirmHighlightsDTO firmHighlightsDTO = new FirmHighlightsDTO();
        firmHighlightsDTO.setMarketCapitalization(firmEODHighlightsTO.getMarketCapitalization());
        firmHighlightsDTO.setMarketCapitalizationMln(firmEODHighlightsTO.getMarketCapitalizationMln());
        firmHighlightsDTO.seteBITDA(firmEODHighlightsTO.geteBITDA());
        firmHighlightsDTO.setpERatio(firmEODHighlightsTO.getpERatio());
        firmHighlightsDTO.setpEGRatio(firmEODHighlightsTO.getpEGRatio());
        firmHighlightsDTO.setWallStreetTargetPrice(firmEODHighlightsTO.getWallStreetTargetPrice());
        firmHighlightsDTO.setBookValue(firmEODHighlightsTO.getBookValue());
        firmHighlightsDTO.setDividendShare(firmEODHighlightsTO.getDividendShare());
        firmHighlightsDTO.setDividendYield(firmEODHighlightsTO.getDividendYield());
        firmHighlightsDTO.setEarningsShare(firmEODHighlightsTO.getEarningsShare());
        firmHighlightsDTO.setePSEstimateCurrentYear(firmEODHighlightsTO.getePSEstimateCurrentYear());
        firmHighlightsDTO.setePSEstimateNextYear(firmEODHighlightsTO.getePSEstimateNextYear());
        firmHighlightsDTO.setePSEstimateNextQuarter(firmEODHighlightsTO.getePSEstimateNextQuarter());
        firmHighlightsDTO.setePSEstimateCurrentQuarter(firmEODHighlightsTO.getePSEstimateCurrentQuarter());
        firmHighlightsDTO.setMostRecentQuarter(firmEODHighlightsTO.getMostRecentQuarter());
        firmHighlightsDTO.setProfitMargin(firmEODHighlightsTO.getProfitMargin());
        firmHighlightsDTO.setOperatingMarginTTM(firmEODHighlightsTO.getOperatingMarginTTM());
        firmHighlightsDTO.setReturnOnAssetsTTM(firmEODHighlightsTO.getReturnOnAssetsTTM());
        firmHighlightsDTO.setReturnOnEquityTTM(firmEODHighlightsTO.getReturnOnEquityTTM());
        firmHighlightsDTO.setRevenueTTM(firmEODHighlightsTO.getRevenueTTM());
        firmHighlightsDTO.setRevenuePerShareTTM(firmEODHighlightsTO.getRevenuePerShareTTM());
        firmHighlightsDTO.setQuarterlyRevenueGrowthYOY(firmEODHighlightsTO.getQuarterlyRevenueGrowthYOY());
        firmHighlightsDTO.setGrossProfitTTM(firmEODHighlightsTO.getGrossProfitTTM());
        firmHighlightsDTO.setDilutedEpsTTM(firmEODHighlightsTO.getDilutedEpsTTM());
        firmHighlightsDTO.setQuarterlyEarningsGrowthYOY(firmEODHighlightsTO.getQuarterlyEarningsGrowthYOY());

        return firmHighlightsDTO;
      }
    };

    modelMapper.addConverter(toUppercase);

  }

  @PostConstruct
  public void initFirmHighlightsMapper() {

    Converter<EODFirmHighlightsDTO, FirmHighlightsDTO> toUppercase = new AbstractConverter<EODFirmHighlightsDTO, FirmHighlightsDTO>() {

      @Override
      protected FirmHighlightsDTO convert(EODFirmHighlightsDTO eODFirmHighlightsDTO) {
        FirmHighlightsDTO firmHighlightsDTO = new FirmHighlightsDTO();
        firmHighlightsDTO.setMarketCapitalization(eODFirmHighlightsDTO.getMarketCapitalization());
        firmHighlightsDTO.setMarketCapitalizationMln(eODFirmHighlightsDTO.getMarketCapitalizationMln());
        firmHighlightsDTO.seteBITDA(eODFirmHighlightsDTO.getEBITDA());
        firmHighlightsDTO.setpERatio(eODFirmHighlightsDTO.getPERatio());
        firmHighlightsDTO.setpEGRatio(eODFirmHighlightsDTO.getPEGRatio());
        firmHighlightsDTO.setWallStreetTargetPrice(eODFirmHighlightsDTO.getWallStreetTargetPrice());
        firmHighlightsDTO.setBookValue(eODFirmHighlightsDTO.getBookValue());
        firmHighlightsDTO.setDividendShare(eODFirmHighlightsDTO.getDividendShare());
        firmHighlightsDTO.setDividendYield(eODFirmHighlightsDTO.getDividendYield());
        firmHighlightsDTO.setEarningsShare(eODFirmHighlightsDTO.getEarningsShare());
        firmHighlightsDTO.setePSEstimateCurrentYear(eODFirmHighlightsDTO.getEPSEstimateCurrentYear());
        firmHighlightsDTO.setePSEstimateNextYear(eODFirmHighlightsDTO.getEPSEstimateNextYear());
        firmHighlightsDTO.setePSEstimateNextQuarter(eODFirmHighlightsDTO.getEPSEstimateNextQuarter());
        firmHighlightsDTO.setePSEstimateCurrentQuarter(eODFirmHighlightsDTO.getEPSEstimateCurrentQuarter());
        firmHighlightsDTO.setMostRecentQuarter(eODFirmHighlightsDTO.getMostRecentQuarter());
        firmHighlightsDTO.setProfitMargin(eODFirmHighlightsDTO.getProfitMargin());
        firmHighlightsDTO.setOperatingMarginTTM(eODFirmHighlightsDTO.getOperatingMarginTTM());
        firmHighlightsDTO.setReturnOnAssetsTTM(eODFirmHighlightsDTO.getReturnOnAssetsTTM());
        firmHighlightsDTO.setReturnOnEquityTTM(eODFirmHighlightsDTO.getReturnOnEquityTTM());
        firmHighlightsDTO.setRevenueTTM(eODFirmHighlightsDTO.getRevenueTTM());
        firmHighlightsDTO.setRevenuePerShareTTM(eODFirmHighlightsDTO.getRevenuePerShareTTM());
        firmHighlightsDTO.setQuarterlyRevenueGrowthYOY(eODFirmHighlightsDTO.getQuarterlyRevenueGrowthYOY());
        firmHighlightsDTO.setGrossProfitTTM(eODFirmHighlightsDTO.getGrossProfitTTM());
        firmHighlightsDTO.setDilutedEpsTTM(eODFirmHighlightsDTO.getDilutedEpsTTM());
        firmHighlightsDTO.setQuarterlyEarningsGrowthYOY(eODFirmHighlightsDTO.getQuarterlyEarningsGrowthYOY());

        return firmHighlightsDTO;
      }
    };

    modelMapper.addConverter(toUppercase);

  }

  @PostConstruct
  public void initFirmHighlightsTOMapper() {

    Converter<FirmHighlightsDTO, FirmHighlightsTO> toUppercase = new AbstractConverter<FirmHighlightsDTO, FirmHighlightsTO>() {

      @Override
      protected FirmHighlightsTO convert(FirmHighlightsDTO firmHighlightsDTO) {
        FirmHighlightsTO firmHighlightsTO = new FirmHighlightsTO();
        firmHighlightsTO.setMarketCapitalization(firmHighlightsTO.getMarketCapitalization());
        firmHighlightsTO.setMarketCapitalizationMln(firmHighlightsTO.getMarketCapitalizationMln());
        firmHighlightsTO.seteBITDA(firmHighlightsTO.geteBITDA());
        firmHighlightsTO.setpERatio(firmHighlightsTO.getpERatio());
        firmHighlightsTO.setpEGRatio(firmHighlightsTO.getpEGRatio());
        firmHighlightsTO.setWallStreetTargetPrice(firmHighlightsTO.getWallStreetTargetPrice());
        firmHighlightsTO.setBookValue(firmHighlightsTO.getBookValue());
        firmHighlightsTO.setDividendShare(firmHighlightsTO.getDividendShare());
        firmHighlightsTO.setDividendYield(firmHighlightsTO.getDividendYield());
        firmHighlightsTO.setEarningsShare(firmHighlightsTO.getEarningsShare());
        firmHighlightsTO.setePSEstimateCurrentYear(firmHighlightsTO.getePSEstimateCurrentYear());
        firmHighlightsTO.setePSEstimateNextYear(firmHighlightsTO.getePSEstimateNextYear());
        firmHighlightsTO.setePSEstimateNextQuarter(firmHighlightsTO.getePSEstimateNextQuarter());
        firmHighlightsTO.setePSEstimateCurrentQuarter(firmHighlightsTO.getePSEstimateCurrentQuarter());
        firmHighlightsTO.setMostRecentQuarter(firmHighlightsTO.getMostRecentQuarter());
        firmHighlightsTO.setProfitMargin(firmHighlightsTO.getProfitMargin());
        firmHighlightsTO.setOperatingMarginTTM(firmHighlightsTO.getOperatingMarginTTM());
        firmHighlightsTO.setReturnOnAssetsTTM(firmHighlightsTO.getReturnOnAssetsTTM());
        firmHighlightsTO.setReturnOnEquityTTM(firmHighlightsTO.getReturnOnEquityTTM());
        firmHighlightsTO.setRevenueTTM(firmHighlightsTO.getRevenueTTM());
        firmHighlightsTO.setRevenuePerShareTTM(firmHighlightsTO.getRevenuePerShareTTM());
        firmHighlightsTO.setQuarterlyRevenueGrowthYOY(firmHighlightsTO.getQuarterlyRevenueGrowthYOY());
        firmHighlightsTO.setGrossProfitTTM(firmHighlightsTO.getGrossProfitTTM());
        firmHighlightsTO.setDilutedEpsTTM(firmHighlightsTO.getDilutedEpsTTM());
        firmHighlightsTO.setQuarterlyEarningsGrowthYOY(firmHighlightsTO.getQuarterlyEarningsGrowthYOY());

        return firmHighlightsTO;
      }
    };

    modelMapper.addConverter(toUppercase);

  }

}