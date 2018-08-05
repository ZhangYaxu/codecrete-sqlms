#
# ${table} audit table
#
# DROP TABLE IF EXISTS ${table}Audit;

CREATE TABLE ${table}Audit (
  id INTEGER NOT NULL AUTO_INCREMENT,
  ${table?lower_case}Id INTEGER NOT NULL,
  event VARCHAR(10) NOT NULL,
  field VARCHAR(64) NOT NULL,
  value TEXT,
  invoker VARCHAR(255) NOT NULL,
  created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY(id)
)
  CHARACTER SET = utf8
  ENGINE = InnoDB;