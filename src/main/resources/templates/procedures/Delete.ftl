DELIMITER $$

#
# Stored procedure to soft delete ${table} records
#
# DROP PROCEDURE IF EXISTS delete${table};

CREATE DEFINER = '${user}'@'${host}' PROCEDURE delete${table} (
  IN id INTEGER
)
  SQL SECURITY DEFINER
  BEGIN

    UPDATE ${table}
    SET deleted = NOW()
    WHERE ${table}.id = id;

  END $$

DELIMITER ;