DELIMITER $$

#
# Before any row is deleted from the UserRole table this trigger inserts the values
# to be deleted into the UserRoleAudit table.
#
CREATE DEFINER = '${user}'@'${host}' TRIGGER beforeDelete${table}
  BEFORE DELETE ON ${table}
  FOR EACH ROW
  BEGIN

    # Make sure triggers are enabled first before running
    IF runTrigger() THEN

      [#list fields as field]
          [@utils.beforeDelete field=field/]
      [/#list]

    END IF;
  END $$

DELIMITER ;