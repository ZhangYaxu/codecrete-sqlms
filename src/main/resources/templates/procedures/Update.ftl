#
# Stored procedure to update an existing ${table} record.
#
DROP PROCEDURE IF EXISTS update${table};

DELIMITER $$

CREATE DEFINER = '${definer}'@'${host}' PROCEDURE update${table} (

  # Define input parameters
  <#list fields as field>
  IN ${field} ${type[field]}<#sep>, </#sep>
  </#list>
)
  SQL SECURITY DEFINER
  BEGIN

    UPDATE ${table}
    SET
      <#list fields as field>
      ${table}.${field} = ${field}<#sep>, </#sep>
      </#list>

    WHERE ${table}.deleted IS NULL
    AND ${table}.id = id;

  END $$

DELIMITER ;
