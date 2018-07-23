<#-- Before update trigger audit table insert -->
<#macro beforeUpdate field>
    IF NEW.${field} <> OLD.${field} THEN

      INSERT INTO ${table}Audit (${table?uncap_first}Id, event, field, value, invoker)
      VALUES (OLD.id, 'UPDATE', '${field}', NEW.${field}, USER());

    END IF;
</#macro>