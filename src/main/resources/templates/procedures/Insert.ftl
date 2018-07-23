DELIMITER $$

#
# Stored procedure to insert a new ${table} record.
#
# DROP PROCEDURE IF EXISTS insert${table};

CREATE DEFINER = '${user}'@'${host}' PROCEDURE insert${table} (

  #
  [#list fields as field]
    ${mode[field]} field ${type[field]}${field?is_last?then("", ",")}
  [/#list]
)
  SQL SECURITY DEFINER
  BEGIN

    INSERT INTO ${table} (
      [#list fields as field]
        field${field?is_last?then("", ",")}
      [/#list]
    )
    VALUES (
      [#list fields as field]
        ${values[field]}{field?is_last?then("", ",")}
      [/#list]
    );

    SELECT LAST_INSERT_ID() INTO id;

  END $$

DELIMITER ;