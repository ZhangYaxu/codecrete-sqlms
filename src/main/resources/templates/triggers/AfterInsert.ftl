[#import "/templates/macros/triggers/AfterInsert.ftl" as macro]

DELIMITER $$

#
# Trigger inserts a copy of all ${table} rows into the ${auditTable} table after an
# insert event.
#
CREATE DEFINER = '${user}'@'${host}' TRIGGER afterInsert${table}
  AFTER INSERT ON ${table}
  FOR EACH ROW
  BEGIN

    # Make sure triggers are enabled first before running
    IF runTrigger() THEN

        # Insert records into ${auditTable} table
        [#list fields as field]
            [@macro.afterInsert field=field/]
        [/#list]

        INSERT INTO ${auditTable} (userId, event, field, value, invoker)
        VALUES (NEW.id, 'INSERT', '${field}', NEW.${field}, USER());

    END IF;
  END $$

DELIMITER ;