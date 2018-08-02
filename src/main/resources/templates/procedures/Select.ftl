DELIMITER $$

#
# Stored procedure to select the specified ${table} record
#
# DROP PROCEDURE IF EXISTS select${table};

CREATE DEFINER = '${definer}'@'${host}' PROCEDURE select${table} (

  # Define procedure parameters
  <#list fields as field>
  ${mode[field]} ${field} ${type[field]}<#sep>, </#sep>
  </#list>
)
  SQL SECURITY DEFINER
  BEGIN

    SELECT
    <#list fields as field>
      ${table}.${field}<#sep>, </#sep>
    </#list>
    INTO
    <#list fields as field>
      ${field}<#sep>, </#sep>
    </#list>
    FROM ${table}
    WHERE ${table}.deleted IS NULL
    AND ${table}.${unique} = ${unique};

  END $$

DELIMITER ;