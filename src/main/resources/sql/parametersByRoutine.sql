SELECT
  parameter_name,
  parameter_mode,
  data_type
FROM information_schema.Parameters
WHERE specific_schema = ?
AND specific_name = ?
