

CREATE INDEX STATISTICS_MODL_SRV_TIME ON statistics
(
  module,server, timestmp
);

create index navajoaccess_indx on navajoaccess (
  username,
  webservice
);

CREATE INDEX NAVAJOACCCESS_INDX_WS ON navajoaccess (
  WEBSERVICE
);
CREATE INDEX NAVAJOACCCESS_INDX_US ON navajoaccess (
  USERNAME
);

