<#-- Before delete trigger audit table insert -->
<#macro beforeDelete field>
    INSERT INTO ${table}Audit (${table?uncap_first}Id, event, field, value, invoker)
    VALUES (OLD.id, 'DELETE', '${field}', OLD.${field}, USER());
</#macro>