package ch.nblotti.securities.loader;

public enum LOADER_STATES {
  READY,
  GET_DATES,
  LOAD_NYSE,
  LOAD_NASDAQ,
  SAVE_FIRM,
  ERROR,
  DONE;
}