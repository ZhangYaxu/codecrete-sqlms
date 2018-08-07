#
# Stored procedure to insert a new ${table} record.
#
DROP PROCEDURE IF EXISTS insert${table};

DELIMITER $$

CREATE DEFINER = '${definer}'@'${host}' PROCEDURE insert${table} (

  # Define procedure parameters
  <#list fields as field>
  ${mode[field]} ${field} ${type[field]}<#sep>, </#sep>
  </#list>
)
  SQL SECURITY DEFINER
  BEGIN

    INSERT INTO ${table} (
    <#list fields as field>
      ${field}<#sep>, </#sep>
    </#list>
    )
    VALUES (
    <#list fields as field>
      ?<#sep>, </#sep>
    </#list>
    );

    SELECT LAST_INSERT_ID() INTO id;

  END $$

DELIMITER ;
