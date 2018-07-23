DELIMITER $$

#
# Trigger inserts a copy of all ${table} rows into the ${auditTable} table after an
# insert event.
#
CREATE DEFINER = '${user}'@'${host}' TRIGGER afterInsert${table}
  AFTER INSERT ON ${table}
  FOR EACH ROW
  BEGIN

    # Insert records into ${auditTable} table
    [#list fields as field]
        [@utils.afterInsert field=field/]
    [/#list]

    INSERT INTO ${auditTable} (userId, event, field, value, invoker)
    VALUES (NEW.id, 'INSERT', '${field}', NEW.${field}, USER());

  END $$

DELIMITER ;