<#-- After insert trigger audit table insert -->
<#macro afterInsert field>
    INSERT INTO ${table}Audit (${table?uncap_first}Id, event, field, value, invoker)
    VALUES (NEW.id, 'INSERT', '${field}', NEW.${field}, USER());
</#macro>