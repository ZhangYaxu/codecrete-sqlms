[#import "/templates/macros/triggers/BeforeDelete.ftl" as macro]

DELIMITER $$

#
# Before any row is deleted from the ${table} table this trigger inserts the values
# to be deleted into the ${table}Audit table.
#
CREATE DEFINER = '${user}'@'${host}' TRIGGER beforeDelete${table}
  BEFORE DELETE ON ${table}
  FOR EACH ROW
  BEGIN

    # Make sure triggers are enabled first before running
    IF runTrigger() THEN

      [#list fields as field]
          [@macro.beforeDelete field=field/]
      [/#list]

    END IF;
  END $$

DELIMITER ;