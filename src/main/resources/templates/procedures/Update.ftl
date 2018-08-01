DELIMITER $$

#
# Stored procedure to update an existing ${table} record.
#
DROP PROCEDURE IF EXISTS update${table};

CREATE DEFINER = '${user}'@'${host}' PROCEDURE update${table} (

  # Define
  [#list fields?keys as field]
      ${mode[field]} field ${type[field]}${field?is_last?then("", ",")}
  [/#list]
)
  SQL SECURITY DEFINER
  BEGIN

    UPDATE ${table}
    SET

      #
      [#list fields?keys as field]
        ${table}.${field} = ${fields[field]}${field?is_last?then("", ",")}
      [/#list]

    WHERE ${table}.id = id;

  END $$

DELIMITER ;