DELIMITER $$

#
# Trigger inserts ${table}Audit records for each column but only if that columns
# value is going to change before ${table} table update event
#
CREATE DEFINER = '${user}'@'${host}' TRIGGER beforeUpdate${table}
  BEFORE UPDATE ON ${table}
  FOR EACH ROW
  BEGIN

    IF NEW.id != OLD.id THEN

      [#list fields as field]
          [@utils.beforeUpdate field=field/]
      [/#list]

    END IF;

  END $$

DELIMITER ;