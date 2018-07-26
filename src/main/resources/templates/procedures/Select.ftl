DELIMITER $$

#
# Stored procedure to select the specified ${table} record
#
# DROP PROCEDURE IF EXISTS select${table};

CREATE DEFINER = '${definer}'@'${host}' PROCEDURE select${table} (

  # Define procedure parameters
  [#list fields?keys as field]
      ${mode[field]} field ${type[field]}${field?is_last?then("", ",")}
  [/#list]
)
  SQL SECURITY DEFINER
  BEGIN

    SELECT
      [#list fields?keys as field]
          ${table}.${field}${field?is_last?then("", ",")}
      [/#list]
    INTO
      [#list fields?keys as field]
        ${field}${field?is_last?then("", ",")}
      [/#list]
    FROM ${table}
    WHERE ${table}.deleted IS NULL;
          AND ${table}.${unique} = ${values[unique]};

  END $$

DELIMITER ;